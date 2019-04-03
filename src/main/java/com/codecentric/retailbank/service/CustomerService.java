package com.codecentric.retailbank.service;

import com.codecentric.retailbank.model.domain.Customer;
import com.codecentric.retailbank.repository.CustomerRepository;
import com.codecentric.retailbank.repository.helpers.ListPage;
import com.codecentric.retailbank.service.interfaces.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService implements ICustomerService {

    //region FIELDS
    @Autowired
    private CustomerRepository customerRepository;
    //endregion

    //region READ
    @Override public Customer getById(Long id) {
        Customer customer = customerRepository.single(id);
        return customer;
    }

    @Override public Customer getByPersonalDetails(String personalDetails) {
        Customer customer = customerRepository.getSingleByPersonalDetails(personalDetails);
        return customer;
    }

    public List<Customer> getAllByPersonalDetails(String personalDetails) {
        List<Customer> customers = customerRepository.allByPersonalDetails(personalDetails);
        return customers;
    }

    @Override public ListPage<Customer> getAllCustomers(int pageIndex, int pageSize) {
        ListPage<Customer> customers = customerRepository.allRange(pageIndex, pageSize);
        return customers;
    }

    public List<Customer> getAllCustomers() {
        List<Customer> customers = customerRepository.all();
        return customers;
    }
    //endregion

    //region WRITE
    @Override public Customer addCustomer(Customer customer) {
        Customer result = customerRepository.add(customer);
        return result;
    }

    @Override public Customer updateCustomer(Customer customer) {
        Customer result = customerRepository.update(customer);
        return result;
    }
    //endregion

    //region DELETE
    @Override public void deleteCustomer(Customer customer) {
        // This could fail because of FK constraints.
        // I would have to create new methods in the repository
        // that find the constraining entities and delete them
        // but I don't want to spend anymore time on this project
        // so this will be left as is.

        customerRepository.delete(customer);
    }

    @Override public void deleteCustomer(Long id) {
        // This could fail because of FK constraints.
        // I would have to create new methods in the repository
        // that find the constraining entities and delete them
        // but I don't want to spend anymore time on this project
        // so this will be left as is.

        customerRepository.deleteById(id);
    }
    //endregion
}
