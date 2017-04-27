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
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="HIBERNATE_SEQ")
    @SequenceGenerator(name="HIBERNATE_SEQ", sequenceName="HIBERNATE_SEQ", allocationSize=1)
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		AccountType other = (AccountType) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (type != other.type)
			return false;
		return true;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public AccountTypeEnum getType() {
		return type;
	}
	public void setType(AccountTypeEnum typeName) {
		this.type = typeName;
	}
	
	

}
