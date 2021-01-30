package com.roulette.com.web.rest;

import com.roulette.com.RedisTestContainerExtension;
import com.roulette.com.RouletteApp;
import com.roulette.com.domain.Game;
import com.roulette.com.repository.GameRepository;
import com.roulette.com.service.GameService;
import com.roulette.com.service.dto.GameDTO;
import com.roulette.com.service.mapper.GameMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link GameResource} REST controller.
 */
@SpringBootTest(classes = RouletteApp.class)
@ExtendWith({ RedisTestContainerExtension.class, MockitoExtension.class })
@AutoConfigureMockMvc
@WithMockUser
public class GameResourceIT {

    private static final String DEFAULT_ROULETTE = "AAAAAAAAAA";
    private static final String UPDATED_ROULETTE = "BBBBBBBBBB";

    private static final String DEFAULT_WINNING_BET = "AAAAAAAAAA";
    private static final String UPDATED_WINNING_BET = "BBBBBBBBBB";

    private static final Integer DEFAULT_EARNED_VALUE = 1;
    private static final Integer UPDATED_EARNED_VALUE = 2;

    private static final Boolean DEFAULT_WINNING_HOUSE = false;
    private static final Boolean UPDATED_WINNING_HOUSE = true;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GameMapper gameMapper;

    @Autowired
    private GameService gameService;

    @Autowired
    private MockMvc restGameMockMvc;

    private Game game;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Game createEntity() {
        Game game = new Game()
            .roulette(DEFAULT_ROULETTE)
            .winningBet(DEFAULT_WINNING_BET)
            .earnedValue(DEFAULT_EARNED_VALUE)
            .winningHouse(DEFAULT_WINNING_HOUSE);
        return game;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Game createUpdatedEntity() {
        Game game = new Game()
            .roulette(UPDATED_ROULETTE)
            .winningBet(UPDATED_WINNING_BET)
            .earnedValue(UPDATED_EARNED_VALUE)
            .winningHouse(UPDATED_WINNING_HOUSE);
        return game;
    }

    @BeforeEach
    public void initTest() {
        gameRepository.deleteAll();
        game = createEntity();
    }

    @Test
    public void createGame() throws Exception {
        int databaseSizeBeforeCreate = gameRepository.findAll().size();
        // Create the Game
        GameDTO gameDTO = gameMapper.toDto(game);
        restGameMockMvc.perform(post("/api/games")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gameDTO)))
            .andExpect(status().isCreated());

        // Validate the Game in the database
        List<Game> gameList = gameRepository.findAll();
        assertThat(gameList).hasSize(databaseSizeBeforeCreate + 1);
        Game testGame = gameList.get(gameList.size() - 1);
        assertThat(testGame.getRoulette()).isEqualTo(DEFAULT_ROULETTE);
        assertThat(testGame.getWinningBet()).isEqualTo(DEFAULT_WINNING_BET);
        assertThat(testGame.getEarnedValue()).isEqualTo(DEFAULT_EARNED_VALUE);
        assertThat(testGame.isWinningHouse()).isEqualTo(DEFAULT_WINNING_HOUSE);
    }

    @Test
    public void createGameWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gameRepository.findAll().size();

        // Create the Game with an existing ID
        game.setId("existing_id");
        GameDTO gameDTO = gameMapper.toDto(game);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGameMockMvc.perform(post("/api/games")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gameDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Game in the database
        List<Game> gameList = gameRepository.findAll();
        assertThat(gameList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllGames() throws Exception {
        // Initialize the database
        gameRepository.save(game);

        // Get all the gameList
        restGameMockMvc.perform(get("/api/games?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(game.getId())))
            .andExpect(jsonPath("$.[*].roulette").value(hasItem(DEFAULT_ROULETTE)))
            .andExpect(jsonPath("$.[*].winningBet").value(hasItem(DEFAULT_WINNING_BET)))
            .andExpect(jsonPath("$.[*].earnedValue").value(hasItem(DEFAULT_EARNED_VALUE)))
            .andExpect(jsonPath("$.[*].winningHouse").value(hasItem(DEFAULT_WINNING_HOUSE.booleanValue())));
    }
    
    @Test
    public void getGame() throws Exception {
        // Initialize the database
        gameRepository.save(game);

        // Get the game
        restGameMockMvc.perform(get("/api/games/{id}", game.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(game.getId()))
            .andExpect(jsonPath("$.roulette").value(DEFAULT_ROULETTE))
            .andExpect(jsonPath("$.winningBet").value(DEFAULT_WINNING_BET))
            .andExpect(jsonPath("$.earnedValue").value(DEFAULT_EARNED_VALUE))
            .andExpect(jsonPath("$.winningHouse").value(DEFAULT_WINNING_HOUSE.booleanValue()));
    }
    @Test
    public void getNonExistingGame() throws Exception {
        // Get the game
        restGameMockMvc.perform(get("/api/games/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateGame() throws Exception {
        // Initialize the database
        gameRepository.save(game);

        int databaseSizeBeforeUpdate = gameRepository.findAll().size();

        // Update the game
        Game updatedGame = gameRepository.findById(game.getId()).get();
        updatedGame
            .roulette(UPDATED_ROULETTE)
            .winningBet(UPDATED_WINNING_BET)
            .earnedValue(UPDATED_EARNED_VALUE)
            .winningHouse(UPDATED_WINNING_HOUSE);
        GameDTO gameDTO = gameMapper.toDto(updatedGame);

        restGameMockMvc.perform(put("/api/games")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gameDTO)))
            .andExpect(status().isOk());

        // Validate the Game in the database
        List<Game> gameList = gameRepository.findAll();
        assertThat(gameList).hasSize(databaseSizeBeforeUpdate);
        Game testGame = gameList.get(gameList.size() - 1);
        assertThat(testGame.getRoulette()).isEqualTo(UPDATED_ROULETTE);
        assertThat(testGame.getWinningBet()).isEqualTo(UPDATED_WINNING_BET);
        assertThat(testGame.getEarnedValue()).isEqualTo(UPDATED_EARNED_VALUE);
        assertThat(testGame.isWinningHouse()).isEqualTo(UPDATED_WINNING_HOUSE);
    }

    @Test
    public void updateNonExistingGame() throws Exception {
        int databaseSizeBeforeUpdate = gameRepository.findAll().size();

        // Create the Game
        GameDTO gameDTO = gameMapper.toDto(game);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGameMockMvc.perform(put("/api/games")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gameDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Game in the database
        List<Game> gameList = gameRepository.findAll();
        assertThat(gameList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteGame() throws Exception {
        // Initialize the database
        gameRepository.save(game);

        int databaseSizeBeforeDelete = gameRepository.findAll().size();

        // Delete the game
        restGameMockMvc.perform(delete("/api/games/{id}", game.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Game> gameList = gameRepository.findAll();
        assertThat(gameList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
