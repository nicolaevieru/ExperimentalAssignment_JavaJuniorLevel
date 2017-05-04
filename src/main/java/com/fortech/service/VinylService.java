package com.fortech.service;

import com.fortech.model.Token;
import com.fortech.model.Vinyl;
import com.fortech.model.dto.VinylCanOrderListDto;
import com.fortech.model.dto.VinylCreateDto;
import com.fortech.model.dto.VinylInventoryListDto;

public interface VinylService {

	Vinyl save(VinylCreateDto vinyl);

	void addVinylToCart(Integer vinylId, Object requestBody);

	VinylInventoryListDto getInventory(Token token);
	
	VinylCanOrderListDto getVinyls();

}