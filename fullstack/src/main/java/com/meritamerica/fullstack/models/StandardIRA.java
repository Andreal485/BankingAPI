package com.meritamerica.fullstack.models;

import javax.persistence.Entity;

@Entity
public class StandardIRA extends BankAccount {
	static final double DEFAULT_INTEREST_RATE = .01;
	
	private long accountholder;
	

	
	public StandardIRA() { 
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
