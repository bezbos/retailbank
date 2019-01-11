package com.codecentric.retailbank.service;

import com.codecentric.retailbank.model.domain.RefTransactionType;
import com.codecentric.retailbank.model.domain.Transaction;
import com.codecentric.retailbank.repository.RefTransactionTypeRepository;
import com.codecentric.retailbank.repository.TransactionRepository;
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

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RefTransactionTypeRepository refTransactionTypeRepository;
    @Autowired
    private TransactionRepository transactionRepository;


    public RefTransactionTypeService() {
        super();
    }


    @Override
    public RefTransactionType getById(Long id) {
        RefTransactionType refTransactionType = refTransactionTypeRepository.getOne(id);
        return refTransactionType;
    }

    @Override
    public RefTransactionType getByCode(String code) {
        RefTransactionType refTransactionType = refTransactionTypeRepository.findByCode(code);
        return refTransactionType;
    }

    @Override
    public List<RefTransactionType> getAllRefTransactionTypes() {
        List<RefTransactionType> refTransactionTypes = refTransactionTypeRepository.findAll();
        return refTransactionTypes;
    }

    @Override
    public RefTransactionType addRefTransactionType(RefTransactionType refTransactionType) {
        RefTransactionType result = refTransactionTypeRepository.save(refTransactionType);
        return result;
    }

    @Override
    public RefTransactionType updateRefTransactionType(RefTransactionType refTransactionType) {
        RefTransactionType result = refTransactionTypeRepository.save(refTransactionType);
        return result;
    }

    @Override
    public void deleteRefTransactionType(RefTransactionType refTransactionType) {
        // Delete any transactions with a FK constraint to this refTransactionType
        List<Transaction> transactions = transactionRepository.findByType(refTransactionType);
        transactionRepository.deleteAll(transactions);

        // Delete the actual RefTransactionType
        refTransactionTypeRepository.delete(refTransactionType);
    }

    @Override
    public void deleteRefTransactionType(Long id) {
        RefTransactionType refTransactionType = refTransactionTypeRepository.getOne(id);

        // Delete any transactions with a FK constraint to this refTransactionType
        List<Transaction> transactions = transactionRepository.findByType(refTransactionType);
        transactionRepository.deleteAll(transactions);

        // Delete the actual RefTransactionType
        refTransactionTypeRepository.delete(refTransactionType);
    }
}
