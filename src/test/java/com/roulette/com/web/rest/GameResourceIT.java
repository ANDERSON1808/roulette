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

import com.roulette.com.domain.enumeration.typeColour;
@SpringBootTest(classes = RouletteApp.class)
@ExtendWith({ RedisTestContainerExtension.class, MockitoExtension.class })
@AutoConfigureMockMvc
@WithMockUser
public class GameResourceIT {

    private static final String DEFAULT_ROULETTE = "AAAAAAAAAA";
    private static final String UPDATED_ROULETTE = "BBBBBBBBBB";

    private static final Long DEFAULT_WINNING_NUMBER = 1L;
    private static final Long UPDATED_WINNING_NUMBER = 2L;

    private static final Integer DEFAULT_WINNER = 1;
    private static final Integer UPDATED_WINNER = 2;

    private static final Long DEFAULT_EARNED_VALUE = 1L;
    private static final Long UPDATED_EARNED_VALUE = 2L;

    private static final typeColour DEFAULT_COLOUR = typeColour.RED;
    private static final typeColour UPDATED_COLOUR = typeColour.BLACK;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GameMapper gameMapper;

    @Autowired
    private GameService gameService;

    @Autowired
    private MockMvc restGameMockMvc;

    private Game game;

    public static Game createEntity() {
        Game game = new Game()
            .roulette(DEFAULT_ROULETTE)
            .WinningNumber(DEFAULT_WINNING_NUMBER)
            .winner(DEFAULT_WINNER)
            .earnedValue(DEFAULT_EARNED_VALUE)
            .colour(DEFAULT_COLOUR);
        return game;
    }
    public static Game createUpdatedEntity() {
        Game game = new Game()
            .roulette(UPDATED_ROULETTE)
            .WinningNumber(UPDATED_WINNING_NUMBER)
            .winner(UPDATED_WINNER)
            .earnedValue(UPDATED_EARNED_VALUE)
            .colour(UPDATED_COLOUR);
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
        assertThat(testGame.getWinningNumber()).isEqualTo(DEFAULT_WINNING_NUMBER);
        assertThat(testGame.getWinner()).isEqualTo(DEFAULT_WINNER);
        assertThat(testGame.getEarnedValue()).isEqualTo(DEFAULT_EARNED_VALUE);
        assertThat(testGame.getColour()).isEqualTo(DEFAULT_COLOUR);
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
            .andExpect(jsonPath("$.[*].WinningNumber").value(hasItem(DEFAULT_WINNING_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].winner").value(hasItem(DEFAULT_WINNER)))
            .andExpect(jsonPath("$.[*].earnedValue").value(hasItem(DEFAULT_EARNED_VALUE.intValue())))
            .andExpect(jsonPath("$.[*].colour").value(hasItem(DEFAULT_COLOUR.toString())));
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
            .andExpect(jsonPath("$.WinningNumber").value(DEFAULT_WINNING_NUMBER.intValue()))
            .andExpect(jsonPath("$.winner").value(DEFAULT_WINNER))
            .andExpect(jsonPath("$.earnedValue").value(DEFAULT_EARNED_VALUE.intValue()))
            .andExpect(jsonPath("$.colour").value(DEFAULT_COLOUR.toString()));
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
            .WinningNumber(UPDATED_WINNING_NUMBER)
            .winner(UPDATED_WINNER)
            .earnedValue(UPDATED_EARNED_VALUE)
            .colour(UPDATED_COLOUR);
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
        assertThat(testGame.getWinningNumber()).isEqualTo(UPDATED_WINNING_NUMBER);
        assertThat(testGame.getWinner()).isEqualTo(UPDATED_WINNER);
        assertThat(testGame.getEarnedValue()).isEqualTo(UPDATED_EARNED_VALUE);
        assertThat(testGame.getColour()).isEqualTo(UPDATED_COLOUR);
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
