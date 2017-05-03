package com.fortech.model.dto;

import com.fortech.model.Token;

public class VinylCreateDto {

	private Token tokenObject;
	
	private String token;
	
	private String name;

	private double cost;

	private int stock;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public Token getTokenObject() {
		return tokenObject;
	}

	public void setTokenObject(Token tokenObject) {
		this.tokenObject = tokenObject;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
