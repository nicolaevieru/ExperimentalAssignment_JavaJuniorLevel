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
			validateIsManager();
		}		
	}

	private void validateToken() {
		if (toValidate == null) {
			throw new BadRequestException("Invalid token");
		}
	}

	private void validateIsManager() {
		if (toValidate.getAccount().getAccountType().getType() != AccountTypeEnum.STORE_MANAGER) {
			throw new ForbiddenException("Not authorised!");
		}
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}
