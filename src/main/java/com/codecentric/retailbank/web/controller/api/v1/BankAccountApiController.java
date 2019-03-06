package com.codecentric.retailbank.web.controller.api.v1;

import com.codecentric.retailbank.model.domain.BankAccount;
import com.codecentric.retailbank.model.dto.BankAccountDto;
import com.codecentric.retailbank.repository.helpers.ListPage;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.codecentric.retailbank.constants.Constant.PAGE_SIZE;

@RestController
@RequestMapping("/api/v1")
public class BankAccountApiController {

    //region FIELDS
    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final BankAccountService bankAccountService;
    //endregion

    //region CONSTRUCTOR
    @Autowired public BankAccountApiController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }
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
                .forEach(x -> bankAccountDtos.add(
                        new BankAccountDto(
                                x.getId(),
                                x.getStatus().getDto(),
                                x.getType().getDto(),
                                x.getCustomer().getDto(),
                                x.getBalance(),
                                x.getDetails()
                        )));

        PageableList<BankAccountDto> pageableBankAccountDtos = new PageableList<>(pageIndex, bankAccountDtos, bankAccounts.getPageCount());

        return pageableBankAccountDtos.currentPage == null
                //  404 NOT FOUND
                ? new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE)
                //  200 OK
                : new ResponseEntity<>(pageableBankAccountDtos, HttpStatus.OK);
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

        return bankAccount == null
                //  404 NOT FOUND
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                //  200 OK
                : new ResponseEntity<>(bankAccountDto, HttpStatus.OK);
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

        return bankAccount == null
                //  404 NOT FOUND
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                //  200 OK
                : new ResponseEntity<>(bankAccountDto, HttpStatus.OK);
    }
    //endregion

    //region HTTP POST
    @PostMapping("/bankAccount")
    ResponseEntity<BankAccountDto> createBank(@RequestBody BankAccountDto clientDto) {
        try {
            bankAccountService.addAccount(clientDto.getDBModel());
        } catch (Exception e) {
            e.printStackTrace();
            //  400 BAD REQUEST
            return new ResponseEntity<>(clientDto, HttpStatus.BAD_REQUEST);
        }

        //  201 CREATED
        return new ResponseEntity<>(clientDto, HttpStatus.CREATED);
    }
    //endregion

    //region HTTP PUT
    @PutMapping("/bankAccount")
    ResponseEntity<BankAccountDto> updateBank(@RequestBody BankAccountDto clientDto) {
        try {
            bankAccountService.updateAccount(clientDto.getDBModel());
        } catch (Exception e) {
            e.printStackTrace();
            //  400 BAD REQUEST
            return new ResponseEntity<>(clientDto, HttpStatus.BAD_REQUEST);
        }

        //  200 OK
        return new ResponseEntity<>(clientDto, HttpStatus.OK);
    }
    //endregion

    //region HTTP DELETE
    @DeleteMapping("/bankAccount/{id}")
    ResponseEntity<BankAccountDto> deleteBank(@PathVariable("id") Long id) {
        try {
            bankAccountService.deleteAccount(id);
        } catch (Exception e) {
            e.printStackTrace();
            //  400 BAD REQUEST
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        //  204 NO CONTENT
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    //endregion
}
