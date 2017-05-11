package com.fortech.controller.Account;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fortech.controller.AbstractTest;
import com.fortech.repository.CartRepository;
import com.fortech.repository.ItemRepository;
import com.fortech.repository.VinylRepository;

import io.restassured.response.Response;

public class RemoveItemFromCartIT extends AbstractTest {
	
	Integer userId = EXISTING_CUSTOMER_ID;
	Integer itemId; 
	Integer vinylId = 1001;
	
	String URL;
	
	@Autowired
	private ItemRepository itemRepository;
	
	@Autowired
	private VinylRepository vinylRepository;
	
	@Autowired
	private CartRepository cartRepository;
	
	@Before
	public void setup(){
		String addItemToCartURL = String.format("/api/vinyls/%d/cart", vinylId);
		
		requestJson.put("token", EXISTING_CUSTOMER_TOKEN);
		requestJson.put("quantity", "1");
		sendPostRequest(addItemToCartURL);
		
		itemId = itemRepository.findByVinylAndCartId(vinylRepository.findOne(vinylId), EXISTING_CART_ID).getId();
		
		URL = String.format("/api/users/%d/cart/%d", userId,itemId);
	}
	
	@Test
	public void testWhenRequestJsonAndUserIdAndItemIdAreValidReturnNoContent(){
		Integer vinylStockBeforeRequest = vinylRepository.findOne(vinylId).getStock();
		Double cartCostBeforeItemRemoval = cartRepository.findOne(EXISTING_CART_ID).getCost();
		
		Response response = sendDeleteRequest(URL);
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_NO_CONTENT);
		assertThat(itemRepository.findOne(itemId)).isEqualTo(null);
		assertThat(vinylRepository.findOne(vinylId).getStock()).isEqualTo(vinylStockBeforeRequest + 1);
		assertThat(cartRepository.findOne(EXISTING_CART_ID)).isEqualTo(cartCostBeforeItemRemoval - vinylRepository.findOne(vinylId).getCost());
		
	}

}






