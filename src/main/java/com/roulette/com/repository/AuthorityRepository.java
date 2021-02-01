package com.roulette.com.repository;

import com.roulette.com.domain.Authority;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface AuthorityRepository extends MongoRepository<Authority, String> {
}
