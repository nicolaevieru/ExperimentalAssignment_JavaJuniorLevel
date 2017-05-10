package com.fortech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fortech.model.Item;
import com.fortech.model.Token;
import com.fortech.repository.ItemRepository;
import com.fortech.service.exception.BadRequestException;
import com.fortech.service.exception.UnauthorizedException;
import com.fortech.service.validator.TokenValidator;
import com.fortech.service.validator.Validator;

@Service("itemService")
public class ItemServiceImpl implements ItemService {

	@Autowired
	ItemRepository itemRepository;

	@Autowired
	TokenService tokenService;

	@Override
	public void deleteItem(String tokenHash, Integer itemId, Integer userId) {

		Token token = tokenService.findByHash(tokenHash);
		if (token == null) {
			throw new UnauthorizedException("Invalid token");
		}

		Item itemToBeDeleted = itemRepository.findByIdInActiveCart(itemId, userId);
		if (itemToBeDeleted == null) {
			throw new BadRequestException("Invalid item");
		}

		Validator<Token> validator = new TokenValidator(token, userId);
		validator.validate();
		deleteItem(itemId);
	}

	@Override
	public void findOne() {

	}

	@Override
	public void deleteItem(Integer id) {
		itemRepository.delete(id);
	}

}
