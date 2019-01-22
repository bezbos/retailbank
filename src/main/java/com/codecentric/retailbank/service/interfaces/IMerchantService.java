package com.codecentric.retailbank.service.interfaces;

import com.codecentric.retailbank.model.domain.Merchant;

import java.util.List;

public interface IMerchantService {

    Merchant getById(Long id);

    Merchant getByDetails(String details);

    List<Merchant> getAllMerchants();

    Merchant addMerchant(Merchant merchant);

    Merchant updateMerchant(Merchant merchant);

    void deleteMerchant(Merchant merchant);

    void deleteMerchant(Long id);

}
