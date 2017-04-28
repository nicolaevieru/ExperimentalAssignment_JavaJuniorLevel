package com.fortech.service;

import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fortech.model.Cart;
import com.fortech.model.Item;
import com.fortech.model.Token;
import com.fortech.model.Vinyl;
import com.fortech.model.dto.AddVinylToCartDto;
import com.fortech.repository.CartRepository;
import com.fortech.repository.ItemRepository;
import com.fortech.repository.TokenRepository;
import com.fortech.repository.VinylRepository;
import com.fortech.service.validator.AddVinylToCartValidator;
import com.fortech.service.validator.Validator;
import com.fortech.service.validator.VinylCreateValidator;;

@Service("vinylService")
public class VinylServiceImpl implements VinylService {

	@Autowired
	private VinylRepository vinylRepository;

	@Autowired
	private TokenRepository tokenRepository;
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private ItemRepository itemRepository;
	
	@Autowired
	private AddVinylToCartValidator vinylToCartValidator;
	

	@Override
	public Vinyl save(Vinyl vinyl) {
		Validator<Vinyl> validator = new VinylCreateValidator(vinyl);
		
		validator.validate();
		
		return vinylRepository.save(vinyl);
	}
	

	@Override
	public void addVinylToCart(Integer vinylId, Object requestBody) {

		AddVinylToCartDto vinylToCartDto = processRequestBody(vinylId,requestBody);
		
		vinylToCartValidator.setValue(vinylToCartDto);
		vinylToCartValidator.validate();

		Cart cart = findUserActiveCart(vinylToCartDto);		
		Vinyl vinyl = vinylRepository.findOne(vinylId);		
		Item item = new Item(vinylToCartDto.getQuantity(),cart,vinyl);		
		itemRepository.save(item);
		
		updateVinylInfo(vinylRepository.findOne(vinylId), item.getQuantity());

	}
	

	private void updateVinylInfo(Vinyl vinylToAddInCart, Integer quantity) {
		vinylToAddInCart.setStock(vinylToAddInCart.getStock() - quantity);
		vinylRepository.save(vinylToAddInCart);
	}
	

	private Cart findUserActiveCart(AddVinylToCartDto vinylToCartDto) {
		return cartRepository.findByAccount(vinylToCartDto.getToken().getAccount());
	}
	

	private AddVinylToCartDto processRequestBody(Integer vinylId, Object requestBody){
		int quantity = Integer.parseInt((String) ((Map) requestBody).get("quantity"));
		String tokenHash = (String) ((Map) requestBody).get("token");
		Token token = tokenRepository.findByHash(tokenHash);
		
		return new AddVinylToCartDto(vinylId, quantity, token);
	}
	
}