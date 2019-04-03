package com.codecentric.retailbank.web.controller.api.v1;

import com.codecentric.retailbank.model.domain.RefTransactionType;
import com.codecentric.retailbank.model.dto.RefTransactionTypeDto;
import com.codecentric.retailbank.security.UsersUtil;
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

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class RefTransactionTypeApiController {

    //region FIELDS
    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private RefTransactionTypeService refTransactionTypeService;
    //endregion


    //region HTTP GET
    @GetMapping("/refTransactionTypes")
    ResponseEntity<List<RefTransactionTypeDto>> refTransactionTypes() {
        List<RefTransactionType> refTransactionTypes = refTransactionTypeService.getAllRefTransactionTypes();
        List<RefTransactionTypeDto> refTransactionTypeDtos = refTransactionTypes.stream().map(type -> new RefTransactionTypeDto(
                type.getId(),
                type.getCode(),
                type.getDescription(),
                type.getIsDeposit(),
                type.getIsWithdrawal()
        )).collect(Collectors.toList());

        return refTransactionTypeDtos.size() == 0
                //  404 NOT FOUND
                ? ResponseEntity.notFound().build()
                //  200 OK
                : ResponseEntity.ok().location(URI.create("/refTransactionTypes")).body(refTransactionTypeDtos);
    }

    @GetMapping("/refTransactionType/{id}")
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

        return refTransactionTypeDto == null
                //  404 NOT FOUND
                ? ResponseEntity.notFound().build()
                //  200 OK
                : ResponseEntity.ok().location(URI.create("/refTransactionType/" + id)).body(refTransactionTypeDto);
    }

    @GetMapping("/refTransactionType")
    ResponseEntity<RefTransactionTypeDto> refTransactionTypeByDetails(@RequestParam("code") String code) {
        RefTransactionType refTransactionType = refTransactionTypeService.getByCode(code);
        RefTransactionTypeDto refTransactionTypeDto = refTransactionType == null
                ? null
                : new RefTransactionTypeDto(refTransactionType.getId(),
                refTransactionType.getCode(),
                refTransactionType.getDescription(),
                refTransactionType.getIsDeposit(),
                refTransactionType.getIsWithdrawal());

        return refTransactionTypeDto == null
                //  404 NOT FOUND
                ? ResponseEntity.notFound().build()
                //  200 OK
                : ResponseEntity.ok().location(URI.create("/refTransactionType?code=" + code)).body(refTransactionTypeDto);
    }
    //endregion

    //region HTTP POST
    @PostMapping("/refTransactionType")
    ResponseEntity<RefTransactionTypeDto> createRefTransactionType(@RequestBody RefTransactionTypeDto dto) {

        if(!UsersUtil.isAdmin()) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        RefTransactionType result;
        try {
            result = refTransactionTypeService.addRefTransactionType(dto.getDBModel());
        } catch (Exception e) {
            e.printStackTrace();
            //  400 BAD REQUEST
            return ResponseEntity.badRequest().body(dto);
        }

        //  201 CREATED
        return ResponseEntity.created(URI.create("/refTransactionType/" + result.getId())).body(result.getDto());
    }
    //endregion

    //region HTTP PUT
    @PutMapping("/refTransactionType")
    ResponseEntity<RefTransactionTypeDto> updateRefTransactionType(@RequestBody RefTransactionTypeDto dto) {

        if(!UsersUtil.isAdmin()) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        RefTransactionType result;
        try {
            result = refTransactionTypeService.updateRefTransactionType(dto.getDBModel());
        } catch (Exception e) {
            e.printStackTrace();
            //  400 BAD REQUEST
            return ResponseEntity.badRequest().body(dto);
        }

        //  200 OK
        return ResponseEntity.ok().location(URI.create("/refTransactionType/" + result.getId())).body(result.getDto());
    }
    //endregion

    //region HTTP DELETE
    @DeleteMapping("/refTransactionType/{id}")
    ResponseEntity<RefTransactionTypeDto> deleteRefTransactionType(@PathVariable("id") Long id) {

        if(!UsersUtil.isAdmin()) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        try {
            refTransactionTypeService.deleteRefTransactionType(id);
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
