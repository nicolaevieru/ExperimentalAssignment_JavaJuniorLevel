package com.fortech.service.validator;

public abstract class Validator<T> {
	protected T toValidate;
	
	public Validator() {
	}

	public Validator(T toValidate) {
		this.toValidate = toValidate;
	}

	public abstract void validate();
	
	public void setToValidate(T value) {
		toValidate = value;
	}

}
