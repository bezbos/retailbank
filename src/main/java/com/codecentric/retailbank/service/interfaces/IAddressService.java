package com.codecentric.retailbank.service.interfaces;

import com.codecentric.retailbank.model.domain.OLD.AddressOLD;
import com.codecentric.retailbank.repository.JDBC.wrappers.ListPage;

import java.util.List;

public interface IAddressService {

    // GET
    AddressOLD getById(Long id);

    AddressOLD getByLine1(String line1);

    List<AddressOLD> getAllAddress();

    // CREATE
    AddressOLD addAddress(AddressOLD address);

    // UPDATE
    AddressOLD updateAddress(AddressOLD address);

    // DELETE
    void deleteAddress(AddressOLD address);

    void deleteAddress(Long id);

    ListPage<AddressOLD> getAllAddressesByPage(int pageIndex, int pageSize);
}
