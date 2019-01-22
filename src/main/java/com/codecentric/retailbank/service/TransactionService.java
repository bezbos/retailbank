package com.codecentric.retailbank.service;

import com.codecentric.retailbank.model.domain.Transaction;
import com.codecentric.retailbank.repository.TransactionRepository;
import com.codecentric.retailbank.service.interfaces.ITransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TransactionService implements ITransactionService {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private TransactionRepository transactionRepository;


    @Override public Transaction getById(Long id) {
        Transaction transaction = transactionRepository.getSingle(id);
        return transaction;
    }

    @Override public Transaction getByDetails(String details) {
        Transaction transaction = transactionRepository.getSingleByDetails(details);
        return transaction;
    }

    @Override public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = transactionRepository.findAll();
        return transactions;
    }

    @Override public Transaction addTransaction(Transaction transaction) {
        Transaction result = transactionRepository.add(transaction);
        return result;
    }

    @Override public Transaction updateTransaction(Transaction transaction) {
        Transaction result = transactionRepository.update(transaction);
        return result;
    }

    @Override public void deleteTransaction(Transaction transaction) {
        transactionRepository.delete(transaction);
    }

    @Override public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }
}
