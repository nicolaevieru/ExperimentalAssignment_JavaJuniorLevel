package com.fortech.controller.Account;
import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.fortech.service.AccountService;

import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@AutoConfigureTestDatabase
public class AccountControllerIT {

	private static final int PORT = 9000;
	
	@Autowired
	private AccountService accountService;

	

	

	

	

	

	
	
	

	@Before
	public void populateDatabase() {
		Map<String, String> map = new HashMap<>();
		map.put("token", "123456");
		map.put("name", "ion");
		map.put("cost", "aa");
		map.put("stock", "12");
		

		given().auth().basic("admin", "secret").port(PORT).contentType(ContentType.JSON).body(map).when()
				.post("/api/vinyls").then().assertThat().statusCode(201);
	}

}
