package com.fortech.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fortech.model.Cart;
import com.fortech.model.CartState;
import com.fortech.model.CartStateEnum;
import com.fortech.model.Item;
import com.fortech.model.Token;
import com.fortech.model.Vinyl;
import com.fortech.model.dto.AddVinylToCartDto;
import com.fortech.model.dto.VinylCreateDto;
import com.fortech.repository.CartRepository;
import com.fortech.repository.CartStateRepository;
import com.fortech.repository.ItemRepository;
import com.fortech.repository.TokenRepository;
import com.fortech.repository.VinylRepository;
import com.fortech.service.validator.AddVinylToCartValidator;
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
	private CartStateRepository cartStateRepository;

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private AddVinylToCartValidator vinylToCartValidator;

	@Autowired
	private VinylCreateValidator vinylCreateValidator;

	@Override
	public Vinyl save(VinylCreateDto vinylCreateDto) {
		Vinyl vinyl = new Vinyl(vinylCreateDto);

		vinylCreateDto.setTokenObject(tokenRepository.findByHash(vinylCreateDto.getToken()));

		vinylCreateValidator.setToValidate(vinylCreateDto);
		vinylCreateValidator.validate();

		return vinylRepository.save(vinyl);
	}

	@Override
	public void addVinylToCart(Integer vinylId, Object requestBody) {

		AddVinylToCartDto vinylToCartDto = processRequestBody(vinylId, requestBody);

		vinylToCartValidator.setToValidate(vinylToCartDto);
		vinylToCartValidator.validate();

		CartState activeCartState = cartStateRepository.findByType(CartStateEnum.ACTIV);
		Cart cart = findUserActiveCart(vinylToCartDto,activeCartState);
		Vinyl vinyl = vinylRepository.findOne(vinylId);
		Item item;
		
		if((item = itemRepository.findByVinylAndCart(vinyl, cart)) == null){
			item = new Item(vinylToCartDto.getQuantity(), cart, vinyl);
		} else{
			item.setQuantity(item.getQuantity() + vinylToCartDto.getQuantity());
		}

		itemRepository.save(item);

		updateVinylInfo(vinyl, vinylToCartDto.getQuantity());
		updateCartInfo(cart, vinyl, vinylToCartDto.getQuantity());

	}

	private void updateCartInfo(Cart cart, Vinyl vinylToAddInCart, Integer quantity) {
		cart.setCost(cart.getCost() + (vinylToAddInCart.getCost() * quantity));
		cartRepository.save(cart);
	}

	private void updateVinylInfo(Vinyl vinylToAddInCart, Integer quantity) {
		vinylToAddInCart.setStock(vinylToAddInCart.getStock() - quantity);
		vinylRepository.save(vinylToAddInCart);
	}

	private Cart findUserActiveCart(AddVinylToCartDto vinylToCartDto,CartState cartState) {
		return cartRepository.findByAccountAndCartState(vinylToCartDto.getToken().getAccount(),cartState);
	}

	private AddVinylToCartDto processRequestBody(Integer vinylId, Object requestBody) {
		int quantity = Integer.parseInt((String) ((Map) requestBody).get("quantity"));
		String tokenHash = (String) ((Map) requestBody).get("token");
		Token token = tokenRepository.findByHash(tokenHash);

		return new AddVinylToCartDto(vinylId, quantity, token);
	}

}