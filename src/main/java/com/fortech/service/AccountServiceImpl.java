package com.fortech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fortech.model.Account;
import com.fortech.model.AccountType;
import com.fortech.model.Cart;
import com.fortech.model.CartState;
import com.fortech.model.CartStateEnum;
import com.fortech.model.dto.AccountCreateDto;
import com.fortech.repository.AccountRepository;
import com.fortech.repository.AccountTypeRepository;
import com.fortech.repository.CartRepository;
import com.fortech.repository.CartStateRepository;
import com.fortech.service.exception.ForbiddenException;
import com.fortech.service.validator.AccountValidator;
import com.fortech.service.validator.Validator;

@Service("accountService")
public class AccountServiceImpl implements AccountService {

	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	AccountTypeRepository accountTypeRepository;
	
	@Autowired
	CartRepository cartRepository;
	
	@Autowired
	CartStateRepository cartStateRepository;
	
	@Override
	@Transactional
	public Account save(Account account) {
		if (accountRepository.findByEmail(account.getEmail()) != null) {
			throw new ForbiddenException("Email address already in use");
		}
		AccountType accountType;
		accountType = accountTypeRepository.findByType(account.getAccountType().getType());
		if (accountType == null) {
			accountType = accountTypeRepository.save(account.getAccountType());
		}
		
		account.setAccountType(accountType);
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
		Cart firstCart = createFirstCart(account);
		
		return savedAccount;
	}
	
	
	private Cart createFirstCart(Account account){
		CartState cartState;	
		Cart firstCart = new Cart();
		
		cartState = cartStateRepository.findByType(CartStateEnum.ACTIV);
				
		firstCart.setAccount(account);
		firstCart.setCartState(cartState);
		
		return cartRepository.save(firstCart);
		
	}

	

}
