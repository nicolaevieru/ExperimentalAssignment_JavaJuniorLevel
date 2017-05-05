package com.fortech.service.validator;

import com.fortech.model.AccountTypeEnum;
import com.fortech.model.Token;
import com.fortech.service.exception.UnauthorizedException;

public class IsManagerValidator extends Validator<Token> {
	
	public IsManagerValidator(Token toValidate){
		super(toValidate);
	}
	
	public IsManagerValidator() {
		
	}

	@Override
	public void validate() {
		if (toValidate == null || toValidate.getAccount().getAccountType().getType() != AccountTypeEnum.STORE_MANAGER) {
			throw new UnauthorizedException("Unathorised");
		}
	}

}
