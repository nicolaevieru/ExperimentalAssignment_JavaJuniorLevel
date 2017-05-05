package com.fortech.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hibernate_seq")
	@SequenceGenerator(name = "hibernate_seq", sequenceName = "hibernate_seq", allocationSize = 1)
	private Integer id;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ACCOUNTID")
	private Account account;

	@Column(name = "COST")
	double cost = 0.0;

	@Column(name="ORDERDATE")
	private Date orderDate;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "STATEID")
	private CartState cartState;

	public Cart() {
	}

	public Cart(Account account, CartState cartState) {
		super();
		this.account = account;
		this.cartState = cartState;
	}

	public Account getAccount() {
		return account;
	}

	public CartState getCartState() {
		return cartState;
	}

	public double getCost() {
		return cost;
	}

	public Integer getId() {
		return id;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public void setCartState(CartState cartState) {
		this.cartState = cartState;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

}
