package com.fortech.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fortech.model.dto.AccountCreateDto;
import com.fortech.model.dto.AccountDeleteDto;
import com.fortech.model.dto.AccountLoginDto;
import com.fortech.model.dto.CartDetailsDto;
import com.fortech.model.dto.CustomerListDto;
import com.fortech.model.dto.OrderDto;
import com.fortech.service.AccountService;
import com.fortech.service.ItemService;
import com.fortech.service.TokenService;

@RestController
@RequestMapping(value = "api")
public class AccountController {

	@Autowired
	private AccountService accountService;

	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private ItemService itemService;

	@RequestMapping(value = "users", method = RequestMethod.POST)
	public ResponseEntity<AccountCreateDto> createAccount(@RequestBody AccountCreateDto request) {
		accountService.save(request);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@RequestMapping(value = "users/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON)
	public ResponseEntity<Map<String, String>> loginAccount(@RequestBody AccountLoginDto request) {
		Map<String, String> json = new HashMap<String, String>();
		json.put("token", tokenService.save(request).getHash());
		json.put("account id", accountService.findByEmail(request.getEmail()).getId().toString());
		return new ResponseEntity<>(json, HttpStatus.OK);
	}

	@RequestMapping(value = "users/{id}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON)
	public ResponseEntity<AccountDeleteDto> deleteAccount(@PathVariable("id") Integer id,
			@RequestBody AccountDeleteDto credentials) {
		accountService.delete(id, credentials);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "users/{userId}/cart", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON)
	public ResponseEntity<CartDetailsDto> getCartDetails(@PathVariable Integer userId,@RequestHeader HttpHeaders requestHeader) {

		CartDetailsDto cartDetailsResponse = accountService.getCartDetails(userId, requestHeader);

		return new ResponseEntity<>(cartDetailsResponse, HttpStatus.OK);
	}

	@RequestMapping(value = "users/{userId}/orders", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<OrderDto>> getAllCustomerOrders(@PathVariable Integer userId,
			@RequestHeader HttpHeaders requestHeader) {

		List<OrderDto> ordersDto = accountService.getAllCustomerOrders(userId, requestHeader);

		return new ResponseEntity<>(ordersDto, HttpStatus.OK);
	}

	@RequestMapping(value = "users/{userId}/orders", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON)
	public ResponseEntity<OrderDto> placeOrder(@PathVariable Integer userId, @RequestHeader HttpHeaders requestHeader) {

		
		accountService.placeOrder(userId, requestHeader);

		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "customers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<CustomerListDto> getCustomers(@RequestHeader HttpHeaders header) {

		return new ResponseEntity<>(accountService.getCustomers(tokenService.findByHash(header.getFirst("token"))),
				HttpStatus.OK);
	}

	@RequestMapping(value = "users/{userId}/cart/{itemId}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON)
	public ResponseEntity<AccountDeleteDto> removeItemFromCart(@PathVariable("userId") Integer userId,
			@PathVariable("itemId") Integer itemId, @RequestBody Map<String, String> requestBody) {
		
		itemService.deleteItem(requestBody.get("token"), itemId, userId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
