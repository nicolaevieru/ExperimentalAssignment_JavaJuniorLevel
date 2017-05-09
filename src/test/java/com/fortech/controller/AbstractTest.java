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

import io.restassured.http.Header;
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
	protected Header requestHeader = new Header("token", "123456");
	protected Map<String, String> requestJson;
	
	protected RequestSpecification sendGetRequest() {
		return given().auth().basic(USERNAME, PASSWORD).port(PORT).header(requestHeader);
	}
}
