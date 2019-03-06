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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    //region FIELDS
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final String CONTROLLER_NAME = "customer";

    private final CustomerService customerService;
    private final AddressService addressService;
    private final BranchService branchService;
    //endregion

    //region CONTROLLER
    @Autowired public CustomerController(CustomerService customerService,
                                         AddressService addressService,
                                         BranchService branchService) {
        this.customerService = customerService;
        this.addressService = addressService;
        this.branchService = branchService;
    }
    //endregion

    //region INDEX
    @GetMapping({"", "/", "/index", "/list"})
    public ModelAndView getIndexPage(Model model) {
        List<Customer> customers = customerService.getAllCustomers();

        return new ModelAndView(CONTROLLER_NAME + "/index", "customers", customers);
    }
    //endregion
}
