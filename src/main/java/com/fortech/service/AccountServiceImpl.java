package com.fortech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fortech.model.Account;
import com.fortech.model.AccountType;
import com.fortech.model.dto.AccountCreateDto;
import com.fortech.repository.AccountRepository;
import com.fortech.repository.AccountTypeRepository;
import com.fortech.service.exception.BadRequestException;
import com.fortech.service.validator.AccountValidator;
import com.fortech.service.validator.Validator;

@Service("accountService")
public class AccountServiceImpl implements AccountService {

	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	AccountTypeRepository accountTypeRepository;
	
	@Override
	@Transactional
	public Account save(Account account) {
		if (accountRepository.findByEmail(account.getEmail()) != null) {
			throw new BadRequestException("Account already exists");
		}
		AccountType accountType;
		accountType = accountTypeRepository.findByType(account.getAccountType().getType());
		if (accountType == null) {
			accountType = accountTypeRepository.save(account.getAccountType());
		}
		
		account.setAccountType(accountType);
		return accountRepository.save(account);
	}

	@Override
	public Account findByEmail(String email) {
		return accountRepository.findByEmail(email);
	}

	@Override
	public Account save(AccountCreateDto toSave) {
		Validator<AccountCreateDto> accountValidator = new AccountValidator(toSave);
		accountValidator.validate();
		Account account = new Account(toSave);
		return save(account);
	}

	

}
