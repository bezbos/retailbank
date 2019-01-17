package com.codecentric.retailbank.service;

import com.codecentric.retailbank.model.domain.OLD.AddressOLD;
import com.codecentric.retailbank.repository.JDBC.AddressRepositoryJDBC;
import com.codecentric.retailbank.repository.JDBC.wrappers.ListPage;
import com.codecentric.retailbank.repository.SpringData.AddressRepository;
import com.codecentric.retailbank.repository.SpringData.BankAccountRepository;
import com.codecentric.retailbank.repository.SpringData.BranchRepository;
import com.codecentric.retailbank.repository.SpringData.CustomerRepository;
import com.codecentric.retailbank.repository.SpringData.TransactionRepository;
import com.codecentric.retailbank.service.interfaces.IAddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private AddressRepositoryJDBC addressRepositoryJDBC;


    public AddressService() {
        super();
    }


    @Override
    public AddressOLD getById(Long id) {
        AddressOLD address = addressRepositoryJDBC.getSingle(id);
        return address;
    }

    @Override
    public AddressOLD getByLine1(String line1) {
        AddressOLD address = addressRepositoryJDBC.getSingleByLine1(line1);
        return address;
    }

    @Override
    public List<AddressOLD> getAllAddress() {
        List<AddressOLD> addresses = addressRepositoryJDBC.findAll();
        return addresses;
    }

    @Override
    public ListPage<AddressOLD> getAllAddressesByPage(int pageIndex, int pageSize) {
        ListPage<AddressOLD> addresses = addressRepositoryJDBC.findAllRangeOrDefault(pageIndex, pageSize);
        return addresses;
    }

    @Override
    public AddressOLD addAddress(AddressOLD address) {
        AddressOLD result = addressRepositoryJDBC.add(address);
        return result;
    }

    @Override
    public AddressOLD updateAddress(AddressOLD address) {
        AddressOLD result = addressRepositoryJDBC.update(address);
        return result;
    }

    @Override
    public void deleteAddress(AddressOLD address) {
        // Remove any foreign key constraints
        branchRepository.findByAddress(address).forEach(branch -> {
            branch.setAddress(null);
        });
        customerRepository.findByAddress(address).forEach(customer -> {
            customer.setAddress(null);
        });

        // Delete the actual address
        addressRepositoryJDBC.delete(address);
    }

    @Override
    public void deleteAddress(Long id) {
        AddressOLD address = addressRepository.getOne(id);

        // Remove any foreign key constraints
        branchRepository.findByAddress(address).forEach(branch -> {
            branch.setAddress(null);
        });
        customerRepository.findByAddress(address).forEach(customer -> {
            customer.setAddress(null);
        });

        // Delete the actual address
        addressRepositoryJDBC.delete(address);
    }

}
