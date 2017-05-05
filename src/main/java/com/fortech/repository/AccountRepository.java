package com.fortech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fortech.model.Account;
import com.fortech.model.dto.CustomerDto;

@Repository("accountRepository")
public interface AccountRepository extends CrudRepository<Account, Integer> {
	Account findByEmail(String email);
	
	@Query("SELECT t.account FROM Token t LEFT JOIN t.account a WHERE t.hash = :token")
	Account findByToken(@Param("token") String token);
	
	@Query("FROM Account a LEFT JOIN a.accountStatus accs WHERE (a.accountStatus.status != com.fortech.model.AccountStatusEnum.DELETED AND a.email = :email)")
	Account findByEmailAndNotDeleted(@Param("email") String email);
	
	@Query("select new com.fortech.model.dto.CustomerDto(a.email, a.firstName, a.lastName) from Account a LEFT JOIN a.accountStatus accs WHERE  a.accountStatus.status != com.fortech.model.AccountStatusEnum.DELETED")
	List<CustomerDto> getCustomers();
}
