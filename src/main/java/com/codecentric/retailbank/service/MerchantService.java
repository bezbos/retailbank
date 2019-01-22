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

    //region FIELDS
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    //endregion

    //region REPOSITORIES
    @Autowired
    private MerchantRepository merchantRepository;
    //endregion


    //region READ
    @Override public Merchant getById(Long id) {
        Merchant merchant = merchantRepository.single(id);
        return merchant;
    }

    @Override public Merchant getByDetails(String details) {
        Merchant merchant = merchantRepository.singleByDetails(details);
        return merchant;
    }

    @Override public List<Merchant> getAllMerchants() {
        List<Merchant> merchants = merchantRepository.all();
        return merchants;
    }
    //endregion

    //region WRITE
    @Override public Merchant addMerchant(Merchant merchant) {
        Merchant result = merchantRepository.add(merchant);
        return result;
    }

    @Override public Merchant updateMerchant(Merchant merchant) {
        Merchant result = merchantRepository.update(merchant);
        return result;
    }
    //endregion

    //region DELETE
    @Override public void deleteMerchant(Merchant merchant) {
        merchantRepository.delete(merchant);
    }

    @Override public void deleteMerchant(Long id) {
        merchantRepository.deleteById(id);
    }
    //endregion
}
