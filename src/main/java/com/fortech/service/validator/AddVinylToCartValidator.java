package com.fortech.service.validator;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortech.model.Vinyl;
import com.fortech.model.dto.AddItemToCartDto;
import com.fortech.repository.VinylRepository;
import com.fortech.service.exception.InvalidRequestField;

public class AddVinylToCartValidator extends Validator<AddItemToCartDto> {
	
	@Autowired
	private VinylRepository vinylRepository;
	
	public AddVinylToCartValidator(AddItemToCartDto toValidate) {
		super(toValidate);
	}

	@Override
	public void validate() {
		validateQuantity();
		validateStock();
	}
	
	private void validateQuantity(){
		if(toValidate.getQuantity() <= 0){
			throw new InvalidRequestField("Quantity should be bigger or equal to 0.");
		}
	}
	
	private void validateStock(){
		int vinylsInStock = vinylRepository.findOne(toValidate.getItemId()).getStock();
		
		if(toValidate.getQuantity() > vinylsInStock){
			throw new InvalidRequestField("There are not enough vinyls in stock to process your request.");
		}
		
	}

}
