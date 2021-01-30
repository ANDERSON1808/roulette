package com.roulette.com.service.mapper;


import com.roulette.com.domain.*;
import com.roulette.com.service.dto.GameDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Game} and its DTO {@link GameDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GameMapper extends EntityMapper<GameDTO, Game> {



    default Game fromId(String id) {
        if (id == null) {
            return null;
        }
        Game game = new Game();
        game.setId(id);
        return game;
    }
}
