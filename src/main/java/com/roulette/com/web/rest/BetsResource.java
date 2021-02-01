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

    @PostMapping("/bets/{user}")
    public ResponseEntity<BetsDTO> createBets(@RequestBody BetsDTO betsDTO ,@PathVariable String user) throws URISyntaxException {
        log.debug("REST request to save Bets : {}", betsDTO);
        if (betsDTO.getColorBet()!=null && betsDTO.getBetValue()!=null) {
            throw new BadRequestAlertException("You cannot post for two color and number at the same time", ENTITY_NAME, "DO_NOT_BET");
        }
        BetsDTO result = betsService.openBeet(betsDTO,user);
        if (result == null) {
            throw new BadRequestAlertException("You do not have enough money for the bet ", ENTITY_NAME,"insufficient");
        }
        return ResponseEntity.created(new URI("/api/bets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }
}
