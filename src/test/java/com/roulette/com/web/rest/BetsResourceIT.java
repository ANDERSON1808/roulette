package com.roulette.com.web.rest;

import com.roulette.com.RouletteApp;
import com.roulette.com.domain.Bets;
import com.roulette.com.repository.BetsRepository;
import com.roulette.com.service.BetsService;
import com.roulette.com.service.dto.BetsDTO;
import com.roulette.com.service.mapper.BetsMapper;

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

@SpringBootTest(classes = RouletteApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class BetsResourceIT {

    private static final String DEFAULT_ROULETTE = "AAAAAAAAAA";
    private static final String UPDATED_ROULETTE = "BBBBBBBBBB";

    private static final String DEFAULT_USER = "AAAAAAAAAA";
    private static final String UPDATED_USER = "BBBBBBBBBB";

    private static final Integer DEFAULT_BET_NUMBER = 1;
    private static final Integer UPDATED_BET_NUMBER = 2;


    private static final Integer DEFAULT_BET_VALUE = 1;
    private static final Integer UPDATED_BET_VALUE = 2;

    private static final Boolean DEFAULT_STATE = false;
    private static final Boolean UPDATED_STATE = true;

    @Autowired
    private BetsRepository betsRepository;

    @Autowired
    private BetsMapper betsMapper;

    @Autowired
    private BetsService betsService;

    @Autowired
    private MockMvc restBetsMockMvc;

    private Bets bets;

    public static Bets createEntity() {
        Bets bets = new Bets()
            .roulette(DEFAULT_ROULETTE)
            .user(DEFAULT_USER)
            .betNumber(DEFAULT_BET_NUMBER)
            .betValue(DEFAULT_BET_VALUE)
            .state(DEFAULT_STATE);
        return bets;
    }
    public static Bets createUpdatedEntity() {
        Bets bets = new Bets()
            .roulette(UPDATED_ROULETTE)
            .user(UPDATED_USER)
            .betNumber(UPDATED_BET_NUMBER)
            .betValue(UPDATED_BET_VALUE)
            .state(UPDATED_STATE);
        return bets;
    }

    @BeforeEach
    public void initTest() {
        betsRepository.deleteAll();
        bets = createEntity();
    }

    @Test
    public void createBets() throws Exception {
        int databaseSizeBeforeCreate = betsRepository.findAll().size();
        // Create the Bets
        BetsDTO betsDTO = betsMapper.toDto(bets);
        restBetsMockMvc.perform(post("/api/bets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(betsDTO)))
            .andExpect(status().isCreated());

        // Validate the Bets in the database
        List<Bets> betsList = betsRepository.findAll();
        assertThat(betsList).hasSize(databaseSizeBeforeCreate + 1);
        Bets testBets = betsList.get(betsList.size() - 1);
        assertThat(testBets.getRoulette()).isEqualTo(DEFAULT_ROULETTE);
        assertThat(testBets.getUser()).isEqualTo(DEFAULT_USER);
        assertThat(testBets.getBetNumber()).isEqualTo(DEFAULT_BET_NUMBER);
        assertThat(testBets.getBetValue()).isEqualTo(DEFAULT_BET_VALUE);
        assertThat(testBets.isState()).isEqualTo(DEFAULT_STATE);
    }

    @Test
    public void createBetsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = betsRepository.findAll().size();

        // Create the Bets with an existing ID
        bets.setId("existing_id");
        BetsDTO betsDTO = betsMapper.toDto(bets);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBetsMockMvc.perform(post("/api/bets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(betsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Bets in the database
        List<Bets> betsList = betsRepository.findAll();
        assertThat(betsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllBets() throws Exception {
        // Initialize the database
        betsRepository.save(bets);

        // Get all the betsList
        restBetsMockMvc.perform(get("/api/bets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bets.getId())))
            .andExpect(jsonPath("$.[*].roulette").value(hasItem(DEFAULT_ROULETTE)))
            .andExpect(jsonPath("$.[*].user").value(hasItem(DEFAULT_USER)))
            .andExpect(jsonPath("$.[*].betNumber").value(hasItem(DEFAULT_BET_NUMBER)))
            .andExpect(jsonPath("$.[*].betValue").value(hasItem(DEFAULT_BET_VALUE)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.booleanValue())));
    }

    @Test
    public void getBets() throws Exception {
        // Initialize the database
        betsRepository.save(bets);

        // Get the bets
        restBetsMockMvc.perform(get("/api/bets/{id}", bets.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bets.getId()))
            .andExpect(jsonPath("$.roulette").value(DEFAULT_ROULETTE))
            .andExpect(jsonPath("$.user").value(DEFAULT_USER))
            .andExpect(jsonPath("$.betNumber").value(DEFAULT_BET_NUMBER))
            .andExpect(jsonPath("$.betValue").value(DEFAULT_BET_VALUE))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.booleanValue()));
    }
    @Test
    public void getNonExistingBets() throws Exception {
        // Get the bets
        restBetsMockMvc.perform(get("/api/bets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateBets() throws Exception {
        // Initialize the database
        betsRepository.save(bets);

        int databaseSizeBeforeUpdate = betsRepository.findAll().size();

        // Update the bets
        Bets updatedBets = betsRepository.findById(bets.getId()).get();
        updatedBets
            .roulette(UPDATED_ROULETTE)
            .user(UPDATED_USER)
            .betNumber(UPDATED_BET_NUMBER)
            .betValue(UPDATED_BET_VALUE)
            .state(UPDATED_STATE);
        BetsDTO betsDTO = betsMapper.toDto(updatedBets);

        restBetsMockMvc.perform(put("/api/bets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(betsDTO)))
            .andExpect(status().isOk());

        // Validate the Bets in the database
        List<Bets> betsList = betsRepository.findAll();
        assertThat(betsList).hasSize(databaseSizeBeforeUpdate);
        Bets testBets = betsList.get(betsList.size() - 1);
        assertThat(testBets.getRoulette()).isEqualTo(UPDATED_ROULETTE);
        assertThat(testBets.getUser()).isEqualTo(UPDATED_USER);
        assertThat(testBets.getBetNumber()).isEqualTo(UPDATED_BET_NUMBER);
        assertThat(testBets.getBetValue()).isEqualTo(UPDATED_BET_VALUE);
        assertThat(testBets.isState()).isEqualTo(UPDATED_STATE);
    }

    @Test
    public void updateNonExistingBets() throws Exception {
        int databaseSizeBeforeUpdate = betsRepository.findAll().size();

        // Create the Bets
        BetsDTO betsDTO = betsMapper.toDto(bets);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBetsMockMvc.perform(put("/api/bets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(betsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Bets in the database
        List<Bets> betsList = betsRepository.findAll();
        assertThat(betsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteBets() throws Exception {
        // Initialize the database
        betsRepository.save(bets);

        int databaseSizeBeforeDelete = betsRepository.findAll().size();

        // Delete the bets
        restBetsMockMvc.perform(delete("/api/bets/{id}", bets.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Bets> betsList = betsRepository.findAll();
        assertThat(betsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
