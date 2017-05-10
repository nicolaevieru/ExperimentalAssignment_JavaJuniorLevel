package com.fortech.service.validator;

import org.apache.log4j.Logger;

import com.fortech.model.Token;
import com.fortech.service.exception.UnauthorizedException;

public class TokenValidator extends Validator<Token> {
	Logger logger = Logger.getLogger(TokenValidator.class);

	Integer accountId;

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public TokenValidator() {
	}

	public TokenValidator(Token toValidate, Integer accountId) {
		super(toValidate);
		this.accountId = accountId;
	}

	@Override
	public void validate() {
		
		validateSameAccountAsToken();

	}

	private void validateSameAccountAsToken() {
		if(toValidate.getAccount().getId() != accountId) {
			logger.error("Error while trying to validate that account is the same as the token provided.");
			throw new UnauthorizedException("Not authorised");
		}
		
	}

}
