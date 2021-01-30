package com.roulette.com.web.rest;

import com.roulette.com.RedisTestContainerExtension;
import com.roulette.com.RouletteApp;
import com.roulette.com.domain.Roulette;
import com.roulette.com.repository.RouletteRepository;
import com.roulette.com.service.RouletteService;
import com.roulette.com.service.dto.RouletteDTO;
import com.roulette.com.service.mapper.RouletteMapper;

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

import com.roulette.com.domain.enumeration.typeColour;
/**
 * Integration tests for the {@link RouletteResource} REST controller.
 */
@SpringBootTest(classes = RouletteApp.class)
@ExtendWith({ RedisTestContainerExtension.class, MockitoExtension.class })
@AutoConfigureMockMvc
@WithMockUser
public class RouletteResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_MAXIMUM = 2;
    private static final Integer UPDATED_MAXIMUM = 1;

    private static final typeColour DEFAULT_COLOUR = typeColour.RED;
    private static final typeColour UPDATED_COLOUR = typeColour.BLACK;

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_STATE = false;
    private static final Boolean UPDATED_STATE = true;

    @Autowired
    private RouletteRepository rouletteRepository;

    @Autowired
    private RouletteMapper rouletteMapper;

    @Autowired
    private RouletteService rouletteService;

    @Autowired
    private MockMvc restRouletteMockMvc;

    private Roulette roulette;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Roulette createEntity() {
        Roulette roulette = new Roulette()
            .name(DEFAULT_NAME)
            .maximum(DEFAULT_MAXIMUM)
            .colour(DEFAULT_COLOUR)
            .code(DEFAULT_CODE)
            .state(DEFAULT_STATE);
        return roulette;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Roulette createUpdatedEntity() {
        Roulette roulette = new Roulette()
            .name(UPDATED_NAME)
            .maximum(UPDATED_MAXIMUM)
            .colour(UPDATED_COLOUR)
            .code(UPDATED_CODE)
            .state(UPDATED_STATE);
        return roulette;
    }

    @BeforeEach
    public void initTest() {
        rouletteRepository.deleteAll();
        roulette = createEntity();
    }

    @Test
    public void createRoulette() throws Exception {
        int databaseSizeBeforeCreate = rouletteRepository.findAll().size();
        // Create the Roulette
        RouletteDTO rouletteDTO = rouletteMapper.toDto(roulette);
        restRouletteMockMvc.perform(post("/api/roulettes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rouletteDTO)))
            .andExpect(status().isCreated());

        // Validate the Roulette in the database
        List<Roulette> rouletteList = rouletteRepository.findAll();
        assertThat(rouletteList).hasSize(databaseSizeBeforeCreate + 1);
        Roulette testRoulette = rouletteList.get(rouletteList.size() - 1);
        assertThat(testRoulette.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRoulette.getMaximum()).isEqualTo(DEFAULT_MAXIMUM);
        assertThat(testRoulette.getColour()).isEqualTo(DEFAULT_COLOUR);
        assertThat(testRoulette.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testRoulette.isState()).isEqualTo(DEFAULT_STATE);
    }

    @Test
    public void createRouletteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rouletteRepository.findAll().size();

        // Create the Roulette with an existing ID
        roulette.setId("existing_id");
        RouletteDTO rouletteDTO = rouletteMapper.toDto(roulette);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRouletteMockMvc.perform(post("/api/roulettes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rouletteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Roulette in the database
        List<Roulette> rouletteList = rouletteRepository.findAll();
        assertThat(rouletteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllRoulettes() throws Exception {
        // Initialize the database
        rouletteRepository.save(roulette);

        // Get all the rouletteList
        restRouletteMockMvc.perform(get("/api/roulettes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(roulette.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].maximum").value(hasItem(DEFAULT_MAXIMUM)))
            .andExpect(jsonPath("$.[*].colour").value(hasItem(DEFAULT_COLOUR.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.booleanValue())));
    }
    
    @Test
    public void getRoulette() throws Exception {
        // Initialize the database
        rouletteRepository.save(roulette);

        // Get the roulette
        restRouletteMockMvc.perform(get("/api/roulettes/{id}", roulette.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(roulette.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.maximum").value(DEFAULT_MAXIMUM))
            .andExpect(jsonPath("$.colour").value(DEFAULT_COLOUR.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.booleanValue()));
    }
    @Test
    public void getNonExistingRoulette() throws Exception {
        // Get the roulette
        restRouletteMockMvc.perform(get("/api/roulettes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateRoulette() throws Exception {
        // Initialize the database
        rouletteRepository.save(roulette);

        int databaseSizeBeforeUpdate = rouletteRepository.findAll().size();

        // Update the roulette
        Roulette updatedRoulette = rouletteRepository.findById(roulette.getId()).get();
        updatedRoulette
            .name(UPDATED_NAME)
            .maximum(UPDATED_MAXIMUM)
            .colour(UPDATED_COLOUR)
            .code(UPDATED_CODE)
            .state(UPDATED_STATE);
        RouletteDTO rouletteDTO = rouletteMapper.toDto(updatedRoulette);

        restRouletteMockMvc.perform(put("/api/roulettes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rouletteDTO)))
            .andExpect(status().isOk());

        // Validate the Roulette in the database
        List<Roulette> rouletteList = rouletteRepository.findAll();
        assertThat(rouletteList).hasSize(databaseSizeBeforeUpdate);
        Roulette testRoulette = rouletteList.get(rouletteList.size() - 1);
        assertThat(testRoulette.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRoulette.getMaximum()).isEqualTo(UPDATED_MAXIMUM);
        assertThat(testRoulette.getColour()).isEqualTo(UPDATED_COLOUR);
        assertThat(testRoulette.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testRoulette.isState()).isEqualTo(UPDATED_STATE);
    }

    @Test
    public void updateNonExistingRoulette() throws Exception {
        int databaseSizeBeforeUpdate = rouletteRepository.findAll().size();

        // Create the Roulette
        RouletteDTO rouletteDTO = rouletteMapper.toDto(roulette);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRouletteMockMvc.perform(put("/api/roulettes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rouletteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Roulette in the database
        List<Roulette> rouletteList = rouletteRepository.findAll();
        assertThat(rouletteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteRoulette() throws Exception {
        // Initialize the database
        rouletteRepository.save(roulette);

        int databaseSizeBeforeDelete = rouletteRepository.findAll().size();

        // Delete the roulette
        restRouletteMockMvc.perform(delete("/api/roulettes/{id}", roulette.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Roulette> rouletteList = rouletteRepository.findAll();
        assertThat(rouletteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
