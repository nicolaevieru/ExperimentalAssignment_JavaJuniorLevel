package com.fortech.service.validator;

import org.apache.log4j.Logger;

import com.fortech.model.AccountTypeEnum;
import com.fortech.model.Token;
import com.fortech.service.exception.UnauthorizedException;

public class IsManagerValidator extends Validator<Token> {
	Logger logger = Logger.getLogger(IsManagerValidator.class);

	public IsManagerValidator(Token toValidate){
		super(toValidate);
	}
	
	public IsManagerValidator() {
		
	}

	@Override
	public void validate() {
		if (toValidate == null || toValidate.getAccount().getAccountType().getType() != AccountTypeEnum.STORE_MANAGER) {
			logger.error("Error while trying to validate that user is manager.");
			throw new UnauthorizedException("Unathorised");
		}
	}

}
