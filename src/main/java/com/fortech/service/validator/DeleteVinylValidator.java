package com.fortech.service.validator;

import com.fortech.model.Token;

public class DeleteVinylValidator extends Validator<Token> {
	
	IsManagerValidator isManagerValidator;
	
	public DeleteVinylValidator() {
	
	}
	
	public DeleteVinylValidator(Token toValidate) {
		super(toValidate);
		isManagerValidator = new IsManagerValidator(this.toValidate);
	}
	
	@Override
	public void validate() {
		isManagerValidator.validate();
		
	}
	
}
