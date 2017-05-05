package com.fortech.service;

public interface ItemService {
	void deleteItem(Integer id);
	void deleteItem(String tokenHash, Integer itemId, Integer accountId);
	void findOne();

}
