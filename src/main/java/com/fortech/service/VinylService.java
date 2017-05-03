package com.fortech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fortech.model.Vinyl;
import com.fortech.repository.VinylRepository;

@Service("vinylService")
public class VinylService {

	@Autowired
	private VinylRepository vinylRepository;

	public Vinyl save(Vinyl vinyl) {
		return vinylRepository.save(vinyl);
	}

}
