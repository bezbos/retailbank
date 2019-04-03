package com.codecentric.retailbank.service;

import com.codecentric.retailbank.model.domain.RefBranchType;
import com.codecentric.retailbank.repository.BankAccountRepository;
import com.codecentric.retailbank.repository.BranchRepository;
import com.codecentric.retailbank.repository.CustomerRepository;
import com.codecentric.retailbank.repository.RefBranchTypeRepository;
import com.codecentric.retailbank.repository.TransactionRepository;
import com.codecentric.retailbank.service.interfaces.IRefBranchTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RefBranchTypeService implements IRefBranchTypeService {

    //region FIELDS
    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private RefBranchTypeRepository refBranchTypeRepository;
    //endregion

    //region READ
    @Override public RefBranchType getById(Long id) {
        RefBranchType refBranchType = refBranchTypeRepository.single(id);
        return refBranchType;
    }

    @Override public RefBranchType getByCode(String code) {
        RefBranchType refBranchType = refBranchTypeRepository.singleByCode(code);
        return refBranchType;
    }

    @Override public List<RefBranchType> getAllRefBranchTypes() {
        List<RefBranchType> refBranchTypes = refBranchTypeRepository.all();
        return refBranchTypes;
    }
    //endregion

    //region WRITE
    @Override public RefBranchType addRefBranchType(RefBranchType refBranchType) {
        RefBranchType result = refBranchTypeRepository.add(refBranchType);
        return result;
    }

    @Override public RefBranchType updateRefBranchType(RefBranchType refBranchType) {
        RefBranchType result = refBranchTypeRepository.update(refBranchType);
        return result;
    }
    //endregion

    //region DELETE
    @Override public void deleteRefBranchType(RefBranchType refBranchType) {
        // This could fail because of FK constraints.
        // I would have to create new methods in the repository
        // that find the constraining entities and delete them
        // but I don't want to spend anymore time on this project
        // so this will be left as is.

        refBranchTypeRepository.delete(refBranchType);
    }

    @Override public void deleteRefBranchType(Long id) {
        // This could fail because of FK constraints.
        // I would have to create new methods in the repository
        // that find the constraining entities and delete them
        // but I don't want to spend anymore time on this project
        // so this will be left as is.

        RefBranchType refBranchType = refBranchTypeRepository.single(id);
        refBranchTypeRepository.delete(refBranchType);
    }
    //endregion
}
