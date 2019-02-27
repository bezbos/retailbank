package com.codecentric.retailbank.service.interfaces;

import com.codecentric.retailbank.model.domain.BankAccount;
import com.codecentric.retailbank.repository.helpers.ListPage;

public interface IBankAccountService {

    BankAccount getById(Long id);

    BankAccount getByDetails(String details);

    ListPage<BankAccount> getAllAccounts(int pageIndex, int pageSize);

    BankAccount addAccount(BankAccount account);

    BankAccount updateAccount(BankAccount account);

    void deleteAccount(BankAccount account);

    void deleteAccount(Long id);
}
