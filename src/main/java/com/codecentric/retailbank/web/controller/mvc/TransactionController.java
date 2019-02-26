package com.codecentric.retailbank.web.controller.mvc;

import com.codecentric.retailbank.model.domain.Transaction;
import com.codecentric.retailbank.service.BankAccountService;
import com.codecentric.retailbank.service.MerchantService;
import com.codecentric.retailbank.service.RefTransactionTypeService;
import com.codecentric.retailbank.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/transaction")
public class TransactionController {

    //region FIELDS
    private Logger LOGGER = LoggerFactory.getLogger(getClass());
    private String CONTROLLER_NAME = "transaction";
    //endregion

    //region SERVICES
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private RefTransactionTypeService transactionTypeService;
    @Autowired
    private BankAccountService bankAccountService;
    //endregion

    //region INDEX
    @RequestMapping(value = {"", "/", "/index"})
    public String getIndexPage(Model model) {
        List<Transaction> transactions = transactionService.getAllTransactions();

        model.addAttribute("transactions", transactions);
        return CONTROLLER_NAME + "/index";
    }
    //endregion

}
