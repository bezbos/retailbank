package com.codecentric.retailbank.web.controller.mvc;

import com.codecentric.retailbank.model.domain.BankAccount;
import com.codecentric.retailbank.service.BankAccountService;
import com.codecentric.retailbank.service.CustomerService;
import com.codecentric.retailbank.service.RefAccountStatusService;
import com.codecentric.retailbank.service.RefAccountTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/bankAccount")
public class BankAccountController {

    //region FIELDS
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final String CONTROLLER_NAME = "bankAccount";

    private final BankAccountService bankAccountService;
    private final RefAccountStatusService refAccountStatusService;
    private final RefAccountTypeService refAccountTypeService;
    private final CustomerService customerService;
    //endregion

    //region CONSTRUCTOR
    @Autowired public BankAccountController(BankAccountService bankAccountService,
                                            RefAccountStatusService refAccountStatusService,
                                            RefAccountTypeService refAccountTypeService,
                                            CustomerService customerService) {
        this.bankAccountService = bankAccountService;
        this.refAccountStatusService = refAccountStatusService;
        this.refAccountTypeService = refAccountTypeService;
        this.customerService = customerService;
    }
    //endregion


    //region INDEX
    @GetMapping({"", "/", "/index"})
    public ModelAndView getIndexPage() {
        List<BankAccount> bankAccounts = bankAccountService.getAllAccounts();

        return new ModelAndView(CONTROLLER_NAME + "/index", "bankAccounts", bankAccounts);
    }
    //endregion

}
