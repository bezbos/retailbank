package com.codecentric.retailbank.service;

import com.codecentric.retailbank.model.domain.RefAccountType;
import com.codecentric.retailbank.repository.BankAccountRepository;
import com.codecentric.retailbank.repository.RefAccountTypeRepository;
import com.codecentric.retailbank.repository.TransactionRepository;
import com.codecentric.retailbank.service.interfaces.IRefAccountTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RefAccountTypeService implements IRefAccountTypeService {

    //region FIELDS
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    //endregion

    //region REPOSITORIES
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
        RefAccountType refAccountType = refAccountTypeRepository.singleByCode(code);
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
        // Recursively find and delete any FK constraints to this refAccountType
//        bankAccountRepository.findByType(refAccountType).forEach(bankAccount ->{
//            transactionRepository.findByAccount(bankAccount).forEach(transaction ->{
//                transactionRepository.delete(transaction);
//            });
//            bankAccountRepository.delete(bankAccount);
//        });

        // Delete the actual refAccountType
        refAccountTypeRepository.delete(refAccountType);
    }

    @Override public void deleteRefAccountType(Long id) {
        RefAccountType refAccountType = refAccountTypeRepository.single(id);

        // Recursively find and delete any FK constraints to this refAccountType
//        bankAccountRepository.findByType(refAccountType).forEach(bankAccount ->{
//            transactionRepository.findByAccount(bankAccount).forEach(transaction ->{
//                transactionRepository.delete(transaction);
//            });
//            bankAccountRepository.delete(bankAccount);
//        });

        // Delete the actual refAccountType
        refAccountTypeRepository.delete(refAccountType);
    }
    //endregion
}
