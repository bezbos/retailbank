package com.codecentric.retailbank.web.controller;

import com.codecentric.retailbank.model.domain.Bank;
import com.codecentric.retailbank.model.domain.BankAccount;
import com.codecentric.retailbank.model.dto.BankDto;
import com.codecentric.retailbank.service.BankService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
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


    @RequestMapping(value = {"", "/", "/index", "/list"}, method = RequestMethod.GET)
    public String getIndexPage(Model model) {
        List<Bank> banks = bankService.getAllBanks();

        model.addAttribute("banks", banks);
        return CONTROLLER_NAME + "/list";
    }

    @RequestMapping(value = {"/edit/{id}", "/create/{id}", "/form/{id}"}, method = RequestMethod.GET)
    public ModelAndView getEditPage(@PathVariable("id") Long id,
                                    Model model){

        Bank bank = bankService.getById(id);

        BankDto bankDto = new BankDto(bank.getId(), bank.getDetails());

        return new ModelAndView(CONTROLLER_NAME + "/form", "bankDto", bankDto);
    }

    @RequestMapping(value = "/formSubmit", method = RequestMethod.POST)
    public ModelAndView onFormSubmit(@ModelAttribute("bankDto") @Valid BankDto bankDto,
                                     BindingResult result){

        // Check if valid
        if(bankDto == null || result.hasErrors())
            return new ModelAndView(CONTROLLER_NAME + "/form", "bankDto", bankDto);

        // Try adding/updating bank
        try {
            if(bankDto.getId() != null){
                Bank updatedBank = bankService.getById(bankDto.getId());
                updatedBank.setDetails(bankDto.getDetails());
                bankService.updateBank(updatedBank);
            }else{
                Bank newBank = new Bank(bankDto.getDetails());
                bankService.addBank(newBank);
            }
        }catch(Exception ex){
            LOGGER.error(ex.getMessage());

            // If we get here something went wrong
            return new ModelAndView(CONTROLLER_NAME + "/form", "bankDto", bankDto);
        }

        return new ModelAndView("redirect:/" + CONTROLLER_NAME + "/list");
    }

    // ############ TESTS ############ //
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
}
