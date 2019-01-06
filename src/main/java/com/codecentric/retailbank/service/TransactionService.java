package com.codecentric.retailbank.service;

import com.codecentric.retailbank.model.domain.Transaction;
import com.codecentric.retailbank.repository.TransactionRepository;
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

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private TransactionRepository repo;


    public TransactionService() {
        super();
    }


    @Override
    public Transaction getById(Long id) {
        Transaction transaction = repo.getOne(id);
        return transaction;
    }

    @Override
    public Transaction getByDetails(String details) {
        Transaction transaction = repo.findByDetails(details);
        return transaction;
    }

    @Override
    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = repo.findAll();
        return transactions;
    }

    @Override
    public Transaction addTransaction(Transaction transaction) {
        Transaction result = repo.save(transaction);
        return result;
    }

    @Override
    public Transaction updateTransaction(Transaction transaction) {
        Transaction result = repo.save(transaction);
        return result;
    }

    @Override
    public void deleteTransaction(Transaction transaction) {
        repo.delete(transaction);
    }

    @Override
    public void deleteTransaction(Long id) {
        repo.deleteById(id);
    }
}
