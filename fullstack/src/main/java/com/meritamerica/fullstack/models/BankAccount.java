package com.meritamerica.fullstack.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;

import com.meritamerica.fullstack.exceptions.ExceedsAvailableBalanceException;
/*
 * import javax.validation.constraints NotBlank; NotEmpty; Max
 */
import com.meritamerica.fullstack.exceptions.NegativeAmountException;

@Entity
//@MappedSuperclass
@Table(name = "bank_accounts", catalog = "test")
public class BankAccount {
	
	@Min(0)
	protected double balance=0;
	
	@DecimalMin("0.0")
	@DecimalMax("0.99999")
	protected double interestRate;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected long id;
	

	



	protected Date accountOpenedOn;
	
	
	public BankAccount() {
		this.accountOpenedOn = new Date();
	}
	
	
	
	

	public void withdraw(double ammount) {
		balance-= ammount;
	}
	
	
	
	
	public double futureValue(int years) {
		if(years == 0) { return this.balance; }
		return futureValue(years - 1) * (1 + this.interestRate);
	}

	
	public double getBalance() { return balance; }
	public double getInterestRate() { return interestRate; }
	public long getid() { return id; }
	public Date getAccountOpenedOn() { return  accountOpenedOn; }

	public void setBalance(double balance) { this.balance = balance; }
	public void setInterestRate(double interestRate) { this.interestRate = interestRate; }
	public void setid(long accountNumber) { this.id = accountNumber; }
	public void setAccountOpenedOn(Date accountOpenedOn) { this.accountOpenedOn = accountOpenedOn; }

	
	public CDOffering getBestOffering(List<CDOffering> offering) {
		if(offering.isEmpty()) {return null;}
		double largestGain=0;
		double profit =0;
		int index=0;
		for( int i = 0; i<offering.size(); i++) {
			CDOffering temp = offering.get(i);
			for (int j = 0; j < temp.getTerm(); j++) {
				profit += balance * temp.getInterestRate();
		
			}
			if(profit>largestGain) {
				largestGain = profit;
				index = offering.indexOf(temp);
			}
		}
		
		return offering.get(index);
	}

}
