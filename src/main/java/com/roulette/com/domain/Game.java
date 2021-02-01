package com.roulette.com.domain;

import com.roulette.com.domain.enumeration.typeColour;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

@Document(collection = "game")
public class Game implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("roulette")
    private String roulette;

    @Field("winning_number")
    private Long WinningNumber;

    @Field("winner")
    private Integer winner;

    @Field("earned_value")
    private Long earnedValue;

    @Field("colour")
    private typeColour colour;

    @Field("msn")
    private String msn;


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

    public Long getWinningNumber() {
        return WinningNumber;
    }

    public Game WinningNumber(Long WinningNumber) {
        this.WinningNumber = WinningNumber;
        return this;
    }

    public void setWinningNumber(Long WinningNumber) {
        this.WinningNumber = WinningNumber;
    }

    public Integer getWinner() {
        return winner;
    }

    public Game winner(Integer winner) {
        this.winner = winner;
        return this;
    }

    public void setWinner(Integer winner) {
        this.winner = winner;
    }

    public Long getEarnedValue() {
        return earnedValue;
    }

    public Game earnedValue(Long earnedValue) {
        this.earnedValue = earnedValue;
        return this;
    }

    public void setEarnedValue(Long earnedValue) {
        this.earnedValue = earnedValue;
    }

    public typeColour getColour() {
        return colour;
    }

    public Game colour(typeColour colour) {
        this.colour = colour;
        return this;
    }

    public void setColour(typeColour colour) {
        this.colour = colour;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    public String getMsn() {
        return msn;
    }

    public void setMsn(String msn) {
        this.msn = msn;
    }
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
            ", WinningNumber=" + getWinningNumber() +
            ", winner=" + getWinner() +
            ", earnedValue=" + getEarnedValue() +
            ", colour='" + getColour() + "'" +
            "}";
    }
}
