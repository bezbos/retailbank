package com.codecentric.retailbank.repository.SpringData;

import com.codecentric.retailbank.model.domain.OLD.BankAccount;
import com.codecentric.retailbank.model.domain.OLD.Customer;
import com.codecentric.retailbank.model.domain.OLD.RefAccountStatus;
import com.codecentric.retailbank.model.domain.OLD.RefAccountType;
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
