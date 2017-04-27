package com.fortech.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fortech.model.Vinyl;
import com.fortech.model.dto.VinylCreateDto;
import com.fortech.service.VinylService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import net.minidev.json.JSONObject;

@RestController
@RequestMapping("/api/vinyls")
public class VinylController {

	@Autowired
	private VinylService vinylService;

	@ApiOperation(value = "Create and store a vinyl in the database.")
	@ApiResponses(value={
			@ApiResponse(code = org.apache.http.HttpStatus.SC_CREATED ,message= ""),
			@ApiResponse(code = org.apache.http.HttpStatus.SC_BAD_REQUEST ,message= "If the json request fields are not valid.")
	}
			
			)
	@RequestMapping(method = RequestMethod.POST,consumes = "application/json")
	public ResponseEntity<Vinyl> createVinyl(@RequestBody VinylCreateDto vinylCreateDto) {
		
		Vinyl vinyl = new Vinyl(vinylCreateDto);
		
		vinylService.save(vinyl);
		
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "{vinylId}/cart",method = RequestMethod.POST,consumes = "application/json")
	public ResponseEntity<Vinyl> addVinylToCart(@PathVariable Long vinylId,@RequestBody Object object) {
				
		int quantity = Integer.parseInt((String)((Map)object).get("quantity"));
		
		vinylService.addVinylToCart(vinylId,quantity);

		return new ResponseEntity<>(HttpStatus.CREATED);

	}	
	
}
