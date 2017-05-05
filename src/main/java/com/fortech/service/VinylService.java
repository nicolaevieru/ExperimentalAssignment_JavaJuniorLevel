package com.fortech.service;

import org.springframework.http.HttpHeaders;

import com.fortech.model.Vinyl;
import com.fortech.model.dto.VinylCreateDto;

public interface VinylService {

	Vinyl save(VinylCreateDto vinyl);

	void addVinylToCart(Integer vinylId, Object requestBody);

	void updateVinylInfo(Integer vinylId, VinylCreateDto vinylUpdateDto);

}