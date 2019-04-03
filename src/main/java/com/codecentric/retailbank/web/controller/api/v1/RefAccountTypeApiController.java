package com.codecentric.retailbank.web.controller.api.v1;

import com.codecentric.retailbank.model.domain.RefAccountType;
import com.codecentric.retailbank.model.dto.RefAccountTypeDto;
import com.codecentric.retailbank.security.UsersUtil;
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

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class RefAccountTypeApiController {

    //region FIELDS
    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private RefAccountTypeService refAccountTypeService;
    //endregion


    //region HTTP GET
    @GetMapping("/refAccountTypes")
    ResponseEntity<List<RefAccountTypeDto>> refAccountTypes() {

        List<RefAccountType> refAccountTypes = refAccountTypeService.getAllRefAccountTypes();
        List<RefAccountTypeDto> refAccountTypeDtos = refAccountTypes.stream().map(type -> new RefAccountTypeDto(
                type.getId(),
                type.getCode(),
                type.getDescription(),
                type.getIsCheckingType(),
                type.getIsSavingsType(),
                type.getIsCertificateOfDepositType(),
                type.getIsMoneyMarketType(),
                type.getIsIndividualRetirementType()
        )).collect(Collectors.toList());

        return refAccountTypeDtos.size() == 0
                //  404 NOT FOUND
                ? ResponseEntity.notFound().build()
                //  200 OK
                : ResponseEntity.ok().location(URI.create("/refAccountTypes")).body(refAccountTypeDtos);
    }

    @GetMapping("/refAccountType/{id}")
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

        return refAccountTypeDto == null
                //  404 NOT FOUND
                ? ResponseEntity.notFound().build()
                //  200 OK
                : ResponseEntity.ok().location(URI.create("/refAccountType/" + id)).body(refAccountTypeDto);
    }

    @GetMapping("/refAccountType")
    ResponseEntity<RefAccountTypeDto> refAccountTypeByDetails(@RequestParam("code") String code) {
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

        return refAccountTypeDto == null
                //  404 NOT FOUND
                ? ResponseEntity.notFound().build()
                //  200 OK
                : ResponseEntity.ok().location(URI.create("/refAccountType?code=" + code)).body(refAccountTypeDto);
    }
    //endregion

    //region HTTP POST
    @PostMapping("/refAccountType")
    ResponseEntity<RefAccountTypeDto> createRefAccountType(@RequestBody RefAccountTypeDto dto) {

        if(!UsersUtil.isAdmin()) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        RefAccountType result;
        try {
            result = refAccountTypeService.addRefAccountType(dto.getDBModel());
        } catch (Exception e) {
            e.printStackTrace();
            //  400 BAD REQUEST
            return ResponseEntity.badRequest().body(dto);
        }

        //  201 CREATED
        return ResponseEntity.created(URI.create("/refAccountType/" + result.getId())).body(result.getDto());
    }
    //endregion

    //region HTTP PUT
    @PutMapping("/refAccountType")
    ResponseEntity<RefAccountTypeDto> updateRefAccountType(@RequestBody RefAccountTypeDto clientDto) {

        if(!UsersUtil.isAdmin()) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        RefAccountType result;
        try {
            result= refAccountTypeService.updateRefAccountType(clientDto.getDBModel());
        } catch (Exception e) {
            e.printStackTrace();
            //  400 BAD REQUEST
            return ResponseEntity.badRequest().build();
        }

        //  200 OK
        return ResponseEntity.ok().location(URI.create("/refAccountType/" + result.getId())).body(result.getDto());
    }
    //endregion

    //region HTTP DELETE
    @DeleteMapping("/refAccountType/{id}")
    ResponseEntity<RefAccountTypeDto> deleteRefAccountType(@PathVariable("id") Long id) {

        if(!UsersUtil.isAdmin()) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        try {
            refAccountTypeService.deleteRefAccountType(id);
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
