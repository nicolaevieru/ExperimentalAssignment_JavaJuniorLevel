package com.fortech.model;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Min;

import com.fortech.model.dto.VinylCreateDto;

@Entity
public class Vinyl {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hibernate_seq")
	@SequenceGenerator(name = "hibernate_seq", sequenceName = "hibernate_seq", allocationSize = 1)
	private Integer id;

	@Column(name = "name")
	private String name;

	@Column(name = "cost")
	private double cost = 0.0;

	@Column(name = "IS_AVAILABLE")
	private boolean available = true;

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	@Column(name = "stock")
	@Min(0)
	private int stock = 0;

	public Vinyl() {
	}

	public Vinyl(Vinyl copyVinyl) {
		this.id = copyVinyl.getId();
		this.name = copyVinyl.getName();
		this.cost = copyVinyl.getCost();
		this.stock = copyVinyl.getStock();
	}

	public Vinyl(VinylCreateDto vinyl) {
		super();
		this.name = vinyl.getName();
		this.cost = vinyl.getCost();
		this.stock = vinyl.getStock();
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

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

}
