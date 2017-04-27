package com.fortech.service.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST,reason = "The password is invalid, it must contain 1 upercase letter, 1 special character, 1 digit and at it must be at least 6 characters long")
@SuppressWarnings("serial")
public class InvalidPasswordException extends RuntimeException {

}
