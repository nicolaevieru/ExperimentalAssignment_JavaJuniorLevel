package com.fortech.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.fortech.model.Vinyl;
import com.fortech.repository.VinylRepository;
import com.fortech.service.VinylService;

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

	@org.junit.Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		
		this.mockMvc = MockMvcBuilders.standaloneSetup(vinylController).build();
	}
	
	@Test
	public void test() {
		Vinyl vinyl = new Vinyl();
		vinyl.setCost(100.0);
		vinyl.setName("bbbb");
		vinyl.setStock(100);
		vinylRepo.save(vinyl);
	}
	
	@Test
	public void testMockito() throws Exception{
		
		
		this.mockMvc.perform(post("/api/vinyls")
					.param("token", "513879925")
					.param("name","ion")
					.param("cost", "12")
					.param("stock", "100"))
					.andExpect(status().isCreated());
	}

}
