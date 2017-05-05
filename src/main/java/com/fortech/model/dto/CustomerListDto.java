package com.fortech.model.dto;

import java.util.List;

public class CustomerListDto {

	private List<CustomerDto> customers;

	public CustomerListDto() {

	}

	public CustomerListDto(List<CustomerDto> customers) {
		this.customers = customers;
	}

	public List<CustomerDto> getCustomers() {
		return customers;
	}

	public void setCustomers(List<CustomerDto> customers) {
		this.customers = customers;
	}

}
