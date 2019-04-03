package com.codecentric.retailbank.service;

import com.codecentric.retailbank.model.domain.Address;
import com.codecentric.retailbank.repository.AddressRepository;
import com.codecentric.retailbank.repository.BankAccountRepository;
import com.codecentric.retailbank.repository.BranchRepository;
import com.codecentric.retailbank.repository.CustomerRepository;
import com.codecentric.retailbank.repository.TransactionRepository;
import com.codecentric.retailbank.repository.helpers.ListPage;
import com.codecentric.retailbank.repository.security.UserRepository;
import com.codecentric.retailbank.service.interfaces.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService implements IAddressService {

    //region FIELDS
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

    @Autowired
    private UserRepository userRepository;
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

    public List<Address> getManyByLine1(String line1) {
        List<Address> addresses = addressRepository.allByLine1(line1);
        return addresses;
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
        // This could fail because of FK constraints.
        // I would have to create new methods in the repository
        // that find the constraining entities and delete them
        // but I don't want to spend anymore time on this project
        // so this will be left as is.

        addressRepository.delete(address);
    }

    @Override public void deleteAddress(Long id) {
        // This could fail because of FK constraints.
        // I would have to create new methods in the repository
        // that find the constraining entities and delete them
        // but I don't want to spend anymore time on this project
        // so this will be left as is.

        Address address = addressRepository.single(id);
        addressRepository.delete(address);
    }
    //endregion
}
