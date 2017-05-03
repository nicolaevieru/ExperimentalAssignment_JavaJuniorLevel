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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.fortech.model.Account;
import com.fortech.model.AccountType;
import com.fortech.model.AccountTypeEnum;
import com.fortech.model.Token;
import com.fortech.model.dto.AccountCreateDto;
import com.fortech.model.dto.AccountDeleteDto;
import com.fortech.model.dto.AccountLoginDto;
import com.fortech.model.dto.CustomerListDto;
import com.fortech.service.AccountService;
import com.fortech.service.CartService;
import com.fortech.service.TokenService;

@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")

public class AccountControllerIT {

	private static final String URL = "http://localhost:9000/api/users";

	private AccountCreateDto testAccount;
	private AccountLoginDto testLogin;
	private AccountDeleteDto deleteCredentials;

	@Autowired
	private AccountService accountService;

	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private CartService cartService;

	@Test
	public void testPostWithValidDataReturnsCreated() {

		assertEquals(HttpStatus.CREATED, sendCreateRequest().getStatusCode());
	}

	@Test
	public void testPostWithInvalidEmailReturns400() {

		testAccount.setEmail("emailwithnoat");
		assertEquals(HttpStatus.BAD_REQUEST, sendCreateRequest().getStatusCode());
	}

	@Test
	public void testPostWithInvalidPasswordlReturns400() {

		testAccount.setPassword("password");
		assertEquals(HttpStatus.BAD_REQUEST, sendCreateRequest().getStatusCode());
	}

	@Test
	public void testPostWithInvalidNameReturns400() {

		testAccount.setFirstName("J");
		assertEquals(HttpStatus.BAD_REQUEST, sendCreateRequest().getStatusCode());
	}

	@Test
	public void testDeleteWithValidTokenAndIdReturns204() {
		
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
		sendCreateRequest();
		assertEquals(HttpStatus.OK, sendLoginRequest().getStatusCode());
	}

	@Test
	public void testLoginWithInvalidPasswordReturns400() {
		testLogin.setPassword("notThePassword");
		assertEquals(HttpStatus.BAD_REQUEST, sendLoginRequest().getStatusCode());
	}

	@Test
	public void testLoginWithInvalidEmailReturns400() {
		testLogin.setEmail("notTheEmail");
		assertEquals(HttpStatus.BAD_REQUEST, sendLoginRequest().getStatusCode());
	}
	
	@Test
	public void testGetCustomersWithValidTokenReturns200() {
		
		Account managerAccount = new Account(createAccount("testMan", "testMan", "testman@example.com", "password"));
		managerAccount.setAccountType(new AccountType(AccountTypeEnum.STORE_MANAGER));
		managerAccount = accountService.save(managerAccount);
		restTemplate.withBasicAuth("admin", "secret").postForEntity(URL+"/login", new AccountLoginDto(managerAccount.getEmail(),"password"), AccountCreateDto.class);
		Token token = tokenService.findByAccountId(managerAccount.getId());
	
		HttpHeaders headers = new HttpHeaders();
		headers.set("token", token.getHash());
		
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
		assertEquals(HttpStatus.OK, this.restTemplate.withBasicAuth("admin", "secret").getRestTemplate().exchange("http://localhost:9000/api/customers", HttpMethod.GET,entity, CustomerListDto.class).getStatusCode());
	}
	
	
	@Test
	public void testGetCustomersWithCustomerTokenReturns403() {
		
		restTemplate.withBasicAuth("admin", "secret").postForEntity(URL+"/login", new AccountLoginDto(testLogin.getEmail(),testLogin.getPassword()), AccountCreateDto.class);
		sendCreateRequest();
		sendLoginRequest();
		Token token = tokenService.findByAccountId(accountService.findByEmail(testLogin.getEmail()).getId());
		HttpHeaders headers = new HttpHeaders();
		headers.set("token", token.getHash());
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
		assertEquals(HttpStatus.UNAUTHORIZED, this.restTemplate.withBasicAuth("admin", "secret").getRestTemplate().exchange("http://localhost:9000/api/customers", HttpMethod.GET,entity, CustomerListDto.class).getStatusCode());
	}


	public AccountCreateDto createAccount(String firstName, String lastName, String email, String password) {

		AccountCreateDto testAccount = new AccountCreateDto();
		testAccount.setFirstName(firstName);
		testAccount.setLastName(lastName);
		testAccount.setEmail(email);
		testAccount.setPassword(password);
		return testAccount;
	}

	private ResponseEntity<AccountLoginDto> sendLoginRequest() {
		return restTemplate.withBasicAuth("admin", "secret").postForEntity(URL + "/login", testLogin, AccountLoginDto.class);
	}

	private ResponseEntity<AccountCreateDto> sendCreateRequest() {

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
		testLogin.setEmail("johndoe@example.com");
		testLogin.setPassword("Pas$wo5rdaaaa");
		testAccount = createAccount("John", "Doe", "johndoe@example.com", "Pas$wo5rdaaaa");
		tokenService.deleteAll();
		cartService.deleteAll();
		accountService.deleteAll();

	}

}
