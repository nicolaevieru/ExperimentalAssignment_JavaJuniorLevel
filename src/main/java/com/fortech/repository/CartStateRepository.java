package com.fortech.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fortech.model.CartState;
import com.fortech.model.CartStateEnum;

@Repository(value = "cartStateRepository")
public interface CartStateRepository extends CrudRepository<CartState, Integer>{
	CartState findByType(CartStateEnum type);

}
