package com.fortech.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.fortech.model.Vinyl;
import com.fortech.repository.VinylRepository;

@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@AutoConfigureTestDatabase
public class TestTest {
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	VinylRepository vinylRepo;

	@Test
	public void test() {
		Vinyl vinyl = new Vinyl();
		vinyl.setCost(100.0);
		vinyl.setName("bbbb");
		vinyl.setStock(100);
		vinylRepo.save(vinyl);
	}

}
