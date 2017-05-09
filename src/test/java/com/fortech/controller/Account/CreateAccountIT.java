package com.fortech.controller.Account;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.fortech.controller.AbstractTest;
import com.fortech.service.AccountService;

public class CreateAccountIT extends AbstractTest {
	
	private static final String URL = "api/users/";
	
	@Autowired
	private AccountService accountService;
	
	@Test
	public void testPostWithValidDataReturnsCreatedAndUserIsCreated() {
		/*sendPostRequest(URL).then().assertThat().statusCode(HttpStatus.CREATED.value()).and()assertThat(accountService.findByEmail(this.requestJson.get("email")), is(notNullValue()));
		sendPostRequest(URL).then().assertThat().statusCode(HttpStatus.CREATED.value()).and().assertThat().a*/
	}
	
	@Test
	public void testPostWithTooShortFirstNameReturnsBadRequest() {
		requestJson.put("firstName", "");
		sendPostRequest(URL).then().assertThat().statusCode(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	public void testPostWithTooShortLastNameReturnsBadRequest() {
		requestJson.put("lastName", "");
		sendPostRequest(URL).then().assertThat().statusCode(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	public void testPostWithInvalidPasswordlReturnsBadRequest() {
		requestJson.put("password", "weakpassword");
		sendPostRequest(URL).then().assertThat().statusCode(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	public void testPostWithEmailWithoutAtSignReturnsBadRequest() {
		requestJson.put("email", "emailwithoutat");
		sendPostRequest(URL).then().assertThat().statusCode(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	public void testPostWithNullFirstNameReturnsBadRequest() {
		requestJson.put("firstName", null);
		sendPostRequest(URL).then().assertThat().statusCode(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	public void testPostWithNullLastNameReturnsBadRequest() {
		requestJson.put("lastName", null);
		sendPostRequest(URL).then().assertThat().statusCode(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	public void testThatNewAccountIsPersisted() {
		
	}
	
	@Before
	public void setup() {
		requestJson = new HashMap<>();
		requestJson.put("firstName", "TestFirst"); 
		requestJson.put("lastName", "TestLast");
		requestJson.put("email", "test@email.com");
		requestJson.put("password", "P@ssword123");
	}
	
	
	
}
