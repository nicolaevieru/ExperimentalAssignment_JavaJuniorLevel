package com.fortech.controller;

import static io.restassured.RestAssured.given;

/*import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;*/

/*import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;*/

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import io.restassured.http.ContentType;

public class VinylCreateIT extends AbstractTest {

	@Test
	public void testWhenVinylInfoIsOkCreateVinyl() {
		Map<String, String> map = new HashMap<>();
		map.put("token", "123456");
		map.put("name", "Linkin Park");
		map.put("cost", "aa");
		map.put("stock", "12");

		given().auth().basic("admin", "secret").port(9000).contentType(ContentType.JSON).body(map).when()
				.post("/api/vinyls").then().assertThat().statusCode(201);
	}

}
