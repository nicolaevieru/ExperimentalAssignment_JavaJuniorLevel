package com.fortech.controller.Account;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.hasItem;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.fortech.controller.AbstractTest;
import com.fortech.model.Account;
import com.fortech.model.AccountStatus;
import com.fortech.model.AccountStatusEnum;
import com.fortech.model.AccountType;
import com.fortech.model.AccountTypeEnum;
import com.fortech.service.AccountService;

import io.restassured.http.Header;
import io.restassured.response.Response;

public class GetCustomersIT extends AbstractTest {
	
	@Autowired
	AccountService accountService;
	
	@Test
	public void testGetCustomersWithValidTokenReturnsOK() {
		Response response =sendGetRequest().get("/api/customers");		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	public void testGetCustomerReturnsOriginalCustomer() {
		sendGetRequest().get("api/customers").then().assertThat().body("customers.email", hasItem("customer@email.com"));
	}
	
	@Test
	public void testGetCustomersWithInvalidTokenReturnsUnauthorized() {
		requestHeader = new Header("token","11111");
		Response response =sendGetRequest().get("/api/customers");		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
	}
	
	@Test
	public void testGetCustomersWithNoTokenInHeaderReturnsUnauthorized() {
		given().auth().basic(USERNAME, PASSWORD).port(PORT).get("/api/customers")
															.then()
															.assertThat()
															.statusCode(HttpStatus.UNAUTHORIZED.value());

	}
	
	@Test
	public void testNewCustomerIsInGetCustomersResponse() {
		Account testAccount= new Account();
		testAccount.setAccountStatus(new AccountStatus(AccountStatusEnum.ACTIVE));
		testAccount.setAccountType(new AccountType(AccountTypeEnum.CUSTOMER));
		testAccount.setEmail("newcust@email.com");
		testAccount.setFirstName("New");
		testAccount.setLastName("New");
		testAccount.setPasswordHash("password");
		accountService.save(testAccount);
		
		sendGetRequest().get("api/customers").then().assertThat().body("customers.email", hasItem("newcust@email.com"));
	}

}
