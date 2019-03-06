package com.codecentric.retailbank.service;

import com.codecentric.retailbank.exception.nullpointer.ArgumentNullException;
import com.codecentric.retailbank.exception.nullpointer.InvalidOperationException;
import com.codecentric.retailbank.model.domain.RefTransactionType;
import com.codecentric.retailbank.repository.RefTransactionTypeRepository;
import com.codecentric.retailbank.repository.TransactionRepository;
import com.codecentric.retailbank.service.interfaces.IRefTransactionTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RefTransactionTypeService implements IRefTransactionTypeService {

    //region FIELDS
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final RefTransactionTypeRepository refTransactionTypeRepository;
    private final TransactionRepository transactionRepository;
    //endregion

    //region CONSTRUCTOR
    @Autowired public RefTransactionTypeService(RefTransactionTypeRepository refTransactionTypeRepository,
                                                TransactionRepository transactionRepository) {
        this.refTransactionTypeRepository = refTransactionTypeRepository;
        this.transactionRepository = transactionRepository;
    }
    //endregion


    //region READ
    @Override public RefTransactionType getById(Long id) {
        RefTransactionType refTransactionType = refTransactionTypeRepository.single(id);
        return refTransactionType;
    }

    @Override public RefTransactionType getByCode(String code) {
        RefTransactionType refTransactionType = null;
        try {
            refTransactionType = refTransactionTypeRepository.singleByCode(code);
        } catch (ArgumentNullException e) {
            e.printStackTrace();
        } catch (InvalidOperationException e) {
            LOGGER.warn("Handled an \"InvalidOperationException\". Returning the first element from multiple elements.", e);
            return (RefTransactionType) e.getPreservedData();
        }

        return refTransactionType;
    }

    @Override public List<RefTransactionType> getAllRefTransactionTypes() {
        List<RefTransactionType> refTransactionTypes = refTransactionTypeRepository.all();
        return refTransactionTypes;
    }
    //endregion

    //region WRITE
    @Override public RefTransactionType addRefTransactionType(RefTransactionType refTransactionType) {
        RefTransactionType result = refTransactionTypeRepository.add(refTransactionType);
        return result;
    }

    @Override public RefTransactionType updateRefTransactionType(RefTransactionType refTransactionType) {
        RefTransactionType result = refTransactionTypeRepository.update(refTransactionType);
        return result;
    }
    //endregion

    //region DELETE
    @Override public void deleteRefTransactionType(RefTransactionType refTransactionType) {
        // Delete any transactions with a FK constraint to this refTransactionType
//        List<Transaction> transactions = transactionRepository.findByType(refTransactionType);
//        transactionRepository.deleteAll(transactions);

        // Delete the actual RefTransactionType
        refTransactionTypeRepository.delete(refTransactionType);
    }

    @Override public void deleteRefTransactionType(Long id) {
        RefTransactionType refTransactionType = refTransactionTypeRepository.single(id);

        // Delete any transactions with a FK constraint to this refTransactionType
//        List<Transaction> transactions = transactionRepository.findByType(refTransactionType);
//        transactionRepository.deleteAll(transactions);

        // Delete the actual RefTransactionType
        refTransactionTypeRepository.delete(refTransactionType);
    }
    //endregion
}
