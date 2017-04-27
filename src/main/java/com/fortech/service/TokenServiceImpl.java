package com.fortech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fortech.model.Token;
import com.fortech.model.dto.AccountLoginDto;
import com.fortech.repository.TokenRepository;
import com.fortech.service.validator.LoginValidator;

@Service("tokenService")
public class TokenServiceImpl implements TokenService {
	
	private final static int PRIME = 41;
	
	@Autowired
	TokenRepository tokenRepository;
	
	@Autowired
	AccountService accountService;
	
	@Override
	public Token save(AccountLoginDto emailAndPass) {
		Token token = new Token();
		token.setAccount(accountService.findByEmail(emailAndPass.getEmail()));
		token.setHash(String.valueOf(emailAndPass.hashCode() + PRIME));
		LoginValidator loginValidator = new LoginValidator(emailAndPass);
		loginValidator.setAccount(token.getAccount());
		loginValidator.validate();
		return save(token);
	}

	@Override
	public Token save(Token toSave) {
		return tokenRepository.save(toSave);
	}
}
