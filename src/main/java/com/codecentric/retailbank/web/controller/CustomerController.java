package com.codecentric.retailbank.web.controller;

import com.codecentric.retailbank.model.domain.OLD.Customer;
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


    public CustomerController() {
        super();
    }


    @RequestMapping(value = {"", "/", "/index"})
    public String getIndexPage(Model model) {
        List<Customer> customers = customerService.getAllCustomers();

        model.addAttribute("customers", customers);
        return CONTROLLER_NAME + "/index";
    }
}
