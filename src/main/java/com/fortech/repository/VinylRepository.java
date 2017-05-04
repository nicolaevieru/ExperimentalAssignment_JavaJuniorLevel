package com.fortech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fortech.model.Vinyl;
import com.fortech.model.dto.VinylCanOrderDto;
import com.fortech.model.dto.VinylInventoryDto;

@Repository("vinylRepository")
public interface VinylRepository extends CrudRepository<Vinyl, Integer> {
	Vinyl findByName(String name);

	@Query("select new com.fortech.model.dto.VinylInventoryDto(v.id, v.name, v.stock) from Vinyl v")
	List<VinylInventoryDto> getInventory();
	
	@Query("select new com.fortech.model.dto.VinylCanOrderDto(v.id, v.name, v.cost) from Vinyl v WHERE v.available = true")
	List<VinylCanOrderDto> getVinyls();
}
