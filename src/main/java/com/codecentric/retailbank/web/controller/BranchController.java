package com.codecentric.retailbank.web.controller;

import com.codecentric.retailbank.model.domain.Branch;
import com.codecentric.retailbank.repository.BranchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
    private BranchRepository branchRepository;


    public BranchController() {
        super();
    }


    @RequestMapping(value = {"", "/", "/index"})
    public String getIndexPage(Model model) {
        List<Branch> branches = branchRepository.findAll();

        model.addAttribute("branches", branches);
        return CONTROLLER_NAME + "/index";
    }
}
