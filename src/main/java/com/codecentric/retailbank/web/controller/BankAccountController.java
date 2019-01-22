package com.codecentric.retailbank.web.controller;

import com.codecentric.retailbank.model.domain.BankAccount;
import com.codecentric.retailbank.service.BankAccountService;
import com.codecentric.retailbank.service.CustomerService;
import com.codecentric.retailbank.service.RefAccountStatusService;
import com.codecentric.retailbank.service.RefAccountTypeService;
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

    //region FIELDS
    private Logger LOGGER = LoggerFactory.getLogger(getClass());
    private String CONTROLLER_NAME = "bankAccount";
    //endregion

    //region SERVICES
    @Autowired
    private BankAccountService bankAccountService;
    @Autowired
    private RefAccountStatusService refAccountStatusService;
    @Autowired
    private RefAccountTypeService refAccountTypeService;
    @Autowired
    private CustomerService customerService;
    //endregion

    //region INDEX
    @RequestMapping(value = {"", "/", "/index"})
    public String getIndexPage(Model model) {
        List<BankAccount> bankAccounts = bankAccountService.getAllAccounts();

        model.addAttribute("bankAccounts", bankAccounts);
        return CONTROLLER_NAME + "/index";
    }
    //endregion

}
