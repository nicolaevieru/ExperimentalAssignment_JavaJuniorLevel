package com.fortech.controller.vinylController;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.fortech.controller.AbstractTest;

import io.restassured.http.ContentType;

public class VinylCreateIT extends AbstractTest {

	private Map<String, String> requestJSON = new HashMap<>();
	

	@Test
	public void testWhenVinylInfoIsOkReturn201() {
		populateRequestJSON("123456", "Linkin Park", "120", "50");

		given().auth().basic(USERNAME, PASSWORD).port(PORT).contentType(ContentType.JSON).body(requestJSON).when()
				.post("/api/vinyls").then().assertThat().statusCode(201);
	}

	@Test
	public void testWhenCostIsLessThanZeroReturn400() {
		populateRequestJSON("123456", "Linkin Park", "-120", "50");

		given().auth().basic(USERNAME, PASSWORD).port(PORT).contentType(ContentType.JSON).body(requestJSON).when()
				.post("/api/vinyls").then().assertThat().statusCode(400);
	}

	@Test
	public void testWhenStockIsLessThanZeroReturn400CreateVinyl() {
		populateRequestJSON("123456", "Linkin Park", "120", "-50");

		given().auth().basic(USERNAME, PASSWORD).port(PORT).contentType(ContentType.JSON).body(requestJSON).when()
				.post("/api/vinyls").then().assertThat().statusCode(400);
	}

	@Test
	public void testWhenCostIsNonNumericReturn400() {
		populateRequestJSON("123456", "Linkin Park", "asdf", "50");

		given().auth().basic(USERNAME, PASSWORD).port(PORT).contentType(ContentType.JSON).body(requestJSON).when()
				.post("/api/vinyls").then().assertThat().statusCode(400);
	}

	@Test
	public void testWhenStockIsNonNumericReturn400() {
		populateRequestJSON("123456", "Linkin Park", "120", "asdf");

		given().auth().basic(USERNAME, PASSWORD).port(PORT).contentType(ContentType.JSON).body(requestJSON).when()
				.post("/api/vinyls").then().assertThat().statusCode(400);
	}

	@Test
	public void testWhenCustomerCreatesVinylReturn401() {
		populateRequestJSON("12345", "Linkin Park", "120", "50");

		given().auth().basic(USERNAME, PASSWORD).port(PORT).contentType(ContentType.JSON).body(requestJSON).when()
				.post("/api/vinyls").then().assertThat().statusCode(401);
	}

	@Test
	public void testWhenTokenIsInvalidReturn400() {
		populateRequestJSON("000000", "Linkin Park", "120", "50");

		given().auth().basic(USERNAME, PASSWORD).port(PORT).contentType(ContentType.JSON).body(requestJSON).when()
				.post("/api/vinyls").then().assertThat().statusCode(400);
	}

	@Test
	public void testWhenNameIsNullReturn400() {
		populateRequestJSON("123456", "", "120", "50");

		given().auth().basic(USERNAME, PASSWORD).port(PORT).contentType(ContentType.JSON).body(requestJSON).when()
				.post("/api/vinyls").then().assertThat().statusCode(400);
	}
	
	@Test
	public void testWhenCostIsNullReturn400() {
		populateRequestJSON("123456", "Linkin Park", "", "50");

		given().auth().basic(USERNAME, PASSWORD).port(PORT).contentType(ContentType.JSON).body(requestJSON).when()
				.post("/api/vinyls").then().assertThat().statusCode(400);
	}

	
	@Test
	public void testWhenStockIsNullReturn400() {
		populateRequestJSON("123456", "Linkin Park", "120", "");

		given().auth().basic(USERNAME, PASSWORD).port(PORT).contentType(ContentType.JSON).body(requestJSON).when()
				.post("/api/vinyls").then().assertThat().statusCode(400);
	}

	private void populateRequestJSON(String token, String name, String cost, String stock) {
		requestJSON.put("token", token);
		requestJSON.put("name", name);
		requestJSON.put("cost", cost);
		requestJSON.put("stock", stock);
	}

}
