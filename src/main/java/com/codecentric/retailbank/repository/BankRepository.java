package com.codecentric.retailbank.repository;

import com.codecentric.retailbank.model.domain.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository extends JpaRepository<Bank, Long> {
}
