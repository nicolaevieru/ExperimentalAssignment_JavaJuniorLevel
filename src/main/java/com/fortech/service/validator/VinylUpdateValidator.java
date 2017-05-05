package com.fortech.service.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortech.repository.VinylRepository;
import com.fortech.service.exception.BadRequestException;


@Component
public class VinylUpdateValidator extends VinylCreateValidator{
	
	@Autowired
	VinylRepository vinylRepository;
	
	Integer vinylId;
	
	@Override
	public void validate() {
		validateId();
		validateToken();
		validateManagerToken();
		validateName();
		validateCost();
		validateStock();

	}
		
	private void validateId(){
		if(vinylRepository.findOne(vinylId) == null){
			throw new BadRequestException("Vinyl with id " + vinylId + " does not exist.");
		}
	}

	public void setVinylId(Integer vinylId) {
		this.vinylId = vinylId;
	}
	
}
