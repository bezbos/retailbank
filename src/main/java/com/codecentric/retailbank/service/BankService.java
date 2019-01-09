package com.codecentric.retailbank.service;

import com.codecentric.retailbank.model.domain.*;
import com.codecentric.retailbank.repository.*;
import com.codecentric.retailbank.service.interfaces.IBankService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class BankService implements IBankService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private BankRepository bankRepository;
    @Autowired
    private BranchRepository branchRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    public BankService() {
        super();
    }


    @Override
    public Bank getById(Long id) {
        Bank bank = bankRepository.findById(id).get();

        return bank;
    }

    @Override
    public Bank getByDetails(String details) {
        Bank bank = bankRepository.findByDetails(details);

        return bank;
    }

    @Override
    public List<Bank> getAllBanks() {
        List<Bank> banks = bankRepository.findAll();
        return banks;
    }

    @Override
    public Page<Bank> getAllBanksByPage(Integer pageIndex, Integer pageSize) {
        Pageable page = new PageRequest(pageIndex, pageSize);
        Page<Bank> banks = bankRepository.findAll(page);
        return banks;
    }

    @Override
    public Bank addBank(Bank bank) {
        Bank result = bankRepository.save(bank);
        return result;
    }

    @Override
    public Bank updateBank(Bank bank) {
        Bank result = bankRepository.save(bank);
        return result;
    }

    @Override
    public void deleteBank(Bank bank) {
        // Recursively find and delete any FK constraints that this bank has
        branchRepository.findByBank(bank).forEach(branch ->{
            customerRepository.findByBranch(branch).forEach(customer ->{
                bankAccountRepository.findByCustomer(customer).forEach(bankAccount ->{
                    transactionRepository.findByAccount(bankAccount).forEach(transaction -> {
                        transactionRepository.delete(transaction);
                    });
                    bankAccountRepository.delete(bankAccount);
                });
                customerRepository.delete(customer);
            });
            branchRepository.delete(branch);
        });

        // Delete the actual bank
        bankRepository.delete(bank);
    }

    @Override
    public void deleteBank(Long id) {
        // Get the bank with this id
        Bank bank = bankRepository.getOne(id);

        // Recursively find and delete any FK constraints that this bank has
        branchRepository.findByBank(bank).forEach(branch ->{
            customerRepository.findByBranch(branch).forEach(customer ->{
                bankAccountRepository.findByCustomer(customer).forEach(bankAccount ->{
                    transactionRepository.findByAccount(bankAccount).forEach(transaction -> {
                        transactionRepository.delete(transaction);
                    });
                    bankAccountRepository.delete(bankAccount);
                });
                customerRepository.delete(customer);
            });
            branchRepository.delete(branch);
        });

        // Delete the actual bank
        bankRepository.delete(bank);
    }
}
