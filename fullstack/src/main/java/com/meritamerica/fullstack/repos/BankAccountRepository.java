package com.meritamerica.fullstack.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meritamerica.fullstack.models.BankAccount;


public interface BankAccountRepository  extends JpaRepository<BankAccount, Long>{
	Optional<BankAccount>  findByid(long id);

	 

}


