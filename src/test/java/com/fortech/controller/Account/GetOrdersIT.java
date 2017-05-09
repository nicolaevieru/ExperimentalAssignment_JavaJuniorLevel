package com.fortech.controller.Account;

import static io.restassured.RestAssured.given;

import java.util.HashMap;

import org.junit.Test;
import org.springframework.http.HttpStatus;

import com.fortech.controller.AbstractTest;

import io.restassured.http.ContentType;
import io.restassured.http.Header;

public class GetOrdersIT extends AbstractTest {

	private static final String EXISTING_USER_ORDERS_URL = "api/users/1001/orders";

	@Test
	public void testGetOrdersWithManagerValidTokenReturnsOk() {
		sendGetRequest(EXISTING_USER_ORDERS_URL).then().assertThat().statusCode(HttpStatus.OK.value());
	}

	@Test
	public void testGetOrdersWithInvalidValidTokenReturnsBadRequest() {
		requestHeader = new Header("token", "5555");
		sendGetRequest(EXISTING_USER_ORDERS_URL).then().assertThat().statusCode(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	public void testGetOrdersWithNoTokenInHeaderReturnsBadRequest() {
		given().auth().basic(USERNAME, PASSWORD).port(PORT)
				.contentType(ContentType.JSON.toString())
				.get(EXISTING_USER_ORDERS_URL)
				.then()
				.assertThat()
				.statusCode(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	public void testGetOrderWithValidTokenReturnsActiveCart() {
		requestJson = new HashMap<>();
		requestJson.put("quantity", "2");
		requestJson.put("token", EXISTING_CUSTOMER_TOKEN);
		sendPostRequest("/api/vinyls/1000/cart");
		sendGetRequest(EXISTING_USER_ORDERS_URL).body().asString();
		sendPostRequest("/api/vinyls/1000/cart");
		given().auth().basic(USERNAME, PASSWORD).port(PORT)
		.contentType(ContentType.JSON.toString()).header(new Header("token", EXISTING_CUSTOMER_TOKEN))
		.put(EXISTING_USER_ORDERS_URL);
		
		System.err.println(sendGetRequest(EXISTING_USER_ORDERS_URL).body().asString());
		
		
	}

}
