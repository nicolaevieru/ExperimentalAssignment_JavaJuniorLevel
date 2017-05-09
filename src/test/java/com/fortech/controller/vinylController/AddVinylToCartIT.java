package com.fortech.controller.vinylController;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.junit.Test;

import com.fortech.controller.AbstractTest;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class AddVinylToCartIT extends AbstractTest {

	private Map<String, String> requestJSON = new HashMap<>();
	private String URL = "/api/vinyls/1000/cart";

	@Test
	public void testWhenRequestJSONIsOkReturn202() {
		populateRequestJSON("12345", "1");
		Response response = sendPostRequest(requestJSON, URL);
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_ACCEPTED);
		
	}

	@Test
	public void testWhenProvidedTokenIsInvalid() {
		populateRequestJSON("00000", "1");
		Response response = sendPostRequest(requestJSON, URL);
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_BAD_REQUEST);
	}

	@Test
	public void testWhenTokenIsNullReturn400() {
		populateRequestJSON("", "1");

		given().auth().basic(USERNAME, PASSWORD).port(PORT).contentType(ContentType.JSON).body(requestJSON).when()
				.post("/api/vinyls/1000/cart").then().assertThat().statusCode(400);
	}

	@Test
	public void testWhenQuantityIsLessThanZeroReturn400() {
		populateRequestJSON("12345", "-1");

		given().auth().basic(USERNAME, PASSWORD).port(PORT).contentType(ContentType.JSON).body(requestJSON).when()
				.post("/api/vinyls/1000/cart").then().assertThat().statusCode(400);
	}

	@Test
	public void testWhenQuantityIsNotNumericReturn400() {
		populateRequestJSON("12345", "asdf");

		given().auth().basic(USERNAME, PASSWORD).port(PORT).contentType(ContentType.JSON).body(requestJSON).when()
				.post("/api/vinyls/1000/cart").then().assertThat().statusCode(400);
	}

	@Test
	public void testWhenQuantityIsNullReturn400() {
		populateRequestJSON("12345", "");

		given().auth().basic(USERNAME, PASSWORD).port(PORT).contentType(ContentType.JSON).body(requestJSON).when()
				.post("/api/vinyls/1000/cart").then().assertThat().statusCode(400);
	}
	
	@Test
	public void testWhenVinylIdDoesNotExistInDatabaseReturn400() {
		populateRequestJSON("12345", "1");

		given().auth().basic(USERNAME, PASSWORD).port(PORT).contentType(ContentType.JSON).body(requestJSON).when()
				.post("/api/vinyls/99999/cart").then().assertThat().statusCode(400);
	}

	@Test
	public void testWhenQuantityIsBiggerThanAvailableStockReturn400() {
		populateRequestJSON("12345", "100000");

		given().auth().basic(USERNAME, PASSWORD).port(PORT).contentType(ContentType.JSON).body(requestJSON).when()
				.post("/api/vinyls/1000/cart").then().assertThat().statusCode(400);
	}

	private void populateRequestJSON(String token, String quantity) {
		requestJSON.put("token", token);
		requestJSON.put("quantity", quantity);
	}

}
