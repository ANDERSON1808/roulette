package com.roulette.com.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * A Game.
 */
@Document(collection = "game")
public class Game implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("roulette")
    private String roulette;

    @Field("winning_bet")
    private String winningBet;

    @Field("earned_value")
    private Integer earnedValue;

    @Field("winning_house")
    private Boolean winningHouse;

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

    public Game roulette(String roulette) {
        this.roulette = roulette;
        return this;
    }

    public void setRoulette(String roulette) {
        this.roulette = roulette;
    }

    public String getWinningBet() {
        return winningBet;
    }

    public Game winningBet(String winningBet) {
        this.winningBet = winningBet;
        return this;
    }

    public void setWinningBet(String winningBet) {
        this.winningBet = winningBet;
    }

    public Integer getEarnedValue() {
        return earnedValue;
    }

    public Game earnedValue(Integer earnedValue) {
        this.earnedValue = earnedValue;
        return this;
    }

    public void setEarnedValue(Integer earnedValue) {
        this.earnedValue = earnedValue;
    }

    public Boolean isWinningHouse() {
        return winningHouse;
    }

    public Game winningHouse(Boolean winningHouse) {
        this.winningHouse = winningHouse;
        return this;
    }

    public void setWinningHouse(Boolean winningHouse) {
        this.winningHouse = winningHouse;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Game)) {
            return false;
        }
        return id != null && id.equals(((Game) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Game{" +
            "id=" + getId() +
            ", roulette='" + getRoulette() + "'" +
            ", winningBet='" + getWinningBet() + "'" +
            ", earnedValue=" + getEarnedValue() +
            ", winningHouse='" + isWinningHouse() + "'" +
            "}";
    }
}
