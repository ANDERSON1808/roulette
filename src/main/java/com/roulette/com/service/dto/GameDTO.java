package com.roulette.com.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.roulette.com.domain.Game} entity.
 */
public class GameDTO implements Serializable {
    
    private String id;

    private String roulette;

    private String winningBet;

    private Integer earnedValue;

    private Boolean winningHouse;

    
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

    public String getWinningBet() {
        return winningBet;
    }

    public void setWinningBet(String winningBet) {
        this.winningBet = winningBet;
    }

    public Integer getEarnedValue() {
        return earnedValue;
    }

    public void setEarnedValue(Integer earnedValue) {
        this.earnedValue = earnedValue;
    }

    public Boolean isWinningHouse() {
        return winningHouse;
    }

    public void setWinningHouse(Boolean winningHouse) {
        this.winningHouse = winningHouse;
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
            ", winningBet='" + getWinningBet() + "'" +
            ", earnedValue=" + getEarnedValue() +
            ", winningHouse='" + isWinningHouse() + "'" +
            "}";
    }
}
