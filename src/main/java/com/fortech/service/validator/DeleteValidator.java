package com.fortech.service.validator;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortech.model.AccountTypeEnum;
import com.fortech.model.Token;
import com.fortech.model.dto.AccountDeleteDto;
import com.fortech.service.TokenService;
import com.fortech.service.encoder.PasswordEncoder;
import com.fortech.service.exception.BadRequestException;
import com.fortech.service.exception.ForbiddenException;

@Component("deleteValidator")
public class DeleteValidator extends Validator<AccountDeleteDto> {

	Logger logger = Logger.getLogger(DeleteValidator.class);
	
	Token token;
	Integer idToBeDeleted;

	@Autowired
	TokenService tokenService;

	public DeleteValidator(AccountDeleteDto toValidate) {
		super(toValidate);
	}
	
	public DeleteValidator() {
	}

	@Override
	public void validate() {
		validateToken();
		validatePassword();
		if (token.getAccount().getId() != idToBeDeleted) {
			validateIsManager();
		}
	}

	private void validatePassword() {
		PasswordEncoder encoder = new PasswordEncoder();
		if (!encoder.getEncoder().matches(this.getToValidate().getPassword(), token.getAccount().getPasswordHash())) {
			logger.error("Error while trying to validate password.");
			throw new BadRequestException("Invalid password");
		}

	}

	public Integer getIdToBeDeleted() {
		return idToBeDeleted;
	}

	public void setIdToBeDeleted(Integer idToBeDeleted) {
		this.idToBeDeleted = idToBeDeleted;
	}

	private void validateToken() {
		this.token = tokenService.findByHash(this.toValidate.getToken());
		if (token == null) {
			logger.error("Error while trying to validate token.");
			throw new BadRequestException("Invalid token");
		}
	}

	private void validateIsManager() {
		if (token.getAccount().getAccountType().getType() != AccountTypeEnum.STORE_MANAGER) {
			logger.error("Error while trying to validate that user is a manager.");
			throw new ForbiddenException("Not authorised");
		}
	}

}
