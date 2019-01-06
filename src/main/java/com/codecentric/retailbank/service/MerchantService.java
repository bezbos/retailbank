package com.codecentric.retailbank.service;

import com.codecentric.retailbank.model.domain.Merchant;
import com.codecentric.retailbank.repository.MerchantRepository;
import com.codecentric.retailbank.service.interfaces.IMerchantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class MerchantService implements IMerchantService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MerchantRepository repo;


    public MerchantService() {
        super();
    }


    @Override
    public Merchant getById(Long id) {
        Merchant merchant = repo.getOne(id);
        return merchant;
    }

    @Override
    public Merchant getByDetails(String details) {
        Merchant merchant = repo.findByDetails(details);
        return merchant;
    }

    @Override
    public List<Merchant> getAllMerchants() {
        List<Merchant> merchants = repo.findAll();
        return merchants;
    }

    @Override
    public Merchant addMerchant(Merchant merchant) {
        Merchant result = repo.save(merchant);
        return result;
    }

    @Override
    public Merchant updateMerchant(Merchant merchant) {
        Merchant result = repo.save(merchant);
        return result;
    }

    @Override
    public void deleteMerchant(Merchant merchant) {
        repo.delete(merchant);
    }

    @Override
    public void deleteMerchant(Long id) {
        repo.deleteById(id);
    }
}
