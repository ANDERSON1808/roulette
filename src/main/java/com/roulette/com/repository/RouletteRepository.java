package com.roulette.com.repository;

import com.roulette.com.domain.Roulette;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface RouletteRepository extends MongoRepository<Roulette, String> {
}
