package com.fortech.service.validator;

import com.fortech.model.CartStateEnum;
import com.fortech.service.exception.BadRequestException;

public class CartStateValidator extends Validator<CartStateEnum> {

	public CartStateValidator() {

	}

	public CartStateValidator(CartStateEnum toValidate) {
		this.toValidate = toValidate;
	}

	@Override
	public void validate() {
		if (toValidate == CartStateEnum.ACTIVE) {
			throw new BadRequestException("Invalid order state");
		}

	}

}
