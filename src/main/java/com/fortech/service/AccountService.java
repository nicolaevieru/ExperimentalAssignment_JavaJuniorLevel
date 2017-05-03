package com.fortech.service;

import com.fortech.model.Account;
import com.fortech.model.dto.AccountCreateDto;
import com.fortech.model.dto.AccountDeleteDto;

public interface AccountService {
	
	 Account save(Account toSave);
	 Account save(AccountCreateDto toSave);
	 Account findByEmail(String email);
	 Account findOne(Integer id);
	 void delete(Integer id);
	 void delete(Integer id, AccountDeleteDto credentials);

}
