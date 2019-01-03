package com.codecentric.retailbank.web.controller;

import com.codecentric.retailbank.model.domain.Bank;
import com.codecentric.retailbank.repository.BankRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
    private BankRepository bankRepository;


    public BankController(){super();}


    @RequestMapping(value = {"", "/", "/index"})
    public String getIndexPage(Model model){
        List<Bank> banks = bankRepository.findAll();

        model.addAttribute("banks", banks);
        return CONTROLLER_NAME + "/index";
    }
}
