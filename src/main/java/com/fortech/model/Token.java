package com.fortech.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class Token {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HIBERNATE_SEQ")
	@SequenceGenerator(name = "HIBERNATE_SEQ", sequenceName = "HIBERNATE_SEQ", allocationSize=1)
	Integer id;
	
	private String hash;
	
	@OneToOne
	@JoinColumn(name = "account_id")
	private Account account;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

}
