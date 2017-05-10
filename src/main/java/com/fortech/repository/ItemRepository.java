package com.fortech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.fortech.model.Cart;
import com.fortech.model.CartState;
import com.fortech.model.Item;
import com.fortech.model.Vinyl;

public interface ItemRepository extends CrudRepository<Item, Integer> {
	
	List<Item>findByVinyl(Vinyl vinyl);
	@Query("FROM Item i LEFT JOIN i.cart.cartState cs where (i.id =:id AND cs.type = com.fortech.model.CartStateEnum.ACTIVE)")
	Item findByIdInActiveCart(@Param("id") Integer id);
	List<Item> findByCart(Cart cart);
	List<Item> findByVinylAndCart_CartState(Vinyl vinyl,CartState cartstate);
	Item findByVinylAndCart(Vinyl vinyl,Cart cart); 

}
