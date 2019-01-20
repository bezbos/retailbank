package com.codecentric.retailbank.service;

import com.codecentric.retailbank.model.domain.Bank;
import com.codecentric.retailbank.repository.JDBC.BankRepositoryJDBC;
import com.codecentric.retailbank.repository.JDBC.wrappers.ListPage;
import com.codecentric.retailbank.repository.SpringData.BankAccountRepository;
import com.codecentric.retailbank.repository.SpringData.BranchRepository;
import com.codecentric.retailbank.repository.SpringData.CustomerRepository;
import com.codecentric.retailbank.repository.SpringData.TransactionRepository;
import com.codecentric.retailbank.service.interfaces.IBankService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class BankService implements IBankService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private BranchRepository branchRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private BankRepositoryJDBC bankRepositoryJDBC;


    @Override public Bank getById(Long id) {
        Bank bank = bankRepositoryJDBC.getSingle(id);
        return bank;
    }

    @Override public Bank getByDetails(String details) {
        Bank bank = bankRepositoryJDBC.getSingleByDetails(details);
        return bank;
    }

    @Override public List<Bank> getAllBanks() {
        List<Bank> banks = bankRepositoryJDBC.findAll();
        return banks;
    }

    @Override public ListPage<Bank> getAllBanksByPage(int pageIndex, int pageSize) {
        ListPage<Bank> banks = bankRepositoryJDBC.findAllRange(pageIndex, pageSize);
        return banks;
    }

    @Override public Bank addBank(Bank bank) {
        Bank result = bankRepositoryJDBC.add(bank);
        return result;
    }

    @Override public Bank updateBank(Bank bank) {
        Bank result = bankRepositoryJDBC.update(bank);
        return result;
    }

    @Override public void deleteBank(Bank bank) {
//        // Recursively find and delete any FK constraints that this bank has
//        branchRepository.findByBank(bank).forEach(branch -> {
//            customerRepository.findByBranch(branch).forEach(customer -> {
//                bankAccountRepository.findByCustomer(customer).forEach(bankAccount -> {
//                    transactionRepository.findByAccount(bankAccount).forEach(transaction -> {
//                        transactionRepository.delete(transaction);
//                    });
//                    bankAccountRepository.delete(bankAccount);
//                });
//                customerRepository.delete(customer);
//            });
//            branchRepository.delete(branch);
//        });

        // Delete the actual bank
        bankRepositoryJDBC.delete(bank);
    }

    @Override public void deleteBank(Long id) {
        // Get the bank with this id
        Bank bank = bankRepositoryJDBC.getSingle(id);

//        // Recursively find and delete any FK constraints that this bank has
//        branchRepository.findByBank(bank).forEach(branch -> {
//            customerRepository.findByBranch(branch).forEach(customer -> {
//                bankAccountRepository.findByCustomer(customer).forEach(bankAccount -> {
//                    transactionRepository.findByAccount(bankAccount).forEach(transaction -> {
//                        transactionRepository.delete(transaction);
//                    });
//                    bankAccountRepository.delete(bankAccount);
//                });
//                customerRepository.delete(customer);
//            });
//            branchRepository.delete(branch);
//        });

        // Delete the actual bank
        bankRepositoryJDBC.delete(bank);
    }

    public List<Bank> getBanksBatch(Iterable<Long> ids) {
        return bankRepositoryJDBC.getBatchByIds(ids);
    }
}
