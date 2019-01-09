package com.codecentric.retailbank.service.interfaces;

import com.codecentric.retailbank.model.domain.Bank;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IBankService {

    // GET
    Bank getById(Long id);

    Bank getByDetails(String details);

    List<Bank> getAllBanks();

    Page<Bank> getAllBanksByPage(Integer pageIndex, Integer pageSize);

    // CREATE
    Bank addBank(Bank bank);

    // UPDATE
    Bank updateBank(Bank bank);

    // DELETE
    void deleteBank(Bank bank);

    void deleteBank(Long id);
}
