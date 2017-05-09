package com.fortech.controller.vinylController;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.http.HttpStatus;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fortech.controller.AbstractTest;
import com.fortech.repository.CartRepository;

import io.restassured.response.Response;

public class AddVinylToCartIT extends AbstractTest {

	private String URL = "/api/vinyls/1000/cart";

	@Autowired
	CartRepository cartRepository;

	@Test
	public void testWhenProvidedRequestJSONIsValidReturnAccepted() {
		populateRequestJSON("12345", "1");
		Response response = sendPostRequest(URL);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_ACCEPTED);
	}

	@Test
	public void testWhenProvidedRequestJSONIsValidModifyCartCostInDatabase() {
		Double cartCostBeforeRequest = cartRepository.findByAccountIdAndCartStateId(1001, 1).getCost();

		populateRequestJSON("12345", "1");
		sendPostRequest(URL);

		assertThat(cartRepository.findByAccountIdAndCartStateId(1001, 1).getCost())
				.isGreaterThan(cartCostBeforeRequest);
	}

	@Test
	public void testWhenProvidedTokenInRequestJsonIsInvalidReturnBadRequest() {
		populateRequestJSON("00000", "1");
		Response response = sendPostRequest(URL);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_BAD_REQUEST);
	}

	@Test
	public void testWhenProvidedTokenInRequestJsonIsNullReturnBadRequest() {
		populateRequestJSON("", "1");

		Response response = sendPostRequest(URL);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_BAD_REQUEST);
	}

	@Test
	public void testWhenProvidedQuantityInRequestJsonIsLessThanZeroReturnBadRequest() {
		populateRequestJSON("12345", "-1");

		Response response = sendPostRequest(URL);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_BAD_REQUEST);
	}

	@Test
	public void testWhenProvidedQuantityInRequestJsonIsNotNumericReturnBadRequest() {
		populateRequestJSON("12345", "asdf");

		Response response = sendPostRequest(URL);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_BAD_REQUEST);
	}

	@Test
	public void testWhenProvidedQuantityInRequestJsonIsNullReturnBadRequest() {
		populateRequestJSON("12345", "");

		Response response = sendPostRequest(URL);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_BAD_REQUEST);
	}

	@Test
	public void testWhenProvidedVinylIdInRequestUrlDoesNotExistInDatabaseReturnBadRequest() {
		populateRequestJSON("12345", "1");

		Response response = sendPostRequest("/api/vinyls/9999999/cart");

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_BAD_REQUEST);
	}

	@Test
	public void testWhenProvidedQuantityInRequestJsonIsBiggerThanAvailableStockReturnBadRequest() {
		populateRequestJSON("12345", "100000");

		Response response = sendPostRequest(URL);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_BAD_REQUEST);
	}

	private void populateRequestJSON(String token, String quantity) {
		requestJSON.put("token", token);
		requestJSON.put("quantity", quantity);
	}

}