package com.meritamerica.fullstack.models;

import javax.persistence.Entity;

@Entity
public class DBACheckingAccount extends BankAccount {
	static final double DEFAULT_INTEREST_RATE = .00;
	
	private long accountholder;
	

	
	public DBACheckingAccount() { 
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
