package com.codecentric.retailbank.service.interfaces;

import com.codecentric.retailbank.model.domain.Bank;
import com.codecentric.retailbank.repository.JDBC.wrappers.ListPage;

import java.util.List;

public interface IBankService {

    // GET
    Bank getById(Long id);

    Bank getByDetails(String details);

    List<Bank> getAllBanks();

    ListPage<Bank> getAllBanksByPage(int pageIndex, int pageSize);

    // CREATE
    Bank addBank(Bank bank);

    // UPDATE
    Bank updateBank(Bank bank);

    // DELETE
    void deleteBank(Bank bank);

    void deleteBank(Long id);
}
