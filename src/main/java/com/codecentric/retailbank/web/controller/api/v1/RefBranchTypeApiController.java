package com.codecentric.retailbank.web.controller.api.v1;

import com.codecentric.retailbank.model.domain.RefBranchType;
import com.codecentric.retailbank.model.dto.RefBranchTypeDto;
import com.codecentric.retailbank.service.RefBranchTypeService;
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
public class RefBranchTypeApiController {

    // TODO(bosko): Implement logging
    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private RefBranchTypeService refBranchTypeService;

    //region HTTP GET
    @GetMapping(value = "/refBranchTypes")
    ResponseEntity<List<RefBranchTypeDto>> refBranchTypes() {

        List<RefBranchType> refBranchTypes = refBranchTypeService.getAllRefBranchTypes();
        List<RefBranchTypeDto> refBranchTypeDtos = new ArrayList<>();
        refBranchTypes
                .forEach(
                        x -> refBranchTypeDtos.add(new RefBranchTypeDto(
                                x.getId(),
                                x.getCode(),
                                x.getDescription(),
                                x.getIsLargeUrban(),
                                x.getIsSmallRural(),
                                x.getIsMediumSuburban()
                        )));

        return refBranchTypeDtos.size() == 0
                //  404 NOT FOUND
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                //  200 OK
                : new ResponseEntity<>(refBranchTypeDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/refBranchType/{id}")
    ResponseEntity<RefBranchTypeDto> refBranchTypeById(@PathVariable("id") Long id) {
        RefBranchType refBranchType = refBranchTypeService.getById(id);
        RefBranchTypeDto refBranchTypeDto = refBranchType == null
                ? null
                : new RefBranchTypeDto(
                refBranchType.getId(),
                refBranchType.getCode(),
                refBranchType.getDescription(),
                refBranchType.getIsLargeUrban(),
                refBranchType.getIsSmallRural(),
                refBranchType.getIsMediumSuburban());

        return refBranchType == null
                //  404 NOT FOUND
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                //  200 OK
                : new ResponseEntity<>(refBranchTypeDto, HttpStatus.OK);
    }

    @GetMapping(value = "/refBranchType")
    ResponseEntity<RefBranchTypeDto> refBranchTypeByDetails(@RequestParam("code") String code) {
        RefBranchType refBranchType = refBranchTypeService.getByCode(code);
        RefBranchTypeDto refBranchTypeDto = refBranchType == null
                ? null
                : new RefBranchTypeDto(
                refBranchType.getId(),
                refBranchType.getCode(),
                refBranchType.getDescription(),
                refBranchType.getIsLargeUrban(),
                refBranchType.getIsSmallRural(),
                refBranchType.getIsMediumSuburban());

        return refBranchType == null
                //  404 NOT FOUND
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                //  200 OK
                : new ResponseEntity<>(refBranchTypeDto, HttpStatus.OK);
    }
    //endregion

    //region HTTP POST
    @PostMapping(value = "/refBranchType")
    ResponseEntity<RefBranchTypeDto> createRefBranchType(@RequestBody RefBranchTypeDto clientDto) {
        try {
            refBranchTypeService.addRefBranchType(clientDto.getDBModel());
        } catch (Exception e) {
            //  400 BAD REQUEST
            return new ResponseEntity<>(clientDto, HttpStatus.BAD_REQUEST);
        }

        //  201 CREATED
        return new ResponseEntity<>(clientDto, HttpStatus.CREATED);
    }
    //endregion

    //region HTTP PUT
    @PutMapping(value = "/refBranchType")
    ResponseEntity<RefBranchTypeDto> updateRefBranchType(@RequestBody RefBranchTypeDto clientDto) {
        try {
            refBranchTypeService.updateRefBranchType(clientDto.getDBModel());
        } catch (Exception e) {
            //  400 BAD REQUEST
            return new ResponseEntity<>(clientDto, HttpStatus.BAD_REQUEST);
        }

        //  200 OK
        return new ResponseEntity<>(clientDto, HttpStatus.OK);
    }
    //endregion

    //region HTTP DELETE
    @DeleteMapping(value = "/refBranchType/{id}")
    ResponseEntity<RefBranchTypeDto> deleteRefBranchType(@PathVariable("id") Long id) {
        try {
            refBranchTypeService.deleteRefBranchType(id);
        } catch (Exception e) {
            //  400 BAD REQUEST
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        //  204 NO CONTENT
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    //endregion
}
