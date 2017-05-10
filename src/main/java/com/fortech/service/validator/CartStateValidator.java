package com.fortech.service.validator;

import org.apache.log4j.Logger;

import com.fortech.model.CartStateEnum;
import com.fortech.service.exception.BadRequestException;

public class CartStateValidator extends Validator<CartStateEnum> {

	Logger logger = Logger.getLogger(CartStateValidator.class);
	
	public CartStateValidator() {

	}

	public CartStateValidator(CartStateEnum toValidate) {
		this.toValidate = toValidate;
	}

	@Override
	public void validate() {
		if (toValidate == CartStateEnum.ACTIVE) {
			logger.error("Error while trying to validate order state.");
			throw new BadRequestException("Invalid order state");
		}

	}

}
