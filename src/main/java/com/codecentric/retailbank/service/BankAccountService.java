package com.codecentric.retailbank.service;

import com.codecentric.retailbank.model.domain.BankAccount;
import com.codecentric.retailbank.repository.JDBC.BankAccountRepositoryJDBC;
import com.codecentric.retailbank.service.interfaces.IBankAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class BankAccountService implements IBankAccountService {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private BankAccountRepositoryJDBC bankAccountRepositoryJDBC;


    @Override
    public BankAccount getById(Long id) {
        BankAccount bankAccount = bankAccountRepositoryJDBC.getSingle(id);
        return bankAccount;
    }

    @Override
    public BankAccount getByDetails(String details) {
        BankAccount bankAccount = bankAccountRepositoryJDBC.getSingleByDetails(details);
        return bankAccount;
    }

    @Override
    public List<BankAccount> getAllAccounts() {
        List<BankAccount> bankAccounts = bankAccountRepositoryJDBC.findAll();
        return bankAccounts;
    }

    @Override
    public BankAccount addAccount(BankAccount account) {
        BankAccount result = bankAccountRepositoryJDBC.add(account);
        return result;
    }

    @Override
    public BankAccount updateAccount(BankAccount account) {
        BankAccount result = bankAccountRepositoryJDBC.update(account);
        return result;
    }

    @Override
    public void deleteAccount(BankAccount account) {
        bankAccountRepositoryJDBC.delete(account);
    }

    @Override
    public void deleteAccount(Long id) {
        bankAccountRepositoryJDBC.deleteById(id);
    }
}
