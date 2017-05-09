package com.fortech.controller.vinylController;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.http.HttpStatus;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fortech.controller.AbstractTest;
import com.fortech.repository.VinylRepository;

import io.restassured.response.Response;

public class DeleteVinylIT extends AbstractTest {

	private Integer vinylId = 1000;
	private String URL = String.format("/api/vinyls/%d", vinylId);


	@Autowired
	VinylRepository vinylRepository;

	@Test
	public void testWhenProvidedRequestJSONIsValidReturnNoContent() {
		populateRequestJSON(EXISTING_MANAGER_TOKEN);
		Response response = sendDeleteRequest(URL);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_NO_CONTENT);
		assertThat(vinylRepository.findOne(vinylId).isAvailable()).isEqualTo(false);

	}

	@Test
	public void testWhenProvidedTokenBelongsToACustomerReturnUnauthorized() {
		populateRequestJSON(EXISTING_CUSTOMER_TOKEN);
		Response response = sendDeleteRequest(URL);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_UNAUTHORIZED);

	}

	@Test
	public void testWhenProvidedTokenIsInvalidReturnUnauthorized() {
		populateRequestJSON("00000");
		Response response = sendDeleteRequest(URL);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_UNAUTHORIZED);
	}

	private void populateRequestJSON(String token) {
		requestJson.put("token", token);
	}

}
