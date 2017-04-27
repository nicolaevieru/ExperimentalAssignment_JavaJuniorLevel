package com.fortech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fortech.model.Vinyl;
import com.fortech.service.VinylService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/vinyls")
public class VinylController {

	@Autowired
	private VinylService vinylService;

	@ApiOperation(value = "Create and store a vinyl in the database.")
	@ApiResponses(value = { @ApiResponse(code = org.apache.http.HttpStatus.SC_CREATED, message = ""),
			@ApiResponse(code = org.apache.http.HttpStatus.SC_BAD_REQUEST, message = "If the json request fields are not valid.") }

	)
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Vinyl> addVinyl(@RequestBody Vinyl vinyl) {

		vinylService.save(vinyl);

		return new ResponseEntity<>(HttpStatus.CREATED);
	}

}
