package com.roulette.com.service.mapper;


import com.roulette.com.domain.*;
import com.roulette.com.service.dto.RouletteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Roulette} and its DTO {@link RouletteDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RouletteMapper extends EntityMapper<RouletteDTO, Roulette> {



    default Roulette fromId(String id) {
        if (id == null) {
            return null;
        }
        Roulette roulette = new Roulette();
        roulette.setId(id);
        return roulette;
    }
}
