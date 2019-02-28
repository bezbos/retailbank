package com.codecentric.retailbank.web.controller.api.v1;

import com.codecentric.retailbank.model.domain.Bank;
import com.codecentric.retailbank.model.dto.BankDto;
import com.codecentric.retailbank.repository.helpers.ListPage;
import com.codecentric.retailbank.service.BankService;
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
public class BankApiController {

    // TODO(bosko): Implement logging
    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private BankService bankService;

    //region HTTP GET
    @GetMapping(value = {"/banks", "/banks/{page}"})
    ResponseEntity<PageableList<BankDto>> banks(@PathVariable("page") Optional<Integer> page) {

        // If pageIndex is less than 1 set it to 1.
        Integer pageIndex = page.isPresent() ? page.get() : 0;
        pageIndex = pageIndex == 0 || pageIndex < 0 || pageIndex == null ?
                0 : pageIndex;

        ListPage<Bank> banks = bankService.getAllBanksByPage(pageIndex, PAGE_SIZE);
        List<BankDto> bankDtos = new ArrayList<>();
        banks.getModels()
                .forEach(
                        x -> bankDtos.add(new BankDto(x.getId(), x.getDetails()))
                );

        PageableList<BankDto> pageableBankDtos = new PageableList<>(pageIndex, bankDtos, banks.getPageCount());

        return pageableBankDtos.currentPage == null
                //  404 NOT FOUND
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                //  200 OK
                : new ResponseEntity<>(pageableBankDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/bank/{id}")
    ResponseEntity<BankDto> bankById(@PathVariable("id") Long id) {
        Bank bank = bankService.getById(id);
        BankDto bankDto = bank == null
                ? null
                : new BankDto(bank.getId(), bank.getDetails());

        return bank == null
                //  404 NOT FOUND
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                //  200 OK
                : new ResponseEntity<>(bankDto, HttpStatus.OK);
    }

    @GetMapping(value = "/bank")
    ResponseEntity<BankDto> bankByDetails(@RequestParam("details") String details) {
        Bank bank = bankService.getByDetails(details);
        BankDto bankDto = bank == null
                ? null
                : new BankDto(bank.getId(), bank.getDetails());

        return bank == null
                //  404 NOT FOUND
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                //  200 OK
                : new ResponseEntity<>(bankDto, HttpStatus.OK);
    }
    //endregion

    //region HTTP POST
    @PostMapping(value = "/bank")
    ResponseEntity<BankDto> createBank(@RequestBody BankDto clientDto) {
        try {
            bankService.addBank(clientDto.getDBModel());
        } catch (Exception e) {
            //  400 BAD REQUEST
            return new ResponseEntity<>(clientDto, HttpStatus.BAD_REQUEST);
        }

        //  201 CREATED
        return new ResponseEntity<>(clientDto, HttpStatus.CREATED);
    }
    //endregion

    //region HTTP PUT
    @PutMapping(value = "/bank")
    ResponseEntity<BankDto> updateBank(@RequestBody BankDto clientDto) {
        try {
            bankService.updateBank(clientDto.getDBModel());
        } catch (Exception e) {
            //  400 BAD REQUEST
            return new ResponseEntity<>(clientDto, HttpStatus.BAD_REQUEST);
        }

        //  200 OK
        return new ResponseEntity<>(clientDto, HttpStatus.OK);
    }
    //endregion

    //region HTTP DELETE
    @DeleteMapping(value = "/bank/{id}")
    ResponseEntity<BankDto> deleteBank(@PathVariable("id") Long id) {
        try {
            bankService.deleteBank(id);
        } catch (Exception e) {
            //  400 BAD REQUEST
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        //  204 NO CONTENT
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    //endregion
}
