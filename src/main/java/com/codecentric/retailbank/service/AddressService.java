package com.codecentric.retailbank.service;

import com.codecentric.retailbank.model.domain.Address;
import com.codecentric.retailbank.repository.AddressRepository;
import com.codecentric.retailbank.service.interfaces.IAddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class AddressService implements IAddressService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AddressRepository repo;


    public AddressService() {
        super();
    }


    @Override
    public Address getById(Long id) {
        Address address = repo.findById(id).get();
        return address;
    }

    @Override
    public Address getByLine1(String line1) {
        Address address = repo.findByLine1(line1);
        return address;
    }

    @Override
    public List<Address> getAllAddress() {
        List<Address> addresses = repo.findAll();
        return addresses;
    }

    @Override
    public Page<Address> getAllAddressesByPage(int pageIndex, int pageSize) {
        Pageable page = new PageRequest(pageIndex, pageSize);
        Page<Address> addresses = repo.findAll(page);
        return addresses;
    }

    @Override
    public Address addAddress(Address address) {
        Address result = repo.save(address);
        return result;
    }

    @Override
    public Address updateAddress(Address address) {
        Address result = repo.save(address);
        return result;
    }

    @Override
    public void deleteAddress(Address address) {
        repo.delete(address);
    }

    @Override
    public void deleteAddress(Long id) {
        repo.deleteById(id);
    }
}
