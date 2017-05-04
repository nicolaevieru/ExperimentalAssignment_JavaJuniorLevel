package com.fortech.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fortech.model.Token;

@Repository("tokenRepository")
public interface TokenRepository extends CrudRepository<Token, Integer> {
	Token findByHash(String hash);
	
	@Query("FROM Token t WHERE t.account.id = :id")
	Token findByAccountId(@Param("id") Integer id);
}
