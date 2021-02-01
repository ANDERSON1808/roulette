package com.roulette.com.repository;

import com.roulette.com.domain.Bets;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@SuppressWarnings("unused")
@Repository
public interface BetsRepository extends MongoRepository<Bets, String> {

    List<Bets> findAllByRouletteAndBetNumber(@Param("Roulette") String Roulette,@Param("BetNumber") Integer BetNumber);
    List<Bets> findAllByRouletteAndColorBet(@Param("Roulette") String Roulette,@Param("ColorBet") String ColorBet);
}
