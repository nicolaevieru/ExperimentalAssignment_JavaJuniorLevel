package com.fortech.service;

import com.fortech.model.Account;
import com.fortech.model.dto.AccountCreateDto;

public interface AccountService {
	
	public Account save(Account toSave);
	public Account save(AccountCreateDto toSave);
	public Account findByEmail(String email);

}
