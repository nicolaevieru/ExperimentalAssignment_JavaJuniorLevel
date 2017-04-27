package com.fortech.controller;


import static org.junit.Assert.assertEquals;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.fortech.model.Vinyl;
import com.fortech.model.dto.VinylCreateDto;
import com.fortech.repository.VinylRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class VinylServiceIT {

	@Autowired
	private TestRestTemplate restTemplate;
		
	@Autowired
	VinylRepository repository;
	
	@Test
	@Transactional
	@Rollback(true)
	public void testWhenVinylInfoIsOkCreateVinyl(){
		VinylCreateDto testVinyl = createTestVinyl("Pink Floyd",233.0,123);
						
		ResponseEntity<VinylCreateDto> responseEntity = restTemplate.postForEntity("/api/vinyls", testVinyl, VinylCreateDto.class);
				
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		
	}
	
	private VinylCreateDto createTestVinyl(String name,double cost,int stock){
		VinylCreateDto testVinyl = new VinylCreateDto();
		
		testVinyl.setName(name);
		testVinyl.setCost(cost);
		testVinyl.setStock(stock);
		
		return testVinyl;
	}
}






