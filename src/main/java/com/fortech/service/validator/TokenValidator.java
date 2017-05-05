package com.fortech.service.validator;

import com.fortech.model.Token;
import com.fortech.service.exception.UnauthorizedException;

public class TokenValidator extends Validator<Token> {
	
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
			throw new UnauthorizedException("Not authorised");
		}
		
	}

}
