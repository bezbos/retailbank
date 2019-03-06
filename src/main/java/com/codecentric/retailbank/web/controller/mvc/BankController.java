package com.codecentric.retailbank.web.controller.mvc;

import com.codecentric.retailbank.model.domain.Bank;
import com.codecentric.retailbank.model.dto.BankDto;
import com.codecentric.retailbank.repository.helpers.ListPage;
import com.codecentric.retailbank.service.BankService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Optional;

import static com.codecentric.retailbank.constants.Constant.PAGE_SIZE;

@Controller
@RequestMapping("/bank")
public class BankController extends BaseController {

    //region FIELDS
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final String CONTROLLER_NAME = "bank";

    private final BankService bankService;
    //endregion

    //region CONSTRUCTOR
    @Autowired public BankController(BankService bankService) {
        this.bankService = bankService;
    }
    //endregion


    //region INDEX
    @GetMapping({"", "/", "/index", "/list", "/index/{pageIdx}", "/list/{pageIdx}"})
    public ModelAndView getIndexPage(@PathVariable Optional<Integer> pageIdx) {
        Integer pageIndex = getValidPageIndex(pageIdx);

        ListPage<Bank> banks = bankService.getAllBanksByPage(pageIndex, PAGE_SIZE);

        return new ModelAndView(CONTROLLER_NAME + "/list", new HashMap<String, Object>() {
            {
                put("currentPageIndex", pageIndex);
                put("totalPages", banks == null ? null : banks.getPageCount());
                put("banks", banks == null ? null : banks.getModels());
            }
        });
    }
    //endregion

    //region FORM
    @GetMapping({"/form", "/form/{id}"})
    public ModelAndView getFormPage(@PathVariable("id") Optional<Long> id) {
        Bank bank = id.isPresent() ?
                bankService.getById(id.get()) : new Bank(0L);

        BankDto bankDto = new BankDto(bank.getId(), bank.getDetails());

        return new ModelAndView(CONTROLLER_NAME + "/form", "bankDto", bankDto);
    }

    @PostMapping("/formSubmit")
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
    //endregion

    //region DELETE
    @GetMapping("/delete/{id}")
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
    //endregion

}
