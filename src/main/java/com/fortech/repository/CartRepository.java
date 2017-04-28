package com.fortech.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fortech.model.Account;
import com.fortech.model.Cart;

@Repository(value = "cartRepository")
public interface CartRepository extends CrudRepository<Cart, Integer> {
	Cart findByAccount(Account account);
	
}
