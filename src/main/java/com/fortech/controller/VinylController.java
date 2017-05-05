package com.fortech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fortech.model.Vinyl;
import com.fortech.model.dto.VinylCreateDto;
import com.fortech.service.VinylService;

@RestController
@RequestMapping("/api/vinyls")
public class VinylController {

	@Autowired
	private VinylService vinylService;
	

	@RequestMapping(method = RequestMethod.POST,consumes = "application/json")
	public ResponseEntity<Vinyl> createVinyl(@RequestBody VinylCreateDto vinylCreateDto) {	
		
		vinylService.save(vinylCreateDto);
		
		return new ResponseEntity<>(HttpStatus.CREATED);		
	}
	
	
	@RequestMapping(value = "{vinylId}/cart",method = RequestMethod.POST,consumes = "application/json")
	public ResponseEntity<Vinyl> addVinylToCart(@PathVariable Integer vinylId,@RequestBody Object requestBody) {
						
		vinylService.addVinylToCart(vinylId,requestBody);

		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}	
	
}
