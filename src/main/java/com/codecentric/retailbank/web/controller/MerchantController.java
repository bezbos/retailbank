package com.codecentric.retailbank.web.controller;

import com.codecentric.retailbank.model.domain.Merchant;
import com.codecentric.retailbank.service.MerchantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/merchant")
public class MerchantController {

    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    /**
     * Represents the name of the current controller context.
     */
    private String CONTROLLER_NAME = "merchant";

    @Autowired
    private MerchantService merchantService;


    public MerchantController(){super();}


    @RequestMapping(value = {"", "/", "/index"})
    public String getIndexPage(Model model){
        List<Merchant> merchants = merchantService.getAllMerchants();

        model.addAttribute("merchants", merchants);
        return CONTROLLER_NAME + "/index";
    }


    // ############ TESTS ############ //
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String getTestPage(Model model) {
        boolean readWorks = true;
        boolean addWorks = true;
        boolean updateWorks = true;
        boolean deleteWorks = true;

        try {
            List<Merchant> merchants = merchantService.getAllMerchants();
            Merchant merchant = merchantService.getById(1L);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            readWorks = false;
        }
        LOGGER.info("LOG: getAllMerchants() completed successfully.");
        LOGGER.info("LOG: getById(1L) completed successfully.");

        try {
            Merchant testMerchant = new Merchant();
            testMerchant.setDetails("TEST");

            merchantService.addMerchant(testMerchant);
            Merchant result = merchantService.getByDetails("TEST");
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            addWorks = false;
        }
        LOGGER.info("LOG: addMerchant(testMerchant) completed successfully.");


        Merchant existingMerchant = merchantService.getByDetails("TEST");
        try {
            existingMerchant.setDetails("TEST UPDATED");

            Merchant result = merchantService.updateMerchant(existingMerchant);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            updateWorks = false;
        }
        LOGGER.info("LOG: updateMerchant(existing) completed successfully.");

        try {
            merchantService.deleteMerchant(existingMerchant);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            deleteWorks = false;
        }
        LOGGER.info("LOG: deleteMerchant(existingMerchant) completed successfully.");

        model.addAttribute("readWorks", readWorks);
        model.addAttribute("addWorks", addWorks);
        model.addAttribute("updateWorks", updateWorks);
        model.addAttribute("deleteWorks", deleteWorks);

        return CONTROLLER_NAME + "/test";
    }
}
