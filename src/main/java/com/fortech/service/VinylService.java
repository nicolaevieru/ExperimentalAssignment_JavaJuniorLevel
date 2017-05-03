package com.fortech.service;

import com.fortech.model.Vinyl;
import com.fortech.model.dto.VinylCreateDto;

public interface VinylService {

	Vinyl save(VinylCreateDto vinyl);

	void addVinylToCart(Integer vinylId, Object requestBody);

}