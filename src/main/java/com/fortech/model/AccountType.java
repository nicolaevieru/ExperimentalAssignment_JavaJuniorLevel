package com.fortech.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class AccountType {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HIBERNATE_SEQ")
	@SequenceGenerator(name = "HIBERNATE_SEQ", sequenceName = "HIBERNATE_SEQ", allocationSize = 1)
	private Integer id;

	@Enumerated(EnumType.STRING)
	private AccountTypeEnum type;

	public AccountType() {

	}

	public AccountType(AccountTypeEnum type) {
		this.type = type;
	}

	public Integer getId() {
		return id;
	}

	public AccountTypeEnum getType() {
		return type;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setType(AccountTypeEnum typeName) {
		this.type = typeName;
	}

}
