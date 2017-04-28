package com.fortech.service.validator;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortech.model.Vinyl;
import com.fortech.model.dto.AddVinylToCartDto;
import com.fortech.repository.TokenRepository;
import com.fortech.repository.VinylRepository;
import com.fortech.service.exception.InvalidRequestField;

public class AddVinylToCartValidator extends Validator<AddVinylToCartDto> {
	
	@Autowired
	private VinylRepository vinylRepository;
	
	@Autowired
	private TokenRepository tokenRepository;
	
	public AddVinylToCartValidator(AddVinylToCartDto toValidate) {
		super(toValidate);
	}

	@Override
	public void validate() {
		validateToken();
		validateQuantity();
		validateStock();
	}
	
	private void validateQuantity(){
		if(toValidate.getQuantity() <= 0){
			throw new InvalidRequestField("Quantity should be bigger or equal to 0.");
		}
	}
	
	private void validateStock(){
		int vinylsInStock = vinylRepository.findOne(toValidate.getVinylId()).getStock();
		
		if(toValidate.getQuantity() > vinylsInStock){
			throw new InvalidRequestField("There are not enough vinyls in stock to process your request.");
		}	
	}
	
	private void validateToken(){
		if(toValidate.getToken() == null){
			throw new InvalidRequestField("Invalid token.Log in or create an account.");
		}
	}
	
}
