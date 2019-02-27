package com.codecentric.retailbank.service.interfaces;

import com.codecentric.retailbank.model.domain.Customer;
import com.codecentric.retailbank.repository.helpers.ListPage;

public interface ICustomerService {

    Customer getById(Long id);

    Customer getByPersonalDetails(String details);

    ListPage<Customer> getAllCustomers(int pageIndex, int pageSize);

    Customer addCustomer(Customer customer);

    Customer updateCustomer(Customer customer);

    void deleteCustomer(Customer customer);

    void deleteCustomer(Long id);
}
