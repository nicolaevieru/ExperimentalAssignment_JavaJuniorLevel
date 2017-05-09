package com.fortech.controller.Account;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import com.fortech.controller.AbstractTest;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class CreateAccountIT extends AbstractTest {
	
	private Map<String, String> requestJson;
	
	@Test
	public void testPostWithValidDataReturnsCreated() {

		given().auth().basic(USERNAME, PASSWORD).port(PORT).contentType(ContentType.JSON).body(requestJson).when()
				.post("/api/users").then().assertThat().statusCode(HttpStatus.CREATED.value());
	}
	
	@Test
	public void testPostWithTooShortFirstNameReturnsBadRequest() {
		requestJson.put("firstName", "");
		given().auth().basic(USERNAME, PASSWORD).port(PORT).contentType(ContentType.JSON).body(requestJson).when()
		.post("/api/users").then().assertThat().statusCode(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	public void testPostWithTooShortLastNameReturnsBadRequest() {
		requestJson.put("lastName", "");
		given().auth().basic(USERNAME, PASSWORD).port(PORT).contentType(ContentType.JSON).body(requestJson).when()
		.post("/api/users").then().assertThat().statusCode(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	public void testPostWithInvalidPasswordlReturnsBadRequest() {
		requestJson.put("password", "weakpassword");
		given().auth().basic(USERNAME, PASSWORD).port(PORT).contentType(ContentType.JSON).body(requestJson).when()
		.post("/api/users").then().assertThat().statusCode(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	public void testPostWithEmailWithoutAtSignReturnsBadRequest() {
		requestJson.put("email", "emailwithoutat");
		given().auth().basic(USERNAME, PASSWORD).port(PORT).contentType(ContentType.JSON).body(requestJson).when()
		.post("/api/users").then().assertThat().statusCode(HttpStatus.BAD_REQUEST.value());
	}
	
	public void testPostWithNullFirstNameReturnsBadRequest() {
		requestJson.put("firstName", null);
		given().auth().basic(USERNAME, PASSWORD).port(PORT).contentType(ContentType.JSON).body(requestJson).when()
		.post("/api/users").then().assertThat().statusCode(HttpStatus.BAD_REQUEST.value());
	}
	
	public void testPostWithNullLastNameReturnsBadRequest() {
		requestJson.put("lastName", null);
		given().auth().basic(USERNAME, PASSWORD).port(PORT).contentType(ContentType.JSON).body(requestJson).when()
		.post("/api/users").then().assertThat().statusCode(HttpStatus.BAD_REQUEST.value());
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
