package com.roulette.com.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.roulette.com.web.rest.TestUtil;

public class BetsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bets.class);
        Bets bets1 = new Bets();
        bets1.setId("id1");
        Bets bets2 = new Bets();
        bets2.setId(bets1.getId());
        assertThat(bets1).isEqualTo(bets2);
        bets2.setId("id2");
        assertThat(bets1).isNotEqualTo(bets2);
        bets1.setId(null);
        assertThat(bets1).isNotEqualTo(bets2);
    }
}
