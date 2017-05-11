package com.fortech.service;

import com.fortech.model.Item;

public interface ItemService {
	void deleteItem(Item item);
	void deleteItem(String tokenHash, Integer itemId, Integer accountId);
	void findOne();

}
