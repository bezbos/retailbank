package com.codecentric.retailbank.repository.SpringData;

import com.codecentric.retailbank.model.domain.OLD.BankAccount;
import com.codecentric.retailbank.model.domain.OLD.RefTransactionType;
import com.codecentric.retailbank.model.domain.OLD.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Transaction findByDetails(String details);

    List<Transaction> findByAccount(BankAccount relatedBankAccount);

    List<Transaction> findByType(RefTransactionType refTransactionType);
}
