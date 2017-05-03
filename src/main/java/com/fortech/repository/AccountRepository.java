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
	Account findByToken(@Param("token") String token);
	
	@Query("FROM Account a LEFT JOIN a.accountStatus accs WHERE (a.accountStatus.status != com.fortech.model.AccountStatusEnum.DELETED AND a.email = :email)")
	Account findByEmailAndNotDeleted(@Param("email") String email);
}
