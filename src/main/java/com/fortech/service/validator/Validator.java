package com.fortech.service.validator;

public abstract class Validator<T> {
	T toValidate;
	Validator(T toValidate) {
		this.toValidate = toValidate;
	}
	public abstract void validate();

}
