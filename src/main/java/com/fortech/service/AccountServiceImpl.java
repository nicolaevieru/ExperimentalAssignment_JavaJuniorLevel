package com.fortech.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import com.fortech.model.Account;
import com.fortech.model.AccountStatus;
import com.fortech.model.AccountStatusEnum;
import com.fortech.model.AccountType;
import com.fortech.model.Cart;
import com.fortech.model.CartState;
import com.fortech.model.CartStateEnum;
import com.fortech.model.Item;
import com.fortech.model.Token;
import com.fortech.model.dto.AccountCreateDto;
import com.fortech.model.dto.AccountDeleteDto;
import com.fortech.model.dto.CartDetailsDto;
import com.fortech.model.dto.CustomerDto;
import com.fortech.model.dto.CustomerListDto;
import com.fortech.model.dto.ItemDto;
import com.fortech.model.dto.OrderDto;
import com.fortech.repository.AccountRepository;
import com.fortech.repository.AccountStatusRepository;
import com.fortech.repository.AccountTypeRepository;
import com.fortech.repository.CartRepository;
import com.fortech.repository.CartStateRepository;
import com.fortech.repository.ItemRepository;
import com.fortech.service.exception.BadRequestException;
import com.fortech.service.exception.ForbiddenException;
import com.fortech.service.exception.UnauthorizedException;
import com.fortech.service.validator.AccountValidator;
import com.fortech.service.validator.CartDetailsValidator;
import com.fortech.service.validator.DeleteValidator;
import com.fortech.service.validator.IsManagerValidator;
import com.fortech.service.validator.OrdersValidator;
import com.fortech.service.validator.Validator;

@Service("accountService")
@Transactional
public class AccountServiceImpl implements AccountService {

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	AccountTypeRepository accountTypeRepository;

	@Autowired
	CartRepository cartRepository;

	@Autowired
	CartStateRepository cartStateRepository;

	@Autowired
	AccountStatusRepository accountStatusRepository;

	@Autowired
	ItemRepository itemRepository;

	@Autowired
	DeleteValidator deleteValidator;

	@Autowired
	CartDetailsValidator cartDetailsValidator;

	@Autowired
	OrdersValidator ordersValidator;

	@Autowired
	TokenService tokenService;

	@Override
	public Account save(Account account) {
		Account existingAccount = accountRepository.findByEmail(account.getEmail());
		AccountStatus accountStatus = accountStatusRepository.findByStatus(account.getAccountStatus().getStatus());
		AccountType accountType = accountTypeRepository.findByType(account.getAccountType().getType());

		if (existingAccount != null && existingAccount.getAccountStatus().getStatus() != AccountStatusEnum.DELETED) {
			if (existingAccount.getAccountStatus().getStatus() != AccountStatusEnum.DELETED) {
				throw new ForbiddenException("Email address already in use");
			} else {
				existingAccount.setAccountStatus(accountStatusRepository.findByStatus(AccountStatusEnum.ACTIVE));
				return accountRepository.save(account);
			}
		}

		if (accountType == null) {
			accountType = accountTypeRepository.save(account.getAccountType());
		}
		if (accountStatus == null) {
			accountStatus = accountStatusRepository.save(account.getAccountStatus());
		}

		account.setAccountType(accountType);
		account.setAccountStatus(accountStatus);

		return accountRepository.save(account);
	}

	@Override
	public Account findByEmail(String email) {
		return accountRepository.findByEmail(email);
	}

	@Override
	public Account save(AccountCreateDto toSave) {
		Validator<AccountCreateDto> accountValidator = new AccountValidator(toSave);
		accountValidator.validate();

		Account account = new Account(toSave);
		Account savedAccount = this.save(account);
		createOpenCart(account);

		return savedAccount;
	}

	private Cart createOpenCart(Account account) {
		CartState cartState;
		Cart firstCart = new Cart();
		firstCart.setAccount(account);
		cartState = cartStateRepository.findByType(CartStateEnum.ACTIVE);
		firstCart.setCartState(cartState);
		firstCart = cartRepository.save(firstCart);
		return firstCart;
	}

	@Override
	public void delete(Integer id) {
		Account deletedAccount = accountRepository.findOne(id);
		AccountStatus deleteStatus = accountStatusRepository.findByStatus(AccountStatusEnum.DELETED);
		if (deleteStatus == null) {
			deleteStatus = accountStatusRepository.save(new AccountStatus(AccountStatusEnum.DELETED));
		}
		deletedAccount.setAccountStatus(deleteStatus);
		accountRepository.save(deletedAccount);
	}

	@Override
	public void delete(Integer id, AccountDeleteDto credentials) {
		deleteValidator.setIdToBeDeleted(id);
		deleteValidator.setToValidate(credentials);
		deleteValidator.validate();

		Token token = tokenService.findByHash(credentials.getToken());
		tokenService.delete(token.getId());
		delete(id);
	}

	@Override
	public CartDetailsDto getCartDetails(Integer userId, HttpHeaders requestHeader) {

		if (requestHeader.getFirst("token") == null) {
			throw new UnauthorizedException("token not valid!");
		}

		Token token = tokenService.findByHash(requestHeader.getFirst("token"));

		cartDetailsValidator.setToValidate(token);
		cartDetailsValidator.setUserId(userId);
		cartDetailsValidator.validate();

		return createCartDetails(userId);
	}

	private CartDetailsDto createCartDetails(Integer userId) {
		List<Item> itemList;
		List<ItemDto> itemsDetails = new ArrayList<>();
		CartDetailsDto cartDetailsResponse = new CartDetailsDto();

		Account userAccount = accountRepository.findOne(userId);
		CartState activeCartState = cartStateRepository.findByType(CartStateEnum.ACTIVE);
		Cart activeCart = cartRepository.findByAccountIdAndCartState(userAccount.getId(), activeCartState);

		itemList = itemRepository.findActiveCartItemsByAccount(userId);

		for (Item item : itemList) {
			String name = item.getVinyl().getName();
			Integer quantity = item.getQuantity();
			Double cost = item.getVinyl().getCost();

			itemsDetails.add(new ItemDto(name, quantity, cost));
		}

		cartDetailsResponse.setNumberOfItems(itemList.size());
		cartDetailsResponse.setTotalCost(activeCart.getCost());
		cartDetailsResponse.setItems(itemsDetails);

		return cartDetailsResponse;

	}

	@Override
	public List<OrderDto> getAllCustomerOrders(Integer userId, HttpHeaders requestHeader) {
		List<Cart> customerCarts;
		List<OrderDto> allCustomerOrders = new ArrayList<>();

		Token token = tokenService.findByHash(requestHeader.getFirst("token"));
		if (token == null) {
			throw new BadRequestException("invalid token!");
		}
		if (userId.intValue() != token.getAccount().getId().intValue()) {
			IsManagerValidator isManagerValidator = new IsManagerValidator();
			isManagerValidator.setToValidate(token);
			isManagerValidator.validate();
		} else {
			ordersValidator.setToValidate(token);
			ordersValidator.setUserId(userId);
			ordersValidator.validate();
		}

		Account customerAccount = accountRepository.findOne(userId);
		customerCarts = cartRepository.findByAccountId(customerAccount.getId());

		for (Cart cart : customerCarts) {
			Double cost = cart.getCost();
			Date orderDate = cart.getOrderDate();
			CartStateEnum cartState = cart.getCartState().getType();

			allCustomerOrders.add(new OrderDto(cost, orderDate, cartState));
		}

		return allCustomerOrders;
	}

	@Override
	public void placeOrder(Integer userId, HttpHeaders requestHeader) {
		Token token = tokenService.findByHash(requestHeader.getFirst("token"));

		ordersValidator.setToValidate(token);
		ordersValidator.setUserId(userId);
		ordersValidator.validate();

		CartState processingCartState = cartStateRepository.findByType(CartStateEnum.PROCESSING);
		CartState activeCartState = cartStateRepository.findByType(CartStateEnum.ACTIVE);

		Cart activeCart = findCustomerActiveCart(token, activeCartState);
		activeCart.setCartState(processingCartState);
		activeCart.setOrderDate(new Date());

		this.createOpenCart(token.getAccount());
	}

	private Cart findCustomerActiveCart(Token token, CartState activeCartState) {
		return cartRepository.findByAccountIdAndCartState(token.getAccount().getId(), activeCartState);
	}

	@Override
	public CustomerListDto getCustomers(Token token) {
		Validator<Token> validator = new IsManagerValidator(token);
		validator.validate();
		List<CustomerDto> customers = accountRepository.getCustomers();
		return new CustomerListDto(customers);
	}

	@Override
	public void deleteAll() {
		accountRepository.deleteAll();

	}

}
