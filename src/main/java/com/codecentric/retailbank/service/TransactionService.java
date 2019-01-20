package com.codecentric.retailbank.service;

import com.codecentric.retailbank.model.domain.Transaction;
import com.codecentric.retailbank.repository.JDBC.TransactionRepositoryJDBC;
import com.codecentric.retailbank.service.interfaces.ITransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class TransactionService implements ITransactionService {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private TransactionRepositoryJDBC transactionRepositoryJDBC;


    @Override
    public Transaction getById(Long id) {
        Transaction transaction = transactionRepositoryJDBC.getSingle(id);
        return transaction;
    }

    @Override
    public Transaction getByDetails(String details) {
        Transaction transaction = transactionRepositoryJDBC.getSingleByDetails(details);
        return transaction;
    }

    @Override
    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = transactionRepositoryJDBC.findAll();
        return transactions;
    }

    @Override
    public Transaction addTransaction(Transaction transaction) {
        Transaction result = transactionRepositoryJDBC.add(transaction);
        return result;
    }

    @Override
    public Transaction updateTransaction(Transaction transaction) {
        Transaction result = transactionRepositoryJDBC.update(transaction);
        return result;
    }

    @Override
    public void deleteTransaction(Transaction transaction) {
        transactionRepositoryJDBC.delete(transaction);
    }

    @Override
    public void deleteTransaction(Long id) {
        transactionRepositoryJDBC.deleteById(id);
    }
}
