package com.meritamerica.fullstack.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meritamerica.fullstack.models.AccountHolder;

public interface AccountHolderRepository extends JpaRepository<AccountHolder, Long> {

	
	AccountHolder findById(long id);
}
