package com.fortech.controller.Account;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import com.fortech.controller.AbstractTest;
import com.fortech.model.Account;
import com.fortech.service.AccountService;
import com.fortech.service.TokenService;

import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class GetCustomersIT extends AbstractTest {
	
	
	private HttpHeaders header;
	
	private Account testAccount;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private TokenService tokenService;
	
	@Test
	public void testGetCustomersWithValidTokenReturnsOK() {
		Response response =send().get("/api/customers");		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());
		
	}

	private RequestSpecification send() {
		return given().auth().basic(USERNAME, PASSWORD).port(PORT).header(new Header("token", "123456"));
	}

}
