package com.fortech.controller.Account;

import java.util.Arrays;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.fortech.controller.AbstractTest;
import com.fortech.model.Account;
import com.fortech.model.AccountStatus;
import com.fortech.model.AccountStatusEnum;
import com.fortech.model.AccountType;
import com.fortech.model.AccountTypeEnum;
import com.fortech.repository.AccountRepository;
import com.fortech.repository.TokenRepository;
import com.fortech.service.AccountService;

public class LoginIT extends AbstractTest {
	
	private static final String URL = "/api/users/login";
	
	private Account testAccount;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private TokenRepository tokenRepository;
	
	
	@Test
	public void testLoginWithValidCredentialsReturnsOK() {
		sendPostRequest(URL).then().assertThat().statusCode(HttpStatus.OK.value());
	}

	@Test
	public void testLoginWithInvalidPasswordReturnsBadRequest() {
		requestJson.put("password", "Password");
		sendPostRequest(URL).then().assertThat().statusCode(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	public void testLoginWithInvalidEmailReturnsBadRequest() {
		requestJson.put("email", "nottheemail@email.com");
		sendPostRequest(URL).then().assertThat().statusCode(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	public void testLoginWithNullEmailReturnsBadRequest() {
		requestJson.put("email", null);
		sendPostRequest(URL).then().assertThat().statusCode(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	public void testLoginWithNullPasswordBadRequest() {
		requestJson.put("password", null);
		sendPostRequest(URL).then().assertThat().statusCode(HttpStatus.BAD_REQUEST.value());
	}
	
	@Before
	public void setup() {
		testAccount = new Account();
		testAccount.setAccountStatus(new AccountStatus(AccountStatusEnum.ACTIVE));
		testAccount.setAccountType(new AccountType(AccountTypeEnum.CUSTOMER));
		testAccount.setEmail("testemaila@email.com");
		testAccount.setPasswordHash("P@ssword123");
		testAccount.setFirstName("Firstname");
		testAccount.setLastName("Lastname");
		testAccount = accountService.save(testAccount);
		
		requestJson = new HashMap<>();
		requestJson.put("email", "testemaila@email.com");
		requestJson.put("password", "P@ssword123");
	}
	
	@After
	public void destroy() {
		tokenRepository.deleteTokensForOtherAccounts(Arrays.asList(1000, 1001));
		accountRepository.delete(testAccount);
	}

}
