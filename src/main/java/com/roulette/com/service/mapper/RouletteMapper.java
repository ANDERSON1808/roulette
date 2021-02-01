package com.roulette.com.service.mapper;


import com.roulette.com.domain.*;
import com.roulette.com.service.dto.RouletteDTO;

import org.mapstruct.*;

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
