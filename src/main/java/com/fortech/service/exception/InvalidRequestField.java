package com.fortech.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(value= HttpStatus.BAD_REQUEST)
public class InvalidRequestField extends RuntimeException {

	public InvalidRequestField(String message) {
		super(message);
	}

}
