package com.fortech.service;

import com.fortech.model.Vinyl;

public interface VinylService {

	Vinyl save(Vinyl vinyl);

	void addVinylToCart(Integer vinylId, Object requestBody);

}