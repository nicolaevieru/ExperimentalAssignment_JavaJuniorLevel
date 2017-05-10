package com.fortech.controller.cart;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.http.HttpStatus;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fortech.controller.AbstractTest;
import com.fortech.model.CartStateEnum;
import com.fortech.repository.CartRepository;

import io.restassured.response.Response;

public class ChangeOrderStatusIT extends AbstractTest {

	private Integer cartId = 1000;
	private String URL = String.format("/api/orders/%d", cartId);

	@Autowired
	private CartRepository cartRepository;

	@Test
	public void testWhenProvidedRequestJsonIsValidReturnOk() {
		populateRequestJSON(EXISTING_MANAGER_TOKEN, "COMPLETED");
		Response response = sendPutRequest(URL);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
		assertThat(cartRepository.findOne(cartId).getCartState().getType()).isEqualTo(CartStateEnum.COMPLETED);
	}

	@Test
	public void testWhenProvidedTokenBelongsToCustomerReturnUnauthorized() {
		populateRequestJSON(EXISTING_CUSTOMER_TOKEN, "COMPLETED");
		Response response = sendPutRequest(URL);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_UNAUTHORIZED);
	}

	@Test
	public void testWhenProvidedCartStatusIsInvalidReturnBadRequest() {
		populateRequestJSON(EXISTING_MANAGER_TOKEN, "NonExistingCartStatus");
		Response response = sendPutRequest(URL);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_BAD_REQUEST);
	}

	@Test
	public void testWhenProvidedCartIdIsInvalidReturnBadRequest() {
		populateRequestJSON(EXISTING_MANAGER_TOKEN, "COMPLETED");
		Response response = sendPutRequest("/api/orders/99999999");

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_BAD_REQUEST);
	}

	@Test
	public void testWhenProvidedCartStatusIsNullReturnBadRequest() {
		populateRequestJSON(EXISTING_MANAGER_TOKEN, "");
		Response response = sendPutRequest(URL);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_BAD_REQUEST);
	}

	@Test
	public void testWhenProvidedAutenthicationTokenIsNullReturnUnauthorized() {
		populateRequestJSON("", "COMPLETED");
		Response response = sendPutRequest(URL);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_UNAUTHORIZED);
	}

	private void populateRequestJSON(String token, String status) {
		requestJson.put("token", token);
		requestJson.put("status", status);
	}

}
