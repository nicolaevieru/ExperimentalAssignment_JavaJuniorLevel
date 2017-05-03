package com.fortech.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fortech.model.AccountStatus;
import com.fortech.model.AccountStatusEnum;

@Repository("accountStatusRepository")
public interface AccountStatusRepository extends CrudRepository<AccountStatus, Integer> {
	AccountStatus findByStatus(AccountStatusEnum type);

}
