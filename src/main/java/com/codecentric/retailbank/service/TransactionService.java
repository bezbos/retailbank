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

import java.util.List;

@Service
public class TransactionService implements ITransactionService {

    //region FIELDS
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private TransactionRepository transactionRepository;
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

    @Override
    public Transaction createPayment(Transaction transaction) {
        Transaction result = transactionRepository.createPayment(transaction);
        return result;
    }
    //endregion

    // Transactions are strictly immutable. They can only be created and read.
}
