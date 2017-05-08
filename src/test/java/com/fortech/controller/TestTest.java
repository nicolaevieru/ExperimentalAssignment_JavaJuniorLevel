package com.fortech.controller;

/*import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;*/

/*import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;*/

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fortech.repository.VinylRepository;
import com.fortech.service.VinylService;

import io.restassured.http.ContentType;

@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@AutoConfigureTestDatabase
public class TestTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Mock
	private VinylService vinylService;

	@InjectMocks
	private VinylController vinylController;

	private MockMvc mockMvc;

	@Autowired
	VinylRepository vinylRepo;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);

		this.mockMvc = MockMvcBuilders.standaloneSetup(vinylController).build();
	}

	/*
	 * @Test public void test() { Vinyl vinyl = new Vinyl();
	 * vinyl.setCost(100.0); vinyl.setName("bbbb"); vinyl.setStock(100);
	 * vinylRepo.save(vinyl); }
	 */
	@Test
	 public void testMockito() throws Exception{
		Map<String, String> map = new HashMap<>();
		map.put("token", "11111");
		map.put("name","ion");
		map.put("cost", "12");
		map .put("stock", "12");
		
		/*get("api/vinyls/");*/
		String json = new ObjectMapper().writeValueAsString(map);
		
		given().auth().basic("admin", "secret").port(9000).
		contentType(ContentType.JSON).
		body(map).when().post("/api/vinyls").then().
		assertThat().statusCode(201);
	/*	get("/vinyls").then().body("lotto.lottoid", equalTo(5));
	  
		  this.mockMvc.perform(post("/api/vinyls").contentType(MediaType.APPLICATION_JSON).content(json))
		     .andExpect(status().isCreated());*/
	 }

}
