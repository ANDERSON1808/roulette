package com.roulette.com.service.dto;

import com.roulette.com.domain.enumeration.typeColour;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Size;
import java.io.Serializable;

public class BetsDTO implements Serializable {

    private String id;

    private String roulette;

    private String user;

    @Range(min = 0,max = 36)
    private Integer betNumber;

    private typeColour colorBet;

    @Range(min = 0, max = 10000)
    private Integer betValue;

    private Boolean state;


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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Integer getBetNumber() {
        return betNumber;
    }

    public void setBetNumber(Integer betNumber) {
        this.betNumber = betNumber;
    }

    public typeColour getColorBet() {
        return colorBet;
    }

    public void setColorBet(typeColour colorBet) {
        this.colorBet = colorBet;
    }

    public Integer getBetValue() {
        return betValue;
    }

    public void setBetValue(Integer betValue) {
        this.betValue = betValue;
    }

    public Boolean isState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BetsDTO)) {
            return false;
        }

        return id != null && id.equals(((BetsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BetsDTO{" +
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
