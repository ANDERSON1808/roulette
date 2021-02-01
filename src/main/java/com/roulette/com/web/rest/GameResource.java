package com.roulette.com.web.rest;

import com.roulette.com.domain.Game;
import com.roulette.com.service.GameService;
import com.roulette.com.web.rest.errors.BadRequestAlertException;
import com.roulette.com.service.dto.GameDTO;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class GameResource {

    private final Logger log = LoggerFactory.getLogger(GameResource.class);

    private static final String ENTITY_NAME = "game";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GameService gameService;

    public GameResource(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/gamesOver/{roulette}")
    public ResponseEntity<List<Game>> gamesOver(@PathVariable String roulette) {
        if (roulette == null) {
            throw new BadRequestAlertException("The number a roulette is important", ENTITY_NAME, "rouletteNull");
        }
        List<Game> gameList = gameService.gameOver(roulette);
        if (gameList == null) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
        return ResponseEntity.ok().body(gameList);
    }
}
