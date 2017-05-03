package com.fortech.controller;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fortech.model.Vinyl;
import com.fortech.model.dto.VinylCreateDto;
import com.fortech.model.dto.VinylInventoryListDto;
import com.fortech.service.TokenService;
import com.fortech.service.VinylService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/vinyls")
public class VinylController {

	@Autowired
	private VinylService vinylService;

	@Autowired
	private TokenService tokenService;

	@ApiOperation(value = "Create and store a vinyl in the database.")
	@ApiResponses(value = { @ApiResponse(code = org.apache.http.HttpStatus.SC_CREATED, message = ""),
			@ApiResponse(code = org.apache.http.HttpStatus.SC_BAD_REQUEST, message = "If the json request fields are not valid.") }

	)
	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<Vinyl> createVinyl(@RequestBody VinylCreateDto vinylCreateDto) {

		vinylService.save(vinylCreateDto);

		return new ResponseEntity<>(HttpStatus.CREATED);

	}

	@RequestMapping(value = "{vinylId}/cart", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<Vinyl> addVinylToCart(@PathVariable Integer vinylId, @RequestBody Object requestBody) {

		vinylService.addVinylToCart(vinylId, requestBody);

		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

	@RequestMapping(value = "inventory", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON)
	public ResponseEntity<VinylInventoryListDto> getInventory(@RequestHeader HttpHeaders header) {

		return new ResponseEntity<>(vinylService.getInventory(tokenService.findByHash(header.getFirst("token"))),
				HttpStatus.OK);
	}

}
