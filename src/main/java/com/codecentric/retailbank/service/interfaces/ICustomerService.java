package com.codecentric.retailbank.service.interfaces;

import com.codecentric.retailbank.model.domain.Customer;

import java.util.List;

public interface ICustomerService {

    Customer getById(Long id);

    Customer getByPersonalDetails(String details);

    List<Customer> getAllCustomers();

    Customer addCustomer(Customer customer);

    Customer updateCustomer(Customer customer);

    void deleteCustomer(Customer customer);

    void deleteCustomer(Long id);
}
