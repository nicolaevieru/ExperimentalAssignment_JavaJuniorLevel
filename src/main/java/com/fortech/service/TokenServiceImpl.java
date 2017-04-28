package com.fortech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
		LoginValidator loginValidator = new LoginValidator(emailAndPass);
		loginValidator.setAccount(token.getAccount());
		loginValidator.validate();
		token.setHash(String.valueOf(emailAndPass.hashCode() + PRIME));
		return save(token);
	}

	@Override
	@Transactional
	public Token save(Token toSave) {
		Token token = tokenRepository.findByHash(toSave.getHash());
		if (token == null) {
			return tokenRepository.save(toSave);
		}
		return token;
	}

	@Override
	public Token findByHash(String token) {
		
		return tokenRepository.findByHash(token);
	}
}
