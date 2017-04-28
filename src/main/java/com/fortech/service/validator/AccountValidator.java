package com.fortech.service.validator;

import java.util.regex.Matcher;

import java.util.regex.Pattern;

import com.fortech.model.dto.AccountCreateDto;
import com.fortech.service.exception.InvalidEmailException;
import com.fortech.service.exception.InvalidNameException;
import com.fortech.service.exception.InvalidPasswordException;

public class AccountValidator extends Validator<AccountCreateDto> {
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
			Pattern.CASE_INSENSITIVE);
	public static final Pattern VALID_PASSWORD_REGEX = Pattern.compile("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,}");

	public AccountValidator(AccountCreateDto toValidate) {
		super(toValidate);
	}
	
	@Override
	public void validate() {
		validateEmail();
		validatePassword();
		validateNames();
	}

	private void validateEmail() {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(toValidate.getEmail());
		if(!matcher.find()) {
			throw new InvalidEmailException();
		}
	}
	
	private void validatePassword() {
		Matcher matcher = VALID_PASSWORD_REGEX.matcher(toValidate.getPassword());
		if(!matcher.find()) {
			throw new InvalidPasswordException();
		}
	}
	
	private void validateNames() {
		if (toValidate.getFirstName() == null || toValidate.getLastName() == null || toValidate.getFirstName().length() <= 1 || toValidate.getLastName().length() <= 1)
			throw new InvalidNameException();
	}
}

