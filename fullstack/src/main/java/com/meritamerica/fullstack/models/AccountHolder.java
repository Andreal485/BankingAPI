package com.meritamerica.fullstack.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

//import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.meritamerica.fullstack.exceptions.AccountLimitExceededException;


//{
//	"firstName":"Dre",
//	"lastName":"Smith",
//	"ssn":"192485962",
//	"accountUserId":"3"
//	}
@Entity 
@Table (name = "accountholders", catalog = "test")
public class AccountHolder implements Comparable <AccountHolder> {
	
	@NotBlank(message = "First Name is required")
	private String firstName;
	
	private String middleName;
	
	@NotBlank(message = "Last Name is required")
	private String lastName;
	
	@Size(min=9, max=11)
	@NotBlank(message = "SSN is required")
	private String ssn;
	

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "account_Id")
	private Long id;
	
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "contactId", referencedColumnName = "contactId")
	private AccountHolderContactDetails accountHolderContactDetails;
	

	
	public void setUsers(Users users) {
		this.users = users;
	}

	
	
	//accountUserId is User id in Database
	//When posting, this needs to be in the DATABASE so we can connect the user and accountholder together
	//but we must know the users id at the time of implementation
	private long accountUserId;
	
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name ="user_id",referencedColumnName = "user_id")
	private Users users;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name ="account_Id", referencedColumnName = "account_Id")
	private List<BankAccount> bankAccounts;
	
	
	
	
	
	

	public Users getUsers() {
		return users;
	}

	public void setUser(Users users) {
		this.users = users;
	}


	
	//new account holder
	public AccountHolder(String firstName, String middleName, String lastName, String ssn) {
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.ssn = ssn;
		this.bankAccounts = new ArrayList <>();
	 
	}
	public AccountHolder() {
		this.bankAccounts = new ArrayList <BankAccount>();
		this.accountHolderContactDetails = new AccountHolderContactDetails();
		
	}
	
	public boolean addPersonalCheckingAccount(PersonalCheckingAccount b)throws AccountLimitExceededException {
		if(b == null ) { return false; }
		for(BankAccount a : bankAccounts) {
			if(a instanceof PersonalCheckingAccount)
				throw new AccountLimitExceededException();
		}
		bankAccounts.add(b);
		return true;
	}
	
	
	public boolean addDBACheckingAccount(DBACheckingAccount b){
		if(b == null) { return false; }
		
		bankAccounts.add(b);
		return true;
	}
	
	public boolean addRolloverIRA(RolloverIRA b)throws AccountLimitExceededException {
		if(b == null) { return false; }
		for(BankAccount a : bankAccounts) {
			if(a instanceof RolloverIRA)
				throw new AccountLimitExceededException();
		}
		bankAccounts.add(b);
		return true;
	}
	public boolean addRothIRA(RothIRA b)throws AccountLimitExceededException {
		if(b == null) { return false; }
		for(BankAccount a : bankAccounts) {
			if(a instanceof RothIRA)
				throw new AccountLimitExceededException();
		}
		bankAccounts.add(b);
		return true;
	}
	public boolean addStandardIRA(StandardIRA b)throws AccountLimitExceededException {
		if(b == null) { return false; }
		for(BankAccount a : bankAccounts) {
			if(a instanceof StandardIRA)
				throw new AccountLimitExceededException();
		}
		bankAccounts.add(b);
		return true;
	
	}
	public boolean addCDAccount(CDAccount b) {
		if(b == null) { return false; }
		bankAccounts.add(b);
		return true;
	}
	
	public String getFirstName() { return firstName; }
	public AccountHolder setFirstName(String s) { this.firstName = s; return this; }

	public String getMiddleName() { return middleName; }
	public AccountHolder setMiddleName(String s) { this.middleName = s; return this;}

	public String getLastName() { return lastName; }
	public AccountHolder setLastName(String s) { this.lastName = s; return this; }

	public List<BankAccount> getBankAccounts() {
		return bankAccounts;
	}

	public void setBankAccounts(List<BankAccount> accounts) {
		this.bankAccounts = accounts;
	}
	
	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {this.id = id;}

	public AccountHolderContactDetails getAccountHolderContactDetails() {
		return accountHolderContactDetails;
	}

	public void setAccountHolderContactDetails(AccountHolderContactDetails accountHolderContactDetails) {
		this.accountHolderContactDetails = accountHolderContactDetails;
	}
	
	public long getAccountUserId() {
		return accountUserId;
	}

	public void setAccountUserId(long accountUserId) {
		this.accountUserId = accountUserId;
	}

	@Override
	public int compareTo(AccountHolder arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	public SavingsAccount getSavingsAccount() {
		BankAccount temp = new SavingsAccount();
		for(BankAccount b : bankAccounts) {
			if(b instanceof SavingsAccount)
				temp = b;
		}
		return (SavingsAccount) temp;
	}
	
	public PersonalCheckingAccount getPersonalCheckingAccount() {
		BankAccount temp = new PersonalCheckingAccount();
		for(BankAccount b : bankAccounts) {
			if(b instanceof PersonalCheckingAccount)
				temp = b;
		}
		return (PersonalCheckingAccount) temp;
	}
	
	public  ArrayList<DBACheckingAccount> getDBACheckingAccounts() {
		ArrayList<DBACheckingAccount> temp = new ArrayList<DBACheckingAccount>();
		for(BankAccount b : bankAccounts) {
			if(b instanceof DBACheckingAccount) {
				temp.add((DBACheckingAccount) b);
			}
		}
		return temp;
	}
	
	public  ArrayList<CDAccount> getCDAccounts() {
		ArrayList<CDAccount> temp = new ArrayList<CDAccount>();
		for(BankAccount b : bankAccounts) {
			if(b instanceof CDAccount) {
				temp.add((CDAccount) b);
			}
		}
		return temp;
	}
	
	public double getBalance(long id) {
		double balance = 0;
		for(BankAccount b : bankAccounts) {
			if(b.id == id)
				balance= b.balance;
		}
		return balance;
	}
	
	public StandardIRA getStandardIRA() {
		BankAccount temp = new StandardIRA();
		for(BankAccount b : bankAccounts) {
			if(b instanceof StandardIRA)
				temp = b;
		}
		return (StandardIRA) temp;
	}
	
	public RothIRA getRothIRA() {
		BankAccount temp = new RothIRA();
		for(BankAccount b : bankAccounts) {
			if(b instanceof RothIRA)
				temp = b;
		}
		return (RothIRA) temp;
	}
	public RolloverIRA getRolloverIRA() {
		BankAccount temp = new RolloverIRA();
		for(BankAccount b : bankAccounts) {
			if(b instanceof RolloverIRA)
				temp = b;
		}
		return (RolloverIRA) temp;
	}
	
	
	
	
	
}
