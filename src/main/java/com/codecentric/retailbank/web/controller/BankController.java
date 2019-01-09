package com.codecentric.retailbank.web.controller;

import com.codecentric.retailbank.model.domain.Bank;
import com.codecentric.retailbank.model.dto.BankDto;
import com.codecentric.retailbank.service.BankService;
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


    @RequestMapping(value = {"", "/", "/index", "/list", "/index/{pageIdx}", "/list/{pageIdx}"}, method = RequestMethod.GET)
    public String getIndexPage(@PathVariable Optional<Integer> pageIdx,
                               Model model) {

        // If pageIndex is less than 1 set it to 1.
        Integer pageIndex = pageIdx.isPresent() ? pageIdx.get() : 0;
        pageIndex = pageIndex == 0 || pageIndex < 0 || pageIndex == null ?
                0 : pageIndex;

        Page<Bank> banks = bankService.getAllBanksByPage(pageIndex, PAGE_SIZE);

        model.addAttribute("currentPageIndex", pageIndex);
        model.addAttribute("totalPages", banks.getTotalPages());
        model.addAttribute("banks", banks.getContent());
        return CONTROLLER_NAME + "/list";
    }

    @RequestMapping(value = {"/form", "/form/{id}"}, method = RequestMethod.GET)
    public ModelAndView getFormPage(@PathVariable("id") Optional<Long> id) {
        Bank bank = id.isPresent() ?
                bankService.getById(id.get()) : new Bank(0L);

        BankDto bankDto = new BankDto(bank.getId(), bank.getDetails());

        return new ModelAndView(CONTROLLER_NAME + "/form", "bankDto", bankDto);
    }

    @RequestMapping(value = "/formSubmit", method = RequestMethod.POST)
    public String onFormSubmit(@ModelAttribute("bankDto") @Valid BankDto bankDto,
                               BindingResult result,
                               Model model,
                               RedirectAttributes redirectAttributes) {

        // Check if valid
        if (bankDto == null || result.hasErrors()) {
            model.addAttribute("bankDto", bankDto);
            return CONTROLLER_NAME + "/form";
        }

        // Try adding/updating bank
        try {
            if (bankDto.getId() != null && bankDto.getId() != 0) {
                Bank updatedBank = bankService.getById(bankDto.getId());
                updatedBank.setDetails(bankDto.getDetails());
                bankService.updateBank(updatedBank);

                redirectAttributes.addAttribute("message", "Successfully updated bank.");
            } else {
                Bank newBank = new Bank(bankDto.getDetails());
                bankService.addBank(newBank);

                redirectAttributes.addAttribute("message", "Successfully created bank.");
            }
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());

            // If we get here something went wrong
            redirectAttributes.asMap().clear();
            model.addAttribute("bankDto", bankDto);
            redirectAttributes.addAttribute("error", ex.getMessage());
            return "redirect:/" + CONTROLLER_NAME + "/form";
        }

        return "redirect:/" + CONTROLLER_NAME + "/list";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String onDeleteSubmit(@PathVariable("id") Long id,
                                 RedirectAttributes redirectAttributes) {
        try {
            bankService.deleteBank(id);

            redirectAttributes.addAttribute("message", "Successfully deleted bank.");
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
