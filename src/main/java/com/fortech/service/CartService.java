package com.fortech.service;

import com.fortech.model.CartStateEnum;
import com.fortech.model.dto.ChangeOrderStatusDto;

public interface CartService {
	void deleteAll();
	
	void updateState(Integer id, ChangeOrderStatusDto newStatusDto);
	void updateState(Integer id, CartStateEnum newCartState);

}
