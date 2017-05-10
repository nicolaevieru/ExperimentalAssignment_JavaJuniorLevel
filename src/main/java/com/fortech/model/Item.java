package com.fortech.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hibernate_seq")
	@SequenceGenerator(name = "hibernate_seq", sequenceName = "hibernate_seq", allocationSize = 1)
	private Integer id;

	@Column(name = "QUANTITY")
	private int quantity;

	@Column(name = "CARTID")
	private Integer cartId;

	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "VINYLID")
	private Vinyl vinyl;

	public Item() {
	}

	public Item(int quantity, Integer cartId, Vinyl vinyl) {
		super();
		this.quantity = quantity;
		this.cartId = cartId;
		this.vinyl = vinyl;
	}

	public Integer getCartId() {
		return cartId;
	}

	public Integer getId() {
		return id;
	}

	public int getQuantity() {
		return quantity;
	}

	public Vinyl getVinyl() {
		return vinyl;
	}

	public void setCart(Integer cartId) {
		this.cartId = cartId;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void setVinyl(Vinyl vinyl) {
		this.vinyl = vinyl;
	}

}
