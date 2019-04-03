package com.codecentric.retailbank.web.controller.api.v1;

import com.codecentric.retailbank.model.domain.Transaction;
import com.codecentric.retailbank.model.dto.TransactionDto;
import com.codecentric.retailbank.repository.helpers.ListPage;
import com.codecentric.retailbank.security.UsersUtil;
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

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.codecentric.retailbank.constants.Constant.PAGE_SIZE;

@RestController
@RequestMapping("/api/v1")
public class TransactionApiController {

    //region FIELDS
    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private TransactionService transactionService;
    //endregion


    //region HTTP GET
    @GetMapping({"/transactions", "/transactions/{page}"})
    ResponseEntity<PageableList<TransactionDto>> transactions(@PathVariable("page") Optional<Integer> page) {

        if (!UsersUtil.isAdmin()) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        // If pageIndex is less than 1 set it to 1.
        Integer pageIndex = page.isPresent() ? page.get() : 0;
        pageIndex = pageIndex == 0 || pageIndex < 0 || pageIndex == null ?
                0 : pageIndex;

        ListPage<Transaction> transactions = transactionService.getAllTransactions(pageIndex, PAGE_SIZE);
        List<TransactionDto> transactionDtos = transactions.getModels().stream().map(transaction -> new TransactionDto(
                transaction.getId(),
                transaction.getSenderAccount().getDto(),
                transaction.getReceiverAccount().getDto(),
                transaction.getMerchant().getDto(),
                transaction.getType().getDto(),
                transaction.getDate(),
                transaction.getAmount(),
                transaction.getDetails()
        )).collect(Collectors.toList());

        PageableList<TransactionDto> pageableTransactionDtos = new PageableList<>(pageIndex, transactionDtos, transactions.getPageCount(), transactions.getModelsCount());

        return pageableTransactionDtos.currentPage == null
                //  404 NOT FOUND
                ? ResponseEntity.notFound().build()
                //  200 OK
                : ResponseEntity.ok().location(URI.create("/transactions/" + pageIndex)).body(pageableTransactionDtos);
    }

    @GetMapping("/transaction/{id}")
    ResponseEntity<TransactionDto> transactionById(@PathVariable("id") Long id) {

        if (!UsersUtil.isAdmin()) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        Transaction transaction = transactionService.getById(id);
        TransactionDto transactionDto = transaction == null
                ? null
                : new TransactionDto(
                transaction.getId(),
                transaction.getSenderAccount().getDto(),
                transaction.getReceiverAccount().getDto(),
                transaction.getMerchant().getDto(),
                transaction.getType().getDto(),
                transaction.getDate(),
                transaction.getAmount(),
                transaction.getDetails());

        return transaction == null
                //  404 NOT FOUND
                ? ResponseEntity.notFound().build()
                //  200 OK
                : ResponseEntity.ok().location(URI.create("/transaction/" + id)).body(transactionDto);
    }

    @GetMapping("/transaction")
    ResponseEntity<TransactionDto> transactionByDetails(@RequestParam("details") String details) {

        if (!UsersUtil.isAdmin()) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        Transaction transaction = transactionService.getByDetails(details);
        TransactionDto transactionDto = transaction == null
                ? null
                : new TransactionDto(
                transaction.getId(),
                transaction.getSenderAccount().getDto(),
                transaction.getReceiverAccount().getDto(),
                transaction.getMerchant().getDto(),
                transaction.getType().getDto(),
                transaction.getDate(),
                transaction.getAmount(),
                transaction.getDetails());

        return transaction == null
                //  404 NOT FOUND
                ? ResponseEntity.notFound().build()
                //  200 OK
                : ResponseEntity.ok().location(URI.create("/transaction?details=" + details)).body(transactionDto);
    }
    //endregion

    //region HTTP POST
    @PostMapping("/transaction")
    ResponseEntity<TransactionDto> createPayment(@RequestBody TransactionDto dto) {

        if (!UsersUtil.isAdmin()) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        Transaction result;
        try {
             result = transactionService.createPayment(dto.getDBModel());
        } catch (Exception e) {
            e.printStackTrace();
            //  400 BAD REQUEST
            return ResponseEntity.badRequest().body(dto);
        }

        //  201 CREATED
        return ResponseEntity.created(URI.create("/transaction/" + result.getId())).body(result.getDto());
    }

    //endregion

    // NOTE: Transactions cannot be updated or deleted.
    // You can only read existing transactions or create new ones.
}
