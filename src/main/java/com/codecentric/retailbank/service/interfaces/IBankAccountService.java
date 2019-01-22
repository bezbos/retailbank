package com.codecentric.retailbank.service.interfaces;

import com.codecentric.retailbank.model.domain.BankAccount;

import java.util.List;

public interface IBankAccountService {

    BankAccount getById(Long id);

    BankAccount getByDetails(String details);

    List<BankAccount> getAllAccounts();

    BankAccount addAccount(BankAccount account);

    BankAccount updateAccount(BankAccount account);

    void deleteAccount(BankAccount account);

    void deleteAccount(Long id);
}
