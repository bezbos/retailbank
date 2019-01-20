package com.codecentric.retailbank.service;

import com.codecentric.retailbank.model.domain.Merchant;
import com.codecentric.retailbank.repository.JDBC.MerchantRepositoryJDBC;
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

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private MerchantRepositoryJDBC merchantRepositoryJDBC;


    @Override
    public Merchant getById(Long id) {
        Merchant merchant = merchantRepositoryJDBC.getSingle(id);
        return merchant;
    }

    @Override
    public Merchant getByDetails(String details) {
        Merchant merchant = merchantRepositoryJDBC.getSingleByDetails(details);
        return merchant;
    }

    @Override
    public List<Merchant> getAllMerchants() {
        List<Merchant> merchants = merchantRepositoryJDBC.findAll();
        return merchants;
    }

    @Override
    public Merchant addMerchant(Merchant merchant) {
        Merchant result = merchantRepositoryJDBC.add(merchant);
        return result;
    }

    @Override
    public Merchant updateMerchant(Merchant merchant) {
        Merchant result = merchantRepositoryJDBC.update(merchant);
        return result;
    }

    @Override
    public void deleteMerchant(Merchant merchant) {
        merchantRepositoryJDBC.delete(merchant);
    }

    @Override
    public void deleteMerchant(Long id) {
        merchantRepositoryJDBC.deleteById(id);
    }
}
