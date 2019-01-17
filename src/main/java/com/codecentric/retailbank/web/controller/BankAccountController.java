package com.codecentric.retailbank.web.controller;

import com.codecentric.retailbank.model.domain.OLD.BankAccount;
import com.codecentric.retailbank.service.BankAccountService;
import com.codecentric.retailbank.service.CustomerService;
import com.codecentric.retailbank.service.RefAccountStatusService;
import com.codecentric.retailbank.service.RefAccountTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/bankAccount")
public class BankAccountController {
    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    /**
     * Represents the name of the current controller context.
     */
    private String CONTROLLER_NAME = "bankAccount";

    @Autowired
    private BankAccountService bankAccountService;
    @Autowired
    private RefAccountStatusService refAccountStatusService;
    @Autowired
    private RefAccountTypeService refAccountTypeService;
    @Autowired
    private CustomerService customerService;


    public BankAccountController() {
        super();
    }


    @RequestMapping(value = {"", "/", "/index"})
    public String getIndexPage(Model model) {
        List<BankAccount> bankAccounts = bankAccountService.getAllAccounts();

        model.addAttribute("bankAccounts", bankAccounts);
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
            List<BankAccount> bankAccounts = bankAccountService.getAllAccounts();
            BankAccount bankAccount = bankAccountService.getById(1L);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            readWorks = false;
        }
        LOGGER.info("LOG: getAllAccounts() completed successfully.");
        LOGGER.info("LOG: getById(1L) completed successfully.");

        try {
            BankAccount testBankAccount = new BankAccount();
            testBankAccount.setStatus(refAccountStatusService.getById(1L));
            testBankAccount.setType(refAccountTypeService.getById(1L));
            testBankAccount.setCustomer(customerService.getById(1L));
            testBankAccount.setDetails("TEST");
            testBankAccount.setBalance(new BigDecimal(1000));

            bankAccountService.addAccount(testBankAccount);
            BankAccount result = bankAccountService.getByDetails("TEST");
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            addWorks = false;
        }
        LOGGER.info("LOG: addBankAccount(testBankAccount) completed successfully.");


        BankAccount existingBankAccount = bankAccountService.getByDetails("TEST");
        try {
            existingBankAccount.setStatus(refAccountStatusService.getById(2L));
            existingBankAccount.setType(refAccountTypeService.getById(2L));
            existingBankAccount.setCustomer(customerService.getById(2L));
            existingBankAccount.setDetails("TEST UPDATED");
            existingBankAccount.setBalance(new BigDecimal(5000));

            BankAccount result = bankAccountService.updateAccount(existingBankAccount);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            updateWorks = false;
        }
        LOGGER.info("LOG: updateBankAccount(existing) completed successfully.");

        try {
            bankAccountService.deleteAccount(existingBankAccount);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            deleteWorks = false;
        }
        LOGGER.info("LOG: deleteBankAccount(existingBankAccount) completed successfully.");

        model.addAttribute("readWorks", readWorks);
        model.addAttribute("addWorks", addWorks);
        model.addAttribute("updateWorks", updateWorks);
        model.addAttribute("deleteWorks", deleteWorks);

        return CONTROLLER_NAME + "/test";
    }
}
