package com.fortech.repository;

import org.springframework.context.annotation.Scope;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fortech.model.Vinyl;
import com.fortech.service.VinylService;

@Repository("vinylRepository")
public interface VinylRepository extends CrudRepository<Vinyl, Long> {
	
}
