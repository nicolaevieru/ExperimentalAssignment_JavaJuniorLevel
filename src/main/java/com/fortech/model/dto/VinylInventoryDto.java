package com.fortech.model.dto;

public class VinylInventoryDto {
	Integer id;
	String name;
	Integer stock;

	public VinylInventoryDto() {

	}

	public VinylInventoryDto(Integer id, String name, Integer stock) {
		this.id = id;
		this.name = name;
		this.stock = stock;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}
}
