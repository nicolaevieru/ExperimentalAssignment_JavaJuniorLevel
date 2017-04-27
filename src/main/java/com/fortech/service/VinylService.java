package com.fortech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fortech.model.Vinyl;
import com.fortech.repository.VinylRepository;
import com.fortech.service.validator.AddVinylToCartValidator;

@Service("vinylService")
public class VinylService {

	@Autowired
	private VinylRepository vinylRepository;
	
	@Autowired
	private AddVinylToCartValidator vinylToCartValidator;
	
	public Vinyl save(Vinyl vinyl) {
		return vinylRepository.save(vinyl);
	}
	
	public void addVinylToCart(Long vinylId,Integer quantity){
		vinylToCartValidator.validate();
	}

}
