package com.codecentric.retailbank.web.controller;

import com.codecentric.retailbank.model.domain.Transaction;
import com.codecentric.retailbank.repository.TransactionRepository;
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
    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    /**
     * Represents the name of the current controller context.
     */
    private String CONTROLLER_NAME = "transaction";

    @Autowired
    private TransactionRepository transactionRepository;


    public TransactionController(){super();}


    @RequestMapping(value = {"", "/", "/index"})
    public String getIndexPage(Model model){
        List<Transaction> transactions = transactionRepository.findAll();

        model.addAttribute("transactions", transactions);
        return CONTROLLER_NAME + "/index";
    }

}
