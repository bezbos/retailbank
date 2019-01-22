package com.codecentric.retailbank.service;

import com.codecentric.retailbank.model.domain.BankAccount;
import com.codecentric.retailbank.repository.BankAccountRepository;
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

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private BankAccountRepository bankAccountRepository;


    @Override public BankAccount getById(Long id) {
        BankAccount bankAccount = bankAccountRepository.getSingle(id);
        return bankAccount;
    }

    @Override public BankAccount getByDetails(String details) {
        BankAccount bankAccount = bankAccountRepository.getSingleByDetails(details);
        return bankAccount;
    }

    @Override public List<BankAccount> getAllAccounts() {
        List<BankAccount> bankAccounts = bankAccountRepository.findAll();
        return bankAccounts;
    }

    @Override public BankAccount addAccount(BankAccount account) {
        BankAccount result = bankAccountRepository.add(account);
        return result;
    }

    @Override public BankAccount updateAccount(BankAccount account) {
        BankAccount result = bankAccountRepository.update(account);
        return result;
    }

    @Override public void deleteAccount(BankAccount account) {
        bankAccountRepository.delete(account);
    }

    @Override public void deleteAccount(Long id) {
        bankAccountRepository.deleteById(id);
    }
}
