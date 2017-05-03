package com.fortech.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.fortech.model.dto.AccountCreateDto;
import com.fortech.service.listener.AccountListener;

@Entity
@EntityListeners(AccountListener.class)
public class Account {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HIBERNATE_SEQ")
	@SequenceGenerator(name = "HIBERNATE_SEQ", sequenceName = "HIBERNATE_SEQ", allocationSize=1)
	private Integer id;
	@Column(name = "PASSWORD_HASH")
	private String passwordHash;
	private String email;
	private String firstName;
	private String lastName;
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "status_id")
	private AccountStatus accountStatus;
	
	public AccountStatus getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(AccountStatus accountStatus) {
		this.accountStatus = accountStatus;
	}

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "type_id")
	private AccountType accountType;
	
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((passwordHash == null) ? 0 : passwordHash.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (passwordHash == null) {
			if (other.passwordHash != null)
				return false;
		} else if (!passwordHash.equals(other.passwordHash))
			return false;
		return true;
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

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	
}
