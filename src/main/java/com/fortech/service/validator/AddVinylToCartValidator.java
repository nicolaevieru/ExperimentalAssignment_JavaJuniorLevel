package com.fortech.service.validator;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortech.model.Vinyl;
import com.fortech.repository.VinylRepository;
import com.fortech.service.exception.InvalidRequestField;

public class AddVinylToCartValidator extends Validator<Integer> {
	
	@Autowired
	private VinylRepository vinylRepository;
	
	private  

	AddVinylToCartValidator(Integer toValidate,Long id) {
		super(toValidate);
	}

	@Override
	public void validate() {
		validateQuantity();
		validateStock();
	}
	
	private void validateQuantity(){
		if(toValidate <= 0){
			throw new InvalidRequestField("Quantity should be bigger or equal to 0.");
		}
	}
	
	private void validateStock(){
		
		
	}

}
