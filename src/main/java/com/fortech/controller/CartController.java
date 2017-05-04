package com.fortech.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fortech.model.dto.ChangeOrderStatusDto;
import com.fortech.service.CartService;

@RestController
@RequestMapping(value = "api")
public class CartController {

	@Autowired
	CartService cartService;

	@RequestMapping(value = "orders/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON)
	public ResponseEntity<ChangeOrderStatusDto> changeOrder(@PathVariable("id") Integer id,
			@RequestBody ChangeOrderStatusDto request) {
		cartService.updateState(id, request);
		return new ResponseEntity<>(HttpStatus.OK);

	}

	@ExceptionHandler({ HttpMessageNotReadableException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public void handleBadRequests(HttpServletResponse response) throws IOException{
		response.sendError(HttpStatus.BAD_REQUEST.value(), "Invalid order state or token");
	}

}
