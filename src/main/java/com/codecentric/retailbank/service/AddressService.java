package com.codecentric.retailbank.service;

import com.codecentric.retailbank.model.domain.Address;
import com.codecentric.retailbank.model.domain.Branch;
import com.codecentric.retailbank.repository.AddressRepository;
import com.codecentric.retailbank.repository.BankAccountRepository;
import com.codecentric.retailbank.repository.BranchRepository;
import com.codecentric.retailbank.repository.CustomerRepository;
import com.codecentric.retailbank.repository.TransactionRepository;
import com.codecentric.retailbank.repository.helpers.ListPage;
import com.codecentric.retailbank.service.interfaces.IAddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AddressService implements IAddressService {

    //region FIELDS
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    //endregion

    //region REPOSITORIES
    @Autowired
    private BranchRepository branchRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AddressRepository addressRepository;
    //endregion


    //region READ
    @Override public Address getById(Long id) {
        Address address = addressRepository.single(id);
        return address;
    }

    @Override public Address getByLine1(String line1) {
        Address address = addressRepository.singleByLine1(line1);
        return address;
    }

    @Override public List<Address> getAllAddress() {
        List<Address> addresses = addressRepository.all();
        return addresses;
    }

    @Override public ListPage<Address> getAllAddressesByPage(int pageIndex, int pageSize) {
        ListPage<Address> addresses = addressRepository.allRange(pageIndex, pageSize);
        return addresses;
    }
    //endregion

    //region WRITE
    @Override public Address addAddress(Address address) {
        Address result = addressRepository.add(address);
        return result;
    }

    @Override public Address updateAddress(Address address) {
        Address result = addressRepository.update(address);
        return result;
    }
    //endregion

    //region DELETE
    @Override public void deleteAddress(Address address) {

        List<Branch> existingBranches = branchRepository.allByAddressId(address.getId());
        for (Branch branch : existingBranches)
            branch.setAddress(null);

        branchRepository.updateBatch(existingBranches);

//        // Remove any foreign key constraints
//        branchRepository.findByAddress(address).forEach(branch -> {
//            branch.setAddress(null);
//        });
//        customerRepository.findByAddress(address).forEach(customer -> {
//            customer.setAddress(null);
//        });

        // Delete the actual address
        addressRepository.delete(address);
    }

    @Override public void deleteAddress(Long id) {
        Address address = addressRepository.single(id);

        List<Branch> existingBranches = branchRepository.allByAddressId(id);
        for (Branch branch : existingBranches)
            branch.setAddress(null);

        branchRepository.updateBatch(existingBranches);

//        // Remove any foreign key constraints
//        branchRepository.findByAddress(address).forEach(branch -> {
//            branch.setAddress(null);
//        });
//        customerRepository.findByAddress(address).forEach(customer -> {
//            customer.setAddress(null);
//        });

        // Delete the actual address
        addressRepository.delete(address);
    }
    //endregion
}
