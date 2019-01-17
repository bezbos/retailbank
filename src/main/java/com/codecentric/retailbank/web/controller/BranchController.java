package com.codecentric.retailbank.web.controller;

import com.codecentric.retailbank.model.domain.Address;
import com.codecentric.retailbank.model.domain.Bank;
import com.codecentric.retailbank.model.domain.OLD.BranchOLD;
import com.codecentric.retailbank.model.domain.OLD.RefBranchTypeOLD;
import com.codecentric.retailbank.model.dto.BranchDto;
import com.codecentric.retailbank.service.AddressService;
import com.codecentric.retailbank.service.BankService;
import com.codecentric.retailbank.service.BranchService;
import com.codecentric.retailbank.service.RefBranchTypeService;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Optional;

import static com.codecentric.retailbank.constants.Constant.PAGE_SIZE;

@Controller
@RequestMapping("/branch")
public class BranchController {
    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    /**
     * Represents the name of the current controller context.
     */
    private String CONTROLLER_NAME = "branch";

    @Autowired
    private BranchService branchService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private BankService bankService;
    @Autowired
    private RefBranchTypeService refBranchTypeService;

    public BranchController() {
        super();
    }


    @RequestMapping(value = {"", "/", "/index", "/list", "/index/{pageIdx}", "/list/{pageIdx}"}, method = RequestMethod.GET)
    public String getIndexPage(@PathVariable Optional<Integer> pageIdx,
                               Model model) {

        // If pageIndex is less than 1 set it to 1.
        Integer pageIndex = pageIdx.isPresent() ? pageIdx.get() : 0;
        pageIndex = pageIndex == 0 || pageIndex < 0 || pageIndex == null ?
                0 : pageIndex;

        Page<BranchOLD> branches = branchService.getAllBranchesByPage(pageIndex, PAGE_SIZE);

        model.addAttribute("currentPageIndex", pageIndex);
        model.addAttribute("totalPages", branches.getTotalPages());
        model.addAttribute("branches", branches);
        return CONTROLLER_NAME + "/index";
    }

    @RequestMapping(value = {"/form", "/form/{id}"}, method = RequestMethod.GET)
    public String getFormPage(@PathVariable("id") Optional<Long> id,
                              Model model) {
        BranchOLD branch = id.isPresent() ?
                branchService.getById(id.get()) : new BranchOLD(0L);

        BranchDto branchDto = new BranchDto(
                branch.getId(),
                branch.getAddress(),
                branch.getBank(),
                branch.getType(),
                branch.getDetails()
        );

        model.addAttribute("branchDto", branchDto);
        model.addAttribute("allBanks", bankService.getAllBanks());
        model.addAttribute("allTypes", refBranchTypeService.getAllRefBranchTypes());
        return CONTROLLER_NAME + "/form";
    }

    @RequestMapping(value = "/formSubmit", method = RequestMethod.POST)
    public String onFormSubmit(@ModelAttribute("branchDto") @Valid BranchDto dto,
                               BindingResult result,
                               Model model,
                               RedirectAttributes redirectAttributes) {

        // Check if valid
        if (dto == null || result.hasErrors()) {
            model.addAttribute("branchDto", dto);
            return CONTROLLER_NAME + "/form";
        }

        // Try adding/updating branch
        try {
            if (dto.getId() != null && dto.getId() != 0) {
                BranchOLD updatedBranch = branchService.getById(dto.getId());

                Address address = addressService.getByLine1(dto.getAddress().getLine1());
                if (address == null) {
                    address = new Address();
                    address.setLine1(dto.getAddress().getLine1());

                    addressService.addAddress(address);
                }

                Bank bank = bankService.getById(dto.getBank().getId());

                RefBranchTypeOLD refBranchType = refBranchTypeService.getById(dto.getType().getId());

                updatedBranch.setFields(
                        address,
                        bank,
                        refBranchType,
                        dto.getDetails()
                );

                branchService.updateBranch(updatedBranch);

                redirectAttributes.addAttribute("message", "Successfully updated branch.");
            } else {
                BranchOLD newBranch = new BranchOLD();

                Address address = addressService.getByLine1(dto.getAddress().getLine1());
                if (address == null) {
                    address = new Address();
                    address.setLine1(dto.getAddress().getLine1());

                    addressService.addAddress(address);
                }

                Bank bank = bankService.getById(dto.getBank().getId());

                RefBranchTypeOLD refBranchType = refBranchTypeService.getById(dto.getType().getId());

                newBranch.setFields(
                        address,
                        bank,
                        refBranchType,
                        dto.getDetails()
                );

                branchService.addBranch(newBranch);

                redirectAttributes.addAttribute("message", "Successfully created branch.");
            }
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());

            // If we get here something went wrong
            redirectAttributes.asMap().clear();
            model.addAttribute("branchDto", dto);
            redirectAttributes.addAttribute("error", ex.getMessage());
            return "redirect:/" + CONTROLLER_NAME + "/form";
        }

        return "redirect:/" + CONTROLLER_NAME + "/list";
    }
}
