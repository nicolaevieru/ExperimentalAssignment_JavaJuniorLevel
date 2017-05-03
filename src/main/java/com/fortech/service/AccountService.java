package com.fortech.service;

import org.springframework.http.HttpHeaders;

import com.fortech.model.Account;
import com.fortech.model.dto.AccountCreateDto;
import com.fortech.model.dto.AccountDeleteDto;
import com.fortech.model.dto.CartDetailsDto;

public interface AccountService {
	
	public Account save(Account toSave);
	public Account save(AccountCreateDto toSave);
	public Account findByEmail(String email);
	public Account findOne(Integer id);
	public void delete(Integer id);
	public void delete(Integer id, AccountDeleteDto credentials);
	public CartDetailsDto getCartDetails(Integer userId,HttpHeaders requestHeader);

}
