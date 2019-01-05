package com.codecentric.retailbank.web.controller;

import com.codecentric.retailbank.model.domain.Address;
import com.codecentric.retailbank.service.AddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/address")
public class AddressController {

    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    /**
     * Represents the name of the current controller context.
     */
    private String CONTROLLER_NAME = "address";

    @Autowired
    private AddressService addressService;


    public AddressController() {
        super();
    }


    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String getTestPage(Model model) {
        boolean readWorks = true;
        boolean addWorks = true;
        boolean updateWorks = true;
        boolean deleteWorks = true;

        try {
            List<Address> addresses = addressService.getAllAddress();
            Address address = addressService.getById(1L);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            readWorks = false;
        }
        LOGGER.info("LOG: getAllAddresses() completed successfully.");
        LOGGER.info("LOG: getById(1L) completed successfully.");

        try {
            Address testAddress = new Address();
            testAddress.setLine1("TEST");
            testAddress.setTownCity("TEST");
            testAddress.setZipPostcode("TEST");
            testAddress.setStateProvinceCountry("TEST");
            testAddress.setCountry("TEST");

            addressService.addAddress(testAddress);
            Address result = addressService.getByLine1("TEST");
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            addWorks = false;
        }
        LOGGER.info("LOG: addAddress(testAddress) completed successfully.");


        Address existingAddress = addressService.getByLine1("TEST");
        try {
            existingAddress.setLine1("TEST UPDATED");
            existingAddress.setTownCity("TEST UPDATED");
            existingAddress.setZipPostcode("TEST UPDATED");
            existingAddress.setStateProvinceCountry("TEST UPDATED");
            existingAddress.setCountry("TEST UPDATED");
            Address result = addressService.updateAddress(existingAddress);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            updateWorks = false;
        }
        LOGGER.info("LOG: updateAddress(existing) completed successfully.");

        try {
            addressService.deleteAddress(existingAddress);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            deleteWorks = false;
        }
        LOGGER.info("LOG: deleteAddress(existingAddress) completed successfully.");

        model.addAttribute("readWorks", readWorks);
        model.addAttribute("addWorks", addWorks);
        model.addAttribute("updateWorks", updateWorks);
        model.addAttribute("deleteWorks", deleteWorks);

        return CONTROLLER_NAME + "/test";
    }

    @RequestMapping(value = {"", "/", "/index"}, method = RequestMethod.GET)
    public String getIndexPage(Model model) {
        List<Address> addresses = addressService.getAllAddress();

        model.addAttribute("addresses", addresses);
        return CONTROLLER_NAME + "/index";
    }
}
