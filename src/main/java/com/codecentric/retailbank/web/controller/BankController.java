package com.codecentric.retailbank.web.controller;

import com.codecentric.retailbank.model.domain.Bank;
import com.codecentric.retailbank.model.dto.BankDto;
import com.codecentric.retailbank.repository.JDBC.BankRepositoryJDBC;
import com.codecentric.retailbank.repository.JDBC.wrappers.ListPage;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.sql.SQLException;
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
                               Model model) throws SQLException {

        // If pageIndex is less than 1 set it to 1.
        Integer pageIndex = pageIdx.isPresent() ? pageIdx.get() : 0;
        pageIndex = pageIndex == 0 || pageIndex < 0 || pageIndex == null ?
                0 : pageIndex;

        BankRepositoryJDBC bankRepositoryJDBC = new BankRepositoryJDBC();
        bankRepositoryJDBC.singleOrDefault(0L);
        bankRepositoryJDBC.single(1L);
        bankRepositoryJDBC.update(new Bank(1L, "ANOTHER TEST"));
        bankRepositoryJDBC.delete(new Bank(1L));
        bankRepositoryJDBC.deleteById(2L);

        ListPage<Bank> banks = bankService.getAllBanksByPage(pageIndex, PAGE_SIZE);

        model.addAttribute("currentPageIndex", pageIndex);
        model.addAttribute("totalPages", banks.getPageCount());
        model.addAttribute("banks", banks.getModels());
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

}
