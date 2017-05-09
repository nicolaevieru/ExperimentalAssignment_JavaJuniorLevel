package com.fortech.controller.vinylController;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.http.HttpStatus;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fortech.controller.AbstractTest;
import com.fortech.model.Vinyl;
import com.fortech.repository.VinylRepository;

import io.restassured.response.Response;

public class UpdateVinylInfoIT extends AbstractTest {

	private Integer vinylId = 1000;
	private String URL = String.format("/api/vinyls/%d", vinylId);
	
	@Autowired
	private VinylRepository vinylRepository;

	@Test
	public void testWhenProvidedRequestJSONIsValidReturnOk() {
		populateRequestJSON(EXISTING_MANAGER_TOKEN, "Linkin Park Modified", "100", "150");
		Response response = sendPutRequest(URL);
		Vinyl updatedVinyl = vinylRepository.findOne(vinylId);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
		assertThat(updatedVinyl.getName()).isEqualTo("Linkin Park Modified");
		assertThat(updatedVinyl.getCost()).isEqualTo(100);
		assertThat(updatedVinyl.getStock()).isEqualTo(150);

	}
	
	@Test
	public void testWhenProvidedVinylIdInUrlIsInvalidReturnBadRequest() {
		populateRequestJSON("000000", "Linkin Park Modified", "100", "150");

		Response response = sendPutRequest(URL);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_BAD_REQUEST);
	}

	@Test
	public void testWhenProvidedCostIsLessThanZeroReturnBadRequest() {
		populateRequestJSON(EXISTING_MANAGER_TOKEN, "Linkin Park Modified", "-100", "150");

		Response response = sendPutRequest(URL);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_BAD_REQUEST);
	}

	@Test
	public void testWhenProvidedStockIsLessThanZeroReturnBadRequest() {
		populateRequestJSON(EXISTING_MANAGER_TOKEN, "Linkin Park Modified", "100", "-150");

		Response response = sendPutRequest(URL);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_BAD_REQUEST);
	}

	@Test
	public void testWhenProvidedCostIsNonNumericReturnBadRequest() {
		populateRequestJSON(EXISTING_MANAGER_TOKEN, "Linkin Park Modified", "asdf", "150");

		Response response = sendPutRequest(URL);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_BAD_REQUEST);
	}

	@Test
	public void testWhenProvidedStockIsNonNumericReturnBadRequest() {
		populateRequestJSON(EXISTING_MANAGER_TOKEN, "Linkin Park Modified", "100", "asdf");

		Response response = sendPutRequest(URL);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_BAD_REQUEST);
	}

	@Test
	public void testWhenCustomerCreatesVinylReturnUNAUTHORIZED() {
		populateRequestJSON(EXISTING_CUSTOMER_TOKEN, "Linkin Park Modified", "100", "150");

		Response response = sendPutRequest(URL);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_UNAUTHORIZED);
	}

	@Test
	public void testWhenProvidedTokenIsInvalidReturnBadRequest() {
		populateRequestJSON("000000", "Linkin Park Modified", "100", "150");

		Response response = sendPutRequest(URL);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_BAD_REQUEST);
	}

	@Test
	public void testWhenProvidedNameIsNullReturnBadRequest() {
		populateRequestJSON(EXISTING_MANAGER_TOKEN, "", "100", "150");

		Response response = sendPutRequest(URL);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_BAD_REQUEST);
	}

	@Test
	public void testWhenCostIsNullReturn400() {
		populateRequestJSON(EXISTING_MANAGER_TOKEN, "Linkin Park Modified", "", "150");

		Response response = sendPutRequest(URL);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_BAD_REQUEST);
	}

	@Test
	public void testWhenProvidedStockIsNullReturnBadRequest() {
		populateRequestJSON(EXISTING_MANAGER_TOKEN, "Linkin Park Modified", "100", "");

		Response response = sendPutRequest(URL);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_BAD_REQUEST);
	}

	
	private void populateRequestJSON(String token, String name, String cost, String stock) {
		requestJson.put("token", token);
		requestJson.put("name", name);
		requestJson.put("cost", cost);
		requestJson.put("stock", stock);
	}

	
}
