package com.codecentric.retailbank.service;

import com.codecentric.retailbank.model.domain.Customer;
import com.codecentric.retailbank.repository.SpringData.CustomerRepository;
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
    private CustomerRepository repo;


    public CustomerService() {
        super();
    }


    @Override
    public Customer getById(Long id) {
        Customer customer = repo.getOne(id);
        return customer;
    }

    @Override
    public Customer getByPersonalDetails(String details) {
        Customer customer = repo.findByPersonalDetails(details);
        return customer;
    }

    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> customers = repo.findAll();
        return customers;
    }

    @Override
    public Customer addCustomer(Customer customer) {
        Customer result = repo.save(customer);
        return result;
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        Customer result = repo.save(customer);
        return result;
    }

    @Override
    public void deleteCustomer(Customer customer) {
        repo.delete(customer);
    }

    @Override
    public void deleteCustomer(Long id) {
        repo.deleteById(id);
    }
}
