package com.fortech.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fortech.model.Token;

@Repository("tokenRepository")
public interface TokenRepository extends CrudRepository<Token, Integer> {

}
