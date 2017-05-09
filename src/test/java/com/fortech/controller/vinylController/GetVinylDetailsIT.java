package com.fortech.controller.vinylController;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.http.HttpStatus;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fortech.controller.AbstractTest;
import com.fortech.model.Vinyl;
import com.fortech.repository.VinylRepository;

import io.restassured.http.Header;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class GetVinylDetailsIT extends AbstractTest {

	private Integer vinylId = 1000;
	private String URL = String.format("/api/vinyls/%d", vinylId);
	
	@Autowired
	private VinylRepository vinylRepository;
	
	@Test
	public void testWhenProvidedRequestInfoIsValidReturnOK(){
		Response response = sendGetRequest(URL);	
		JsonPath jsonPath = new JsonPath(response.getBody().asString());
		Vinyl requestedvinyl = vinylRepository.findOne(vinylId);
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_OK);	
		assertThat(jsonPath.getString("name")).isEqualTo(requestedvinyl.getName());
		assertThat(jsonPath.getDouble("cost")).isEqualTo(requestedvinyl.getCost());
		assertThat(jsonPath.getInt("stock")).isEqualTo(requestedvinyl.getStock());
	}
	
	@Test
	public void testWhenProvidedVinylIdInUrlIsInvalidReturnBadRequest(){
		Response response = sendGetRequest("/api/vinyls/99999999");	
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_BAD_REQUEST);
	}
	
	@Test
	public void testWhenProvidedAuthenticationTokenIsInvalidReturnUnauthorized(){
		requestHeader = new Header("token", "0000000");
		Response response = sendGetRequest(URL);	
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_UNAUTHORIZED);
	}

}
