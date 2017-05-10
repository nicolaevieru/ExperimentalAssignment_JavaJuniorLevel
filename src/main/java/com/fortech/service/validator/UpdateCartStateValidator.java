package com.fortech.service.validator;

import com.fortech.model.CartStateEnum;
import com.fortech.model.Token;
import com.fortech.model.dto.ChangeOrderStatusDto;

public class UpdateCartStateValidator extends Validator<ChangeOrderStatusDto> {

	Validator<Token> managerValidator;
	Validator<CartStateEnum> stateValidator;

	public UpdateCartStateValidator(Validator<Token> managerValidator, Validator<CartStateEnum> stateValidator) {
		super();
		this.managerValidator = managerValidator;
		this.stateValidator = stateValidator;
	}

	@Override
	public void validate() {
		managerValidator.validate();
		stateValidator.validate();

	}

}
