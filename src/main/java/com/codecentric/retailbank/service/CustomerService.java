package com.codecentric.retailbank.service;

import com.codecentric.retailbank.model.domain.Customer;
import com.codecentric.retailbank.repository.CustomerRepository;
import com.codecentric.retailbank.service.interfaces.ICustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CustomerService implements ICustomerService {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private CustomerRepository customerRepository;


    @Override public Customer getById(Long id) {
        Customer customer = customerRepository.getSingle(id);
        return customer;
    }

    @Override public Customer getByPersonalDetails(String details) {
        Customer customer = customerRepository.getSingleByPersonalDetails(details);
        return customer;
    }

    @Override public List<Customer> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers;
    }

    @Override public Customer addCustomer(Customer customer) {
        Customer result = customerRepository.add(customer);
        return result;
    }

    @Override public Customer updateCustomer(Customer customer) {
        Customer result = customerRepository.update(customer);
        return result;
    }

    @Override public void deleteCustomer(Customer customer) {
        customerRepository.delete(customer);
    }

    @Override public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}
