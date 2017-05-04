package com.fortech.service;

import java.util.List;

import org.springframework.http.HttpHeaders;

import com.fortech.model.Account;
import com.fortech.model.dto.AccountCreateDto;
import com.fortech.model.dto.AccountDeleteDto;
import com.fortech.model.dto.CartDetailsDto;
import com.fortech.model.dto.OrderDto;

public interface AccountService {
	
	 Account save(Account toSave);
	 Account save(AccountCreateDto toSave);
	 Account findByEmail(String email);
	 Account findOne(Integer id);
	 void delete(Integer id);
	 void delete(Integer id, AccountDeleteDto credentials);
	 CartDetailsDto getCartDetails(Integer userId,HttpHeaders requestHeader);
	 List<OrderDto> getAllCustomerOrders(Integer userId,HttpHeaders requestHeader);

}
