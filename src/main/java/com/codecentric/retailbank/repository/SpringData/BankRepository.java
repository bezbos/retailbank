package com.codecentric.retailbank.repository.SpringData;

import com.codecentric.retailbank.model.domain.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRepository extends JpaRepository<Bank, Long> {
    Bank findByDetails(String details);
}
