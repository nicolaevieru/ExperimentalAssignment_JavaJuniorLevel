package com.fortech.controller.Account;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.fortech.controller.AbstractTest;
import com.fortech.model.Cart;
import com.fortech.model.CartStateEnum;
import com.fortech.repository.CartRepository;
import com.fortech.repository.CartStateRepository;

import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class PlaceOrderIT extends AbstractTest {
	
	@Autowired
	CartRepository cartRepository;
	
	@Autowired
	CartStateRepository cartStateRepository;
	
	@Test
	public void testRequestWithValidCustomerTokenReturnsOk() {
		requestHeader = new Header("token", EXISTING_CUSTOMER_TOKEN);
		sendPutRequest(EXISTING_CUSTOMER_ORDERS_URL).then().assertThat().statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void testRequestWithInvalidTokenReturnsBadRequest() {
		requestHeader = new Header("token", "12555");
		sendPutRequest(EXISTING_CUSTOMER_ORDERS_URL).then().assertThat().statusCode(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	public void testRequestWithValidManagerTokenReturnsForbidden() {
		requestHeader = new Header("token", EXISTING_MANAGER_TOKEN);
		sendPutRequest(EXISTING_CUSTOMER_ORDERS_URL).then().assertThat().statusCode(HttpStatus.FORBIDDEN.value());
	}
	
	@Test
	public void testRequestWithValidCustomerTokenCreatesNewActiveCart() {
		requestHeader = new Header("token", EXISTING_CUSTOMER_TOKEN);
		sendPutRequest(EXISTING_CUSTOMER_ORDERS_URL);
		assertNotEquals(EXISTING_CART_ID, cartRepository.findActiveByAccount(EXISTING_CUSTOMER_ID).getId().intValue());
	}
	
	@Test
	public void testRequestWithValidCustomerTokenSetActiveCartToProcessing() {
		requestHeader = new Header("token", EXISTING_CUSTOMER_TOKEN);
		sendPutRequest(EXISTING_CUSTOMER_ORDERS_URL);
		assertEquals(EXISTING_CART_ID, cartRepository.findByAccountIdAndCartStateId(EXISTING_CUSTOMER_ID, PROCESSING_CART_STATE_ID).getId().intValue());
	}
	
	@Test
	public void testRequestWithValidCustomerTokenSetsOrderDate() {
		requestHeader = new Header("token", EXISTING_CUSTOMER_TOKEN);
		sendPutRequest(EXISTING_CUSTOMER_ORDERS_URL);
		assertNotNull(cartRepository.findByAccountIdAndCartStateId(EXISTING_CUSTOMER_ID, PROCESSING_CART_STATE_ID).getOrderDate());
	}
	
	@After
	public void resetData() {
		Cart activeCart = cartRepository.findOne(EXISTING_CART_ID);
		activeCart.setCartState(cartStateRepository.findByType(CartStateEnum.ACTIVE));
		activeCart.setCost(0);
		activeCart.setOrderDate(null);
		cartRepository.save(activeCart);
		cartRepository.deleteOtherCarts(EXISTING_CART_ID);
	}
	
	@Before
	public void destroy() {
		cartRepository.deleteOtherCarts(EXISTING_CART_ID);
	}
	
	@Override
	protected Response sendPutRequest(String URL) {
		RequestSpecification request = given().auth().basic(USERNAME, PASSWORD).port(PORT).header(requestHeader).contentType(ContentType.JSON);
		return request.put(URL);
	}
}
