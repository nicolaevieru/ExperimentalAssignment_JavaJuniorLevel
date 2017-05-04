package com.fortech.service.validator;

import org.springframework.stereotype.Component;

import com.fortech.model.AccountTypeEnum;
import com.fortech.model.dto.VinylCreateDto;
import com.fortech.service.exception.BadRequestException;
import com.fortech.service.exception.UnauthorizedException;

@Component
public class VinylCreateValidator extends Validator<VinylCreateDto> {

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

	private void validateName() {
		if (toValidate.getName() == null) {
			throw new BadRequestException("A name must be provided for the vinyl.");
		}
	}

	private void validateCost() {
		if (toValidate.getCost() < 0.0) {
			throw new BadRequestException("Cost can't be less than zero.");
		}
	}

	private void validateStock() {
		if (toValidate.getStock() < 0) {
			throw new BadRequestException("Stock can't be less than zero.");
		}
	}

	private void validateToken() {
		if (toValidate.getToken() == null) {
			throw new BadRequestException("Invalid token.Log in or create an account.");
		}
	}

	private void validateManagerToken() {
		if (toValidate.getTokenObject().getAccount().getAccountType().getType() != AccountTypeEnum.STORE_MANAGER) {
			throw new UnauthorizedException("Only a manager can add new vinyls to the store.");
		}
	}
}