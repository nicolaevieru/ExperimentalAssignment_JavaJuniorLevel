package com.fortech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fortech.model.Account;
import com.fortech.model.dto.CustomerDto;

@Repository("accountRepository")
public interface AccountRepository extends CrudRepository<Account, Integer> {
	
	Account findByEmail(String email);
	
	@Query("select new com.fortech.model.dto.CustomerDto(a.email, a.firstName, a.lastName) from Account a LEFT JOIN a.accountStatus accs WHERE a.accountStatus.status != com.fortech.model.AccountStatusEnum.DELETED AND a.accountType.type = com.fortech.model.AccountTypeEnum.CUSTOMER")
	List<CustomerDto> getCustomers();
}
