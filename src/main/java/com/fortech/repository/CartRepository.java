package com.fortech.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fortech.model.Account;
import com.fortech.model.Cart;
import com.fortech.model.CartState;

@Repository(value = "cartRepository")
public interface CartRepository extends CrudRepository<Cart, Integer> {
	List<Cart> findByAccount(Account account);
	Cart findByAccountAndCartState(Account account,CartState cartState);
	List<Cart> findByCartState(CartState cartState);
	
}