package com.fortech.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class AccountStatus {
	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="HIBERNATE_SEQ")
    @SequenceGenerator(name="HIBERNATE_SEQ", sequenceName="HIBERNATE_SEQ", allocationSize=1)
	private Integer id;
	
	@Enumerated(EnumType.STRING)
	private AccountStatusEnum status;
	
	public AccountStatus(AccountStatusEnum status) {
		this.status = status;
	}
	public AccountStatus() {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public AccountStatusEnum getStatus() {
		return status;
	}

	public void setStatus(AccountStatusEnum status) {
		this.status = status;
	}

}
