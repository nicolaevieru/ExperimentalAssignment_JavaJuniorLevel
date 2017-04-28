package com.fortech.model.dto;

import com.fortech.model.Token;

public class AddVinylToCartDto {

	private Integer vinylId;

	private int quantity;

	private Token token;

	public AddVinylToCartDto(Integer vinylId, int quantity, Token token) {
		super();
		this.vinylId = vinylId;
		this.quantity = quantity;
		this.token = token;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Integer getVinylId() {
		return vinylId;
	}

	public void setVinylId(Integer vinylId) {
		this.vinylId = vinylId;
	}

	public Token getToken() {
		return token;
	}

	public void setToken(Token token) {
		this.token = token;
	}

}
