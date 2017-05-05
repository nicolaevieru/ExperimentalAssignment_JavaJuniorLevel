package com.fortech.model.dto;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

public class CartDetailsDto implements Serializable {

	private Integer numberOfItems;
	private Double totalCost;
	private List<ItemDto> items = new ArrayList<>();

	public Integer getNumberOfItems() {
		return numberOfItems;
	}

	public void setNumberOfItems(Integer numberOfItems) {
		this.numberOfItems = numberOfItems;
	}

	public Double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(Double totalCost) {
		this.totalCost = totalCost;
	}

	public List<ItemDto> getItems() {
		return items;
	}

	public void setItems(List<ItemDto> items) {
		this.items = items;
	}

}
