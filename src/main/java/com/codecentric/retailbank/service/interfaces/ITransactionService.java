package com.codecentric.retailbank.service.interfaces;

import com.codecentric.retailbank.model.domain.Transaction;

import java.util.List;

public interface ITransactionService {

    Transaction getById(Long id);

    Transaction getByDetails(String details);

    List<Transaction> getAllTransactions();

    Transaction addTransaction(Transaction transaction);

    Transaction updateTransaction(Transaction transaction);

    void deleteTransaction(Transaction transaction);

    void deleteTransaction(Long id);
}
