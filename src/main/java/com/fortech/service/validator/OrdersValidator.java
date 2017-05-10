package com.fortech.service.validator;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortech.model.Token;
import com.fortech.service.TokenService;
import com.fortech.service.exception.BadRequestException;
import com.fortech.service.exception.ForbiddenException;

@Component
public class OrdersValidator extends Validator<Token> {
	Logger logger = Logger.getLogger(OrdersValidator.class);

	Integer userId;

	@Autowired
	TokenService tokenService;

	@Override
	public void validate() {
		validateToken();
		if (!toValidate.getAccount().getId().equals(userId)) {
			logger.error("Error while trying to validate that user id does not belong to another user.");
			throw new ForbiddenException("Not authorised!");
		}		
	}

	private void validateToken() {
		if (toValidate == null) {
			logger.error("Error while trying to validate token.");
			throw new BadRequestException("Invalid token");
		}
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}
