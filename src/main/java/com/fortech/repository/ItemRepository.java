package com.fortech.repository;

import org.springframework.data.repository.CrudRepository;

import com.fortech.model.Item;

public interface ItemRepository extends CrudRepository<Item, Integer> {

}
