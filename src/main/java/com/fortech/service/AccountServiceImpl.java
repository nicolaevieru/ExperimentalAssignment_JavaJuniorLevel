package com.fortech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fortech.model.Account;
import com.fortech.model.AccountType;
import com.fortech.repository.AccountRepository;
import com.fortech.repository.AccountTypeRepository;

@Service("accountService")
public class AccountServiceImpl implements AccountService {

	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	AccountTypeRepository accountTypeRepository;
	
	@Override
	@Transactional
	public Account save(Account account) {
		AccountType accountType;
		accountType = accountTypeRepository.findByType(account.getAccountType().getType());
		if (accountType == null) {
			accountType = accountTypeRepository.save(account.getAccountType());
		}
		
		account.setAccountType(accountType);
		return accountRepository.save(account);
	}

	

}
