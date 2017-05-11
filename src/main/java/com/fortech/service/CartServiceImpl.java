package com.fortech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fortech.model.Cart;
import com.fortech.model.CartState;
import com.fortech.model.CartStateEnum;
import com.fortech.model.Token;
import com.fortech.model.dto.ChangeOrderStatusDto;
import com.fortech.repository.CartRepository;
import com.fortech.repository.CartStateRepository;
import com.fortech.service.exception.BadRequestException;
import com.fortech.service.validator.CartStateValidator;
import com.fortech.service.validator.IsManagerValidator;
import com.fortech.service.validator.UpdateCartStateValidator;
import com.fortech.service.validator.Validator;

@Service("cartService")
public class CartServiceImpl implements CartService {

	@Autowired
	CartRepository cartRepository;

	@Autowired
	CartStateRepository cartStateRepository;

	@Autowired
	TokenService tokenService;

	@Override
	public void deleteAll() {
		cartRepository.deleteAll();

	}

	@Override
	public void updateState(Integer id, ChangeOrderStatusDto newStatusDto) {

		Token token = tokenService.findByHash(newStatusDto.getToken());
		Validator<ChangeOrderStatusDto> validator;

		if (cartRepository.findOne(id) == null) {
			throw new BadRequestException("invalid order");
		}
		validator = new UpdateCartStateValidator(new IsManagerValidator(token),
				new CartStateValidator(newStatusDto.getStatus()));
		validator.validate();

		updateState(id, newStatusDto.getStatus());
	}

	@Override
	public void updateState(Integer id, CartStateEnum newState) {

		Cart cart = cartRepository.findOne(id);
		CartState cartState = cartStateRepository.findByType(newState);
		cart.setCartState(cartState);
		cartRepository.save(cart);
	}

}
