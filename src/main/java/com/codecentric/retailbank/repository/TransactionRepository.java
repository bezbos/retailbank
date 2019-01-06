package com.codecentric.retailbank.repository;

import com.codecentric.retailbank.model.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Transaction findByDetails(String details);
}
