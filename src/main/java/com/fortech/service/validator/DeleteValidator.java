package com.fortech.service.validator;

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

	Token token;
	Integer idToBeDeleted;

	@Autowired
	TokenService tokenService;

	public DeleteValidator(AccountDeleteDto toValidate) {
		super(toValidate);
	}
	
	public DeleteValidator() {
		// TODO Auto-generated constructor stub
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
			throw new BadRequestException("Invalid token");
		}
	}

	private void validateIsManager() {
		if (token.getAccount().getAccountType().getType() != AccountTypeEnum.STORE_MANAGER) {
			throw new ForbiddenException("Not authorised");
		}
	}

}
