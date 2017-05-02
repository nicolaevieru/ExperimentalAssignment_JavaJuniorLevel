package com.fortech.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.fortech.model.Account;
import com.fortech.model.Token;
import com.fortech.model.dto.AccountCreateDto;
import com.fortech.model.dto.AccountLoginDto;
import com.fortech.model.dto.AddVinylToCartDto;
import com.fortech.model.dto.VinylCreateDto;

@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class VinylServiceIT {

	@Autowired
	private TestRestTemplate restTemplate;

	
	private AccountCreateDto testAccount;
	private VinylCreateDto testCreateVinyl;
	private AddVinylToCartDto testAddVinylToCart;

	@Before
	public void resetData() {
		createTestVinyl("Pink Floyd", 233.0, 123);
	}

	@Test
	public void testWhenVinylInfoIsOkCreateVinyl() {
		assertEquals(HttpStatus.CREATED, sendRequestCreateVinyl("/api/vinyls").getStatusCode());

	}
	
	@Test
	public void testWhenCostIsLessThanZeroReturn400CreateVinyl(){
		testCreateVinyl.setCost(-233.0);
		assertEquals(HttpStatus.BAD_REQUEST, sendRequestCreateVinyl("/api/vinyls").getStatusCode());
	}
	
	@Test
	public void testStockIsLessThanZeroReturn400CreateVinyl(){
		testCreateVinyl.setStock(-123);
		assertEquals(HttpStatus.BAD_REQUEST, sendRequestCreateVinyl("/api/vinyls").getStatusCode());
	}
	
	@Test
	public void testWhenInfoIsOkAddVinylToCart(){
		createAccountAndLogin("John", "Oliver", "lol@lol.com","Pas$wo5rdaaaa");
		
		Token token = createAccountToken();
				
		createTestAddVinylToCart(3, 3, token);
		
		assertEquals(HttpStatus.OK, sendRequestAddVinylToCart("/api/vinyls/"+ testAddVinylToCart.getVinylId() +"/cart").getStatusCode());
		
	}

	private VinylCreateDto createTestVinyl(String name, double cost, int stock) {
		testCreateVinyl = new VinylCreateDto();

		testCreateVinyl.setName(name);
		testCreateVinyl.setCost(cost);
		testCreateVinyl.setStock(stock);

		return testCreateVinyl;
	}
	
	private AddVinylToCartDto createTestAddVinylToCart(Integer vinylId,int quantity,Token token){
		return testAddVinylToCart = new AddVinylToCartDto(vinylId, quantity, token);
	}
	
	private Token createAccountToken(){
		Token token = new Token();
		
		Account account = new Account(this.testAccount);
		
		token.setAccount(account);
		token.setHash("dfa3ae42f4957b983caa00194415c1a5082a294993d70d0020a6f0a89f50128d5ccec53e9daaca06");
		
		return token;
	}
	
	private AccountCreateDto createAccountAndLogin(String firstName, String lastName, String email, String password) {
	    testAccount = new AccountCreateDto();
		
		testAccount.setFirstName(firstName);
		testAccount.setLastName(lastName);
		testAccount.setEmail(email);
		testAccount.setPassword(password);
		
		AccountLoginDto testLogin = new AccountLoginDto();
		testLogin.setEmail("lol@lol.com");
		testLogin.setPassword("Pas$wo5rdaaaa");

		
		restTemplate.withBasicAuth("admin", "secret").postForEntity("/api/users", testAccount, AccountCreateDto.class);
		restTemplate.withBasicAuth("admin", "secret").postForEntity("/api/users/login", testLogin, AccountLoginDto.class);
		
		return testAccount;
	}
	
	private ResponseEntity<VinylCreateDto> sendRequestCreateVinyl(String url) {
		return restTemplate.withBasicAuth("admin", "secret").postForEntity(url, testCreateVinyl, VinylCreateDto.class);
	}
	
	private ResponseEntity<AddVinylToCartDto> sendRequestAddVinylToCart(String url) {
		return restTemplate.withBasicAuth("admin", "secret").postForEntity(url, testAddVinylToCart, AddVinylToCartDto.class);
	}

}
