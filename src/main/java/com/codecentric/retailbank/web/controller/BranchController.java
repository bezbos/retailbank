package com.codecentric.retailbank.web.controller;

import com.codecentric.retailbank.model.domain.Branch;
import com.codecentric.retailbank.service.AddressService;
import com.codecentric.retailbank.service.BankService;
import com.codecentric.retailbank.service.BranchService;
import com.codecentric.retailbank.service.RefBranchTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

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

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String getTestPage(Model model) {
        boolean readWorks = true;
        boolean addWorks = true;
        boolean updateWorks = true;
        boolean deleteWorks = true;

        try {
            List<Branch> branches = branchService.getAllBranches();
            Branch branch = branchService.getById(1L);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            readWorks = false;
        }
        LOGGER.info("LOG: getAllBranches() completed successfully.");
        LOGGER.info("LOG: getById(1L) completed successfully.");

        try {
            Branch testBranch = new Branch();
            testBranch.setDetails("TEST");
            testBranch.setAddress(addressService.getById(1L));
            testBranch.setBank(bankService.getById(1L));
            testBranch.setType(refBranchTypeService.getById(1L));


            branchService.addBranch(testBranch);
            Branch result = branchService.getByDetails("TEST");
            if (result == null)
                addWorks = false;
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            addWorks = false;
        }
        LOGGER.info("LOG: addBranch(testBranch) completed successfully.");


        Branch existingBranch = branchService.getByDetails("TEST");
        try {
            existingBranch.setDetails("TEST");
            existingBranch.setAddress(addressService.getById(2L));
            existingBranch.setBank(bankService.getById(2L));
            existingBranch.setType(refBranchTypeService.getById(2L));

            Branch result = branchService.updateBranch(existingBranch);
            if (result == null)
                updateWorks = false;
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            updateWorks = false;
        }
        LOGGER.info("LOG: updateBranch(existing) completed successfully.");

        try {
            branchService.deleteBranch(existingBranch);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            deleteWorks = false;
        }
        LOGGER.info("LOG: deleteBranch(existingBranch) completed successfully.");

        model.addAttribute("readWorks", readWorks);
        model.addAttribute("addWorks", addWorks);
        model.addAttribute("updateWorks", updateWorks);
        model.addAttribute("deleteWorks", deleteWorks);

        return CONTROLLER_NAME + "/test";
    }

    @RequestMapping(value = {"", "/", "/index"})
    public String getIndexPage(Model model) {
        List<Branch> branches = branchService.getAllBranches();

        model.addAttribute("branches", branches);
        return CONTROLLER_NAME + "/index";
    }
}
