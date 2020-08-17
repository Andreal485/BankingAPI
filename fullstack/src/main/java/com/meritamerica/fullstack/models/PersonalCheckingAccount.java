package com.meritamerica.fullstack.models;

import javax.persistence.Entity;

import com.meritamerica.fullstack.models.BankAccount;
@Entity
public class PersonalCheckingAccount extends BankAccount {
	
private long accountholder;
	


	static final double DEFAULT_INTEREST_RATE = .0000;
	
	
	public PersonalCheckingAccount() {
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