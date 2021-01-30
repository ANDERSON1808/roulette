package com.roulette.com.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.roulette.com.web.rest.TestUtil;

public class GameDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GameDTO.class);
        GameDTO gameDTO1 = new GameDTO();
        gameDTO1.setId("id1");
        GameDTO gameDTO2 = new GameDTO();
        assertThat(gameDTO1).isNotEqualTo(gameDTO2);
        gameDTO2.setId(gameDTO1.getId());
        assertThat(gameDTO1).isEqualTo(gameDTO2);
        gameDTO2.setId("id2");
        assertThat(gameDTO1).isNotEqualTo(gameDTO2);
        gameDTO1.setId(null);
        assertThat(gameDTO1).isNotEqualTo(gameDTO2);
    }
}
