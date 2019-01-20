package com.codecentric.retailbank.service.interfaces;

import com.codecentric.retailbank.model.domain.BankAccount;

import java.util.List;

public interface IBankAccountService {

    // GET
    BankAccount getById(Long id);

    BankAccount getByDetails(String details);

    List<BankAccount> getAllAccounts();

    // CREATE
    BankAccount addAccount(BankAccount account);

    // UPDATE
    BankAccount updateAccount(BankAccount account);

    // DELETE
    void deleteAccount(BankAccount account);

    void deleteAccount(Long id);
}
