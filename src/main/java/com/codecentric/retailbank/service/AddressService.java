package com.codecentric.retailbank.service;

import com.codecentric.retailbank.model.domain.Address;
import com.codecentric.retailbank.repository.SpringData.AddressRepository;
import com.codecentric.retailbank.repository.SpringData.BankAccountRepository;
import com.codecentric.retailbank.repository.SpringData.BranchRepository;
import com.codecentric.retailbank.repository.SpringData.CustomerRepository;
import com.codecentric.retailbank.repository.SpringData.TransactionRepository;
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
    private AddressRepository addressRepository;
    @Autowired
    private BranchRepository branchRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private TransactionRepository transactionRepository;


    public AddressService() {
        super();
    }


    @Override
    public Address getById(Long id) {
        Address address = addressRepository.findById(id).get();
        return address;
    }

    @Override
    public Address getByLine1(String line1) {
        Address address = addressRepository.findByLine1(line1);
        return address;
    }

    @Override
    public List<Address> getAllAddress() {
        List<Address> addresses = addressRepository.findAll();
        return addresses;
    }

    @Override
    public Page<Address> getAllAddressesByPage(int pageIndex, int pageSize) {
        Pageable page = new PageRequest(pageIndex, pageSize);
        Page<Address> addresses = addressRepository.findAll(page);
        return addresses;
    }

    @Override
    public Address addAddress(Address address) {
        Address result = addressRepository.save(address);
        return result;
    }

    @Override
    public Address updateAddress(Address address) {
        Address result = addressRepository.save(address);
        return result;
    }

    @Override
    public void deleteAddress(Address address) {
        // Remove any foreign key constraints
        branchRepository.findByAddress(address).forEach(branch -> {
            branch.setAddress(null);
        });
        customerRepository.findByAddress(address).forEach(customer -> {
            customer.setAddress(null);
        });

        // Delete the actual address
        addressRepository.delete(address);
    }

    @Override
    public void deleteAddress(Long id) {
        Address address = addressRepository.getOne(id);

        // Remove any foreign key constraints
        branchRepository.findByAddress(address).forEach(branch -> {
            branch.setAddress(null);
        });
        customerRepository.findByAddress(address).forEach(customer -> {
            customer.setAddress(null);
        });

        // Delete the actual address
        addressRepository.delete(address);
    }

}
