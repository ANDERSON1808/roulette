package com.roulette.com.repository;

import com.roulette.com.domain.Game;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Game entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GameRepository extends MongoRepository<Game, String> {
}
