package com.fortech.model.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fortech.model.CartStateEnum;

public class OrderDto implements Serializable {

	private double cost = 0.0;
	private Date orderDate;
	private CartStateEnum cartState;

	public OrderDto(double cost, Date orderDate, CartStateEnum cartState) {
		super();
		this.cost = cost;
		this.orderDate = orderDate;
		this.cartState = cartState;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public CartStateEnum getCartState() {
		return cartState;
	}

	public void setCartState(CartStateEnum cartState) {
		this.cartState = cartState;
	}

}
