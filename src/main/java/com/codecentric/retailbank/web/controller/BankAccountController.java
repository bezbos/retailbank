package com.codecentric.retailbank.web.controller;

import com.codecentric.retailbank.model.domain.BankAccount;
import com.codecentric.retailbank.repository.BankAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/bankAccount")
public class BankAccountController {
    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    /**
     * Represents the name of the current controller context.
     */
    private String CONTROLLER_NAME = "bankAccount";

    @Autowired
    private BankAccountRepository bankAccountRepository;


    public BankAccountController() {
        super();
    }


    @RequestMapping(value = {"", "/", "/index"})
    public String getIndexPage(Model model) {
        List<BankAccount> bankAccounts = bankAccountRepository.findAll();

        model.addAttribute("bankAccounts", bankAccounts);
        return CONTROLLER_NAME + "/index";
    }
}
