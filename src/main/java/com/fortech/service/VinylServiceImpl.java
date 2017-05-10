package com.fortech.service;

import java.util.List;
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
import com.fortech.model.dto.VinylCanOrderListDto;
import com.fortech.model.dto.VinylCreateDto;
import com.fortech.model.dto.VinylDetailsDto;
import com.fortech.model.dto.VinylInventoryListDto;
import com.fortech.repository.CartRepository;
import com.fortech.repository.CartStateRepository;
import com.fortech.repository.ItemRepository;
import com.fortech.repository.TokenRepository;
import com.fortech.repository.VinylRepository;
import com.fortech.service.exception.BadRequestException;
import com.fortech.service.exception.NotFoundException;
import com.fortech.service.exception.UnauthorizedException;
import com.fortech.service.validator.AddVinylToCartValidator;
import com.fortech.service.validator.IsManagerValidator;
import com.fortech.service.validator.Validator;
import com.fortech.service.validator.VinylCreateValidator;
import com.fortech.service.validator.VinylUpdateValidator;;

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
	private CartStateRepository cartStateRepository;

	@Autowired
	private AddVinylToCartValidator vinylToCartValidator;

	@Autowired
	private VinylCreateValidator vinylCreateValidator;

	@Autowired
	private VinylUpdateValidator vinylUpdateValidator;

	@Override
	public Vinyl save(VinylCreateDto vinylCreateDto) {
		Vinyl vinyl = new Vinyl(vinylCreateDto);

		vinylCreateDto.setTokenObject(tokenRepository.findByHash(vinylCreateDto.getToken()));

		vinylCreateValidator.setToValidate(vinylCreateDto);
		vinylCreateValidator.validate();

		return vinylRepository.save(vinyl);
	}

	@Override
	public void addVinylToCart(Integer vinylId, Map<String, String> requestBody) {

		AddVinylToCartDto vinylToCartDto = processRequestBody(vinylId, requestBody);

		vinylToCartValidator.setToValidate(vinylToCartDto);
		vinylToCartValidator.validate();

		CartState activeCartState = cartStateRepository.findByType(CartStateEnum.ACTIVE);
		Cart cart = findUserActiveCart(vinylToCartDto, activeCartState);
		Vinyl vinyl = vinylRepository.findOne(vinylId);
		Item item;

		if ((item = itemRepository.findByVinylAndCartId(vinyl, cart.getId())) == null) {
			item = new Item(vinylToCartDto.getQuantity(), cart.getId(), vinyl);
		} else {
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

	private Cart findUserActiveCart(AddVinylToCartDto vinylToCartDto, CartState cartState) {
		return cartRepository.findByAccountAndCartState(vinylToCartDto.getToken().getAccount(), cartState);
	}

	private AddVinylToCartDto processRequestBody(Integer vinylId, Map<String, String> requestBody) {
		vinylToCartValidator.validateQuantityIsInteger(requestBody.get("quantity"));

		int quantity = Integer.parseInt(requestBody.get("quantity"));
		String tokenHash = requestBody.get("token");
		Token token = tokenRepository.findByHash(tokenHash);

		return new AddVinylToCartDto(vinylId, quantity, token);
	}

	@Override
	public VinylInventoryListDto getInventory(Token token) {
		IsManagerValidator validator = new IsManagerValidator(token);
		validator.validate();
		return new VinylInventoryListDto(vinylRepository.getInventory());
	}

	@Override
	public VinylCanOrderListDto getVinyls(String token) {

		if (tokenRepository.findByHash(token) == null) {
			throw new UnauthorizedException("token not valid");
		}

		return new VinylCanOrderListDto(vinylRepository.getVinyls());
	}

	@Override
	public void deleteVinyl(Integer id, String token) {
		Validator<Token> validator = new IsManagerValidator(tokenRepository.findByHash(token));
		validator.validate();
		Vinyl toBeDeleted = vinylRepository.findOne(id);
		if (toBeDeleted == null) {
			throw new NotFoundException("Vinyl not Found");
		}
		toBeDeleted.setAvailable(false);
		vinylRepository.save(toBeDeleted);
	}

	@Override
	public VinylDetailsDto getDetails(Integer id, String token) {
		if (tokenRepository.findByHash(token) == null) {
			throw new UnauthorizedException("token not valid");
		}

		if (vinylRepository.findOne(id) == null) {
			throw new BadRequestException("Vinyl id invalid!");
		}
		return vinylRepository.getVinylDetails(id);

	}

	@Override
	public void updateVinylInfo(Integer vinylId, VinylCreateDto vinylUpdateDto) {

		vinylUpdateDto.setTokenObject(tokenRepository.findByHash(vinylUpdateDto.getToken()));

		vinylUpdateValidator.setVinylId(vinylId);
		vinylUpdateValidator.setToValidate(vinylUpdateDto);
		vinylUpdateValidator.validate();

		Vinyl vinylToBeUpdated = vinylRepository.findOne(vinylId);
		vinylToBeUpdated.setName(vinylUpdateDto.getName());
		vinylToBeUpdated.setCost(vinylUpdateDto.getCost());
		vinylToBeUpdated.setStock(vinylUpdateDto.getStock());
		vinylRepository.save(vinylToBeUpdated);
		updateAllCarts(vinylId);
	}
	

	private void updateAllCarts(Integer id) {
		
		List<Cart> cartsToBeUpdated = cartRepository.findByVinylInActiveCart(id);

		for (Cart cart : cartsToBeUpdated) {
			Double newCartCost = 0.0;
			for (Item i: cart.getItems()) {
				newCartCost = newCartCost + (i.getQuantity()*i.getVinyl().getCost());
			}
			cart.setCost(newCartCost);
		}
		cartRepository.save(cartsToBeUpdated);
	}

}
