package com.fortech.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(value= HttpStatus.BAD_REQUEST,reason = "The email address is invalid")
public class InvalidEmailException extends RuntimeException {
}
