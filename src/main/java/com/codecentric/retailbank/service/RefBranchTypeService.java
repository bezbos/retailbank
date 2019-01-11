package com.codecentric.retailbank.service;

import com.codecentric.retailbank.model.domain.RefBranchType;
import com.codecentric.retailbank.repository.*;
import com.codecentric.retailbank.service.interfaces.IRefBranchTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class RefBranchTypeService implements IRefBranchTypeService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RefBranchTypeRepository refBranchTypeRepository;
    @Autowired
    private BranchRepository branchRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private TransactionRepository transactionRepository;


    public RefBranchTypeService() {
        super();
    }


    @Override
    public RefBranchType getById(Long id) {
        RefBranchType refBranchType = refBranchTypeRepository.findById(id).get();
        return refBranchType;
    }

    @Override
    public RefBranchType getByCode(String code) {
        RefBranchType refBranchType = refBranchTypeRepository.findByCode(code);
        return refBranchType;
    }

    @Override
    public List<RefBranchType> getAllRefBranchTypes() {
        List<RefBranchType> refBranchTypes = refBranchTypeRepository.findAll();
        return refBranchTypes;
    }

    @Override
    public RefBranchType addRefBranchType(RefBranchType refBranchType) {
        RefBranchType result = refBranchTypeRepository.save(refBranchType);
        return result;
    }

    @Override
    public RefBranchType updateRefBranchType(RefBranchType refBranchType) {
        RefBranchType result = refBranchTypeRepository.save(refBranchType);
        return result;
    }

    @Override
    public void deleteRefBranchType(RefBranchType refBranchType) {
        // Recursively find and delete any FK constraints to this refBranchType
        branchRepository.findByType(refBranchType).forEach(branch -> {
            customerRepository.findByBranch(branch).forEach(customer -> {
                bankAccountRepository.findByCustomer(customer).forEach(account -> {
                    transactionRepository.findByAccount(account).forEach(transaction -> {
                        transactionRepository.delete(transaction);
                    });
                    bankAccountRepository.delete(account);
                });
                customerRepository.delete(customer);
            });
            branchRepository.delete(branch);
        });

        // Delete the actual refBranchType
        refBranchTypeRepository.delete(refBranchType);
    }

    @Override
    public void deleteRefBranchType(Long id) {
        RefBranchType refBranchType = refBranchTypeRepository.getOne(id);

        // Recursively find and delete any FK constraints to this refBranchType
        branchRepository.findByType(refBranchType).forEach(branch -> {
            customerRepository.findByBranch(branch).forEach(customer -> {
                bankAccountRepository.findByCustomer(customer).forEach(account -> {
                    transactionRepository.findByAccount(account).forEach(transaction -> {
                        transactionRepository.delete(transaction);
                    });
                    bankAccountRepository.delete(account);
                });
                customerRepository.delete(customer);
            });
            branchRepository.delete(branch);
        });

        // Delete the actual refBranchType
        refBranchTypeRepository.delete(refBranchType);
    }
}
