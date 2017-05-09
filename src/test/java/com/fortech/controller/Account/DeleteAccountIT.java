package com.fortech.controller.Account;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

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
import com.fortech.model.Token;
import com.fortech.repository.AccountRepository;
import com.fortech.repository.TokenRepository;
import com.fortech.service.AccountService;

public class DeleteAccountIT extends AbstractTest {

	private static final String EXISTING_CUSTOMER_URL = "api/users/1001";
	private static final String LOGIN_PASSWORD = "P@ssword123";
	private static final String CUSTOMER_TOKEN = "customerToken";
	private static final String MANAGER_TOKEN = "token";

	private String newCustomerURL;

	@Autowired
	private AccountService accountService;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	TokenRepository tokenRepository;

	private Account managerAccount;
	private Account customerAccount;

	private Token customerToken;
	private Token managerToken;

	@Test
	public void testRequestWithManagerValidTokenReturnsNoContent() {
		setRequestJson(MANAGER_TOKEN);
		sendDeleteRequest(EXISTING_CUSTOMER_URL).then().assertThat().statusCode(HttpStatus.NO_CONTENT.value());

	}

	@Test
	public void testRequestWithCustomerValidTokenOnSameAccountReturnsNoContent() {
		setRequestJson(CUSTOMER_TOKEN);
		sendDeleteRequest(newCustomerURL).then().assertThat().statusCode(HttpStatus.NO_CONTENT.value());

	}

	@Test
	public void testRequestWithCustomerValidTokenDifferentAccountReturnsForbidden() {
		setRequestJson(CUSTOMER_TOKEN);
		sendDeleteRequest(EXISTING_CUSTOMER_URL).then().assertThat().statusCode(HttpStatus.FORBIDDEN.value());

	}

	@Test
	public void testRequestWithCustomerValidTokenOnSameAccountSetsAccountStatusToDeleted() {
		setRequestJson(CUSTOMER_TOKEN);
		sendDeleteRequest(newCustomerURL);
		assertThat(accountRepository.findOne(customerAccount.getId()).getAccountStatus().getStatus(),
				is(AccountStatusEnum.DELETED));
	}
	

	@Before
	public void resetData() {
		managerAccount = new Account();
		managerAccount.setAccountStatus(new AccountStatus(AccountStatusEnum.ACTIVE));
		managerAccount.setAccountType(new AccountType(AccountTypeEnum.STORE_MANAGER));
		managerAccount.setEmail("man@email.com");
		managerAccount.setFirstName("Man");
		managerAccount.setLastName("Man");
		managerAccount.setPasswordHash(LOGIN_PASSWORD);
		managerAccount = accountService.save(managerAccount);

		managerToken = new Token();
		managerToken.setAccount(managerAccount);
		managerToken.setHash(MANAGER_TOKEN);
		managerToken = tokenRepository.save(managerToken);

		customerAccount = new Account();
		customerAccount.setAccountStatus(new AccountStatus(AccountStatusEnum.ACTIVE));
		customerAccount.setAccountType(new AccountType(AccountTypeEnum.CUSTOMER));
		customerAccount.setEmail("newcust@email.com");
		customerAccount.setFirstName("New");
		customerAccount.setLastName("New");
		customerAccount.setPasswordHash(LOGIN_PASSWORD);
		customerAccount = accountService.save(customerAccount);
		newCustomerURL = "api/users/" + customerAccount.getId();

		customerToken = new Token();
		customerToken.setAccount(customerAccount);
		customerToken.setHash(CUSTOMER_TOKEN);
		customerToken = tokenRepository.save(customerToken);
	}

	private void setRequestJson(String token) {
		requestJson = new HashMap<>();
		requestJson.put("token", token);
		requestJson.put("password", LOGIN_PASSWORD);
	}

	@After
	public void destroyData() {
		tokenRepository.delete(Arrays.asList(managerToken, customerToken));
		accountRepository.delete(Arrays.asList(managerAccount, customerAccount));
	}

}
