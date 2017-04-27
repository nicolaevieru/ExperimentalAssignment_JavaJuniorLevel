package com.fortech.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(value= HttpStatus.BAD_REQUEST,reason = "The names must have more than 1 character")
public class InvalidNameException extends RuntimeException {

}
