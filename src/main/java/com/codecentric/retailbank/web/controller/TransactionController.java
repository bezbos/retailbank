package com.codecentric.retailbank.web.controller;

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
import org.springframework.web.bind.annotation.RequestMethod;

import java.math.BigDecimal;
import java.util.Calendar;
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
    private TransactionService transactionService;
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private RefTransactionTypeService transactionTypeService;
    @Autowired
    private BankAccountService bankAccountService;


    public TransactionController() {
        super();
    }


    @RequestMapping(value = {"", "/", "/index"})
    public String getIndexPage(Model model) {
        List<Transaction> transactions = transactionService.getAllTransactions();

        model.addAttribute("transactions", transactions);
        return CONTROLLER_NAME + "/index";
    }


    // ############ TESTS ############ //
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String getTransactionTestPage(Model model) {
        boolean readWorks = true;
        boolean addWorks = true;
        boolean updateWorks = true;
        boolean deleteWorks = true;

        try {
            List<Transaction> transactions = transactionService.getAllTransactions();
            Transaction transaction = transactionService.getById(1L);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            readWorks = false;
        }
        LOGGER.info("LOG: getAllTransactions() completed successfully.");
        LOGGER.info("LOG: getById(1L) completed successfully.");

        try {
            Transaction testTransaction = new Transaction();
            testTransaction.setAccount(bankAccountService.getById(1L));
            testTransaction.setMerchant(merchantService.getById(1L));
            testTransaction.setType(transactionTypeService.getById(1L));
            testTransaction.setDate(Calendar.getInstance().getTime());
            testTransaction.setAmount(new BigDecimal(1000));
            testTransaction.setDetails("TEST");

            transactionService.addTransaction(testTransaction);
            Transaction result = transactionService.getByDetails("TEST");
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            addWorks = false;
        }
        LOGGER.info("LOG: addTransaction(testTransaction) completed successfully.");


        Transaction existingTransaction = transactionService.getByDetails("TEST");
        try {
            existingTransaction.setAccount(bankAccountService.getById(2L));
            existingTransaction.setMerchant(merchantService.getById(2L));
            existingTransaction.setType(transactionTypeService.getById(2L));
            existingTransaction.setDate(Calendar.getInstance().getTime());
            existingTransaction.setAmount(new BigDecimal(5000));
            existingTransaction.setDetails("TEST UPDATED");

            Transaction result = transactionService.updateTransaction(existingTransaction);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            updateWorks = false;
        }
        LOGGER.info("LOG: updateTransaction(existing) completed successfully.");

        try {
            transactionService.deleteTransaction(existingTransaction);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            deleteWorks = false;
        }
        LOGGER.info("LOG: deleteTransaction(existingTransaction) completed successfully.");

        model.addAttribute("readWorks", readWorks);
        model.addAttribute("addWorks", addWorks);
        model.addAttribute("updateWorks", updateWorks);
        model.addAttribute("deleteWorks", deleteWorks);

        return CONTROLLER_NAME + "/test";
    }

}
