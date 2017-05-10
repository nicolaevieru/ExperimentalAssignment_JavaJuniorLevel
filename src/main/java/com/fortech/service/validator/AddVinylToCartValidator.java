package com.fortech.service.validator;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortech.model.dto.AddVinylToCartDto;
import com.fortech.repository.VinylRepository;
import com.fortech.service.exception.BadRequestException;

@Component
public class AddVinylToCartValidator extends Validator<AddVinylToCartDto> {
	
	Logger logger = Logger.getLogger(AddVinylToCartValidator.class);

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
			logger.error("Error while trying to validate vinyl Id.");
			throw new BadRequestException("Vinyl with id " + toValidate.getVinylId() + " does not exist.");
		}
	}

	private void validateQuantity() {
		if (toValidate.getQuantity() <= 0) {
			logger.error("Error while trying to validate quantity.");
			throw new BadRequestException("Quantity should be bigger or equal to 0.");
		}
	}

	private void validateStock() {
		int vinylsInStock = vinylRepository.findOne(toValidate.getVinylId()).getStock();

		if (toValidate.getQuantity() > vinylsInStock) {
			logger.error("Error while trying to validate available stock.");
			throw new BadRequestException("There are not enough vinyls in stock to process your request.");
		}
	}

	private void validateToken() {
		if (toValidate.getToken() == null) {
			logger.error("Error while trying to validate token.");
			throw new BadRequestException("Invalid token.Log in or create an account.");
		}
	}
	
	public void validateQuantityIsInteger(String quantity){
		if(!StringUtils.isNumeric(quantity)){
			logger.error("Error while trying to validate quantity is an integer.");
			throw new BadRequestException("Quantity should be a numeric number.");
		}
	}

}
