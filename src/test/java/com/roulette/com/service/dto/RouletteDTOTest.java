package com.roulette.com.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.roulette.com.web.rest.TestUtil;

public class RouletteDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RouletteDTO.class);
        RouletteDTO rouletteDTO1 = new RouletteDTO();
        rouletteDTO1.setId("id1");
        RouletteDTO rouletteDTO2 = new RouletteDTO();
        assertThat(rouletteDTO1).isNotEqualTo(rouletteDTO2);
        rouletteDTO2.setId(rouletteDTO1.getId());
        assertThat(rouletteDTO1).isEqualTo(rouletteDTO2);
        rouletteDTO2.setId("id2");
        assertThat(rouletteDTO1).isNotEqualTo(rouletteDTO2);
        rouletteDTO1.setId(null);
        assertThat(rouletteDTO1).isNotEqualTo(rouletteDTO2);
    }
}
