package com.fortech.service.encoder;

import org.springframework.security.crypto.password.StandardPasswordEncoder;

public class PasswordEncoder {
	private static final String KEY = "secret";

	private StandardPasswordEncoder encoder = new StandardPasswordEncoder(KEY);
	private String rawPassword;

	public PasswordEncoder() {

	}

	public PasswordEncoder(String rawPassword) {
		this.rawPassword = rawPassword;
	}

	public String encodePassword() {
		return encoder.encode(rawPassword);
	}

	public StandardPasswordEncoder getEncoder() {
		return encoder;
	}

	public String getRawPassword() {
		return rawPassword;
	}

	public void setEncoder(StandardPasswordEncoder encoder) {
		this.encoder = encoder;
	}

	public void setRawPassword(String rawPassword) {
		this.rawPassword = rawPassword;
	}

}
