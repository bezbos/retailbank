package com.codecentric.retailbank.service;

import com.codecentric.retailbank.model.domain.BankAccount;
import com.codecentric.retailbank.repository.BankAccountRepository;
import com.codecentric.retailbank.repository.helpers.ListPage;
import com.codecentric.retailbank.service.interfaces.IBankAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BankAccountService implements IBankAccountService {

    //region FIELDS
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final BankAccountRepository bankAccountRepository;
    //endregion

    //region CONSTRUCTOR
    @Autowired public BankAccountService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }
    //endregion


    //region READ
    @Override public BankAccount getById(Long id) {
        BankAccount bankAccount = bankAccountRepository.single(id);
        return bankAccount;
    }

    @Override public BankAccount getByDetails(String details) {
        BankAccount bankAccount = bankAccountRepository.getSingleByDetails(details);
        return bankAccount;
    }

    @Override public ListPage<BankAccount> getAllAccounts(int pageIndex, int pageSize) {
        ListPage<BankAccount> bankAccounts = bankAccountRepository.allRange(pageIndex, pageSize);
        return bankAccounts;
    }

    public List<BankAccount> getAllAccounts() {
        List<BankAccount> bankAccounts = bankAccountRepository.all();
        return bankAccounts;
    }

    public List<BankAccount> getAllAccountsByDetails(String details) {
        List<BankAccount> bankAccounts = bankAccountRepository.allByDetails(details);
        return bankAccounts;
    }
    //endregion

    //region WRITE
    @Override public BankAccount addAccount(BankAccount account) {
        BankAccount result = bankAccountRepository.add(account);
        return result;
    }

    @Override public BankAccount updateAccount(BankAccount account) {
        BankAccount result = bankAccountRepository.update(account);
        return result;
    }
    //endregion

    //region DELETE
    @Override public void deleteAccount(BankAccount account) {
        bankAccountRepository.delete(account);
    }

    @Override public void deleteAccount(Long id) {
        bankAccountRepository.deleteById(id);
    }
    //endregion
}
