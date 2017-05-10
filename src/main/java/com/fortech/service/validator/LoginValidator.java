package com.fortech.service.validator;

import org.apache.log4j.Logger;

import com.fortech.model.Account;

import com.fortech.model.dto.AccountLoginDto;
import com.fortech.service.encoder.PasswordEncoder;
import com.fortech.service.exception.BadRequestException;

public class LoginValidator extends Validator<AccountLoginDto> {
	Logger logger = Logger.getLogger(LoginValidator.class);

	private Account account;

	public LoginValidator(AccountLoginDto toValidate) {
		super(toValidate);
	}

	@Override
	public void validate() {
		if (account == null) {
			logger.error("Error while trying to validate email and password,the account with the specific credentials does not exist.");
			throw new BadRequestException("Invalid email or password");
		}
		validateEmail();
		validatePassword();
	}

	private void validatePassword() {
		if(this.toValidate.getPassword() == null) {
			logger.error("Error while trying to validate password.");
			throw new BadRequestException("Invalid email or password");
		}
		PasswordEncoder encoder = new PasswordEncoder();
		if (!encoder.getEncoder().matches(this.toValidate.getPassword(), account.getPasswordHash())) {
			logger.error("Error while trying to validate password.");
			throw new BadRequestException("Invalid email or password");
		}
	}

	private void validateEmail() {
		if (!toValidate.getEmail().equals(account.getEmail())) {
			logger.error("Error while trying to validate email.");
			throw new BadRequestException("Invalid email or password");
		}
	}

	public void setAccount(Account account) {
		this.account = account;
	}
	
	public Account getAccount() {
		return this.account;
	}

}
