package com.codecentric.retailbank.service;

import com.codecentric.retailbank.model.domain.Customer;
import com.codecentric.retailbank.repository.JDBC.CustomerRepositoryJDBC;
import com.codecentric.retailbank.service.interfaces.ICustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CustomerService implements ICustomerService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CustomerRepositoryJDBC customerRepositoryJDBC;


    @Override
    public Customer getById(Long id) {
        Customer customer = customerRepositoryJDBC.getSingle(id);
        return customer;
    }

    @Override
    public Customer getByPersonalDetails(String details) {
        Customer customer = customerRepositoryJDBC.getSingleByPersonalDetails(details);
        return customer;
    }

    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> customers = customerRepositoryJDBC.findAll();
        return customers;
    }

    @Override
    public Customer addCustomer(Customer customer) {
        Customer result = customerRepositoryJDBC.add(customer);
        return result;
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        Customer result = customerRepositoryJDBC.update(customer);
        return result;
    }

    @Override
    public void deleteCustomer(Customer customer) {
        customerRepositoryJDBC.delete(customer);
    }

    @Override
    public void deleteCustomer(Long id) {
        customerRepositoryJDBC.deleteById(id);
    }
}
