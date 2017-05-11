package com.fortech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fortech.model.Token;

@Repository("tokenRepository")
public interface TokenRepository extends CrudRepository<Token, Integer> {
	
	Token findByHash(String hash);
	
	@Query("FROM Token t LEFT JOIN t.account WHERE t.account.id = :id")
	Token findByAccountId(@Param("id") Integer id);
	
	@Query("DELETE FROM Token t WHERE t.account.id not in :ids")
	@Modifying
	@Transactional
	void deleteTokensForOtherAccounts(@Param("ids") List<Integer> ids);
}
