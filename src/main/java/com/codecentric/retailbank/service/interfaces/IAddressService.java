package com.codecentric.retailbank.service.interfaces;

import com.codecentric.retailbank.model.domain.Address;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IAddressService {

    // GET
    Address getById(Long id);

    Address getByLine1(String line1);

    List<Address> getAllAddress();

    // CREATE
    Address addAddress(Address address);

    // UPDATE
    Address updateAddress(Address address);

    // DELETE
    void deleteAddress(Address address);

    void deleteAddress(Long id);

    Page<Address> getAllAddressesByPage(int pageIndex, int pageSize);
}
