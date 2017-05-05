package com.fortech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fortech.repository.CartRepository;

@Service("cartService")
public class CartServiceImpl implements CartService {
	
	@Autowired
	CartRepository cartRepository;
	
	@Override
	public void deleteAll() {
		cartRepository.deleteAll();
		
	}

}
