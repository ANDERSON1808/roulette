package com.roulette.com.domain;

import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import javax.validation.constraints.*;

import java.io.Serializable;

import com.roulette.com.domain.enumeration.typeColour;

@Document(collection = "roulette")
public class Roulette implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("name")
    private String name;

    @Range(min=0, max=36)
    @Field("maximum")

    private Integer maximum;

    @Field("code")
    private String code;

    @Field("state")
    private Boolean state;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Roulette name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMaximum() {
        return maximum;
    }

    public Roulette maximum(Integer maximum) {
        this.maximum = maximum;
        return this;
    }

    public void setMaximum(Integer maximum) {
        this.maximum = maximum;
    }


    public String getCode() {
        return code;
    }

    public Roulette code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean isState() {
        return state;
    }

    public Roulette state(Boolean state) {
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
        if (!(o instanceof Roulette)) {
            return false;
        }
        return id != null && id.equals(((Roulette) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Roulette{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", maximum=" + getMaximum() +
            ", code='" + getCode() + "'" +
            ", state='" + isState() + "'" +
            "}";
    }
}
