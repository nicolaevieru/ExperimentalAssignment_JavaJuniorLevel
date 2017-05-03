package com.fortech.service;

import com.fortech.model.Account;
import com.fortech.model.dto.AccountCreateDto;

public interface AccountService {
	
	Account save(Account toSave);
	Account save(AccountCreateDto toSave);
	Account findByEmail(String email);

}
