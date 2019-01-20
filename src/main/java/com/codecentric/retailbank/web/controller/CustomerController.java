package com.codecentric.retailbank.web.controller;

import com.codecentric.retailbank.model.domain.Address;
import com.codecentric.retailbank.model.domain.Branch;
import com.codecentric.retailbank.model.domain.Customer;
import com.codecentric.retailbank.repository.JDBC.CustomerRepositoryJDBC;
import com.codecentric.retailbank.service.AddressService;
import com.codecentric.retailbank.service.BranchService;
import com.codecentric.retailbank.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    /**
     * Represents the name of the current controller context.
     */
    private String CONTROLLER_NAME = "customer";

    @Autowired
    private CustomerService customerService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private BranchService branchService;


    @RequestMapping(value = {"", "/", "/index", "/list"})
    public String getIndexPage(Model model) {
        CustomerRepositoryJDBC jdbc = new CustomerRepositoryJDBC();
        List<Customer> customers = jdbc.findAllOrDefault();
        List<Customer> test1 = jdbc.findAll();
        List<Customer> test2 = jdbc.findAllRange(0, 4).getModels();
        List<Customer> test3 = jdbc.findAllRangeOrDefault(0, 4).getModels();
        Customer test4 = jdbc.getSingle(1L);
        Customer test5 = jdbc.getSingleOrDefault(1L);
        Customer test6 = jdbc.add(new Customer(new Address(1L), new Branch(1L), "JDBC TEST", "JDBC TEST"));
        Customer test7 = jdbc.update(new Customer(2L ,new Address(2L), new Branch(2L), "JDBC UPDATE TEST", "JDBC UPDATE TEST"));


//        List<Customer> customers = customerService.getAllCustomers();

        model.addAttribute("customers", customers);
        return CONTROLLER_NAME + "/index";
    }
}
