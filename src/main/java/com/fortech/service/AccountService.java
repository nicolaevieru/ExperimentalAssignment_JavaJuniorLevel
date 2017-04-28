package com.fortech.service;

import com.fortech.model.Account;
import com.fortech.model.dto.AccountCreateDto;
import com.fortech.model.dto.AccountDeleteDto;

public interface AccountService {
	
	public Account save(Account toSave);
	public Account save(AccountCreateDto toSave);
	public Account findByEmail(String email);
	public void delete(Integer id);
	public void delete(Integer id, AccountDeleteDto credentials);

}
