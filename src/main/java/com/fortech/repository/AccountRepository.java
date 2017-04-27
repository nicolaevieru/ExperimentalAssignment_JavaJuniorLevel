package com.fortech.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fortech.model.Account;

@Repository("accountRepository")
public interface AccountRepository extends CrudRepository<Account, Integer> {
	Account findByEmail(String email);
}
