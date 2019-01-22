package com.codecentric.retailbank.web.controller;

import com.codecentric.retailbank.model.domain.Address;
import com.codecentric.retailbank.model.domain.Bank;
import com.codecentric.retailbank.model.domain.Branch;
import com.codecentric.retailbank.model.domain.RefBranchType;
import com.codecentric.retailbank.model.dto.BranchDto;
import com.codecentric.retailbank.repository.helpers.ListPage;
import com.codecentric.retailbank.service.AddressService;
import com.codecentric.retailbank.service.BankService;
import com.codecentric.retailbank.service.BranchService;
import com.codecentric.retailbank.service.RefBranchTypeService;
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


    @RequestMapping(value = {"", "/", "/index", "/list", "/index/{pageIdx}", "/list/{pageIdx}"}, method = RequestMethod.GET)
    public String getIndexPage(@PathVariable Optional<Integer> pageIdx,
                               Model model) {

        // If pageIndex is less than 1 set it to 1.
        Integer pageIndex = pageIdx.isPresent() ? pageIdx.get() : 0;
        pageIndex = pageIndex == 0 || pageIndex < 0 || pageIndex == null ?
                0 : pageIndex;

        ListPage<Branch> branches = branchService.getAllBranchesByPage(pageIndex, PAGE_SIZE);

        model.addAttribute("currentPageIndex", pageIndex);
        model.addAttribute("totalPages", branches.getPageCount());
        model.addAttribute("branches", branches.getModels());
        return CONTROLLER_NAME + "/index";
    }

    @RequestMapping(value = {"/form", "/form/{id}"}, method = RequestMethod.GET)
    public String getFormPage(@PathVariable("id") Optional<Long> id,
                              Model model) {

        Branch branch;
        BranchDto branchDto;
        if (id.isPresent()) {
            // Get the Branch
            branch = branchService.getById(id.get());

            // Construct the DTO
            branchDto = new BranchDto(
                    branch.getId(),
                    branch.getAddress().getDto(),
                    branch.getBank().getDto(),
                    branch.getRefBranchType().getDto(),
                    branch.getDetails()
            );
        } else {
            // Construct new Branch
            branch = new Branch(0L);

            // Construct the DTO
            branchDto = new BranchDto(branch.getId());
        }

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

        // Check if DTO is valid
        if (dto == null || result.hasErrors()) {
            // If not valid, return form w/ model to client.
            model.addAttribute("branchDto", dto);
            return CONTROLLER_NAME + "/form";
        }

        // Try adding/updating branch
        try {
            // If Branch exists, update it
            if (dto.getId() != null && dto.getId() != 0) {
                // Get the existing Branch
                Branch existingBranch = branchService.getById(dto.getId());

                // Get the FK rows
                Address address = addressService.getByLine1(dto.getAddress().getLine1());
                Bank bank = bankService.getById(dto.getBank().getId());
                RefBranchType refBranchType = refBranchTypeService.getById(dto.getType().getId());

                existingBranch.setFields(
                        address,
                        bank,
                        refBranchType,
                        dto.getDetails()
                );

                branchService.updateBranch(existingBranch);

                redirectAttributes.addAttribute("message", "Successfully updated branch.");
            } else {
                // If Branch doesn't exist, insert it
                Branch branch = new Branch();

                Address existingAddress = addressService.getByLine1(dto.getAddress().getLine1());
                if(existingAddress == null){
                    addressService.addAddress(dto.getAddress().getDBModel());
                    existingAddress = addressService.getByLine1(dto.getAddress().getLine1());
                }

                Bank existingBank = bankService.getById(dto.getBank().getId());
                RefBranchType existingRefBranchType = refBranchTypeService.getById(dto.getType().getId());
                branch.setFields(
                        existingAddress,
                        existingBank,
                        existingRefBranchType,
                        dto.getDetails()
                );

                branchService.addBranch(branch);

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

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String onDeleteSubmit(@PathVariable("id") Long id,
                                 RedirectAttributes redirectAttributes) {
        try {
            branchService.deleteBranch(id);

            redirectAttributes.addAttribute("message", "Successfully deleted branch.");
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
