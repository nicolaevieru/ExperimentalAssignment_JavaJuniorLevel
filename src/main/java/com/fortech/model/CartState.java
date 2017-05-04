package com.fortech.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity(name = "CART_STATE")
public class CartState {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hibernate_seq")
	@SequenceGenerator(name = "hibernate_seq", sequenceName = "hibernate_seq", allocationSize = 1)
	private Integer id;

	@Enumerated(EnumType.STRING)
	@Column(name = "type")
	private CartStateEnum type;

	public CartState(CartStateEnum type) {
		this.type = type;
	}
	
	public CartState() {
		
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public CartStateEnum getType() {
		return type;
	}

	public void setType(CartStateEnum type) {
		this.type = type;
	}

}
