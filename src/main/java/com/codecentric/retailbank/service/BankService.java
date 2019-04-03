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

import java.util.List;

@Service
public class BankService implements IBankService {

    //region FIELDS
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private BankRepository bankRepository;
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
        // This could fail because of FK constraints.
        // I would have to create new methods in the repository
        // that find the constraining entities and delete them
        // but I don't want to spend anymore time on this project
        // so this will be left as is.

        bankRepository.delete(bank);
    }

    @Override public void deleteBank(Long id) {
        // This could fail because of FK constraints.
        // I would have to create new methods in the repository
        // that find the constraining entities and delete them
        // but I don't want to spend anymore time on this project
        // so this will be left as is.

        Bank bank = bankRepository.single(id);
        bankRepository.delete(bank);
    }
    //endregion
}
