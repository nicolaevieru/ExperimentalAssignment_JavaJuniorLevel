package com.fortech.service.validator;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.fortech.model.AccountTypeEnum;
import com.fortech.model.dto.VinylCreateDto;
import com.fortech.service.exception.BadRequestException;
import com.fortech.service.exception.UnauthorizedException;

@Component
public class VinylCreateValidator extends Validator<VinylCreateDto> {
	Logger logger = Logger.getLogger(VinylCreateValidator.class);

	public VinylCreateValidator() {
	}

	public VinylCreateValidator(VinylCreateDto toValidate) {
		super(toValidate);
	}

	@Override
	public void validate() {
		validateToken();
		validateManagerToken();
		validateName();
		validateCost();
		validateStock();
	}

	protected void validateToken() {
		if (toValidate.getTokenObject() == null) {
			logger.error("Error while trying to validate token.");
			throw new BadRequestException("Invalid token.Log in or create an account.");
		}
	}
	
	protected void validateManagerToken() {
		if (toValidate.getTokenObject().getAccount().getAccountType().getType() != AccountTypeEnum.STORE_MANAGER) {
			logger.error("Error while trying to validate that token belongs to a manager.");
			throw new UnauthorizedException("Only a manager can add new vinyls to the store.");
		}
	}

	protected void validateName() {
		if (toValidate.getName() == null || toValidate.getName() == "") {
			logger.error("Error while trying to validate name.");
			throw new BadRequestException("A name must be provided for the vinyl.");
		}
	}

	protected void validateCost() {
		if (toValidate.getCost() < 0.0 || toValidate.getCost() == 0) {
			logger.error("Error while trying to validate cost.");
			throw new BadRequestException("Cost can't be null or less than zero.");
		}
	}

	protected void validateStock() {
		if (toValidate.getStock() < 0 || toValidate.getStock() == 0) {
			logger.error("Error while trying to validate stock.");
			throw new BadRequestException("Stock can't be null or less than zero.");
		}
	}

}
