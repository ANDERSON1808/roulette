package com.roulette.com.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class RouletteMapperTest {

    private RouletteMapper rouletteMapper;

    @BeforeEach
    public void setUp() {
        rouletteMapper = new RouletteMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(rouletteMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(rouletteMapper.fromId(null)).isNull();
    }
}
