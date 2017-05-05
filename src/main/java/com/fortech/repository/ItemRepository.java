package com.fortech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.fortech.model.Item;
import com.fortech.model.Vinyl;

public interface ItemRepository extends CrudRepository<Item, Integer> {
	
	List<Item>findByVinyl(Vinyl vinyl);
	
	@Query("FROM Item i LEFT JOIN i.cart.cartState cs where (i.id =:id AND cs.type = com.fortech.model.CartStateEnum.ACTIVE)")
	Item findByIdInActiveCart(@Param("id") Integer id);

}
