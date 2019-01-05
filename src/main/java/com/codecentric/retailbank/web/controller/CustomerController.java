package com.codecentric.retailbank.web.controller;

import com.codecentric.retailbank.model.domain.Customer;
import com.codecentric.retailbank.repository.CustomerRepository;
import com.codecentric.retailbank.service.AddressService;
import com.codecentric.retailbank.service.BranchService;
import com.codecentric.retailbank.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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


    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String getTestPage(Model model) {
        boolean readWorks = true;
        boolean addWorks = true;
        boolean updateWorks = true;
        boolean deleteWorks = true;

        try {
            List<Customer> customers = customerService.getAllCustomers();
            Customer customer = customerService.getById(1L);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            readWorks = false;
        }
        LOGGER.info("LOG: getAllCustomers() completed successfully.");
        LOGGER.info("LOG: getById(1L) completed successfully.");

        try {
            Customer testCustomer = new Customer();
            testCustomer.setPersonalDetails("TEST");
            testCustomer.setContactDetails("TEST");
            testCustomer.setAddress(addressService.getById(1L));
            testCustomer.setBranch(branchService.getById(1L));


            customerService.addCustomer(testCustomer);
            Customer result = customerService.getByPersonalDetails("TEST");
            if (result == null)
                addWorks = false;
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            addWorks = false;
        }
        LOGGER.info("LOG: addCustomer(testCustomer) completed successfully.");


        Customer existingCustomer = customerService.getByPersonalDetails("TEST");
        try {
            existingCustomer.setPersonalDetails("TEST UPDATED");
            existingCustomer.setContactDetails("TEST UPDATED");
            existingCustomer.setAddress(addressService.getById(2L));
            existingCustomer.setBranch(branchService.getById(2L));

            Customer result = customerService.updateCustomer(existingCustomer);
            if (result == null)
                updateWorks = false;
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            updateWorks = false;
        }
        LOGGER.info("LOG: updateCustomer(existing) completed successfully.");

        try {
            customerService.deleteCustomer(existingCustomer);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            deleteWorks = false;
        }
        LOGGER.info("LOG: deleteCustomer(existingCustomer) completed successfully.");

        model.addAttribute("readWorks", readWorks);
        model.addAttribute("addWorks", addWorks);
        model.addAttribute("updateWorks", updateWorks);
        model.addAttribute("deleteWorks", deleteWorks);

        return CONTROLLER_NAME + "/test";
    }

    @RequestMapping(value = {"", "/", "/index"})
    public String getIndexPage(Model model) {
        List<Customer> customers = customerService.getAllCustomers();

        model.addAttribute("customers", customers);
        return CONTROLLER_NAME + "/index";
    }
}
