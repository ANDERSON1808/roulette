package com.roulette.com.service;

import com.roulette.com.domain.Bets;
import com.roulette.com.repository.BetsRepository;
import com.roulette.com.service.dto.BetsDTO;
import com.roulette.com.service.mapper.BetsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Bets}.
 */
@Service
public class BetsService {

    private final Logger log = LoggerFactory.getLogger(BetsService.class);

    private final BetsRepository betsRepository;

    private final BetsMapper betsMapper;

    public BetsService(BetsRepository betsRepository, BetsMapper betsMapper) {
        this.betsRepository = betsRepository;
        this.betsMapper = betsMapper;
    }

    /**
     * Save a bets.
     *
     * @param betsDTO the entity to save.
     * @return the persisted entity.
     */
    public BetsDTO save(BetsDTO betsDTO) {
        log.debug("Request to save Bets : {}", betsDTO);
        Bets bets = betsMapper.toEntity(betsDTO);
        bets = betsRepository.save(bets);
        return betsMapper.toDto(bets);
    }

    /**
     * Get all the bets.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<BetsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Bets");
        return betsRepository.findAll(pageable)
            .map(betsMapper::toDto);
    }


    /**
     * Get one bets by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<BetsDTO> findOne(String id) {
        log.debug("Request to get Bets : {}", id);
        return betsRepository.findById(id)
            .map(betsMapper::toDto);
    }

    /**
     * Delete the bets by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Bets : {}", id);
        betsRepository.deleteById(id);
    }
}
