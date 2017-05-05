package com.fortech.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.fortech.model.Cart;
import com.fortech.model.CartState;
import com.fortech.model.Item;
import com.fortech.model.Vinyl;

public interface ItemRepository extends CrudRepository<Item, Integer> {
	List<Item> findByCart(Cart cart);
	List<Item> findByVinylAndCart_CartState(Vinyl vinyl,CartState cartstate);
	Item findByVinylAndCart(Vinyl vinyl,Cart cart); 
}
