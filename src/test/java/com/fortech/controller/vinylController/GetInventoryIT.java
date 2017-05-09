package com.fortech.controller.vinylController;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.apache.http.HttpStatus;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fortech.controller.AbstractTest;
import com.fortech.model.Vinyl;
import com.fortech.repository.VinylRepository;

import io.restassured.http.Header;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class GetInventoryIT extends AbstractTest{
	
	private String URL = "/api/vinyls/inventory";
		
	@Autowired
	VinylRepository vinylRepository;
	
	@Test
	public void testWhenProvidedRequestInfoIsValidReturnOK(){
		Response response = sendGetRequest(URL);	
		JsonPath jsonPath = new JsonPath(response.getBody().asString());
		List<Vinyl> vinyls = jsonPath.getList("vinyls");
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_OK);	
		assertThat(vinyls.size()).isEqualTo(vinylRepository.getVinyls().size());
	}

	@Test
	public void testWhenProvidedAuthenticationTokenIsInvalidReturnUnauthorized(){
		requestHeader = new Header("token", "0000000");
		Response response = sendGetRequest(URL);	
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_UNAUTHORIZED);
	}

	@Test
	public void testWhenProvidedAuthenticationTokenBelongsToACustomerReturnUnauthorized(){
		requestHeader = new Header("token", EXISTING_CUSTOMER_TOKEN);
		Response response = sendGetRequest(URL);	
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_UNAUTHORIZED);

	}
}
