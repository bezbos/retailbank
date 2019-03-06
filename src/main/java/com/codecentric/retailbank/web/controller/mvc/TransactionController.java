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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/transaction")
public class TransactionController {

    //region FIELDS
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final String CONTROLLER_NAME = "transaction";

    private final TransactionService transactionService;
    private final MerchantService merchantService;
    private final RefTransactionTypeService transactionTypeService;
    private final BankAccountService bankAccountService;
    //endregion

    //region CONSTRUCTOR
    @Autowired public TransactionController(TransactionService transactionService,
                                            MerchantService merchantService,
                                            RefTransactionTypeService transactionTypeService,
                                            BankAccountService bankAccountService) {
        this.transactionService = transactionService;
        this.merchantService = merchantService;
        this.transactionTypeService = transactionTypeService;
        this.bankAccountService = bankAccountService;
    }
    //endregion

    //region INDEX
    @GetMapping({"", "/", "/index"})
    public ModelAndView getIndexPage(Model model) {
        List<Transaction> transactions = transactionService.getAllTransactions();

        return new ModelAndView(CONTROLLER_NAME + "/index", "transactions", transactions);
    }
    //endregion

}
