package com.roulette.com.service;

import com.roulette.com.domain.Roulette;
import com.roulette.com.repository.RouletteRepository;
import com.roulette.com.service.dto.RouletteDTO;
import com.roulette.com.service.mapper.RouletteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class RouletteService {

    private final Logger log = LoggerFactory.getLogger(RouletteService.class);

    private final RouletteRepository rouletteRepository;

    private final RouletteMapper rouletteMapper;

    public RouletteService(RouletteRepository rouletteRepository, RouletteMapper rouletteMapper) {
        this.rouletteRepository = rouletteRepository;
        this.rouletteMapper = rouletteMapper;
    }

    public RouletteDTO save(RouletteDTO rouletteDTO) {
        log.debug("Request to save Roulette : {}", rouletteDTO);
        Roulette roulette = rouletteMapper.toEntity(rouletteDTO);
        long i = Double.doubleToLongBits(Math.random());
        roulette.setCode(Long.toHexString(i));
        roulette = rouletteRepository.save(roulette);
        return rouletteMapper.toDto(roulette);
    }

    public Page<RouletteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Roulettes");
        return rouletteRepository.findAll(pageable)
            .map(rouletteMapper::toDto);
    }


    public Optional<RouletteDTO> findOne(String id) {
        log.debug("Request to get Roulette : {}", id);
        return rouletteRepository.findById(id)
            .map(rouletteMapper::toDto);
    }

    public void delete(String id) {
        log.debug("Request to delete Roulette : {}", id);
        rouletteRepository.deleteById(id);
    }


    public Object openRouletteAdmin(String id) {
        String msn = "open Roulette";
        Optional<Roulette> optional = rouletteRepository.findById(id);
        if (optional.isPresent()){
            Roulette r = optional.get();
            r.setState(Boolean.TRUE);
            rouletteRepository.save(r);
            return msn;
        }
        return null;
    }
}
