package com.codecentric.retailbank.service;

import com.codecentric.retailbank.model.domain.Merchant;
import com.codecentric.retailbank.repository.MerchantRepository;
import com.codecentric.retailbank.service.interfaces.IMerchantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MerchantService implements IMerchantService {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private MerchantRepository merchantRepository;


    @Override public Merchant getById(Long id) {
        Merchant merchant = merchantRepository.getSingle(id);
        return merchant;
    }

    @Override public Merchant getByDetails(String details) {
        Merchant merchant = merchantRepository.getSingleByDetails(details);
        return merchant;
    }

    @Override public List<Merchant> getAllMerchants() {
        List<Merchant> merchants = merchantRepository.findAll();
        return merchants;
    }

    @Override public Merchant addMerchant(Merchant merchant) {
        Merchant result = merchantRepository.add(merchant);
        return result;
    }

    @Override public Merchant updateMerchant(Merchant merchant) {
        Merchant result = merchantRepository.update(merchant);
        return result;
    }

    @Override public void deleteMerchant(Merchant merchant) {
        merchantRepository.delete(merchant);
    }

    @Override public void deleteMerchant(Long id) {
        merchantRepository.deleteById(id);
    }
}
