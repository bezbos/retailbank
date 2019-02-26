package com.codecentric.retailbank.web.controller.mvc;

import com.codecentric.retailbank.model.domain.Customer;
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

    //region FIELDS
    private Logger LOGGER = LoggerFactory.getLogger(getClass());
    private String CONTROLLER_NAME = "customer";
    //endregion

    //region SERVICES
    @Autowired
    private CustomerService customerService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private BranchService branchService;
    //endregion

    //region INDEX
    @RequestMapping(value = {"", "/", "/index", "/list"})
    public String getIndexPage(Model model) {
        List<Customer> customers = customerService.getAllCustomers();

        model.addAttribute("customers", customers);
        return CONTROLLER_NAME + "/index";
    }
    //endregion
}
