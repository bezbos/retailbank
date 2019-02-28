package com.codecentric.retailbank.web.controller.api.v1;

import com.codecentric.retailbank.model.domain.Transaction;
import com.codecentric.retailbank.model.dto.TransactionDto;
import com.codecentric.retailbank.repository.helpers.ListPage;
import com.codecentric.retailbank.service.TransactionService;
import com.codecentric.retailbank.web.controller.api.v1.helpers.PageableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.codecentric.retailbank.constants.Constant.PAGE_SIZE;

@RestController
@RequestMapping("/api/v1")
public class TransactionApiController {

    // TODO(bosko): Implement logging
    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private TransactionService transactionService;

    //region HTTP GET
    @GetMapping(value = {"/transactions", "/transactions/{page}"})
    ResponseEntity<PageableList<TransactionDto>> transactions(@PathVariable("page") Optional<Integer> page) {

        // If pageIndex is less than 1 set it to 1.
        Integer pageIndex = page.isPresent() ? page.get() : 0;
        pageIndex = pageIndex == 0 || pageIndex < 0 || pageIndex == null ?
                0 : pageIndex;

        ListPage<Transaction> transactions = transactionService.getAllTransactions(pageIndex, PAGE_SIZE);
        List<TransactionDto> transactionDtos = new ArrayList<>();
        transactions.getModels()
                .forEach(
                        x -> transactionDtos.add(
                                new TransactionDto(
                                        x.getId(),
                                        x.getAccount().getDto(),
                                        x.getMerchant().getDto(),
                                        x.getType().getDto(),
                                        x.getDate(),
                                        x.getAmount(),
                                        x.getDetails()
                                )));

        PageableList<TransactionDto> pageableTransactionDtos = new PageableList<>(pageIndex, transactionDtos, transactions.getPageCount());

        return pageableTransactionDtos.currentPage == null
                //  404 NOT FOUND
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                //  200 OK
                : new ResponseEntity<>(pageableTransactionDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/transaction/{id}")
    ResponseEntity<TransactionDto> transactionById(@PathVariable("id") Long id) {
        Transaction transaction = transactionService.getById(id);
        TransactionDto transactionDto = transaction == null
                ? null
                : new TransactionDto(
                transaction.getId(),
                transaction.getAccount().getDto(),
                transaction.getMerchant().getDto(),
                transaction.getType().getDto(),
                transaction.getDate(),
                transaction.getAmount(),
                transaction.getDetails());

        return transaction == null
                //  404 NOT FOUND
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                //  200 OK
                : new ResponseEntity<>(transactionDto, HttpStatus.OK);
    }

    @GetMapping(value = "/transaction")
    ResponseEntity<TransactionDto> transactionByDetails(@RequestParam("details") String details) {
        Transaction transaction = transactionService.getByDetails(details);
        TransactionDto transactionDto = transaction == null
                ? null
                : new TransactionDto(
                transaction.getId(),
                transaction.getAccount().getDto(),
                transaction.getMerchant().getDto(),
                transaction.getType().getDto(),
                transaction.getDate(),
                transaction.getAmount(),
                transaction.getDetails());

        return transaction == null
                //  404 NOT FOUND
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                //  200 OK
                : new ResponseEntity<>(transactionDto, HttpStatus.OK);
    }
    //endregion

    //region HTTP POST
    @PostMapping(value = "/transaction")
    ResponseEntity<TransactionDto> createTransaction(@RequestBody TransactionDto clientDto) {
        try {
            transactionService.addTransaction(clientDto.getDBModel());
        } catch (Exception e) {
            //  400 BAD REQUEST
            return new ResponseEntity<>(clientDto, HttpStatus.BAD_REQUEST);
        }

        //  201 CREATED
        return new ResponseEntity<>(clientDto, HttpStatus.CREATED);
    }
    //endregion

    // NOTE: Transactions cannot be updated or deleted.
    // You can only read existing transactions or create new ones.
}
