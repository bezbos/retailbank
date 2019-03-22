package com.codecentric.retailbank.service;

import com.codecentric.retailbank.exception.nullpointer.ArgumentNullException;
import com.codecentric.retailbank.exception.nullpointer.InvalidOperationException;
import com.codecentric.retailbank.model.domain.Bank;
import com.codecentric.retailbank.repository.BankAccountRepository;
import com.codecentric.retailbank.repository.BankRepository;
import com.codecentric.retailbank.repository.BranchRepository;
import com.codecentric.retailbank.repository.CustomerRepository;
import com.codecentric.retailbank.repository.TransactionRepository;
import com.codecentric.retailbank.repository.helpers.ListPage;
import com.codecentric.retailbank.service.interfaces.IBankService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BankService implements IBankService {

    //region FIELDS
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final BranchRepository branchRepository;
    private final CustomerRepository customerRepository;
    private final BankAccountRepository bankAccountRepository;
    private final TransactionRepository transactionRepository;
    private final BankRepository bankRepository;
    //endregion

    //region CONSTRUCTOR
    @Autowired public BankService(BranchRepository branchRepository,
                                  CustomerRepository customerRepository,
                                  BankAccountRepository bankAccountRepository,
                                  TransactionRepository transactionRepository,
                                  BankRepository bankRepository) {
        this.branchRepository = branchRepository;
        this.customerRepository = customerRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.transactionRepository = transactionRepository;
        this.bankRepository = bankRepository;
    }
    //endregion


    //region READ
    @Override public Bank getById(Long id) {
        Bank bank = bankRepository.single(id);
        return bank;
    }

    @Override public Bank getByDetails(String details) {
        Bank bank = null;
        try {
            bank = bankRepository.singleByDetails(details);
        } catch (ArgumentNullException e) {
            e.printStackTrace();
        } catch (InvalidOperationException e) {
            LOGGER.warn("Handled an \"InvalidOperationException\". Returning the first element from multiple elements.", e);
            return (Bank) e.getPreservedData();
        }
        return bank;
    }

    @Override public List<Bank> getAllBanks() {
        List<Bank> banks = bankRepository.all();
        return banks;
    }

    public List<Bank> getAllBanksByDetails(String details) {
        List<Bank> banks = bankRepository.allByDetails(details);
        return banks;
    }

    @Override public ListPage<Bank> getAllBanksByPage(int pageIndex, int pageSize) {
        ListPage<Bank> banks = bankRepository.allRange(pageIndex, pageSize);
        return banks;
    }
    //endregion

    //region WRITE
    @Override public Bank addBank(Bank bank) {
        Bank result = bankRepository.add(bank);
        return result;
    }

    @Override public Bank updateBank(Bank bank) {
        Bank result = bankRepository.update(bank);
        return result;
    }
    //endregion

    //region DELETE
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
        bankRepository.delete(bank);
    }

    @Override public void deleteBank(Long id) {
        // Get the bank with this id
        Bank bank = bankRepository.single(id);

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
        bankRepository.delete(bank);
    }
    //endregion
}
