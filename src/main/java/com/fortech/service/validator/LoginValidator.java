package com.fortech.service.validator;

import javax.ws.rs.BadRequestException;

import com.fortech.model.Account;
import com.fortech.model.dto.AccountLoginDto;
import com.fortech.service.encoder.PasswordEncoder;

public class LoginValidator extends Validator<AccountLoginDto> {
	
	private Account  account;

	public LoginValidator(AccountLoginDto toValidate) {
		super(toValidate);
	}
	
	@Override
	public void validate() {
		validateEmail();
		validatePassword();
	}
	
	private void validatePassword() {
		PasswordEncoder encoder = new PasswordEncoder();
		if (!encoder.getEncoder().matches(this.toValidate.getPassword(), account.getPasswordHash())) {
			throw new BadRequestException("Invalid email or password");
		}
	}
	
	private void validateEmail() {
		if(!toValidate.getEmail().equals(account.getEmail())) {
			throw new BadRequestException("Invalid email or password");
		}
	}
	
	public void setAccount(Account account) {
		this.account = account;
	}

}
