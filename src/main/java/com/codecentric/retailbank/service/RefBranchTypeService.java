package com.codecentric.retailbank.service;

import com.codecentric.retailbank.model.domain.RefBranchType;
import com.codecentric.retailbank.repository.BankAccountRepository;
import com.codecentric.retailbank.repository.BranchRepository;
import com.codecentric.retailbank.repository.CustomerRepository;
import com.codecentric.retailbank.repository.RefBranchTypeRepository;
import com.codecentric.retailbank.repository.TransactionRepository;
import com.codecentric.retailbank.service.interfaces.IRefBranchTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RefBranchTypeService implements IRefBranchTypeService {

    //region FIELDS
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    //endregion

    //region REPOSITORIES
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
//        // Recursively find and delete any FK constraints to this refBranchType
//        branchRepository.findByType(refBranchType).forEach(branch -> {
//            customerRepository.findByBranch(branch).forEach(customer -> {
//                bankAccountRepository.findByCustomer(customer).forEach(account -> {
//                    transactionRepository.findByAccount(account).forEach(transaction -> {
//                        transactionRepository.delete(transaction);
//                    });
//                    bankAccountRepository.delete(account);
//                });
//                customerRepository.delete(customer);
//            });
//            branchRepository.delete(branch);
//        });

        // Delete the actual refBranchType
        refBranchTypeRepository.delete(refBranchType);
    }

    @Override public void deleteRefBranchType(Long id) {
        RefBranchType refBranchType = refBranchTypeRepository.single(id);

//        // Recursively find and delete any FK constraints to this refBranchType
//        branchRepository.findByType(refBranchType).forEach(branch -> {
//            customerRepository.findByBranch(branch).forEach(customer -> {
//                bankAccountRepository.findByCustomer(customer).forEach(account -> {
//                    transactionRepository.findByAccount(account).forEach(transaction -> {
//                        transactionRepository.delete(transaction);
//                    });
//                    bankAccountRepository.delete(account);
//                });
//                customerRepository.delete(customer);
//            });
//            branchRepository.delete(branch);
//        });

        // Delete the actual refBranchType
        refBranchTypeRepository.delete(refBranchType);
    }
    //endregion
}
