package com.fortech.service.validator;

public abstract class Validator<T> {
	protected T toValidate;

	public abstract void validate();

	public Validator() {
	}

	public Validator(T toValidate) {
		this.toValidate = toValidate;
	}

	public void setToValidate(T toValidate) {
		this.toValidate = toValidate;
	}

	public T getToValidate() {
		return toValidate;
	}
}
