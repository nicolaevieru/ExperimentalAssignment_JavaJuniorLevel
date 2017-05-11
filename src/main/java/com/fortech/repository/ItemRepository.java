package com.fortech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.fortech.model.Item;
import com.fortech.model.Vinyl;

public interface ItemRepository extends CrudRepository<Item, Integer> {
	
	List<Item>findByVinyl(Vinyl vinyl);
	
	@Query("SELECT i FROM Account a JOIN a.carts c JOIN c.items i WHERE a.id=:id and c.cartState.type = com.fortech.model.CartStateEnum.ACTIVE")
	List<Item> findActiveCartItemsByAccount(@Param("id") Integer id);
	
	Item findByVinylAndCartId(Vinyl vinyl,Integer cart);
	}
