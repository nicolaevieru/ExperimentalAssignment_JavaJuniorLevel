package com.fortech.controller.account;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import com.fortech.controller.AbstractTest;

import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;

public class GetAccountCartDetailsIT extends AbstractTest{
	
	private static final String EXISTING_CUSTOMER_CART_URL = "api/users/1001/cart";
	private static final String INVALID_TOKEN = "5555";
	
	@Test
	public void testRequestWithValidTokenReturnsOk() {
		requestHeader = new Header("token",EXISTING_CUSTOMER_TOKEN);
		sendGetRequest(EXISTING_CUSTOMER_CART_URL).then().assertThat().statusCode(HttpStatus.OK.value());	
	}
	
	@Test
	public void testRequestWithValidManagerTokenReturnsOk() {
		requestHeader = new Header("token",EXISTING_MANAGER_TOKEN);
		sendGetRequest(EXISTING_CUSTOMER_CART_URL).then().assertThat().statusCode(HttpStatus.OK.value());	
	}
	
	@Test
	public void testRequestWithInvalidTokenReturnsUnathorised() {
		requestHeader = new Header("token",INVALID_TOKEN);
		sendGetRequest(EXISTING_CUSTOMER_CART_URL).then().assertThat().statusCode(HttpStatus.UNAUTHORIZED.value());	
	}
	
	@Test
	public void testRequestNoTokenInHeaderReturnsUnathorised() {
		given().auth().basic(USERNAME, PASSWORD)
						.port(PORT)
						.contentType(ContentType.JSON.toString())
						.get(EXISTING_CUSTOMER_CART_URL)
						.then().assertThat().statusCode(HttpStatus.UNAUTHORIZED.value());
	}
	
	@Test
	public void testValidRequestReturnsEmptyCartForExistingCustomer() {
		requestHeader = new Header("token",EXISTING_CUSTOMER_TOKEN);
		Response response = sendGetRequest(EXISTING_CUSTOMER_CART_URL);
		assertTrue(response.body().jsonPath().getList("items").isEmpty());
		assertTrue(response.body().jsonPath().getInt("numberOfItems") == 0);
		assertTrue(response.body().jsonPath().getDouble("totalCost") == 0);
	}

}
