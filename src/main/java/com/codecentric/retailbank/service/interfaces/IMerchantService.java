package com.codecentric.retailbank.service.interfaces;

import com.codecentric.retailbank.model.domain.Merchant;

import java.util.List;

public interface IMerchantService {

    // GET
    Merchant getById(Long id);

    Merchant getByDetails(String details);

    List<Merchant> getAllMerchants();

    // CREATE
    Merchant addMerchant(Merchant merchant);

    // UPDATE
    Merchant updateMerchant(Merchant merchant);

    // DELETE
    void deleteMerchant(Merchant merchant);

    void deleteMerchant(Long id);

}
