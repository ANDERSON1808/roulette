package com.roulette.com.repository;

import com.roulette.com.domain.Bets;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Bets entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BetsRepository extends MongoRepository<Bets, String> {
}
