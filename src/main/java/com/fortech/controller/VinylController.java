package com.fortech.controller;

import java.util.Map;

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
import com.fortech.model.dto.VinylCanOrderListDto;
import com.fortech.model.dto.VinylCreateDto;
import com.fortech.model.dto.VinylDetailsDto;
import com.fortech.model.dto.VinylInventoryListDto;
import com.fortech.service.TokenService;
import com.fortech.service.VinylService;

@RestController
@RequestMapping("/api/vinyls")
public class VinylController {

	@Autowired
	private VinylService vinylService;

	@Autowired
	private TokenService tokenService;

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

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<VinylCanOrderListDto> getVinyls(@RequestHeader HttpHeaders header) {

		return new ResponseEntity<>(vinylService.getVinyls(), HttpStatus.OK);

	}

	@RequestMapping(value = "{id}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON)
	public ResponseEntity<VinylCanOrderListDto> deleteVinyl(@RequestBody Map<String, String> request,
			@PathVariable("id") Integer id) {
		vinylService.deleteVinyl(id, request.get("token"));
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);

	}

	@RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<VinylDetailsDto> getDetails(@RequestHeader HttpHeaders header,
			@PathVariable("id") Integer id) {

		return new ResponseEntity<>(vinylService.getDetails(id, header.getFirst("token")), HttpStatus.OK);

	}

	@RequestMapping(value = "{vinylId}", method = RequestMethod.PUT, consumes = "application/json")
	public ResponseEntity<Vinyl> updateVinylInfo(@PathVariable Integer vinylId,
			@RequestBody VinylCreateDto vinylUpdateDto) {

		vinylService.updateVinylInfo(vinylId, vinylUpdateDto);

		return new ResponseEntity<>(HttpStatus.OK);
	}

}
