package com.codecentric.retailbank.repository.SpringData;

import com.codecentric.retailbank.model.domain.BankAccount;
import com.codecentric.retailbank.model.domain.Customer;
import com.codecentric.retailbank.model.domain.RefAccountStatus;
import com.codecentric.retailbank.model.domain.RefAccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    BankAccount findByDetails(String details);

    List<BankAccount> findByCustomer(Customer relatedCustomer);

    List<BankAccount> findByType(RefAccountType type);

    List<BankAccount> findByStatus(RefAccountStatus refAccountStatus);
}
