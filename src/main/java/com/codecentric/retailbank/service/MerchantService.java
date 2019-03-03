package com.codecentric.retailbank.service;

import com.codecentric.retailbank.exception.nullpointer.ArgumentNullException;
import com.codecentric.retailbank.exception.nullpointer.InvalidOperationException;
import com.codecentric.retailbank.model.domain.Merchant;
import com.codecentric.retailbank.repository.MerchantRepository;
import com.codecentric.retailbank.repository.helpers.ListPage;
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
        Merchant merchant = null;
        try {
            merchant = merchantRepository.singleByDetails(details);
        } catch (InvalidOperationException e) {
           LOGGER.warn("Handled an \"InvalidOperationException\". Returning the first element from multiple elements.", e);

           return (Merchant) e.getPreservedData();
        } catch (ArgumentNullException e) {
            e.printStackTrace();
        }
        return merchant;
    }

    @Override public ListPage<Merchant> getAllMerchants(int pageIndex, int pageSize) {
        ListPage<Merchant> merchants = merchantRepository.allRange(pageIndex, pageSize);
        return merchants;
    }

    public List<Merchant> getAllMerchants() {
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
