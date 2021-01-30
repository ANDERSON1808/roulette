package com.roulette.com.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import com.roulette.com.domain.enumeration.typeColour;

/**
 * A DTO for the {@link com.roulette.com.domain.Roulette} entity.
 */
public class RouletteDTO implements Serializable {
    
    private String id;

    private String name;

    @Max(value = 2)
    private Integer maximum;

    private typeColour colour;

    private String code;

    private Boolean state;

    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMaximum() {
        return maximum;
    }

    public void setMaximum(Integer maximum) {
        this.maximum = maximum;
    }

    public typeColour getColour() {
        return colour;
    }

    public void setColour(typeColour colour) {
        this.colour = colour;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
        if (!(o instanceof RouletteDTO)) {
            return false;
        }

        return id != null && id.equals(((RouletteDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RouletteDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", maximum=" + getMaximum() +
            ", colour='" + getColour() + "'" +
            ", code='" + getCode() + "'" +
            ", state='" + isState() + "'" +
            "}";
    }
}
