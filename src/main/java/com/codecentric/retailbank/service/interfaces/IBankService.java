package com.codecentric.retailbank.service.interfaces;

import com.codecentric.retailbank.model.domain.Bank;
import com.codecentric.retailbank.repository.helpers.ListPage;

import java.util.List;

public interface IBankService {

    Bank getById(Long id);

    Bank getByDetails(String details);

    List<Bank> getAllBanks();

    ListPage<Bank> getAllBanksByPage(int pageIndex, int pageSize);

    Bank addBank(Bank bank);

    Bank updateBank(Bank bank);

    void deleteBank(Bank bank);

    void deleteBank(Long id);
}
