package com.codecentric.retailbank.web.controller;

import com.codecentric.retailbank.model.domain.Address;
import com.codecentric.retailbank.model.dto.AddressDto;
import com.codecentric.retailbank.service.AddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static com.codecentric.retailbank.constants.Constant.PAGE_SIZE;

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


    @RequestMapping(value = {"", "/", "/index", "/list", "/list/{pageIdx}"}, method = RequestMethod.GET)
    public String getIndexPage(@PathVariable Optional<Integer> pageIdx,
                               Model model) {

        // If pageIndex is less than 1 set it to 1.
        Integer pageIndex = pageIdx.isPresent() ? pageIdx.get() : 0;
        pageIndex = pageIndex == 0 || pageIndex < 0 || pageIndex == null ?
                0 : pageIndex;

        Page<Address> addresses = addressService.getAllAddressesByPage(pageIndex, PAGE_SIZE);

        model.addAttribute("currentPageIndex", pageIndex);
        model.addAttribute("totalPages", addresses.getTotalPages());
        model.addAttribute("addresses", addresses.getContent());
        return CONTROLLER_NAME + "/index";
    }

    @RequestMapping(value = {"/form", "/form/{id}"}, method = RequestMethod.GET)
    public ModelAndView getFormPage(@PathVariable("id") Optional<Long> id) {
        Address address = id.isPresent() ?
                addressService.getById(id.get()) : new Address(0L);

        AddressDto addressDto = new AddressDto(
                address.getId(),
                address.getLine1(),
                address.getLine2(),
                address.getTownCity(),
                address.getZipPostcode(),
                address.getStateProvinceCountry(),
                address.getCountry(),
                address.getOtherDetails()
        );

        return new ModelAndView(CONTROLLER_NAME + "/form", "addressDto", addressDto);
    }

    @RequestMapping(value = "/formSubmit", method = RequestMethod.POST)
    public String onFormSubmit(@ModelAttribute("addressDto") @Valid AddressDto addressDto,
                               BindingResult result,
                               Model model,
                               RedirectAttributes redirectAttributes){
        // Check if valid
        if (addressDto == null || result.hasErrors()) {
            model.addAttribute("addressDto", addressDto);
            return CONTROLLER_NAME + "/form";
        }

        // Try adding/updating bank
        try {
            if (addressDto.getId() != null && addressDto.getId() != 0) {
                Address updatedAddress = addressService.getById(addressDto.getId());
                updatedAddress.setFields(
                        addressDto.getLine1(),
                        addressDto.getLine2(),
                        addressDto.getTownCity(),
                        addressDto.getZipPostcode(),
                        addressDto.getStateProvinceCountry(),
                        addressDto.getCountry(),
                        addressDto.getOtherDetails()
                );
                addressService.updateAddress(updatedAddress);

                redirectAttributes.addAttribute("message", "Successfully updated address.");
            } else {
                Address newAddress = new Address();
                newAddress.setFields(
                        addressDto.getLine1(),
                        addressDto.getLine2(),
                        addressDto.getTownCity(),
                        addressDto.getZipPostcode(),
                        addressDto.getStateProvinceCountry(),
                        addressDto.getCountry(),
                        addressDto.getOtherDetails()
                );
                addressService.addAddress(newAddress);

                redirectAttributes.addAttribute("message", "Successfully created address.");
            }
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());

            // If we get here something went wrong
            redirectAttributes.asMap().clear();
            model.addAttribute("addressDto", addressDto);
            redirectAttributes.addAttribute("error", ex.getMessage());
            return "redirect:/" + CONTROLLER_NAME + "/form";
        }

        return "redirect:/" + CONTROLLER_NAME + "/list";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String onDeleteSubmit(@PathVariable("id") Long id,
                                 RedirectAttributes redirectAttributes) {
        try {
            addressService.deleteAddress(id);

            redirectAttributes.addAttribute("message", "Successfully deleted address.");
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());

            // Something went wrong.
            redirectAttributes.asMap().clear();
            redirectAttributes.addAttribute("error", ex.getMessage());
            return "redirect:/" + CONTROLLER_NAME + "/list";
        }

        return "redirect:/" + CONTROLLER_NAME + "/list";
    }
    // ############ TESTS ############ //
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

}
