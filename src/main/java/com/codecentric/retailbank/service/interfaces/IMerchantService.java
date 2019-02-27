package com.codecentric.retailbank.service.interfaces;

import com.codecentric.retailbank.model.domain.Merchant;
import com.codecentric.retailbank.repository.helpers.ListPage;

public interface IMerchantService {

    Merchant getById(Long id);

    Merchant getByDetails(String details);

    ListPage<Merchant> getAllMerchants(int pageIndex, int pageSize);

    Merchant addMerchant(Merchant merchant);

    Merchant updateMerchant(Merchant merchant);

    void deleteMerchant(Merchant merchant);

    void deleteMerchant(Long id);

}
