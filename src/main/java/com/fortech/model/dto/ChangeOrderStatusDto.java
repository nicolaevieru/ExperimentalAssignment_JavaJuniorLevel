package com.fortech.model.dto;

import com.fortech.model.CartStateEnum;

public class ChangeOrderStatusDto {

	private String token;
	private CartStateEnum status;

	public ChangeOrderStatusDto() {

	}

	public ChangeOrderStatusDto(String token, CartStateEnum status) {
		this.token =  token;
		this.status = status;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public CartStateEnum getStatus() {
		return this.status;
	}

	public void setStatus(CartStateEnum status) {
		this.status = status;
	}

}
