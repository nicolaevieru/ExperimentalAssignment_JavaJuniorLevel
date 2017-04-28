package com.fortech.service.validator;

public abstract class Validator<T> {
	protected T toValidate;
	public Validator(T toValidate) {
		this.toValidate = toValidate;
	}
	public abstract void validate();
	
	public Validator() {
		
	}
	
	public void setToValidate(T toValidate) {
		this.toValidate = toValidate;
	}
	
	public T getToValidate() {
		return toValidate;
	}

}
