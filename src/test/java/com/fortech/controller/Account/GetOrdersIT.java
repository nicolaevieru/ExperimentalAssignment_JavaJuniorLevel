package com.fortech.controller.Account;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.hasItem;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.fortech.controller.AbstractTest;
import com.fortech.repository.AccountRepository;
import com.fortech.repository.CartRepository;
import com.fortech.repository.ItemRepository;

import io.restassured.http.ContentType;
import io.restassured.http.Header;

public class GetOrdersIT extends AbstractTest {

	
	
	@Autowired
	CartRepository cartRepository;
	
	@Autowired
	ItemRepository itemRepository;
	
	@Autowired
	AccountRepository accountRepository;

	@Test
	public void testGetOrdersWithManagerValidTokenReturnsOk() {
		sendGetRequest(EXISTING_CUSTOMER_ORDERS_URL).then().assertThat().statusCode(HttpStatus.OK.value());
	}

	@Test
	public void testGetOrdersWithInvalidValidTokenReturnsBadRequest() {
		requestHeader = new Header("token", "5555");
		sendGetRequest(EXISTING_CUSTOMER_ORDERS_URL).then().assertThat().statusCode(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	public void testGetOrdersWithNoTokenInHeaderReturnsBadRequest() {
		given().auth().basic(USERNAME, PASSWORD).port(PORT)
				.contentType(ContentType.JSON.toString())
				.get(EXISTING_CUSTOMER_ORDERS_URL)
				.then()
				.assertThat()
				.statusCode(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	public void testGetOrderWithValidCustomerTokenReturnsActiveCart() {
		System.err.println(sendGetRequest(EXISTING_CUSTOMER_ORDERS_URL).body().asString());
		sendGetRequest(EXISTING_CUSTOMER_ORDERS_URL).then().assertThat().body("cartState",hasItem("ACTIVE"));
	}
	
	@Test
	public void testGetOrderWithValidCustomerTokenReturnsPlacedOrder() {
		requestHeader = new Header("token", EXISTING_CUSTOMER_TOKEN);
		given().auth().basic(USERNAME, PASSWORD).port(PORT).contentType(ContentType.JSON).header(requestHeader).put("api/users/1001/orders");
		sendGetRequest(EXISTING_CUSTOMER_ORDERS_URL).then().assertThat().body("cartState",hasItem("PROCESSING"));
	}
	
	@Before
	public void resetData() {
		requestHeader = new Header("token", EXISTING_MANAGER_TOKEN);
	}
}
