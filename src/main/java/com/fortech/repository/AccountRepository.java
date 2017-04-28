package com.fortech.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fortech.model.Account;

@Repository("accountRepository")
public interface AccountRepository extends CrudRepository<Account, Integer> {
	Account findByEmail(String email);
	
	@Query("SELECT t.account FROM Token t LEFT JOIN t.account a WHERE t.hash = :token")
	Account findAccountByToken(@Param("token") String token);
}
