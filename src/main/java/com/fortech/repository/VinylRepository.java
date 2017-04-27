package com.fortech.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fortech.model.Vinyl;

@Repository("vinylRepository")
public interface VinylRepository extends CrudRepository<Vinyl, Long> {

}
