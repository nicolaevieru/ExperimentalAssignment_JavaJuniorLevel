package com.fortech.controller;

import org.junit.Before;
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

@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@AutoConfigureTestDatabase
public class VinylServiceIT {

	@Autowired
	private TestRestTemplate restTemplate;


	@Before
	public void setup() {
		
	}

	@Test
	public void testWhenVinylInfoIsOkCreateVinyl() {

	}
	
	@Test
	public void testWhenCostIsLessThanZeroReturn400CreateVinyl(){
	}
	
	@Test
	public void testStockIsLessThanZeroReturn400CreateVinyl(){
	}
	
	@Test
	public void testWhenInfoIsOkAddVinylToCart(){		
	}
	
	@Test
	public void testGetRequestToVinylsReturns200() {
	}
 	
}
