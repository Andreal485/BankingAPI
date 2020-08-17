package com.meritamerica.fullstack.models;

import javax.persistence.Entity;

@Entity
public class RothIRA extends BankAccount {
	static final double DEFAULT_INTEREST_RATE = .01;
	
	private long accountholder;
	

	
	public RothIRA() { 
		super();
		super.setInterestRate(DEFAULT_INTEREST_RATE);
	}
	public long getAccountholder() {
		return accountholder;
	}




	public void setAccountholder(Long accountholder) {
		this.accountholder = accountholder;
	
	}

}
