package com.meritamerica.fullstack.models;

import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.meritamerica.fullstack.exceptions.ExceedsAvailableBalanceException;
import com.meritamerica.fullstack.exceptions.NegativeAmountException;
@Entity
@Table(name = "transaction", catalog = "test")
public class Transactions {
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name ="id", referencedColumnName = "id")
	private BankAccount source;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name ="id", referencedColumnName = "id")
	private BankAccount target;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private long id; 
	
	public BankAccount getSource() {
		return source;
	}

	public void setSource(BankAccount source) {
		this.source = source;
	}

	public BankAccount getTarget() {
		return target;
	}

	public void setTarget(BankAccount target) {
		this.target = target;
	}
	public long getTargetid() {
		return target.id;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	private double balance;
	
	public long getSourceid() {
		return source.id;
	}



	public boolean withdraw() 
			throws ExceedsAvailableBalanceException, NegativeAmountException {
		if(balance <= 0) { throw new NegativeAmountException(); }
		if(balance > source.balance) { throw new ExceedsAvailableBalanceException(); }

		source.balance -= balance;
		return true;
	}

	public boolean deposit() throws NegativeAmountException {
		if(balance <= 0) { throw new NegativeAmountException(); }

		source.balance += balance;
		return true;
	}

	public boolean depositClose() throws NegativeAmountException {
		if(balance <= 0) { throw new NegativeAmountException(); }

		target.balance += (balance *0.8);
		return true;
	}


	public void transfer() throws ExceedsAvailableBalanceException, NegativeAmountException {

		if(balance <= 0) { throw new NegativeAmountException(); }
		if(balance > source.balance) { throw new ExceedsAvailableBalanceException(); }
		withdraw();
		target.balance += balance;
	}

	public void closeAccount() throws ExceedsAvailableBalanceException, NegativeAmountException {
		balance = source.balance;
		if(source instanceof RolloverIRA|| source instanceof RothIRA || source instanceof StandardIRA) {
			
			source.balance = 0;
			target.balance += (.08*balance);
		} else if (source instanceof DBACheckingAccount|| source instanceof PersonalCheckingAccount || source instanceof CDAccount) {

			
			source.balance =0;
			target.balance+= balance;
			

		}
		else {
		
			source.balance=0;
			target.balance +=balance;
			
		}
	}

	public void transfer(Optional<BankAccount> from, Optional<BankAccount> to, double balance) throws ExceedsAvailableBalanceException, NegativeAmountException {
		source = from.get();
		 target = to.get();
		transfer();


	}
	public void closeAccount(Optional<BankAccount >closing, Optional<BankAccount >staying) throws ExceedsAvailableBalanceException, NegativeAmountException{
		 source = closing.get();
		 target = staying.get();
		closeAccount();

	}
	public void deposit(Optional<BankAccount> account, double amount) throws NegativeAmountException{
		if(account.isPresent()) {
		source = account.get();
		deposit();
		}
	}

	public void withdraw(Optional<BankAccount> account, double amount) throws NegativeAmountException, ExceedsAvailableBalanceException{
		source = account.get();
		withdraw();
	}

}






