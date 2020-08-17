package com.meritamerica.fullstack.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Min;

@Entity
public class CDAccount extends BankAccount {
	
	@Min(1)
	private int term;
	
	private long accountholder;
	
	@OneToOne(cascade = CascadeType.ALL)
	private CDOffering cdoffering;
	

	public CDAccount() { super(); }
	
	
//	@Override
//	public boolean withdraw(double amount) {
//		return false;
//	}
//	
//	@Override
//	public boolean deposit(double amount) {
//		return false;
//	}
	
	
	
	public int getTerm() { return this.term; }
	public void setTerm(int n) { this.term = n; }


	


	
	public long getAccountholder() {
		return accountholder;
	}




	public void setAccountholder(Long accountholder) {
		this.accountholder = accountholder;
	
	}

	public CDOffering getCdoffering() {
		return cdoffering;
	}


	public void setCdoffering(CDOffering cdoffering) {
		this.cdoffering = cdoffering;
	}
	
	

}
