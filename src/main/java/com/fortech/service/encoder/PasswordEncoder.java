package com.fortech.service.encoder;

import org.springframework.security.crypto.password.StandardPasswordEncoder;

public class PasswordEncoder {
	
	private StandardPasswordEncoder encoder;
	private String rawPassword;
	
	public PasswordEncoder() {
		
	}
	
	public PasswordEncoder(String rawPassword) {
		this.rawPassword = rawPassword;
	}
	
	
	public String encodePassword() {
		this.encoder = new StandardPasswordEncoder("secret");
		return encoder.encode(rawPassword);
	}

	public StandardPasswordEncoder getEncoder() {
		return encoder;
	}

	public void setEncoder(StandardPasswordEncoder encoder) {
		this.encoder = encoder;
	}

	public String getRawPassword() {
		return rawPassword;
	}

	public void setRawPassword(String rawPassword) {
		this.rawPassword = rawPassword;
	}
	

}
