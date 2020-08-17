package com.meritamerica.fullstack.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meritamerica.fullstack.models.Transactions;

public interface TransactionsRepository extends JpaRepository<Transactions, Long> {
public Transactions findByid(long id);
public List<Transactions> findAll();
}
