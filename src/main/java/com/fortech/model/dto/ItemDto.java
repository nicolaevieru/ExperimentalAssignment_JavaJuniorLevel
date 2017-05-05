package com.fortech.model.dto;

public class ItemDto {
	
	private String name;
	
	private Integer quantity;
	
	private Double cost;
	

	public ItemDto(String name, Integer quantity, Double cost) {
		super();
		this.name = name;
		this.quantity = quantity;
		this.cost = cost;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}
}
