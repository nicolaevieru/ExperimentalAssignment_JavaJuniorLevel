package com.fortech.service;

import java.util.Map;

import com.fortech.model.Token;
import com.fortech.model.Vinyl;
import com.fortech.model.dto.VinylCanOrderListDto;
import com.fortech.model.dto.VinylCreateDto;
import com.fortech.model.dto.VinylDetailsDto;
import com.fortech.model.dto.VinylInventoryListDto;

public interface VinylService {

	Vinyl save(VinylCreateDto vinyl);
	
	VinylDetailsDto getDetails(Integer id, String token);

	void addVinylToCart(Integer vinylId, Map<String,String> requestBody);

	VinylInventoryListDto getInventory(Token token);
	
	VinylCanOrderListDto getVinyls(String token);

	void deleteVinyl(Integer id, String token);
  
  void updateVinylInfo(Integer vinylId, VinylCreateDto vinylUpdateDto);

}