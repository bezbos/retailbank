package com.codecentric.retailbank.service.interfaces;

import com.codecentric.retailbank.model.domain.Transaction;
import com.codecentric.retailbank.repository.helpers.ListPage;

public interface ITransactionService {

    Transaction getById(Long id);

    Transaction getByDetails(String details);

    ListPage<Transaction> getAllTransactions(int pageIndex, int pageSize);

    Transaction addTransaction(Transaction transaction);

    Transaction updateTransaction(Transaction transaction);

    void deleteTransaction(Transaction transaction);

    void deleteTransaction(Long id);
}
