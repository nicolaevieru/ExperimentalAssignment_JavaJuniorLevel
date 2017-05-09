package com.fortech.controller.vinylController;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.http.HttpStatus;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fortech.controller.AbstractTest;
import com.fortech.repository.VinylRepository;

import io.restassured.response.Response;

public class VinylCreateIT extends AbstractTest {

	private String URL = "/api/vinyls";

	@Autowired
	private VinylRepository vinylRepository;

	@Test
	public void testWhenProvidedRequestJSONIsValidReturnCreated() {
		Long numberOfVinylsBeforeRequest = vinylRepository.count();

		populateRequestJSON("123456", "Linkin Park", "120", "50");
		Response response = sendPostRequest(URL);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_CREATED);
		assertThat(vinylRepository.count()).isEqualTo(numberOfVinylsBeforeRequest + 1);
	}

	@Test
	public void testWhenProvidedCostIsLessThanZeroReturnBadRequest() {
		populateRequestJSON("123456", "Linkin Park", "-120", "50");

		Response response = sendPostRequest(URL);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_BAD_REQUEST);
	}

	@Test
	public void testWhenProvidedStockIsLessThanZeroReturnBadRequest() {
		populateRequestJSON("123456", "Linkin Park", "120", "-50");

		Response response = sendPostRequest(URL);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_BAD_REQUEST);
	}

	@Test
	public void testWhenProvidedCostIsNonNumericReturnBadRequest() {
		populateRequestJSON("123456", "Linkin Park", "asdf", "50");

		Response response = sendPostRequest(URL);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_BAD_REQUEST);
	}

	@Test
	public void testWhenProvidedStockIsNonNumericReturnBadRequest() {
		populateRequestJSON("123456", "Linkin Park", "120", "asdf");

		Response response = sendPostRequest(URL);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_BAD_REQUEST);
	}

	@Test
	public void testWhenCustomerCreatesVinylReturnUNAUTHORIZED() {
		populateRequestJSON("12345", "Linkin Park", "120", "50");

		Response response = sendPostRequest(URL);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_UNAUTHORIZED);
	}

	@Test
	public void testWhenProvidedTokenIsInvalidReturnBadRequest() {
		populateRequestJSON("000000", "Linkin Park", "120", "50");

		Response response = sendPostRequest(URL);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_BAD_REQUEST);
	}

	@Test
	public void testWhenProvidedNameIsNullReturnBadRequest() {
		populateRequestJSON("123456", "", "120", "50");

		Response response = sendPostRequest(URL);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_BAD_REQUEST);
	}

	@Test
	public void testWhenCostIsNullReturn400() {
		populateRequestJSON("123456", "Linkin Park", "", "50");

		Response response = sendPostRequest(URL);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_BAD_REQUEST);
	}

	@Test
	public void testWhenProvidedStockIsNullReturnBadRequest() {
		populateRequestJSON("123456", "Linkin Park", "120", "");

		Response response = sendPostRequest(URL);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_BAD_REQUEST);
	}

	private void populateRequestJSON(String token, String name, String cost, String stock) {
		requestJSON.put("token", token);
		requestJSON.put("name", name);
		requestJSON.put("cost", cost);
		requestJSON.put("stock", stock);
	}

}
