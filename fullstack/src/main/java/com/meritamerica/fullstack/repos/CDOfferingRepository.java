package com.meritamerica.fullstack.repos;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meritamerica.fullstack.models.CDOffering;

public interface CDOfferingRepository extends JpaRepository<CDOffering, Long> {
	
	List<CDOffering> findById(long id);

	List<CDOffering> findAll();

}
