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
import org.springframework.data.repository.query.Param;
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

    @GetMapping("/roulettes")
    public ResponseEntity<List<RouletteDTO>> getAllRoulettes(Pageable pageable) {
        log.debug("REST request to get a page of Roulettes");
        Page<RouletteDTO> page = rouletteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/roulettes/{id}")
    public ResponseEntity<Object>openRoulette(@PathVariable String id){
        if (id == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Object result = rouletteService.openRouletteAdmin(id);
        if (result == null) {
            throw new BadRequestAlertException("Roulette does not exist in the system", ENTITY_NAME, "idnull");
        }
        return ResponseEntity.ok().body(result);
    }
}
