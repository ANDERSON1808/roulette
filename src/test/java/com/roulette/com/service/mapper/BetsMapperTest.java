package com.roulette.com.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class BetsMapperTest {

    private BetsMapper betsMapper;

    @BeforeEach
    public void setUp() {
        betsMapper = new BetsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(betsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(betsMapper.fromId(null)).isNull();
    }
}
