package com.fortech.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import com.fortech.model.dto.AccountCreateDto;
import com.fortech.service.listener.AccountListener;

@Entity
@EntityListeners(AccountListener.class)
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HIBERNATE_SEQ")
	@SequenceGenerator(name = "HIBERNATE_SEQ", sequenceName = "HIBERNATE_SEQ", allocationSize = 1)
	private Integer id;

	@Column(name = "PASSWORD_HASH")
	private String passwordHash;

	private String email;
	private String firstName;
	private String lastName;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "status_id")
	private AccountStatus accountStatus;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "type_id")
	private AccountType accountType;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "account", fetch = FetchType.EAGER)
	List<Cart> carts;

	public List<Cart> getCarts() {
		return carts;
	}

	public void setCarts(List<Cart> carts) {
		this.carts = carts;
	}

	public Account() {
	}

	public Account(AccountCreateDto toCopy) {
		this.firstName = toCopy.getFirstName();
		this.lastName = toCopy.getLastName();
		this.email = toCopy.getEmail();
		this.passwordHash = toCopy.getPassword();
		this.accountType = new AccountType(AccountTypeEnum.CUSTOMER);
		this.accountStatus = new AccountStatus(AccountStatusEnum.ACTIVE);
	}

	public AccountStatus getAccountStatus() {
		return accountStatus;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public String getEmail() {
		return email;
	}

	public String getFirstName() {
		return firstName;
	}

	public Integer getId() {
		return id;
	}

	public String getLastName() {
		return lastName;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setAccountStatus(AccountStatus accountStatus) {
		this.accountStatus = accountStatus;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

}
