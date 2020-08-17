package com.meritamerica.fullstack.models;

import javax.persistence.Entity;

@Entity
public class RolloverIRA extends BankAccount {
	static final double DEFAULT_INTEREST_RATE = .01;
	
	private long accountholder;
	

	
	public RolloverIRA() { 
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
