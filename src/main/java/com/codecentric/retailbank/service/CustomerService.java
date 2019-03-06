package com.codecentric.retailbank.service;

import com.codecentric.retailbank.model.domain.Customer;
import com.codecentric.retailbank.repository.CustomerRepository;
import com.codecentric.retailbank.repository.helpers.ListPage;
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

    //region FIELDS
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final CustomerRepository customerRepository;
    //endregion

    //region CONSTRUCTOR
    @Autowired public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    //endregion


    //region READ
    @Override public Customer getById(Long id) {
        Customer customer = customerRepository.single(id);
        return customer;
    }

    @Override public Customer getByPersonalDetails(String details) {
        Customer customer = customerRepository.getSingleByPersonalDetails(details);
        return customer;
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
        customerRepository.delete(customer);
    }

    @Override public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
    //endregion
}
