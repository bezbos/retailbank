package com.codecentric.retailbank.service;

import com.codecentric.retailbank.model.domain.RefTransactionType;
import com.codecentric.retailbank.repository.JDBC.RefTransactionTypeRepositoryJDBC;
import com.codecentric.retailbank.repository.SpringData.TransactionRepository;
import com.codecentric.retailbank.service.interfaces.IRefTransactionTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class RefTransactionTypeService implements IRefTransactionTypeService {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private RefTransactionTypeRepositoryJDBC refTransactionTypeRepositoryJDBC;
    @Autowired
    private TransactionRepository transactionRepository;


    @Override
    public RefTransactionType getById(Long id) {
        RefTransactionType refTransactionType = refTransactionTypeRepositoryJDBC.getSingle(id);
        return refTransactionType;
    }

    @Override
    public RefTransactionType getByCode(String code) {
        RefTransactionType refTransactionType = refTransactionTypeRepositoryJDBC.getSingleByCode(code);
        return refTransactionType;
    }

    @Override
    public List<RefTransactionType> getAllRefTransactionTypes() {
        List<RefTransactionType> refTransactionTypes = refTransactionTypeRepositoryJDBC.findAll();
        return refTransactionTypes;
    }

    @Override
    public RefTransactionType addRefTransactionType(RefTransactionType refTransactionType) {
        RefTransactionType result = refTransactionTypeRepositoryJDBC.add(refTransactionType);
        return result;
    }

    @Override
    public RefTransactionType updateRefTransactionType(RefTransactionType refTransactionType) {
        RefTransactionType result = refTransactionTypeRepositoryJDBC.update(refTransactionType);
        return result;
    }

    @Override
    public void deleteRefTransactionType(RefTransactionType refTransactionType) {
        // Delete any transactions with a FK constraint to this refTransactionType
//        List<Transaction> transactions = transactionRepository.findByType(refTransactionType);
//        transactionRepository.deleteAll(transactions);

        // Delete the actual RefTransactionType
        refTransactionTypeRepositoryJDBC.delete(refTransactionType);
    }

    @Override
    public void deleteRefTransactionType(Long id) {
        RefTransactionType refTransactionType = refTransactionTypeRepositoryJDBC.getSingle(id);

        // Delete any transactions with a FK constraint to this refTransactionType
//        List<Transaction> transactions = transactionRepository.findByType(refTransactionType);
//        transactionRepository.deleteAll(transactions);

        // Delete the actual RefTransactionType
        refTransactionTypeRepositoryJDBC.delete(refTransactionType);
    }
}
