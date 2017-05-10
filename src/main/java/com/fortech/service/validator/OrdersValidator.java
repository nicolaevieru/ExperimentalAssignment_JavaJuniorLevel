package com.fortech.service.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortech.model.AccountTypeEnum;
import com.fortech.model.Token;
import com.fortech.service.TokenService;
import com.fortech.service.exception.BadRequestException;
import com.fortech.service.exception.ForbiddenException;

@Component
public class OrdersValidator extends Validator<Token> {

	Integer userId;	

	@Autowired
	TokenService tokenService;

	@Override
	public void validate() {
		validateToken();
		if (!toValidate.getAccount().getId().equals(userId)) {
			throw new ForbiddenException("Not authorised!");
		}		
	}

	private void validateToken() {
		if (toValidate == null) {
			throw new BadRequestException("Invalid token");
		}
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}
