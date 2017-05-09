package com.fortech.controller.Account;

import static io.restassured.RestAssured.given;
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

public class GetCustomersIT extends AbstractTest {
	
	private static final String URL = "api/customers";
	private static final String INVALID_TOKEN = "1111";
	
	@Autowired
	AccountService accountService;
	
	@Test
	public void testGetCustomersWithValidTokenReturnsOK() {
		sendGetRequest(URL).then().assertThat().statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void testGetCustomerReturnsOriginalCustomer() {
		sendGetRequest(URL).then().assertThat().body("customers.email", hasItem("customer@email.com"));
	}
	
	@Test
	public void testGetCustomersWithInvalidTokenReturnsUnauthorized() {
		requestHeader = new Header("token",INVALID_TOKEN);
		sendGetRequest(URL).then().assertThat().statusCode(HttpStatus.UNAUTHORIZED.value());		
	}
	
	@Test
	public void testGetCustomersWithNoTokenInHeaderReturnsUnauthorized() {
		given().auth().basic(USERNAME, PASSWORD)
						.port(PORT).get(URL)
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
		
		sendGetRequest(URL).then().assertThat().body("customers.email", hasItem("newcust@email.com"));
	}

}
