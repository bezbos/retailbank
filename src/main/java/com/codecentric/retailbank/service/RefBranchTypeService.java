package com.codecentric.retailbank.service;

import com.codecentric.retailbank.model.domain.OLD.RefBranchTypeOLD;
import com.codecentric.retailbank.repository.JDBC.RefBranchTypeRepositoryJDBC;
import com.codecentric.retailbank.repository.SpringData.BankAccountRepository;
import com.codecentric.retailbank.repository.SpringData.BranchRepository;
import com.codecentric.retailbank.repository.SpringData.CustomerRepository;
import com.codecentric.retailbank.repository.SpringData.RefBranchTypeRepository;
import com.codecentric.retailbank.repository.SpringData.TransactionRepository;
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
    @Autowired
    private RefBranchTypeRepositoryJDBC refBranchTypeRepositoryJDBC;


    @Override
    public RefBranchTypeOLD getById(Long id) {
        RefBranchTypeOLD refBranchType = refBranchTypeRepositoryJDBC.getSingle(id);
        return refBranchType;
    }

    @Override
    public RefBranchTypeOLD getByCode(String code) {
        RefBranchTypeOLD refBranchType = refBranchTypeRepositoryJDBC.getSingleByCode(code);
        return refBranchType;
    }

    @Override
    public List<RefBranchTypeOLD> getAllRefBranchTypes() {
        List<RefBranchTypeOLD> refBranchTypes = refBranchTypeRepositoryJDBC.findAllOrDefault();
        return refBranchTypes;
    }

    @Override
    public RefBranchTypeOLD addRefBranchType(RefBranchTypeOLD refBranchType) {
        RefBranchTypeOLD result = refBranchTypeRepositoryJDBC.add(refBranchType);
        return result;
    }

    @Override
    public RefBranchTypeOLD updateRefBranchType(RefBranchTypeOLD refBranchType) {
        RefBranchTypeOLD result = refBranchTypeRepositoryJDBC.update(refBranchType);
        return result;
    }

    @Override
    public void deleteRefBranchType(RefBranchTypeOLD refBranchType) {
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
        refBranchTypeRepositoryJDBC.delete(refBranchType);
    }

    @Override
    public void deleteRefBranchType(Long id) {
        RefBranchTypeOLD refBranchType = refBranchTypeRepositoryJDBC.getSingle(id);

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
        refBranchTypeRepositoryJDBC.delete(refBranchType);
    }
}
