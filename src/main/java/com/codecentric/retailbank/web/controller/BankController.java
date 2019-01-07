package com.codecentric.retailbank.web.controller;

import com.codecentric.retailbank.model.domain.Bank;
import com.codecentric.retailbank.service.BankService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/bank")
public class BankController {

    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    /**
     * Represents the name of the current controller context.
     */
    private String CONTROLLER_NAME = "bank";

    @Autowired
    private BankService bankService;


    public BankController() {
        super();
    }


    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String getTestPage(Model model) {
        boolean readWorks = true;
        boolean addWorks = true;
        boolean updateWorks = true;
        boolean deleteWorks = true;

        try {
            List<Bank> banks = bankService.getAllBanks();
            Bank bank = bankService.getById(1L);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            readWorks = false;
        }
        LOGGER.info("LOG: getAllBanks() completed successfully.");
        LOGGER.info("LOG: getById(1L) completed successfully.");

        try {
            Bank testBank = new Bank();
            testBank.setDetails("TEST");

            bankService.addBank(testBank);
            Bank result = bankService.getByDetails("TEST");
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            addWorks = false;
        }
        LOGGER.info("LOG: addBank(testBank) completed successfully.");


        Bank existingBank = bankService.getByDetails("TEST");
        try {
            existingBank.setDetails("TEST UPDATED");
            Bank result = bankService.updateBank(existingBank);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            updateWorks = false;
        }
        LOGGER.info("LOG: updateBank(existing) completed successfully.");

        try {
            bankService.deleteBank(existingBank);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            deleteWorks = false;
        }
        LOGGER.info("LOG: deleteBank(existingBank) completed successfully.");

        model.addAttribute("readWorks", readWorks);
        model.addAttribute("addWorks", addWorks);
        model.addAttribute("updateWorks", updateWorks);
        model.addAttribute("deleteWorks", deleteWorks);

        return CONTROLLER_NAME + "/test";
    }

    @RequestMapping(value = {"", "/", "/index", "/list"}, method = RequestMethod.GET)
    public String getIndexPage(Model model) {
        List<Bank> banks = bankService.getAllBanks();

        model.addAttribute("banks", banks);
        return CONTROLLER_NAME + "/list";
    }
}
