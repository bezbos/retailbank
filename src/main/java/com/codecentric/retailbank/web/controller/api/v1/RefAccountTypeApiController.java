package com.codecentric.retailbank.web.controller.api.v1;

import com.codecentric.retailbank.model.domain.RefAccountType;
import com.codecentric.retailbank.model.dto.RefAccountTypeDto;
import com.codecentric.retailbank.service.RefAccountTypeService;
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

@RestController
@RequestMapping("/api/v1")
public class RefAccountTypeApiController {

    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private RefAccountTypeService refAccountTypeService;

    //region HTTP GET
    @GetMapping(value = "/refAccountTypes")
    ResponseEntity<List<RefAccountTypeDto>> refAccountTypes() {

        List<RefAccountType> refAccountTypes = refAccountTypeService.getAllRefAccountTypes();
        List<RefAccountTypeDto> refAccountTypeDtos = new ArrayList<>();
        refAccountTypes
                .forEach(
                        x -> refAccountTypeDtos.add(new RefAccountTypeDto(
                                x.getId(),
                                x.getCode(),
                                x.getDescription(),
                                x.getIsCheckingType(),
                                x.getIsSavingsType(),
                                x.getIsCertificateOfDepositType(),
                                x.getIsMoneyMarketType(),
                                x.getIsIndividualRetirementType()
                        )));

        return refAccountTypeDtos.size() == 0
                //  404 NOT FOUND
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                //  200 OK
                : new ResponseEntity<>(refAccountTypeDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/refAccountType/{id}")
    ResponseEntity<RefAccountTypeDto> refAccountTypeById(@PathVariable("id") Long id) {
        RefAccountType refAccountType = refAccountTypeService.getById(id);
        RefAccountTypeDto refAccountTypeDto = refAccountType == null
                ? null
                : new RefAccountTypeDto(
                refAccountType.getId(),
                refAccountType.getCode(),
                refAccountType.getDescription(),
                refAccountType.getIsCheckingType(),
                refAccountType.getIsSavingsType(),
                refAccountType.getIsCertificateOfDepositType(),
                refAccountType.getIsMoneyMarketType(),
                refAccountType.getIsIndividualRetirementType());

        return refAccountType == null
                //  404 NOT FOUND
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                //  200 OK
                : new ResponseEntity<>(refAccountTypeDto, HttpStatus.OK);
    }

    @GetMapping(value = "/refAccountType")
    ResponseEntity<RefAccountTypeDto> brefAccountTypeByDetails(@RequestParam("code") String code) {
        RefAccountType refAccountType = refAccountTypeService.getByCode(code);
        RefAccountTypeDto refAccountTypeDto = refAccountType == null
                ? null
                : new RefAccountTypeDto(
                refAccountType.getId(),
                refAccountType.getCode(),
                refAccountType.getDescription(),
                refAccountType.getIsCheckingType(),
                refAccountType.getIsSavingsType(),
                refAccountType.getIsCertificateOfDepositType(),
                refAccountType.getIsMoneyMarketType(),
                refAccountType.getIsIndividualRetirementType());

        return refAccountType == null
                //  404 NOT FOUND
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                //  200 OK
                : new ResponseEntity<>(refAccountTypeDto, HttpStatus.OK);
    }
    //endregion

    //region HTTP POST
    @PostMapping(value = "/refAccountType")
    ResponseEntity<RefAccountTypeDto> createRefAccountType(@RequestBody RefAccountTypeDto clientDto) {
        try {
            refAccountTypeService.addRefAccountType(clientDto.getDBModel());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            //  400 BAD REQUEST
            return new ResponseEntity<>(clientDto, HttpStatus.BAD_REQUEST);
        }

        //  201 CREATED
        return new ResponseEntity<>(clientDto, HttpStatus.CREATED);
    }
    //endregion

    //region HTTP PUT
    @PutMapping(value = "/refAccountType")
    ResponseEntity<RefAccountTypeDto> updateRefAccountType(@RequestBody RefAccountTypeDto clientDto) {
        try {
            refAccountTypeService.updateRefAccountType(clientDto.getDBModel());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            //  400 BAD REQUEST
            return new ResponseEntity<>(clientDto, HttpStatus.BAD_REQUEST);
        }

        //  200 OK
        return new ResponseEntity<>(clientDto, HttpStatus.OK);
    }
    //endregion

    //region HTTP DELETE
    @DeleteMapping(value = "/refAccountType/{id}")
    ResponseEntity<RefAccountTypeDto> deleteRefAccountType(@PathVariable("id") Long id) {
        try {
            refAccountTypeService.deleteRefAccountType(id);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            //  400 BAD REQUEST
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        //  204 NO CONTENT
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    //endregion
}
