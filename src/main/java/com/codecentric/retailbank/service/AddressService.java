package com.codecentric.retailbank.service;

import com.codecentric.retailbank.model.domain.Address;
import com.codecentric.retailbank.model.domain.Branch;
import com.codecentric.retailbank.repository.JDBC.AddressRepositoryJDBC;
import com.codecentric.retailbank.repository.JDBC.BranchRepositoryJDBC;
import com.codecentric.retailbank.repository.JDBC.wrappers.ListPage;
import com.codecentric.retailbank.repository.SpringData.AddressRepository;
import com.codecentric.retailbank.repository.SpringData.BankAccountRepository;
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
    private BranchRepositoryJDBC branchRepositoryJDBC;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AddressRepositoryJDBC addressRepositoryJDBC;


    @Override
    public Address getById(Long id) {
        Address address = addressRepositoryJDBC.getSingle(id);
        return address;
    }

    @Override
    public Address getByLine1(String line1) {
        Address address = addressRepositoryJDBC.getSingleByLine1(line1);
        return address;
    }

    @Override
    public List<Address> getAllAddress() {
        List<Address> addresses = addressRepositoryJDBC.findAll();
        return addresses;
    }

    @Override
    public ListPage<Address> getAllAddressesByPage(int pageIndex, int pageSize) {
        ListPage<Address> addresses = addressRepositoryJDBC.findAllRange(pageIndex, pageSize);
        return addresses;
    }

    @Override
    public Address addAddress(Address address) {
        Address result = addressRepositoryJDBC.add(address);
        return result;
    }

    @Override
    public Address updateAddress(Address address) {
        Address result = addressRepositoryJDBC.update(address);
        return result;
    }

    @Override
    public void deleteAddress(Address address) {

        List<Branch> existingBranches = branchRepositoryJDBC.getAllByAddressId(address.getId());
        for (Branch branch : existingBranches)
            branch.setAddress(null);

        branchRepositoryJDBC.updateBatch(existingBranches);

//        // Remove any foreign key constraints
//        branchRepository.findByAddress(address).forEach(branch -> {
//            branch.setAddress(null);
//        });
//        customerRepository.findByAddress(address).forEach(customer -> {
//            customer.setAddress(null);
//        });

        // Delete the actual address
        addressRepositoryJDBC.delete(address);
    }

    @Override
    public void deleteAddress(Long id) {
        Address address = addressRepositoryJDBC.getSingle(id);

        List<Branch> existingBranches = branchRepositoryJDBC.getAllByAddressId(id);
        for (Branch branch : existingBranches)
            branch.setAddress(null);

        branchRepositoryJDBC.updateBatch(existingBranches);

//        // Remove any foreign key constraints
//        branchRepository.findByAddress(address).forEach(branch -> {
//            branch.setAddress(null);
//        });
//        customerRepository.findByAddress(address).forEach(customer -> {
//            customer.setAddress(null);
//        });

        // Delete the actual address
        addressRepositoryJDBC.delete(address);
    }

}
