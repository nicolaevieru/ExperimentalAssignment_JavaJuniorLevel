package com.fortech.service.validator;

import com.fortech.model.Vinyl;
import com.fortech.service.exception.InvalidRequestField;

public class VinylCreateValidator extends Validator<Vinyl> {

	public VinylCreateValidator(Vinyl toValidate) {
		super(toValidate);
	}

	@Override
	public void validate() {
		validateName();
		validateCost();
	}
	
	
	private void validateName(){
		if(toValidate.getName() == null){
			throw new InvalidRequestField("A name must be provided for the vinyl.");
		}
	}
	
	private void validateCost(){
		if(toValidate.getCost() < 0.0){
			throw new InvalidRequestField("Cost can't be less than zero.");

		}
	}
}
