package com.fortech.controller;

import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.fortech.model.Account;
import com.fortech.model.dto.AccountCreateDto;
import com.fortech.model.dto.AccountDeleteDto;
import com.fortech.model.dto.AccountLoginDto;
import com.fortech.service.AccountService;

@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class AccountControllerIT {

	private static final String URL = "http://localhost:9000/api/users";

	private AccountCreateDto testAccount;
	private AccountLoginDto testLogin;
	private AccountDeleteDto deleteCredentials;

	@Autowired
	private AccountService accountService;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void testPostWithValidDataReturnsCreated() {

		assertEquals(HttpStatus.CREATED, sendRequest().getStatusCode());
	}

	@Test
	public void testPostWithInvalidEmailReturns400() {

		testAccount.setEmail("emailwithnoat");
		assertEquals(HttpStatus.BAD_REQUEST, sendRequest().getStatusCode());
	}

	@Test
	public void testPostWithInvalidPasswordlReturns400() {

		testAccount.setPassword("password");
		assertEquals(HttpStatus.BAD_REQUEST, sendRequest().getStatusCode());
	}

	@Test
	public void testPostWithInvalidNameReturns400() {

		testAccount.setFirstName("J");
		assertEquals(HttpStatus.BAD_REQUEST, sendRequest().getStatusCode());
	}

	@Test
	public void testDeleteWithValidTokenAndIDReturns204() {
		
		AccountLoginDto emailAndPass = new AccountLoginDto();
		emailAndPass.setEmail("johndoelogin@delete.com");
		emailAndPass.setPassword("Pas$word4login");
		
		Account testAccount = new Account(createAccount("Test", "Delete", "johndoelogin@delete.com", "Pas$word4login"));
		testAccount = accountService.save(testAccount);
		restTemplate.withBasicAuth("admin", "secret").postForEntity(URL, emailAndPass, AccountLoginDto.class);

		deleteCredentials = new AccountDeleteDto();
		deleteCredentials.setToken(String.valueOf(Math.abs(emailAndPass.hashCode() + 41)));
		deleteCredentials.setPassword("Pas$word4login");
		
		assertEquals(HttpStatus.NO_CONTENT, sendDeleteRequest("/" + testAccount.getId().toString()).getStatusCode());
	}

	@Test
	public void testLoginWithValidCredentialsReturns200() {
		AccountCreateDto testAccountForLogin = createAccount("Ass", "asas", "johndoelogin@example.com",
				"Pas$word4login");
		restTemplate.withBasicAuth("admin", "secret").postForEntity(URL, testAccountForLogin, AccountCreateDto.class);
		assertEquals(HttpStatus.OK, sendRequest("/login").getStatusCode());
	}

	@Test
	public void testLoginWithInvalidPasswordReturns400() {
		testLogin.setPassword("notThePassword");
		assertEquals(HttpStatus.BAD_REQUEST, sendRequest("/login").getStatusCode());
	}

	@Test
	public void testLoginWithInvalidEmailReturns400() {
		testLogin.setEmail("notTheEmail");
		assertEquals(HttpStatus.BAD_REQUEST, sendRequest("/login").getStatusCode());
	}

	public AccountCreateDto createAccount(String firstName, String lastName, String email, String password) {

		AccountCreateDto testAccount = new AccountCreateDto();
		testAccount.setFirstName(firstName);
		testAccount.setLastName(lastName);
		testAccount.setEmail(email);
		testAccount.setPassword(password);
		return testAccount;
	}

	private ResponseEntity<AccountLoginDto> sendRequest(String url) {
		return restTemplate.withBasicAuth("admin", "secret").postForEntity(URL + url, testLogin, AccountLoginDto.class);
	}

	private ResponseEntity<AccountCreateDto> sendRequest() {

		return restTemplate.withBasicAuth("admin", "secret").postForEntity(URL, testAccount, AccountCreateDto.class);
	}

	private ResponseEntity<AccountDeleteDto> sendDeleteRequest(String url) {
		URI uri = null;
		try {
			uri = new URI(URL + url);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return restTemplate.withBasicAuth("admin", "secret").exchange(
				new RequestEntity<AccountDeleteDto>(deleteCredentials, HttpMethod.DELETE, uri), AccountDeleteDto.class);
	}

	@Before
	public void resetData() {
		testLogin = new AccountLoginDto();
		testLogin.setEmail("johndoelogin@example.com");
		testLogin.setPassword("Pas$word4login");
		testAccount = createAccount("John", "Doe", "johndoe@example.com", "Pas$wo5rdaaaa");

	}

}
