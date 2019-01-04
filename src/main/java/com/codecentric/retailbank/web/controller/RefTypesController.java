package com.codecentric.retailbank.web.controller;

import com.codecentric.retailbank.model.domain.RefAccountStatus;
import com.codecentric.retailbank.model.domain.RefAccountTypes;
import com.codecentric.retailbank.repository.RefAccountStatusRepository;
import com.codecentric.retailbank.repository.RefAccountTypesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/refTypes")
public class RefTypesController {
    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    /**
     * Represents the name of the current controller context.
     */
    private String CONTROLLER_NAME = "refTypes";

    @Autowired
    private RefAccountTypesRepository refAccountTypesRepository;

    @Autowired
    private RefAccountStatusRepository refAccountStatusRepository;

    public RefTypesController(){super();}


    @RequestMapping(value = {"", "/", "/index"})
    public String getIndexPage(Model model){
        List<RefAccountTypes> refAccountTypes = refAccountTypesRepository.findAll();
        List<RefAccountStatus> refAccountStatuses = refAccountStatusRepository.findAll();

        model.addAttribute("refAccountTypes", refAccountTypes);
        model.addAttribute("refAccountStatuses", refAccountStatuses);
        return CONTROLLER_NAME + "/index";
    }
}
