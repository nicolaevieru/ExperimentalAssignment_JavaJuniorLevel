package com.fortech.service.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortech.model.dto.AddVinylToCartDto;
import com.fortech.repository.VinylRepository;
import com.fortech.service.exception.BadRequestException;

@Component
public class AddVinylToCartValidator extends Validator<AddVinylToCartDto> {

	public AddVinylToCartValidator() {
	}
	
	public AddVinylToCartValidator(AddVinylToCartDto toValidate) {
		super(toValidate);
	}

	@Autowired
	private VinylRepository vinylRepository;

	@Override
	public void validate() {
		validateToken();
		validateId();
		validateQuantity();
		validateStock();
	}
	
	private void validateId(){
		if(vinylRepository.findOne(toValidate.getVinylId()) == null){
			throw new BadRequestException("Vinyl with id " + toValidate.getVinylId() + " does not exist.");
		}
	}

	private void validateQuantity() {
		if (toValidate.getQuantity() <= 0) {
			throw new BadRequestException("Quantity should be bigger or equal to 0.");
		}
	}

	private void validateStock() {
		int vinylsInStock = vinylRepository.findOne(toValidate.getVinylId()).getStock();

		if (toValidate.getQuantity() > vinylsInStock) {
			throw new BadRequestException("There are not enough vinyls in stock to process your request.");
		}
	}

	private void validateToken() {
		if (toValidate.getToken() == null) {
			throw new BadRequestException("Invalid token.Log in or create an account.");
		}
	}

}
