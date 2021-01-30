package com.roulette.com.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.roulette.com.web.rest.TestUtil;

public class RouletteTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Roulette.class);
        Roulette roulette1 = new Roulette();
        roulette1.setId("id1");
        Roulette roulette2 = new Roulette();
        roulette2.setId(roulette1.getId());
        assertThat(roulette1).isEqualTo(roulette2);
        roulette2.setId("id2");
        assertThat(roulette1).isNotEqualTo(roulette2);
        roulette1.setId(null);
        assertThat(roulette1).isNotEqualTo(roulette2);
    }
}
