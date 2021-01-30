package com.roulette.com.service.dto;

import java.io.Serializable;
import org.hibernate.validator.constraints.Range;

/**
 * A DTO for the {@link com.roulette.com.domain.Roulette} entity.
 */
public class RouletteDTO implements Serializable {

    private String id;

    private String name;

    @Range(min=0, max=36)
    private Integer maximum = 36;

    private String code;

    private Boolean state = false;


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
            ", code='" + getCode() + "'" +
            ", state='" + isState() + "'" +
            "}";
    }
}
