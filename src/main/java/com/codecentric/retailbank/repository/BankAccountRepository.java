package com.codecentric.retailbank.repository;

import com.codecentric.retailbank.model.domain.BankAccount;
import com.codecentric.retailbank.model.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    BankAccount findByDetails(String details);

    BankAccount findByCustomer(Customer relatedCustomer);
}
