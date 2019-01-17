package com.codecentric.retailbank.service;

import com.codecentric.retailbank.model.domain.OLD.BankAccount;
import com.codecentric.retailbank.repository.SpringData.BankAccountRepository;
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

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private BankAccountRepository repo;


    public BankAccountService() {
        super();
    }


    @Override
    public BankAccount getById(Long id) {
        BankAccount bankAccount = repo.getOne(id);
        return bankAccount;
    }

    @Override
    public BankAccount getByDetails(String details) {
        BankAccount bankAccount = repo.findByDetails(details);
        return bankAccount;
    }

    @Override
    public List<BankAccount> getAllAccounts() {
        List<BankAccount> bankAccounts = repo.findAll();
        return bankAccounts;
    }

    @Override
    public BankAccount addAccount(BankAccount account) {
        BankAccount result = repo.save(account);
        return result;
    }

    @Override
    public BankAccount updateAccount(BankAccount account) {
        BankAccount result = repo.save(account);
        return result;
    }

    @Override
    public void deleteAccount(BankAccount account) {
        repo.delete(account);
    }

    @Override
    public void deleteAccount(Long id) {
        repo.deleteById(id);
    }
}
