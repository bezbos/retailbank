package com.codecentric.retailbank.web.controller;

import com.codecentric.retailbank.model.domain.Branch;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

        Page<Branch> branches = branchService.getAllBranchesByPage(pageIndex, PAGE_SIZE);

        model.addAttribute("currentPageIndex", pageIndex);
        model.addAttribute("totalPages", branches.getTotalPages());
        model.addAttribute("branches", branches);
        return CONTROLLER_NAME + "/index";
    }


}
