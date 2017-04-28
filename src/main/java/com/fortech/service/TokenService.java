package com.fortech.service;

import com.fortech.model.Token;
import com.fortech.model.dto.AccountLoginDto;

public interface TokenService {
	
	Token save(AccountLoginDto toSave);
	Token save(Token toSave);
}
