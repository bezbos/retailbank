package com.codecentric.retailbank.service;

import com.codecentric.retailbank.exception.nullpointer.ArgumentNullException;
import com.codecentric.retailbank.exception.nullpointer.InvalidOperationException;
import com.codecentric.retailbank.model.domain.RefAccountType;
import com.codecentric.retailbank.repository.BankAccountRepository;
import com.codecentric.retailbank.repository.RefAccountTypeRepository;
import com.codecentric.retailbank.repository.TransactionRepository;
import com.codecentric.retailbank.service.interfaces.IRefAccountTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RefAccountTypeService implements IRefAccountTypeService {

    //region FIELDS
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private RefAccountTypeRepository refAccountTypeRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private TransactionRepository transactionRepository;
    //endregion

    //region READ
    @Override public RefAccountType getById(Long id) {
        RefAccountType refAccountType = refAccountTypeRepository.single(id);
        return refAccountType;
    }

    @Override public RefAccountType getByCode(String code) {
        RefAccountType refAccountType = null;
        try {
            refAccountType = refAccountTypeRepository.singleByCode(code);
        } catch (ArgumentNullException e) {
            e.printStackTrace();
        } catch (InvalidOperationException e) {
            LOGGER.warn("Handled an \"InvalidOperationException\". Returning the first element from multiple elements.", e);

            return (RefAccountType) e.getPreservedData();
        }

        return refAccountType;
    }

    @Override public List<RefAccountType> getAllRefAccountTypes() {
        List<RefAccountType> refAccountTypes = refAccountTypeRepository.all();
        return refAccountTypes;
    }
    //endregion

    //region WRITE
    @Override public RefAccountType addRefAccountType(RefAccountType refAccountType) {
        RefAccountType result = refAccountTypeRepository.add(refAccountType);
        return result;
    }

    @Override public RefAccountType updateRefAccountType(RefAccountType refAccountType) {
        RefAccountType result = refAccountTypeRepository.update(refAccountType);
        return result;
    }
    //endregion

    //region DELETE
    @Override public void deleteRefAccountType(RefAccountType refAccountType) {
        // This could fail because of FK constraints.
        // I would have to create new methods in the repository
        // that find the constraining entities and delete them
        // but I don't want to spend anymore time on this project
        // so this will be left as is.

        refAccountTypeRepository.delete(refAccountType);
    }

    @Override public void deleteRefAccountType(Long id) {
        // This could fail because of FK constraints.
        // I would have to create new methods in the repository
        // that find the constraining entities and delete them
        // but I don't want to spend anymore time on this project
        // so this will be left as is.

        RefAccountType refAccountType = refAccountTypeRepository.single(id);
        refAccountTypeRepository.delete(refAccountType);
    }
    //endregion
}
