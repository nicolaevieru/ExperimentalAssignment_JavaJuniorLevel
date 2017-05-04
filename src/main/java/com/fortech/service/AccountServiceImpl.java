package com.fortech.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fortech.model.Account;
import com.fortech.model.AccountStatus;
import com.fortech.model.AccountStatusEnum;
import com.fortech.model.AccountType;
import com.fortech.model.Cart;
import com.fortech.model.CartState;
import com.fortech.model.CartStateEnum;
import com.fortech.model.Token;
import com.fortech.model.dto.AccountCreateDto;
import com.fortech.model.dto.AccountDeleteDto;
import com.fortech.model.dto.CustomerDto;
import com.fortech.model.dto.CustomerListDto;
import com.fortech.repository.AccountRepository;
import com.fortech.repository.AccountStatusRepository;
import com.fortech.repository.AccountTypeRepository;
import com.fortech.repository.CartRepository;
import com.fortech.repository.CartStateRepository;
import com.fortech.service.exception.ForbiddenException;
import com.fortech.service.validator.AccountValidator;
import com.fortech.service.validator.DeleteValidator;
import com.fortech.service.validator.IsManagerValidator;
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
	DeleteValidator deleteValidator;
	
	@Autowired
	TokenService tokenService;
	
	@Override
	public Account save(Account account) {
		Account existingAccount = accountRepository.findByEmail(account.getEmail());
		if (existingAccount != null && existingAccount.getAccountStatus().getStatus() != AccountStatusEnum.DELETED) {
			if ( existingAccount.getAccountStatus().getStatus() != AccountStatusEnum.DELETED) {
				throw new ForbiddenException("Email address already in use");
			} else {
				existingAccount.setAccountStatus(accountStatusRepository.findByStatus(AccountStatusEnum.ACTIVE));
				return accountRepository.save(account);
			}	
		}
		AccountStatus accountStatus;
		AccountType accountType;
		accountStatus = accountStatusRepository.findByStatus(account.getAccountStatus().getStatus());
		accountType = accountTypeRepository.findByType(account.getAccountType().getType());
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
		Cart firstCart = createFirstCart(account);
		
		return savedAccount;
	}
	
	
	private Cart createFirstCart(Account account){
		CartState cartState;	
		Cart firstCart = new Cart();
		
		cartState = cartStateRepository.findByType(CartStateEnum.ACTIVE);
				
		firstCart.setAccount(account);
		firstCart.setCartState(cartState);
		
		return cartRepository.save(firstCart);
		
	}

	@Override
	public void delete(Integer id) {
		Account deletedAccount = accountRepository.findOne(id);
		AccountStatus deleteStatus = accountStatusRepository.findByStatus(AccountStatusEnum.DELETED);
		if (deleteStatus == null ) {
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
	public Account findOne(Integer id) {
		return accountRepository.findOne(id);
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
