package com.fortech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.fortech.model.Item;
import com.fortech.model.Vinyl;

public interface ItemRepository extends CrudRepository<Item, Integer> {
	
	List<Item>findByVinyl(Vinyl vinyl);
	@Query("SELECT i FROM account JOIN a.cart.items i WHERE a.id=:id")
	List<Item> findInActiveCartByAccount(@Param("id") Integer id);
	List<Item> findByCartId(Integer id);
	
	@Query("SELECT i FROM Cart c JOIN c.items i WHERE c.id = i.id AND c.cartState.type = com.fortech.model.CartStateEnum.ACTIVE AND i.vinyl.id =:id")
	Item findByUserAndIdInActiveCarts(@Param("itemId") Integer itemId);
	
/*	List<Item> findByVinylAndCart_CartState(Vinyl vinyl,CartState cartstate);*/
	
	@Query("SELECT i FROM Cart c JOIN c.items i WHERE c.id = i.id AND c.cartState.type = com.fortech.model.CartStateEnum.ACTIVE AND i.vinyl.id =:id")
	List<Item> findByVinylIdInActiveCarts(@Param("id") Integer id);
	
	@Query("From Item")
	List<Item> findByAccountInActiveCarts();
	
	@Query("")
	Item findByIdInActiveCart(@Param("itemId") Integer itemId, @Param("userId") Integer userId);
	
/*	@Query("From Item i LEFT JOIN Cart c ON i.cartId = c.id LEFT JOIN c.account acc ON i.account.id = acc.id  WHERE c.cartState.type = com.fortech.model.CartStateEnum.ACTIVE AND c.account.id =:id")
	List<Item> findByAccountInActiveCarts(@Param("id") Integer id); */
	
	Item findByVinylAndCartId(Vinyl vinyl,Integer cart);
	}
