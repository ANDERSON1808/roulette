package com.roulette.com.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.roulette.com.web.rest.TestUtil;

public class BetsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BetsDTO.class);
        BetsDTO betsDTO1 = new BetsDTO();
        betsDTO1.setId("id1");
        BetsDTO betsDTO2 = new BetsDTO();
        assertThat(betsDTO1).isNotEqualTo(betsDTO2);
        betsDTO2.setId(betsDTO1.getId());
        assertThat(betsDTO1).isEqualTo(betsDTO2);
        betsDTO2.setId("id2");
        assertThat(betsDTO1).isNotEqualTo(betsDTO2);
        betsDTO1.setId(null);
        assertThat(betsDTO1).isNotEqualTo(betsDTO2);
    }
}
