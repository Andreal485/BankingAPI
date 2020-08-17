package com.meritamerica.fullstack.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transaction;
import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.meritamerica.fullstack.exceptions.AccountLimitExceededException;
import com.meritamerica.fullstack.exceptions.ExceedsAvailableBalanceException;
import com.meritamerica.fullstack.exceptions.NegativeAmountException;
import com.meritamerica.fullstack.exceptions.NotFoundException;
import com.meritamerica.fullstack.models.*;
import com.meritamerica.fullstack.repos.*;
import com.meritamerica.fullstack.security.JwtUtil;
import com.meritamerica.fullstack.security.MyUserDetailsServices;

@RestController
public class MeritBankController {
	List<String> strings = new ArrayList<String>(); 
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private MyUserDetailsServices userDetailsServices;
	@Autowired
	private JwtUtil jwtTokenUtil;
	@Autowired
	private AccountHolderContactDetailsRepository accountHolderContactRepo;
	@Autowired
	private AccountHolderRepository accountHolderRepo;
	@Autowired
	private BankAccountRepository bankAccountRepo;
	@Autowired
	private CDOfferingRepository cdOfferingRepo;
	//@Autowired
	//private PersonalCheckingAccountRepository bankAccountRepo;
	//@Autowired
	//private SavingsAccountRepository bankAccountRepo;
	@Autowired
	private UsersRepository usersRepository;
	//@Autowired
	//private DBACheckingAccountRepository bankAccountRepo;
	//@Autowired
	//private RolloverIRARepository bankAccountRepo;
	//@Autowired
	//private RothIRARepository bankAccountRepo;
	//@Autowired
	//private StandardIRARepository bankAccountRepo;
	@Autowired
	TransactionsRepository  transactionsRepo;
	

	//List<AccountHolder> ac = new ArrayList<AccountHolder>(); 

	//List<CheckingAccount> ca = new ArrayList<CheckingAccount>(); 

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String test() {
		return "Welcome to MeritBank"; 
	}

	@GetMapping(value="/strings") //@RequestMapping(value = "/strings", method = RequestMethod.GET)
	public List<String> getStrings() {
		return strings; 
	}

	@PostMapping(value = "/strings")
	//http://localhost:8080/strings POST, insert, then GET, you should see all inputs from prior
	public String addString(@RequestBody String string) {
		//String string = "test"; 
		strings.add(string); 
		return string; 

	} 

	@GetMapping(value = "/AccountHolders")

	public List<AccountHolder> getAccountHolders(){

		return accountHolderRepo.findAll();

	}

	@PostMapping(value = "/AccountHolders")
	@ResponseStatus(HttpStatus.CREATED)
	public AccountHolder addAccountHolder(@RequestBody @Valid AccountHolder accountHolder) {
		Users users = usersRepository.getOne(accountHolder.getAccountUserId());
		users.setAccount(accountHolder);
		accountHolder.setUser(users);
		accountHolderRepo.save(accountHolder);
		return accountHolder; 
	}

	@PostMapping(value = "/ContactDetails")
	@ResponseStatus(HttpStatus.CREATED)
	public void addContactDetails(@RequestBody @Valid AccountHolderContactDetails contactDetails) {
		accountHolderContactRepo.save(contactDetails);
	}

	@GetMapping(value = "/ContactDetails")
	public List<AccountHolderContactDetails> getContactDetails(){
		return accountHolderContactRepo.findAll();
	}

	//Check URL Mapping

	@GetMapping(value = "/AccountHolders/{id}")
	public AccountHolder getACById(@PathVariable (name = "id" )long id)  throws NotFoundException {
		//if (id > ac.size() -1) {
		//throw new NotFoundException ("invalid id"); 
		return accountHolderRepo.findById(id); 
	}

	@PostMapping(value ="/AccountHolders/{id}/DBACheckingAccounts")
	@ResponseStatus(HttpStatus.CREATED)
	public BankAccount addCheckingAccount(@RequestBody @Valid DBACheckingAccount checkingAccount, @PathVariable
			(name = "id") long id) throws NotFoundException{
		AccountHolder a = accountHolderRepo.findById(id); 
		a.addDBACheckingAccount(checkingAccount);
		bankAccountRepo.save(checkingAccount); 
		return checkingAccount; 
	}
	@GetMapping(value = "/AccountHolders/{id}/DBACheckingAccounts")
	@ResponseStatus(HttpStatus.OK)
	public Optional<BankAccount> getDBACheckingAccount(@PathVariable (name = "id") long id) throws NotFoundException {
		return bankAccountRepo.findByid(id);


	
	}

	
	@PostMapping(value ="/AccountHolders/{id}/PersonalCheckingAccounts")
	@ResponseStatus(HttpStatus.CREATED)
	public BankAccount addCheckingAccount(@RequestBody @Valid PersonalCheckingAccount checkingAccount, @PathVariable
			(name = "id") long id) throws NotFoundException, AccountLimitExceededException{
		AccountHolder a = accountHolderRepo.findById(id); 
		a.addPersonalCheckingAccount(checkingAccount);
		bankAccountRepo.save(checkingAccount); 
		return checkingAccount; 
	}


	@GetMapping(value = "/AccountHolders/{id}/PersonalCheckingAccount")
	@ResponseStatus(HttpStatus.OK)
	public PersonalCheckingAccount getPersonalCheckingAccount(@PathVariable (name = "id") long id) throws NotFoundException {
		return accountHolderRepo.findById(id).getPersonalCheckingAccount();

		//AccountHolder a = accountHolderRepo.findById(id); 
		//return a.getCheckingAccounts(); 
	}
//	@PostMapping(value ="/AccountHolders/{id}/SavingsAccounts")
//	@ResponseStatus(HttpStatus.CREATED)
//	public SavingsAccount addSavingsAccount(@RequestBody @Valid SavingsAccount savingsAccount, @PathVariable
//			(name = "id") long id) throws NotFoundException{
//		AccountHolder a = accountHolderRepo.findById(id);
//		a.addSavingsAccount(savingsAccount); 
//		savingsAccountRepo.save(savingsAccount);
//		return savingsAccount; 
//	}

	@GetMapping(value = "/AccountHolders/{id}/SavingsAccounts")
	@ResponseStatus(HttpStatus.OK)
	public Optional<BankAccount> getSavingsAccount(@PathVariable (name = "id") long id) throws NotFoundException {
		return bankAccountRepo.findByid(id);


		//AccountHolder a = accountHolderRepo.findById(id); 
		//return a.getSavingsAccounts(); 
	}

	@PostMapping(value ="/AccountHolders/{id}/CDAccounts")
	@ResponseStatus(HttpStatus.CREATED)
	public CDAccount addCDAccount(@RequestBody @Valid CDAccount cdAccount, @PathVariable
			(name = "id") long id) throws NotFoundException{
		AccountHolder a = accountHolderRepo.findById(id); 
		a.addCDAccount(cdAccount);
		bankAccountRepo.save(cdAccount);

		return cdAccount; 
	}

	@GetMapping(value = "/AccountHolders/{id}/CDAccounts")
	@ResponseStatus(HttpStatus.OK)
	public Optional<BankAccount> getCDAccount(@PathVariable (name = "id") long id) throws NotFoundException {
		return bankAccountRepo.findByid(id);

		//AccountHolder a = accountHolderRepo.findById(id); 

		//return a.getCDAccounts(); 
	}

	@PostMapping(value ="/CDOffering")
	@ResponseStatus(HttpStatus.CREATED)
	public CDOffering addCDOffering(@RequestBody @Valid CDOffering cdOffering) {
		cdOfferingRepo.save(cdOffering); 
		return cdOffering; 
	}
	
	
	@PostMapping(value = "/")

	@GetMapping(value = "/CDOffering")
	@ResponseStatus(HttpStatus.OK)
	public List<CDOffering> getCDOffering() throws NotFoundException {
		return cdOfferingRepo.findAll(); 
	}
	
	@GetMapping(value = "Me/CDAccounts/{id}/BestOffering")
	@ResponseStatus(HttpStatus.OK)
	public CDOffering getBestCDOffering(@PathVariable (name = "id") long id) throws NotFoundException {
		BankAccount optional =  bankAccountRepo.getOne(id);
		 List<CDOffering> offerings = cdOfferingRepo.findAll();
		 return optional.getBestOffering(offerings);
		 
		 
	}


	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)throws Exception
	{ 

		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())	
					);
		}
		catch (BadCredentialsException e) {
			throw new Exception("Incorrect Credentials" ,e);
		}
		final UserDetails userDetails = userDetailsServices
				.loadUserByUsername(authenticationRequest.getUsername());
		
		final String jwt = jwtTokenUtil.generateToken(userDetails);
		//If successful we will call on a 201 status and the payload in the status will pass through
		//the response.
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}
	
	@PostMapping(value = "/authenticate/CreateUser")
	public ResponseEntity<?> createUser(@RequestBody Users users){
		usersRepository.save(users);
		return ResponseEntity.ok(users);
	}
	//The following endpoints have be adjusted; there was an issues with the methods and the type mismatching.
	//The solution was to add a .longValue() after get ID because ID type was Long and we needed to get the long value 
	@GetMapping(value = "/Me")
	public AccountHolder getMe(@RequestHeader (name = "Authorization")String token){
		token = token.substring(7);
		Users users = usersRepository.findByUsername(jwtTokenUtil.extractUsername(token)).get();
		Long x = users.getAccount().getId();
		return accountHolderRepo.findById(x.longValue());
	}
	
	@PostMapping(value = "/Me/PersonalCheckingAccounts")
	public PersonalCheckingAccount addMeChecking(@RequestHeader (name = "Authorization")String token, @RequestBody PersonalCheckingAccount checking) throws NegativeAmountException, AccountLimitExceededException {
		token = token.substring(7);
		Users users = usersRepository.findByUsername(jwtTokenUtil.extractUsername(token)).get();
		AccountHolder account = accountHolderRepo.findById(users.getAccount().getId().longValue()); 
		account.addPersonalCheckingAccount(checking);
		accountHolderRepo.save(account);
		return checking; 
	}
	
	@GetMapping(value = "/Me/DBACheckingAccounts")
	public ArrayList<DBACheckingAccount> getMeDBAChecking(@RequestHeader (name = "Authorization")String token) {
		token = token.substring(7);
		Users users = usersRepository.findByUsername(jwtTokenUtil.extractUsername(token)).get();
		return accountHolderRepo.findById(users.getAccount().getId().longValue()).getDBACheckingAccounts();
	}
	@PostMapping(value = "/Me/DBACheckingAccounts")
	public DBACheckingAccount addMeDBACheckingAccount(@RequestHeader (name = "Authorization")String token, @RequestBody DBACheckingAccount account) throws NegativeAmountException {
		token = token.substring(7);
		Users users = usersRepository.findByUsername(jwtTokenUtil.extractUsername(token)).get();
		AccountHolder account1 = accountHolderRepo.findById(users.getAccount().getId().longValue());
		account1.addDBACheckingAccount(account);
		accountHolderRepo.save(account1);
		return account;
	}
	
	@GetMapping(value = "/Me/PersonalCheckingAccounts")
	public PersonalCheckingAccount getMeChecking(@RequestHeader (name = "Authorization")String token) {
		token = token.substring(7);
		Users users = usersRepository.findByUsername(jwtTokenUtil.extractUsername(token)).get();
		return accountHolderRepo.findById(users.getAccount().getId().longValue()).getPersonalCheckingAccount();
	}
	
//	@PostMapping(value = "/Me/SavingsAccounts")
//	public SavingsAccount addMeSavings(@RequestHeader (name = "Authorization")String token, @RequestBody SavingsAccount savings) throws NegativeAmountException {
//		token = token.substring(7);
//		Users users = usersRepository.findByUsername(jwtTokenUtil.extractUsername(token)).get();
//		AccountHolder account = accountHolderRepo.findById(users.getAccount().getId().longValue());
//		account.addSavingsAccount(savings);
//		accountHolderRepo.save(account);
//		return savings;
//	}
	
	@GetMapping(value = "/Me/SavingsAccounts")
	public SavingsAccount getMeSavings(@RequestHeader (name = "Authorization")String token) {
		token = token.substring(7);
		Users users = usersRepository.findByUsername(jwtTokenUtil.extractUsername(token)).get();
		return accountHolderRepo.findById(users.getAccount().getId().longValue()).getSavingsAccount();
	}
	
	@PostMapping(value = "/Me/RolloverIRA")
	public BankAccount addMeRollover(@RequestHeader (name = "Authorization")String token, @RequestBody RolloverIRA roll) throws NegativeAmountException, AccountLimitExceededException {
		token = token.substring(7);
		Users users = usersRepository.findByUsername(jwtTokenUtil.extractUsername(token)).get();
		AccountHolder account = accountHolderRepo.findById(users.getAccount().getId().longValue()); 
		account.addRolloverIRA( roll);
		accountHolderRepo.save(account);
		//bankAccountRepo.save(roll); 
		return roll; 
	}
	
	@PostMapping(value = "/Me/Transfer")
	public void Transfer(@RequestHeader (name = "Authorization")String token, @RequestBody Transactions transaction) throws  ExceedsAvailableBalanceException, NegativeAmountException {
		Optional<BankAccount> account1= bankAccountRepo.findByid(transaction.getSourceid());
		Optional<BankAccount> account2= bankAccountRepo.findByid(transaction.getTargetid());
		
		if (account1.isPresent() && account2.isPresent()) {
			transaction.setSource(account1.get());
			transaction.setTarget(account2.get());
			System.out.print(transaction.getSourceid());
			System.out.print(transaction.getTargetid());
		
			transaction.transfer();
			transactionsRepo.save(transaction);
		}
		
		
	}
	@PostMapping(value = "/Me/Close")
	public void close(@RequestHeader (name = "Authorization")String token, @RequestBody Transactions transaction) throws  ExceedsAvailableBalanceException, NegativeAmountException {
		//token = token.substring(7);
		//Users users = usersRepository.findByUsername(jwtTokenUtil.extractUsername(token)).get();
		Optional<BankAccount> account1= bankAccountRepo.findByid(transaction.getSourceid());
		Optional<BankAccount> account2= bankAccountRepo.findByid(transaction.getTargetid());
		if (account1.isPresent() && account2.isPresent()) {
			transaction.setSource(account1.get());
			transaction.setTarget(account2.get());
			//System.out.print(transaction.getSourceid());
			//System.out.print(transaction.getTargetid());
		
			transaction.closeAccount();
			transactionsRepo.save(transaction);
		}
//		transactions.closeAccount(source, target);
		
		
	}
	
	
	@PostMapping(value = "/Me/Withdraw")
	public void withdraw(@RequestHeader (name = "Authorization")String token, @RequestBody Transactions transaction) throws  ExceedsAvailableBalanceException, NegativeAmountException {
		Optional<BankAccount> account1= bankAccountRepo.findByid(transaction.getSourceid());
		if(account1.isPresent()) {
			transaction.setSource(account1.get());
			transaction.withdraw();
			transactionsRepo.save(transaction);
			}
		
		
	}
	@PostMapping(value = "/Me/Deposit")
	public void deposit(@RequestHeader (name = "Authorization")String token, @RequestBody Transactions transaction) throws  ExceedsAvailableBalanceException, NegativeAmountException {
		//token = token.substring(7);
		//Users users = usersRepository.findByUsername(jwtTokenUtil.extractUsername(token)).get();
		//AccountHolder account = accountHolderRepo.findById(users.getAccount().getId().longValue()); 
		Optional<BankAccount> account1= bankAccountRepo.findByid(transaction.getSourceid());
		System.out.print(transaction.getSourceid());
		if(account1.isPresent()) {
		transaction.setSource(account1.get());
		transaction.deposit();
		transactionsRepo.save(transaction);
		}
		
	}
	

@PostMapping(value = "/Me/FinalClose")
public void finalClose(@RequestHeader (name = "Authorization")String token, @RequestBody long sourceId , double balance) throws  ExceedsAvailableBalanceException, NegativeAmountException {
	token = token.substring(7);
	Users users = usersRepository.findByUsername(jwtTokenUtil.extractUsername(token)).get();
	//AccountHolder account = accountHolderRepo.findById(users.getAccount().getId().longValue()); 
	users.setActive(false);


	
	
}


	
	
	@GetMapping(value = "/Me/RolloverIRA")
	public RolloverIRA getMeRollover(@RequestHeader (name = "Authorization")String token) {
		token = token.substring(7);
		Users users = usersRepository.findByUsername(jwtTokenUtil.extractUsername(token)).get();
		return accountHolderRepo.findById(users.getAccount().getId().longValue()).getRolloverIRA();
	}
	
	
	@PostMapping(value = "/Me/RothIRA")
	public RothIRA addMeRoth(@RequestHeader (name = "Authorization")String token, @RequestBody RothIRA roth) throws NegativeAmountException, AccountLimitExceededException {
		token = token.substring(7);
		Users users = usersRepository.findByUsername(jwtTokenUtil.extractUsername(token)).get();
		AccountHolder account = accountHolderRepo.findById(users.getAccount().getId().longValue()); 
		account.addRothIRA(roth);
		accountHolderRepo.save(account);
		return roth; 
	}
	
	@GetMapping(value = "/Me/RothIRA")
	public RothIRA getMeRoth(@RequestHeader (name = "Authorization")String token) {
		token = token.substring(7);
		Users users = usersRepository.findByUsername(jwtTokenUtil.extractUsername(token)).get();
		return accountHolderRepo.findById(users.getAccount().getId().longValue()).getRothIRA();
	}
	
	@PostMapping(value = "/Me/StandardIRA")
	public BankAccount addMeStandard(@RequestHeader (name = "Authorization")String token, @RequestBody StandardIRA standard) throws NegativeAmountException, AccountLimitExceededException {
		token = token.substring(7);
		Users users = usersRepository.findByUsername(jwtTokenUtil.extractUsername(token)).get();
		AccountHolder account = accountHolderRepo.findById(users.getAccount().getId().longValue()); 
		account.addStandardIRA( standard);
		accountHolderRepo.save(account);
		//bankAccountRepo.save(standard); 
		return standard; 
	}
	
	@GetMapping(value = "/Me/StandardIRA")
	public StandardIRA getMeStandard(@RequestHeader (name = "Authorization")String token) {
		token = token.substring(7);
		Users users = usersRepository.findByUsername(jwtTokenUtil.extractUsername(token)).get();
		return accountHolderRepo.findById(users.getAccount().getId().longValue()).getStandardIRA();
	}
	
	@PostMapping(value = "/Me/CDAccounts")
	public CDAccount addMeCDAccount(@RequestHeader (name = "Authorization")String token, @RequestBody CDAccount cdAccount) throws NegativeAmountException {
		token = token.substring(7);
		Users users = usersRepository.findByUsername(jwtTokenUtil.extractUsername(token)).get();
		AccountHolder account = accountHolderRepo.findById(users.getAccount().getId().longValue());
		account.addCDAccount(cdAccount);
		accountHolderRepo.save(account);
		return cdAccount;
	}
	
	@GetMapping(value = "/Me/CDAccounts")
	public List<CDAccount> getMeCDAccount(@RequestHeader (name = "Authorization")String token) {
		token = token.substring(7);
		Users users = usersRepository.findByUsername(jwtTokenUtil.extractUsername(token)).get();
		return accountHolderRepo.findById(users.getAccount().getId().longValue()).getCDAccounts();
	}
	
	


}