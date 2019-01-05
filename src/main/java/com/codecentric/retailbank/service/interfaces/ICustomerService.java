package com.codecentric.retailbank.service.interfaces;

import com.codecentric.retailbank.model.domain.Customer;

import java.util.List;

public interface ICustomerService {

    // GET
    Customer getById(Long id);

    Customer getByPersonalDetails(String details);

    List<Customer> getAllCustomers();

    // CREATE
    Customer addCustomer(Customer customer);

    // UPDATE
    Customer updateCustomer(Customer customer);

    // DELETE
    void deleteCustomer(Customer customer);

    void deleteCustomer(Long id);
}
