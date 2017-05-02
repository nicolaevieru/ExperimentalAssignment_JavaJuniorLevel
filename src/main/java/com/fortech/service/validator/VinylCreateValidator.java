package com.fortech.service.validator;

import com.fortech.model.Vinyl;
import com.fortech.service.exception.BadRequestException;

public class VinylCreateValidator extends Validator<Vinyl> {

	public VinylCreateValidator(Vinyl toValidate) {
		super(toValidate);
	}

	@Override
	public void validate() {
		validateName();
		validateCost();
		validateStock();
	}
	
	
	private void validateName(){
		if(toValidate.getName() == null){
			throw new BadRequestException("A name must be provided for the vinyl.");
		}
	}
	
	private void validateCost(){
		if(toValidate.getCost() < 0.0){
			throw new BadRequestException("Cost can't be less than zero.");

		}
	}
	
	private void validateStock(){
		if(toValidate.getStock() < 0){
			throw new BadRequestException("Stock can't be less than zero.");

		}
	}
}
