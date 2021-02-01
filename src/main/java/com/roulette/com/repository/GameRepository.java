package com.roulette.com.repository;

import com.roulette.com.domain.Game;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@SuppressWarnings("unused")
@Repository
public interface GameRepository extends MongoRepository<Game, String> {
    List<Game> findAllByRoulette(@Param("Roulette")String Roulette);
}
