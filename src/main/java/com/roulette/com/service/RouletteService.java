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

/**
 * Service Implementation for managing {@link Roulette}.
 */
@Service
public class RouletteService {

    private final Logger log = LoggerFactory.getLogger(RouletteService.class);

    private final RouletteRepository rouletteRepository;

    private final RouletteMapper rouletteMapper;

    public RouletteService(RouletteRepository rouletteRepository, RouletteMapper rouletteMapper) {
        this.rouletteRepository = rouletteRepository;
        this.rouletteMapper = rouletteMapper;
    }

    /**
     * Save a roulette.
     *
     * @param rouletteDTO the entity to save.
     * @return the persisted entity.
     */
    public RouletteDTO save(RouletteDTO rouletteDTO) {
        log.debug("Request to save Roulette : {}", rouletteDTO);
        Roulette roulette = rouletteMapper.toEntity(rouletteDTO);
        roulette = rouletteRepository.save(roulette);
        return rouletteMapper.toDto(roulette);
    }

    /**
     * Get all the roulettes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<RouletteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Roulettes");
        return rouletteRepository.findAll(pageable)
            .map(rouletteMapper::toDto);
    }


    /**
     * Get one roulette by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<RouletteDTO> findOne(String id) {
        log.debug("Request to get Roulette : {}", id);
        return rouletteRepository.findById(id)
            .map(rouletteMapper::toDto);
    }

    /**
     * Delete the roulette by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Roulette : {}", id);
        rouletteRepository.deleteById(id);
    }
}
