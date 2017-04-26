package com.fortech.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fortech.model.AccountType;
import com.fortech.model.AccountTypeEnum;

@Repository("accountTypeRepository")
public interface AccountTypeRepository extends CrudRepository<AccountType, Integer>{
	AccountType findByType(AccountTypeEnum type);

}
