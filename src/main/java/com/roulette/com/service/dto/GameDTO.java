package com.roulette.com.service.dto;

import java.io.Serializable;
import com.roulette.com.domain.enumeration.typeColour;

public class GameDTO implements Serializable {

    private String id;

    private String roulette;

    private Long WinningNumber;

    private Integer winner;

    private Long earnedValue;

    private typeColour colour;

    private String msn;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoulette() {
        return roulette;
    }

    public void setRoulette(String roulette) {
        this.roulette = roulette;
    }

    public Long getWinningNumber() {
        return WinningNumber;
    }

    public void setWinningNumber(Long WinningNumber) {
        this.WinningNumber = WinningNumber;
    }

    public Integer getWinner() {
        return winner;
    }

    public void setWinner(Integer winner) {
        this.winner = winner;
    }

    public Long getEarnedValue() {
        return earnedValue;
    }

    public void setEarnedValue(Long earnedValue) {
        this.earnedValue = earnedValue;
    }

    public typeColour getColour() {
        return colour;
    }

    public String getMsn() {
        return msn;
    }

    public void setMsn(String msn) {
        this.msn = msn;
    }

    public void setColour(typeColour colour) {
        this.colour = colour;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GameDTO)) {
            return false;
        }

        return id != null && id.equals(((GameDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GameDTO{" +
            "id=" + getId() +
            ", roulette='" + getRoulette() + "'" +
            ", WinningNumber=" + getWinningNumber() +
            ", winner=" + getWinner() +
            ", earnedValue=" + getEarnedValue() +
            ", colour='" + getColour() + "'" +
            "}";
    }
}
