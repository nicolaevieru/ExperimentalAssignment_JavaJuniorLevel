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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(cost);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + stock;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vinyl other = (Vinyl) obj;
		if (Double.doubleToLongBits(cost) != Double.doubleToLongBits(other.cost))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (stock != other.stock)
			return false;
		return true;
	}

}
