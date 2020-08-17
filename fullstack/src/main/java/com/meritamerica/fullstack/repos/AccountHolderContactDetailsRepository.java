package com.meritamerica.fullstack.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meritamerica.fullstack.models.AccountHolderContactDetails;

public interface AccountHolderContactDetailsRepository extends JpaRepository<AccountHolderContactDetails, Long> {

	AccountHolderContactDetails findById(long id);

	List<AccountHolderContactDetails> findAll();
}
