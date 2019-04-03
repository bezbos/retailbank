package com.codecentric.retailbank.web.controller.api.v1;

import com.codecentric.retailbank.model.domain.RefBranchType;
import com.codecentric.retailbank.model.dto.RefBranchTypeDto;
import com.codecentric.retailbank.security.UsersUtil;
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

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class RefBranchTypeApiController {

    //region FIELDS
    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private RefBranchTypeService refBranchTypeService;
    //endregion


    //region HTTP GET
    @GetMapping("/refBranchTypes")
    ResponseEntity<?> refBranchTypes() {

        List<RefBranchType> refBranchTypes = refBranchTypeService.getAllRefBranchTypes();
        List<RefBranchTypeDto> refBranchTypeDtos = refBranchTypes.stream().map(type -> new RefBranchTypeDto(
                type.getId(),
                type.getCode(),
                type.getDescription(),
                type.getIsLargeUrban(),
                type.getIsSmallRural(),
                type.getIsMediumSuburban()
        )).collect(Collectors.toList());

        return refBranchTypeDtos.size() == 0
                //  404 NOT FOUND
                ? ResponseEntity.notFound().build()
                //  200 OK
                : ResponseEntity.ok().location(URI.create("/refBranchTypes")).body(refBranchTypeDtos);
    }

    @GetMapping("/refBranchType/{id}")
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

        return refBranchTypeDto == null
                //  404 NOT FOUND
                ? ResponseEntity.notFound().build()
                //  200 OK
                : ResponseEntity.ok().location(URI.create("/refBranchType/" + id)).body(refBranchTypeDto);
    }

    @GetMapping("/refBranchType")
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

        return refBranchTypeDto == null
                //  404 NOT FOUND
                ? ResponseEntity.notFound().build()
                //  200 OK
                : ResponseEntity.ok().location(URI.create("/refBranchType/" + code)).body(refBranchTypeDto);
    }
    //endregion

    //region HTTP POST
    @PostMapping("/refBranchType")
    ResponseEntity<RefBranchTypeDto> createRefBranchType(@RequestBody RefBranchTypeDto dto) {

        if(!UsersUtil.isAdmin()) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        RefBranchType result;
        try {
            result= refBranchTypeService.addRefBranchType(dto.getDBModel());
        } catch (Exception e) {
            e.printStackTrace();
            //  400 BAD REQUEST
            return ResponseEntity.badRequest().body(dto);
        }

        //  201 CREATED
        return ResponseEntity.created(URI.create("/refBranchType" + result.getId())).body(result.getDto());
    }
    //endregion

    //region HTTP PUT
    @PutMapping("/refBranchType")
    ResponseEntity<RefBranchTypeDto> updateRefBranchType(@RequestBody RefBranchTypeDto dto) {

        if(!UsersUtil.isAdmin()) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        RefBranchType result;
        try {
            result = refBranchTypeService.updateRefBranchType(dto.getDBModel());
        } catch (Exception e) {
            e.printStackTrace();
            //  400 BAD REQUEST
            return ResponseEntity.badRequest().body(dto);
        }

        //  200 OK
        return ResponseEntity.ok().location(URI.create("/refBranchType/" + result)).body(result.getDto());
    }
    //endregion

    //region HTTP DELETE
    @DeleteMapping("/refBranchType/{id}")
    ResponseEntity<RefBranchTypeDto> deleteRefBranchType(@PathVariable("id") Long id) {

        if(!UsersUtil.isAdmin()) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        try {
            refBranchTypeService.deleteRefBranchType(id);
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
