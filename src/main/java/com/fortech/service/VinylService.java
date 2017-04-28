package com.fortech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fortech.model.Vinyl;
import com.fortech.model.dto.AccountCreateDto;
import com.fortech.model.dto.AddItemToCartDto;
import com.fortech.repository.VinylRepository;
import com.fortech.service.validator.AccountValidator;
import com.fortech.service.validator.AddVinylToCartValidator;
import com.fortech.service.validator.Validator;

@Service("vinylService")
public class VinylService {

	@Autowired
	private VinylRepository vinylRepository;
		
	public Vinyl save(Vinyl vinyl) {
		return vinylRepository.save(vinyl);
	}
	
	public void addVinylToCart(Long vinylId,Integer quantity){
		AddItemToCartDto itemToCartDto = new AddItemToCartDto(vinylId,quantity);
		
		Validator<AddItemToCartDto> vinylToCartValidator = new AddVinylToCartValidator(itemToCartDto);

		vinylToCartValidator.validate();
		
		Vinyl vinylToAddInCart = vinylRepository.findOne(vinylId);
		
		vinylToAddInCart.setStock(vinylToAddInCart.getStock() - quantity);
		
		vinylRepository.save(vinylToAddInCart);
		
		
	}

}
