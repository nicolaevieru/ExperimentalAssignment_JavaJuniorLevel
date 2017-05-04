package com.fortech.service.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortech.model.Token;
import com.fortech.service.TokenService;
import com.fortech.service.exception.BadRequestException;

@Component
public class OrdersValidator extends Validator<Token> {

	Integer userId;

	@Autowired
	TokenService tokenService;

	@Override
	public void validate() {
		validateToken();
		validateIdCorespondsWithToken();
	}

	private void validateToken() {
		if (toValidate == null) {
			throw new BadRequestException("Invalid token");
		}
	}

	private void validateIdCorespondsWithToken() {
		if (toValidate.getAccount().getId() != userId) {
			throw new BadRequestException("Invalid user id,you can only see your carts");
		}
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}
