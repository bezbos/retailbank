package com.codecentric.retailbank.web.controller.api.v1;

import com.codecentric.retailbank.model.domain.RefTransactionType;
import com.codecentric.retailbank.model.dto.RefTransactionTypeDto;
import com.codecentric.retailbank.service.RefTransactionTypeService;
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
public class RefTransactionTypeApiController {

    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private RefTransactionTypeService refTransactionTypeService;

    //region HTTP GET
    @GetMapping(value = "/refTransactionTypes")
    ResponseEntity<List<RefTransactionTypeDto>> refTransactionTypes() {
        List<RefTransactionType> refTransactionTypes = refTransactionTypeService.getAllRefTransactionTypes();
        List<RefTransactionTypeDto> refTransactionTypeDtos = new ArrayList<>();
        refTransactionTypes
                .forEach(
                        x -> refTransactionTypeDtos.add(new RefTransactionTypeDto(
                                x.getId(),
                                x.getCode(),
                                x.getDescription(),
                                x.getIsDeposit(),
                                x.getIsWithdrawal()
                        )));

        return refTransactionTypeDtos.size() == 0
                //  404 NOT FOUND
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                //  200 OK
                : new ResponseEntity<>(refTransactionTypeDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/refTransactionType/{id}")
    ResponseEntity<RefTransactionTypeDto> refTransactionTypeById(@PathVariable("id") Long id) {
        RefTransactionType refTransactionType = refTransactionTypeService.getById(id);
        RefTransactionTypeDto refTransactionTypeDto = refTransactionType == null
                ? null
                : new RefTransactionTypeDto(
                refTransactionType.getId(),
                refTransactionType.getCode(),
                refTransactionType.getDescription(),
                refTransactionType.getIsDeposit(),
                refTransactionType.getIsWithdrawal());

        return refTransactionType == null
                //  404 NOT FOUND
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                //  200 OK
                : new ResponseEntity<>(refTransactionTypeDto, HttpStatus.OK);
    }

    @GetMapping(value = "/refTransactionType")
    ResponseEntity<RefTransactionTypeDto> refTransactionTypeByDetails(@RequestParam("code") String code) {
        RefTransactionType refTransactionType = refTransactionTypeService.getByCode(code);
        RefTransactionTypeDto refTransactionTypeDto = refTransactionType == null
                ? null
                : new RefTransactionTypeDto(refTransactionType.getId(),
                refTransactionType.getCode(),
                refTransactionType.getDescription(),
                refTransactionType.getIsDeposit(),
                refTransactionType.getIsWithdrawal());

        return refTransactionType == null
                //  404 NOT FOUND
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                //  200 OK
                : new ResponseEntity<>(refTransactionTypeDto, HttpStatus.OK);
    }
    //endregion

    //region HTTP POST
    @PostMapping(value = "/refTransactionType")
    ResponseEntity<RefTransactionTypeDto> createRefTransactionType(@RequestBody RefTransactionTypeDto clientDto) {
        try {
            refTransactionTypeService.addRefTransactionType(clientDto.getDBModel());
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
    @PutMapping(value = "/refTransactionType")
    ResponseEntity<RefTransactionTypeDto> updateRefTransactionType(@RequestBody RefTransactionTypeDto clientDto) {
        try {
            refTransactionTypeService.updateRefTransactionType(clientDto.getDBModel());
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
    @DeleteMapping(value = "/refTransactionType/{id}")
    ResponseEntity<RefTransactionTypeDto> deleteRefTransactionType(@PathVariable("id") Long id) {
        try {
            refTransactionTypeService.deleteRefTransactionType(id);
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
