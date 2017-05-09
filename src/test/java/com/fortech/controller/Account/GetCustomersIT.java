package com.fortech.controller.Account;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.Test;
import org.springframework.http.HttpStatus;

import com.fortech.controller.AbstractTest;

import io.restassured.response.Response;

public class GetCustomersIT extends AbstractTest {
	
	@Test
	public void testGetCustomersWithValidTokenReturnsOK() {
		Response response =sendGetRequest().get("/api/customers");		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	public void testGetCustomerReturns1Row() {
		
	}

	

}
