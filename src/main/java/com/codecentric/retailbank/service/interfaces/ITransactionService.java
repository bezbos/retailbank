package com.codecentric.retailbank.service.interfaces;

import com.codecentric.retailbank.model.domain.OLD.Transaction;

import java.util.List;

public interface ITransactionService {

    // GET
    Transaction getById(Long id);

    Transaction getByDetails(String details);

    List<Transaction> getAllTransactions();

    // CREATE
    Transaction addTransaction(Transaction transaction);

    // UPDATE
    Transaction updateTransaction(Transaction transaction);

    // DELETE
    void deleteTransaction(Transaction transaction);

    void deleteTransaction(Long id);
}
