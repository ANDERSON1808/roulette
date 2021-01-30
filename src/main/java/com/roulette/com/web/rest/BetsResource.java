package com.roulette.com.web.rest;

import com.roulette.com.service.BetsService;
import com.roulette.com.web.rest.errors.BadRequestAlertException;
import com.roulette.com.service.dto.BetsDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.roulette.com.domain.Bets}.
 */
@RestController
@RequestMapping("/api")
public class BetsResource {

    private final Logger log = LoggerFactory.getLogger(BetsResource.class);

    private static final String ENTITY_NAME = "bets";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BetsService betsService;

    public BetsResource(BetsService betsService) {
        this.betsService = betsService;
    }

    /**
     * {@code POST  /bets} : Create a new bets.
     *
     * @param betsDTO the betsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new betsDTO, or with status {@code 400 (Bad Request)} if the bets has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bets")
    public ResponseEntity<BetsDTO> createBets(@RequestBody BetsDTO betsDTO) throws URISyntaxException {
        log.debug("REST request to save Bets : {}", betsDTO);
        if (betsDTO.getId() != null) {
            throw new BadRequestAlertException("A new bets cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BetsDTO result = betsService.save(betsDTO);
        return ResponseEntity.created(new URI("/api/bets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /bets} : Updates an existing bets.
     *
     * @param betsDTO the betsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated betsDTO,
     * or with status {@code 400 (Bad Request)} if the betsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the betsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bets")
    public ResponseEntity<BetsDTO> updateBets(@RequestBody BetsDTO betsDTO) throws URISyntaxException {
        log.debug("REST request to update Bets : {}", betsDTO);
        if (betsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BetsDTO result = betsService.save(betsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, betsDTO.getId()))
            .body(result);
    }

    /**
     * {@code GET  /bets} : get all the bets.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bets in body.
     */
    @GetMapping("/bets")
    public ResponseEntity<List<BetsDTO>> getAllBets(Pageable pageable) {
        log.debug("REST request to get a page of Bets");
        Page<BetsDTO> page = betsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /bets/:id} : get the "id" bets.
     *
     * @param id the id of the betsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the betsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bets/{id}")
    public ResponseEntity<BetsDTO> getBets(@PathVariable String id) {
        log.debug("REST request to get Bets : {}", id);
        Optional<BetsDTO> betsDTO = betsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(betsDTO);
    }

    /**
     * {@code DELETE  /bets/:id} : delete the "id" bets.
     *
     * @param id the id of the betsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bets/{id}")
    public ResponseEntity<Void> deleteBets(@PathVariable String id) {
        log.debug("REST request to delete Bets : {}", id);
        betsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
