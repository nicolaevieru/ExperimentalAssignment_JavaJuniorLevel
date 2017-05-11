package com.fortech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fortech.model.Cart;
import com.fortech.model.Item;
import com.fortech.model.Token;
import com.fortech.model.Vinyl;
import com.fortech.repository.CartRepository;
import com.fortech.repository.ItemRepository;
import com.fortech.repository.VinylRepository;
import com.fortech.service.exception.BadRequestException;
import com.fortech.service.exception.UnauthorizedException;
import com.fortech.service.validator.TokenValidator;
import com.fortech.service.validator.Validator;

@Service("itemService")
public class ItemServiceImpl implements ItemService {

	@Autowired
	ItemRepository itemRepository;

	@Autowired
	VinylRepository vinylRepository;

	@Autowired
	CartRepository cartRepository;

	@Autowired
	TokenService tokenService;

	@Autowired
	VinylServiceImpl vinylService;

	@Override
	public void deleteItem(String tokenHash, Integer itemId, Integer userId) {

		Token token = tokenService.findByHash(tokenHash);
		if (token == null) {
			throw new UnauthorizedException("Invalid token");
		}

		Item itemToBeDeleted = itemRepository.findOne(itemId);
		if (itemToBeDeleted == null) {
			throw new BadRequestException("Invalid item");
		}

		Validator<Token> validator = new TokenValidator(token, userId);
		validator.validate();

		deleteItem(itemToBeDeleted);

	}

	@Override
	public void findOne() {

	}

	@Override
	public void deleteItem(Item itemToBeDeleted) {

		Cart cart = cartRepository.findOne(itemToBeDeleted.getCart().getId());
		Vinyl vinyl = vinylRepository.findOne(itemToBeDeleted.getVinyl().getId());
		vinyl.setStock(itemToBeDeleted.getVinyl().getStock() + itemToBeDeleted.getQuantity());
		cart.setCost(cart.getCost() - (itemToBeDeleted.getQuantity() * vinyl.getCost()));

		vinylRepository.save(vinyl);
		cartRepository.save(cart);

		itemRepository.delete(itemToBeDeleted.getId());
	}

}
