package com.fortech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fortech.model.Account;
import com.fortech.model.Cart;
import com.fortech.model.CartState;

@Repository(value = "cartRepository")
public interface CartRepository extends CrudRepository<Cart, Integer> {
	List<Cart> findByAccount(Account account);
	Cart findByAccountAndCartState(Account account,CartState cartState);
	List<Cart> findByCartState(CartState cartState);
	
	@Query("FROM Cart c WHERE c.account.id = :id and c.cartState.id = :cartStateId")
	Cart findByAccountIdAndCartStateId(@Param("id") Integer id,@Param("cartStateId") Integer cartStateId);
	
	@Query("FROM Cart c WHERE c.account.id = :id and c.cartState.id = 3")
	List<Cart> findProcessingByAccount(@Param("id") Integer id);
	
	@Query("FROM Cart c WHERE c.account.id = :id and c.cartState.id = 1")
	Cart findActiveByAccount(@Param("id") Integer id);
	
	@Query("DELETE FROM Cart c WHERE c.id !=:id")
	@Modifying
	@Transactional
	void deleteOtherCarts(@Param("id") Integer id);

}
