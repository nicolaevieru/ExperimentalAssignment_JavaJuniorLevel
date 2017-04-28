package com.fortech.service.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortech.model.dto.AddVinylToCartDto;
import com.fortech.repository.TokenRepository;
import com.fortech.repository.VinylRepository;
import com.fortech.service.exception.InvalidRequestField;

@Component
public class AddVinylToCartValidator extends Validator<AddVinylToCartDto> {

	public AddVinylToCartValidator() {
	}
	
	public AddVinylToCartValidator(AddVinylToCartDto toValidate) {
		super(toValidate);
	}

	@Autowired
	private VinylRepository vinylRepository;

	@Autowired
	private TokenRepository tokenRepository;

	@Override
	public void validate() {
		validateToken();
		validateQuantity();
		validateStock();
	}

	private void validateQuantity() {
		if (toValidate.getQuantity() <= 0) {
			throw new InvalidRequestField("Quantity should be bigger or equal to 0.");
		}
	}

	private void validateStock() {
		int vinylsInStock = vinylRepository.findOne(toValidate.getVinylId()).getStock();

		if (toValidate.getQuantity() > vinylsInStock) {
			throw new InvalidRequestField("There are not enough vinyls in stock to process your request.");
		}
	}

	private void validateToken() {
		if (toValidate.getToken() == null) {
			throw new InvalidRequestField("Invalid token.Log in or create an account.");
		}
	}

}
