package com.fortech.controller;

import static io.restassured.RestAssured.given;

/*import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;*/

/*import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;*/

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@AutoConfigureTestDatabase
public class TestTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void testMockito() {
		Map<String, String> map = new HashMap<>();
		map.put("token", "123456");
		map.put("name", "ion");
		map.put("cost", "aa");
		map.put("stock", "12");

		given().auth().basic("admin", "secret").port(9000).contentType(ContentType.JSON).body(map).when()
				.post("/api/vinyls").then().assertThat().statusCode(201);
	}

}
