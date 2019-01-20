package com.codecentric.retailbank.service;

import com.codecentric.retailbank.model.domain.RefAccountType;
import com.codecentric.retailbank.repository.JDBC.RefAccountTypeRepositoryJDBC;
import com.codecentric.retailbank.repository.SpringData.BankAccountRepository;
import com.codecentric.retailbank.repository.SpringData.TransactionRepository;
import com.codecentric.retailbank.service.interfaces.IRefAccountTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class RefAccountTypeService implements IRefAccountTypeService {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private RefAccountTypeRepositoryJDBC refAccountTypeRepositoryJDBC;
    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private TransactionRepository transactionRepository;


    @Override
    public RefAccountType getById(Long id) {
        RefAccountType refAccountType = refAccountTypeRepositoryJDBC.getSingle(id);
        return refAccountType;
    }

    @Override
    public RefAccountType getByCode(String code) {
        RefAccountType refAccountType = refAccountTypeRepositoryJDBC.getSingleByCode(code);
        return refAccountType;
    }

    @Override
    public List<RefAccountType> getAllRefAccountTypes() {
        List<RefAccountType> refAccountTypes = refAccountTypeRepositoryJDBC.findAll();
        return refAccountTypes;
    }

    @Override
    public RefAccountType addRefAccountType(RefAccountType refAccountType) {
        RefAccountType result = refAccountTypeRepositoryJDBC.add(refAccountType);
        return result;
    }

    @Override
    public RefAccountType updateRefAccountType(RefAccountType refAccountType) {
        RefAccountType result = refAccountTypeRepositoryJDBC.update(refAccountType);
        return result;
    }

    @Override
    public void deleteRefAccountType(RefAccountType refAccountType) {
        // Recursively find and delete any FK constraints to this refAccountType
//        bankAccountRepository.findByType(refAccountType).forEach(bankAccount ->{
//            transactionRepository.findByAccount(bankAccount).forEach(transaction ->{
//                transactionRepository.delete(transaction);
//            });
//            bankAccountRepository.delete(bankAccount);
//        });

        // Delete the actual refAccountType
        refAccountTypeRepositoryJDBC.delete(refAccountType);
    }

    @Override
    public void deleteRefAccountType(Long id) {
        RefAccountType refAccountType = refAccountTypeRepositoryJDBC.getSingle(id);

        // Recursively find and delete any FK constraints to this refAccountType
//        bankAccountRepository.findByType(refAccountType).forEach(bankAccount ->{
//            transactionRepository.findByAccount(bankAccount).forEach(transaction ->{
//                transactionRepository.delete(transaction);
//            });
//            bankAccountRepository.delete(bankAccount);
//        });

        // Delete the actual refAccountType
        refAccountTypeRepositoryJDBC.delete(refAccountType);
    }
}
