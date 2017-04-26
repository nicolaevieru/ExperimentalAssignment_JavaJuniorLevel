package com.fortech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fortech.model.Account;
import com.fortech.model.dto.AccountCreateDTO;
import com.fortech.service.AccountService;
import com.fortech.service.validator.AccountValidator;
import com.fortech.service.validator.Validator;

@RestController
@RequestMapping(value = "api")
public class AccountController {
	
	@Autowired
	private AccountService accountService;
	
	@RequestMapping(value = "users", method = RequestMethod.POST)
	public ResponseEntity<Account> createAccount(@RequestBody AccountCreateDTO request) {
		Validator<AccountCreateDTO> accountValidator = new AccountValidator(request);
		accountValidator.validate();
		Account account = new Account(request);
		accountService.save(account);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
}
