package com.codecentric.retailbank.web.controller.api.v1;

import com.codecentric.retailbank.model.domain.BankAccount;
import com.codecentric.retailbank.model.dto.BankAccountDto;
import com.codecentric.retailbank.repository.helpers.ListPage;
import com.codecentric.retailbank.security.UsersUtil;
import com.codecentric.retailbank.service.BankAccountService;
import com.codecentric.retailbank.web.controller.api.v1.helpers.PageableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.codecentric.retailbank.constants.Constant.PAGE_SIZE;

@RestController
@RequestMapping("/api/v1")
public class BankAccountApiController {

    //region FIELDS
    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private BankAccountService bankAccountService;
    //endregion

    //region HTTP GET
    @GetMapping({"/bankAccounts", "/bankAccounts/{page}"})
    ResponseEntity<PageableList<BankAccountDto>> bankAccounts(@PathVariable("page") Optional<Integer> page) {

        // If pageIndex is less than 1 set it to 1.
        Integer pageIndex = page.isPresent() ? page.get() : 0;
        pageIndex = pageIndex == 0 || pageIndex < 0 || pageIndex == null ?
                0 : pageIndex;

        ListPage<BankAccount> bankAccounts = bankAccountService.getAllAccounts(pageIndex, PAGE_SIZE);
        List<BankAccountDto> bankAccountDtos = new ArrayList<>();
        bankAccounts.getModels()
                .forEach(account -> bankAccountDtos.add(
                        new BankAccountDto(
                                account.getId(),
                                account.getStatus().getDto(),
                                account.getType().getDto(),
                                account.getCustomer().getDto(),
                                account.getBalance(),
                                account.getDetails()
                        )));

        PageableList<BankAccountDto> pageableBankAccountDtos = new PageableList<>(pageIndex, bankAccountDtos, bankAccounts.getPageCount(), bankAccounts.getModelsCount());

        return pageableBankAccountDtos.currentPage == null
                //  404 NOT FOUND
                ? ResponseEntity.notFound().build()
                //  200 OK
                : ResponseEntity.ok().location(URI.create("/bankAccounts/" + pageIndex)).body(pageableBankAccountDtos);
    }

    @GetMapping("/bankAccount/{id}")
    ResponseEntity<BankAccountDto> bankAccountById(@PathVariable("id") Long id) {
        BankAccount bankAccount = bankAccountService.getById(id);
        BankAccountDto bankAccountDto = bankAccount == null
                ? null
                : new BankAccountDto(
                bankAccount.getId(),
                bankAccount.getStatus().getDto(),
                bankAccount.getType().getDto(),
                bankAccount.getCustomer().getDto(),
                bankAccount.getBalance(),
                bankAccount.getDetails());

        return bankAccountDto == null
                //  404 NOT FOUND
                ? ResponseEntity.notFound().build()
                //  200 OK
                : ResponseEntity.ok().location(URI.create("/bankAccount/" + id)).body(bankAccountDto);
    }

    @GetMapping("/bankAccount")
    ResponseEntity<BankAccountDto> bankAccountByDetails(@RequestParam("details") String details) {
        BankAccount bankAccount = bankAccountService.getByDetails(details);
        BankAccountDto bankAccountDto = bankAccount == null
                ? null
                : new BankAccountDto(
                bankAccount.getId(),
                bankAccount.getStatus().getDto(),
                bankAccount.getType().getDto(),
                bankAccount.getCustomer().getDto(),
                bankAccount.getBalance(),
                bankAccount.getDetails());

        return bankAccountDto == null
                //  404 NOT FOUND
                ? ResponseEntity.notFound().build()
                //  200 OK
                : ResponseEntity.ok().location(URI.create("/bankAccount?details=" + details)).body(bankAccountDto);
    }
    //endregion

    //region HTTP POST
    @PostMapping("/bankAccount")
    ResponseEntity<BankAccountDto> createBank(@RequestBody BankAccountDto dto) {

        if(!UsersUtil.isAdmin()) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        BankAccount createdBankAccount;
        try {
            createdBankAccount = bankAccountService.addAccount(dto.getDBModel());
        } catch (Exception e) {
            e.printStackTrace();
            //  400 BAD REQUEST
            return ResponseEntity.badRequest().body(dto);
        }

        //  201 CREATED
        return ResponseEntity.created(URI.create("/bankAccount/" + createdBankAccount.getId())).body(createdBankAccount.getDto());
    }
    //endregion

    //region HTTP PUT
    @PutMapping("/bankAccount")
    ResponseEntity<BankAccountDto> updateBank(@RequestBody BankAccountDto dto) {

        if(!UsersUtil.isAdmin()) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        BankAccount updatedBankAccount;
        try {
             updatedBankAccount = bankAccountService.updateAccount(dto.getDBModel());
        } catch (Exception e) {
            e.printStackTrace();
            //  400 BAD REQUEST
            return ResponseEntity.badRequest().body(dto);
        }

        //  200 OK
        return ResponseEntity.ok().location(URI.create("/bankAccount/" + updatedBankAccount.getId())).body(updatedBankAccount.getDto());
    }
    //endregion

    //region HTTP DELETE
    @DeleteMapping("/bankAccount/{id}")
    ResponseEntity<BankAccountDto> deleteBank(@PathVariable("id") Long id) {

        if(!UsersUtil.isAdmin()) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        try {
            bankAccountService.deleteAccount(id);
        } catch (Exception e) {
            e.printStackTrace();
            //  400 BAD REQUEST
            return ResponseEntity.badRequest().build();
        }

        //  204 NO CONTENT
        return ResponseEntity.noContent().build();
    }
    //endregion
}
