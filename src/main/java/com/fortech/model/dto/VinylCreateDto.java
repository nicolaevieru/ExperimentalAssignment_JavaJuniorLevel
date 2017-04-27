package com.fortech.model.dto;

import javax.persistence.Column;
import javax.validation.constraints.Min;

public class VinylCreateDto {

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

}
