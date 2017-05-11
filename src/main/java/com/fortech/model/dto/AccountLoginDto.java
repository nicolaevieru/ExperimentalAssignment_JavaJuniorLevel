package com.fortech.model.dto;

public class AccountLoginDto {
	
	private String email;
	private String password;
	
	public AccountLoginDto(String email, String password) {
		this.email = email;
		this.password = password;
	}
	
	public AccountLoginDto() {
		
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
