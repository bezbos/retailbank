package com.codecentric.retailbank.service;

import com.codecentric.retailbank.model.domain.Bank;
import com.codecentric.retailbank.repository.BankRepository;
import com.codecentric.retailbank.service.interfaces.IBankService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class BankService implements IBankService {

    Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private BankRepository repo;


    public BankService() {
        super();
    }


    @Override
    public Bank getById(Long id) {
        Bank bank = repo.findById(id).get();

        return bank;
    }

    @Override
    public Bank getByDetails(String details) {
        Bank bank = repo.findByDetails(details);

        return bank;
    }

    @Override
    public List<Bank> getAllBanks() {
        List<Bank> banks = repo.findAll();
        return banks;
    }

    @Override
    public Bank addBank(Bank bank) {
        Bank result = repo.save(bank);
        return result;
    }

    @Override
    public Bank updateBank(Bank bank) {
        Bank result = repo.save(bank);
        return result;
    }

    @Override
    public void deleteBank(Bank bank) {
        repo.delete(bank);
    }

    @Override
    public void deleteBank(Long id) {
        repo.deleteById(id);
    }
}
