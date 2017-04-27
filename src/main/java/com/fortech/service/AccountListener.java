package com.fortech.service;

import javax.persistence.PrePersist;

import com.fortech.model.Account;
import com.fortech.service.encoder.PasswordEncoder;

public class AccountListener {
	
	@PrePersist
	public void accountPrePersist(Account account) {
		PasswordEncoder encoder = new PasswordEncoder(account.getPasswordHash());
		account.setPasswordHash(encoder.encodePassword());
	}

}
