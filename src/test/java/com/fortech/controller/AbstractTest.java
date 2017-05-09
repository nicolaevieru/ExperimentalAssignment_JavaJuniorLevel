package com.fortech.controller;


import static io.restassured.RestAssured.given;

import java.util.Map;

import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@AutoConfigureTestDatabase
public abstract class AbstractTest {
	protected static final int PORT = 9000;
	protected static final String USERNAME = "admin";
	protected static final String PASSWORD = "secret";
	
	protected Response sendPostRequest(Map<String,String> requestJSON,String URL){
		RequestSpecification request = given().auth().basic(USERNAME, PASSWORD).port(PORT).contentType(ContentType.JSON);
		
		return request.body(requestJSON).post(URL);
	}
}
