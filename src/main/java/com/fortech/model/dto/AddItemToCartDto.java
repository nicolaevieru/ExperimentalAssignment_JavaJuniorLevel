package com.fortech.model.dto;

public class AddItemToCartDto {

	private Long itemId;

	private int quantity;

	public AddItemToCartDto(Long itemId, int quantity) {
		super();
		this.itemId = itemId;
		this.quantity = quantity;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
