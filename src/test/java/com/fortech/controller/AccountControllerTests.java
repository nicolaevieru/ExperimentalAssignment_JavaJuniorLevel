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

import com.fortech.model.dto.AccountCreateDto;

@RunWith(SpringRunner.class)
@TestPropertySource(locations="classpath:application-test.properties")
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class AccountControllerTests {
	
	private static final String URL = "http://localhost:9000/api/users";
	private ResponseEntity<AccountCreateDto> response;
	private static AccountCreateDto testAccount;
	
	@Autowired
    private TestRestTemplate restTemplate;
	
	
	@Test
	public void testPostWithValidDataReturnsCreated() {
		
		testAccount = createAccount("John", "Doe","johndoe@example.com","Pas$wo5rdaaaa");
		response = restTemplate.withBasicAuth("admin", "secret").postForEntity(URL, testAccount, AccountCreateDto.class);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		
	}
	@Test
	public void testPostWithInvalidEmailReturns400() {
		
		testAccount = createAccount("John", "Doe","johndoesexample.com","Pas$wo5rdaaaa");
		response = restTemplate.withBasicAuth("admin", "secret").postForEntity(URL, testAccount, AccountCreateDto.class);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		
	}
	
	@Test
	public void testPostWithInvalidPasswordlReturns400() {
		
		testAccount = createAccount("John", "Doe","johndoe@example.com","password");
		response = restTemplate.withBasicAuth("admin", "secret").postForEntity(URL, testAccount, AccountCreateDto.class);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		
	}
	
	@Test
	public void testPostWithInvalidNameReturns400() {
		
		testAccount = createAccount("John", "D","johndoe@example.com","Pas$wo5rdaaaa");
		response = restTemplate.withBasicAuth("admin", "secret").postForEntity(URL, testAccount, AccountCreateDto.class);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		
	}
	
	
	public static AccountCreateDto createAccount(String firstName, String lastName, String email, String password) {
		AccountCreateDto testAccount = new AccountCreateDto();
		testAccount.setFirstName(firstName);
		testAccount.setLastName(lastName);
		testAccount.setEmail(email);
		testAccount.setPassword(password);
		return testAccount;
		
	}
	
	@Before
	public void resetAccount() {
		testAccount = new AccountCreateDto();
	}

}
