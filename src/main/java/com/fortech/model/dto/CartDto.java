package com.fortech.model.dto;

import java.util.ArrayList;
import java.util.List;

import com.fortech.model.Cart;
import com.fortech.model.Item;

public class CartDto {

	private Cart cart;
	
	private List<Item> items = new ArrayList<>();

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}
	
	
}
