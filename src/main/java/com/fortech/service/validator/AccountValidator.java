package com.fortech.service.validator;

import java.util.regex.Matcher;

import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.fortech.model.dto.AccountCreateDto;
import com.fortech.service.exception.BadRequestException;

public class AccountValidator extends Validator<AccountCreateDto> {
	
	Logger logger = Logger.getLogger(AccountValidator.class);
	
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
			logger.error("Error while validating email address.");
			throw new BadRequestException("The email address is invalid");
		}
	}
	
	private void validatePassword() {
		Matcher matcher = VALID_PASSWORD_REGEX.matcher(toValidate.getPassword());
		if(!matcher.find()) {
			logger.error("Error while validating password.");
			throw new BadRequestException("The password is invalid, it must contain 1 upercase letter, 1 special character, 1 digit and at it must be at least 6 characters long");
		}
	}
	
	private void validateNames() {
		if (toValidate.getFirstName() == null || toValidate.getLastName() == null || toValidate.getFirstName().length() <= 1 || toValidate.getLastName().length() <= 1){
			logger.error("Error while validating first name and last name.");
			throw new BadRequestException("The names must have more than 1 character");
		}
	}
}

