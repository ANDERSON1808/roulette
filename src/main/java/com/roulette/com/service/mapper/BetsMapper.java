package com.roulette.com.service.mapper;


import com.roulette.com.domain.*;
import com.roulette.com.service.dto.BetsDTO;

import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {})
public interface BetsMapper extends EntityMapper<BetsDTO, Bets> {



    default Bets fromId(String id) {
        if (id == null) {
            return null;
        }
        Bets bets = new Bets();
        bets.setId(id);
        return bets;
    }
}
