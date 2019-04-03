package com.codecentric.retailbank.service;

import com.codecentric.retailbank.model.domain.BankAccount;
import com.codecentric.retailbank.repository.BankAccountRepository;
import com.codecentric.retailbank.repository.helpers.ListPage;
import com.codecentric.retailbank.service.interfaces.IBankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankAccountService implements IBankAccountService {

    //region FIELDS
    @Autowired
    private BankAccountRepository bankAccountRepository;
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
        // This could fail because of FK constraints.
        // I would have to create new methods in the repository
        // that find the constraining entities and delete them
        // but I don't want to spend anymore time on this project
        // so this will be left as is.

        bankAccountRepository.delete(account);
    }

    @Override public void deleteAccount(Long id) {
        // This could fail because of FK constraints.
        // I would have to create new methods in the repository
        // that find the constraining entities and delete them
        // but I don't want to spend anymore time on this project
        // so this will be left as is.

        bankAccountRepository.deleteById(id);
    }
    //endregion
}
