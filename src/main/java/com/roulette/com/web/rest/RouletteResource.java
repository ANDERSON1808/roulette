package com.roulette.com.web.rest;

import com.roulette.com.service.RouletteService;
import com.roulette.com.web.rest.errors.BadRequestAlertException;
import com.roulette.com.service.dto.RouletteDTO;

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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.roulette.com.domain.Roulette}.
 */
@RestController
@RequestMapping("/api")
public class RouletteResource {

    private final Logger log = LoggerFactory.getLogger(RouletteResource.class);

    private static final String ENTITY_NAME = "roulette";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RouletteService rouletteService;

    public RouletteResource(RouletteService rouletteService) {
        this.rouletteService = rouletteService;
    }

    /**
     * {@code POST  /roulettes} : Create a new roulette.
     *
     * @param rouletteDTO the rouletteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rouletteDTO, or with status {@code 400 (Bad Request)} if the roulette has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/roulettes")
    public ResponseEntity<RouletteDTO> createRoulette(@Valid @RequestBody RouletteDTO rouletteDTO) throws URISyntaxException {
        log.debug("REST request to save Roulette : {}", rouletteDTO);
        if (rouletteDTO.getId() != null) {
            throw new BadRequestAlertException("A new roulette cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RouletteDTO result = rouletteService.save(rouletteDTO);
        return ResponseEntity.created(new URI("/api/roulettes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /roulettes} : Updates an existing roulette.
     *
     * @param rouletteDTO the rouletteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rouletteDTO,
     * or with status {@code 400 (Bad Request)} if the rouletteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rouletteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/roulettes")
    public ResponseEntity<RouletteDTO> updateRoulette(@Valid @RequestBody RouletteDTO rouletteDTO) throws URISyntaxException {
        log.debug("REST request to update Roulette : {}", rouletteDTO);
        if (rouletteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RouletteDTO result = rouletteService.save(rouletteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rouletteDTO.getId()))
            .body(result);
    }

    /**
     * {@code GET  /roulettes} : get all the roulettes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of roulettes in body.
     */
    @GetMapping("/roulettes")
    public ResponseEntity<List<RouletteDTO>> getAllRoulettes(Pageable pageable) {
        log.debug("REST request to get a page of Roulettes");
        Page<RouletteDTO> page = rouletteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /roulettes/:id} : get the "id" roulette.
     *
     * @param id the id of the rouletteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rouletteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/roulettes/{id}")
    public ResponseEntity<RouletteDTO> getRoulette(@PathVariable String id) {
        log.debug("REST request to get Roulette : {}", id);
        Optional<RouletteDTO> rouletteDTO = rouletteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rouletteDTO);
    }

    /**
     * {@code DELETE  /roulettes/:id} : delete the "id" roulette.
     *
     * @param id the id of the rouletteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/roulettes/{id}")
    public ResponseEntity<Void> deleteRoulette(@PathVariable String id) {
        log.debug("REST request to delete Roulette : {}", id);
        rouletteService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
