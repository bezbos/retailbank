package com.codecentric.retailbank.service;

import com.codecentric.retailbank.exception.nullpointer.ArgumentNullException;
import com.codecentric.retailbank.exception.nullpointer.InvalidOperationException;
import com.codecentric.retailbank.model.domain.Transaction;
import com.codecentric.retailbank.repository.TransactionRepository;
import com.codecentric.retailbank.repository.helpers.ListPage;
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

    //region FIELDS
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final TransactionRepository transactionRepository;
    //endregion

    //region CONSTRUCTOR
    @Autowired public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }
    //endregion


    //region READ
    @Override public Transaction getById(Long id) {
        Transaction transaction = transactionRepository.single(id);
        return transaction;
    }

    @Override public Transaction getByDetails(String details) {
        Transaction transaction = null;
        try {
            transaction = transactionRepository.singleByDetails(details);
        } catch (ArgumentNullException e) {
            e.printStackTrace();
        } catch (InvalidOperationException e) {
            LOGGER.warn("Handled an \"InvalidOperationException\". Returning the first element from multiple elements.", e);
            return (Transaction) e.getPreservedData();
        }
        return transaction;
    }

    @Override public ListPage<Transaction> getAllTransactions(int pageIndex, int pageSize) {
        ListPage<Transaction> transactions = transactionRepository.allRange(pageIndex, pageSize);
        return transactions;
    }

    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = transactionRepository.all();
        return transactions;
    }
    //endregion

    //region WRITE
    @Override public Transaction addTransaction(Transaction transaction) {
        Transaction result = transactionRepository.add(transaction);
        return result;
    }

    @Override public Transaction updateTransaction(Transaction transaction) {
        Transaction result = transactionRepository.update(transaction);
        return result;
    }
    //endregion

    //region DELETE
    @Override public void deleteTransaction(Transaction transaction) {
        transactionRepository.delete(transaction);
    }

    @Override public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }
    //endregion
}
