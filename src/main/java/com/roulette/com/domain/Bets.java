package com.roulette.com.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * A Bets.
 */
@Document(collection = "bets")
public class Bets implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("roulette")
    private String roulette;

    @Field("user")
    private String user;

    @Field("bet_number")
    private Integer betNumber;

    @Field("color_bet")
    private Integer colorBet;

    @Field("bet_value")
    private Integer betValue;

    @Field("state")
    private Boolean state;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoulette() {
        return roulette;
    }

    public Bets roulette(String roulette) {
        this.roulette = roulette;
        return this;
    }

    public void setRoulette(String roulette) {
        this.roulette = roulette;
    }

    public String getUser() {
        return user;
    }

    public Bets user(String user) {
        this.user = user;
        return this;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Integer getBetNumber() {
        return betNumber;
    }

    public Bets betNumber(Integer betNumber) {
        this.betNumber = betNumber;
        return this;
    }

    public void setBetNumber(Integer betNumber) {
        this.betNumber = betNumber;
    }

    public Integer getColorBet() {
        return colorBet;
    }

    public Bets colorBet(Integer colorBet) {
        this.colorBet = colorBet;
        return this;
    }

    public void setColorBet(Integer colorBet) {
        this.colorBet = colorBet;
    }

    public Integer getBetValue() {
        return betValue;
    }

    public Bets betValue(Integer betValue) {
        this.betValue = betValue;
        return this;
    }

    public void setBetValue(Integer betValue) {
        this.betValue = betValue;
    }

    public Boolean isState() {
        return state;
    }

    public Bets state(Boolean state) {
        this.state = state;
        return this;
    }

    public void setState(Boolean state) {
        this.state = state;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Bets)) {
            return false;
        }
        return id != null && id.equals(((Bets) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Bets{" +
            "id=" + getId() +
            ", roulette='" + getRoulette() + "'" +
            ", user='" + getUser() + "'" +
            ", betNumber=" + getBetNumber() +
            ", colorBet=" + getColorBet() +
            ", betValue=" + getBetValue() +
            ", state='" + isState() + "'" +
            "}";
    }
}
