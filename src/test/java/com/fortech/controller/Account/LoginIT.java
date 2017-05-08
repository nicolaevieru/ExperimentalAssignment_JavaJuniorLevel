package com.fortech.controller.Account;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fortech.controller.AbstractTest;
import com.fortech.model.Account;
import com.fortech.model.AccountStatus;
import com.fortech.model.AccountStatusEnum;
import com.fortech.model.AccountType;
import com.fortech.model.AccountTypeEnum;
import com.fortech.service.AccountService;
import com.fortech.service.TokenService;

import io.restassured.http.ContentType;

public class LoginIT extends AbstractTest {
	
	private Map<String, String> requestJson;
	
	private Account testAccount;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private TokenService tokenService;
	
	
	@Test
	public void testLoginWithValidCredentialsReturns200() {
		given().auth().basic(USERNAME, PASSWORD).port(PORT).contentType(ContentType.JSON).body(requestJson).when()
		.post("/api/users/login").then().assertThat().statusCode(200);
	}

	@Test
	public void testLoginWithInvalidPasswordReturns400() {
		requestJson.put("password", "Password");
		given().auth().basic(USERNAME, PASSWORD).port(PORT).contentType(ContentType.JSON).body(requestJson).when()
		.post("/api/users/login").then().assertThat().statusCode(400);
	}

	@Test
	public void testLoginWithInvalidEmailReturns400() {
		requestJson.put("email", "nottheemail@email.com");
		given().auth().basic(USERNAME, PASSWORD).port(PORT).contentType(ContentType.JSON).body(requestJson).when()
		.post("/api/users/login").then().assertThat().statusCode(400);
	}
	
	@Test
	public void testLoginWithNullEmailReturns400() {
		requestJson.put("email", null);
		given().auth().basic(USERNAME, PASSWORD).port(PORT).contentType(ContentType.JSON).body(requestJson).when()
		.post("/api/users/login").then().assertThat().statusCode(400);
	}
	
	@Test
	public void testLoginWithNullPassword400() {
		requestJson.put("password", null);
		given().auth().basic(USERNAME, PASSWORD).port(PORT).contentType(ContentType.JSON).body(requestJson).when()
		.post("/api/users/login").then().assertThat().statusCode(400);
	}
	
	@Before
	public void setup() {
		testAccount = new Account();
		testAccount.setAccountStatus(new AccountStatus(AccountStatusEnum.ACTIVE));
		testAccount.setAccountType(new AccountType(AccountTypeEnum.CUSTOMER));
		testAccount.setEmail("testemaila@email.com");
		testAccount.setPasswordHash("P@ssword123");
		testAccount.setFirstName("Firstname");
		testAccount.setLastName("Lastname");
		testAccount = accountService.save(testAccount);
		requestJson = new HashMap<>();
		requestJson.put("email", "testemaila@email.com");
		requestJson.put("password", "P@ssword123");
	}
	
	@After
	public void destroy() {
		tokenService.deleteAll();
		accountService.deleteAll();
	}

}
