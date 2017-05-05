package com.fortech.model.dto;

public class VinylCanOrderDto {

	private Integer id;
	private String name;
	private double cost;

	public VinylCanOrderDto() {

	}

	public VinylCanOrderDto(Integer id, String name, double cost) {
		this.id = id;
		this.name = name;
		this.cost = cost;
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

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

}
