package com.fortech.controller.Account;

import org.junit.Before;
import org.junit.Test;

import com.fortech.controller.AbstractTest;

import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

public class CreateAccountIT extends AbstractTest {
	
	private Map<String, String> requestJson;
	
	@Test
	public void testPostWithValidDataReturns201() {

		given().auth().basic(USERNAME, PASSWORD).port(PORT).contentType(ContentType.JSON).body(requestJson).when()
				.post("/api/users").then().assertThat().statusCode(201);
	}
	
	@Test
	public void testPostWithTooShortFirstNameReturns400() {
		requestJson.put("firstName", "");
		given().auth().basic(USERNAME, PASSWORD).port(PORT).contentType(ContentType.JSON).body(requestJson).when()
		.post("/api/users").then().assertThat().statusCode(400);
	}
	
	@Test
	public void testPostWithTooShortLastNameReturns400() {
		requestJson.put("lastName", "");
		given().auth().basic(USERNAME, PASSWORD).port(PORT).contentType(ContentType.JSON).body(requestJson).when()
		.post("/api/users").then().assertThat().statusCode(400);
	}
	
	@Test
	public void testPostWithInvalidPasswordlReturns400() {
		requestJson.put("password", "weakpassword");
		given().auth().basic(USERNAME, PASSWORD).port(PORT).contentType(ContentType.JSON).body(requestJson).when()
		.post("/api/users").then().assertThat().statusCode(400);
	}
	
	@Test
	public void testPostWithEmailWithoutAtSignReturns400() {
		requestJson.put("email", "emailwithoutat");
		given().auth().basic(USERNAME, PASSWORD).port(PORT).contentType(ContentType.JSON).body(requestJson).when()
		.post("/api/users").then().assertThat().statusCode(400);
	}
	
	public void testPostWithNullFirstNameReturns400() {
		requestJson.put("firstName", null);
		given().auth().basic(USERNAME, PASSWORD).port(PORT).contentType(ContentType.JSON).body(requestJson).when()
		.post("/api/users").then().assertThat().statusCode(400);
	}
	
	public void testPostWithNullLastNameReturns400() {
		requestJson.put("lastName", null);
		given().auth().basic(USERNAME, PASSWORD).port(PORT).contentType(ContentType.JSON).body(requestJson).when()
		.post("/api/users").then().assertThat().statusCode(400);
	}
	
	@Before
	public void setup() {
		requestJson = new HashMap<>();
		requestJson.put("firstName", "TestFirst"); 
		requestJson.put("lastName", "TestLast");
		requestJson.put("email", "test@email.com");
		requestJson.put("password", "P@ssword123");
	}
	
	
}
