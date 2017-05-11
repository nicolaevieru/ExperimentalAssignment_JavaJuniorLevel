package com.fortech.controller.account;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.hasItem;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.fortech.controller.AbstractTest;
import com.fortech.model.Cart;
import com.fortech.model.CartStateEnum;
import com.fortech.repository.AccountRepository;
import com.fortech.repository.CartRepository;
import com.fortech.repository.CartStateRepository;
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
	
	@Autowired
	private CartStateRepository cartStateRepository;

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
		
		Cart activeCart = cartRepository.findOne(EXISTING_CART_ID);
		activeCart.setCartState(cartStateRepository.findByType(CartStateEnum.ACTIVE));
		activeCart.setCost(0);
		activeCart.setOrderDate(null);
		cartRepository.save(activeCart);
		cartRepository.deleteOtherCarts(EXISTING_CART_ID);
	}
}
