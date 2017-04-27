package com.fortech.controller;

import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.core.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.fortech.model.Account;
import com.fortech.model.dto.AccountCreateDto;
import com.fortech.model.dto.AccountLoginDto;
import com.fortech.service.AccountService;
import com.fortech.service.TokenService;

@RestController
@RequestMapping(value = "api")
public class AccountController {
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private TokenService tokenService;
	
	@RequestMapping(value = "users", method = RequestMethod.POST)
	public ResponseEntity<Account> createAccount(@RequestBody AccountCreateDto request) {
		accountService.save(request);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "users/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<Map<String, String>> loginAccount(@RequestBody AccountLoginDto request) {
		Map<String, String> json = new HashMap<String, String>();
		json.put("token", tokenService.save(request).getHash());
		return new ResponseEntity<>(json, HttpStatus.OK);
	}
	
}
