package com.codecentric.retailbank.service.interfaces;

import com.codecentric.retailbank.model.domain.Address;
import com.codecentric.retailbank.repository.helpers.ListPage;

import java.util.List;

public interface IAddressService {

    Address getById(Long id);

    Address getByLine1(String line1);

    List<Address> getAllAddress();

    Address addAddress(Address address);

    Address updateAddress(Address address);

    void deleteAddress(Address address);

    void deleteAddress(Long id);

    ListPage<Address> getAllAddressesByPage(int pageIndex, int pageSize);
}
