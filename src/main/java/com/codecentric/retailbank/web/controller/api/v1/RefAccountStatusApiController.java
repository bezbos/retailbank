package com.codecentric.retailbank.web.controller.api.v1;

import com.codecentric.retailbank.model.domain.RefAccountStatus;
import com.codecentric.retailbank.model.dto.RefAccountStatusDto;
import com.codecentric.retailbank.security.UsersUtil;
import com.codecentric.retailbank.service.RefAccountStatusService;
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

@RestController
@RequestMapping("/api/v1")
public class RefAccountStatusApiController {

    //region FIELDS
    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private RefAccountStatusService refAccountStatusService;
    //endregion


    //region HTTP GET
    @GetMapping({"/refAccountStatuses"})
    ResponseEntity<List<RefAccountStatusDto>> refAccountStatuses() {

        List<RefAccountStatus> refAccountStatuses = refAccountStatusService.getAllRefAccountStatus();
        List<RefAccountStatusDto> refAccountStatusDtos = new ArrayList<>();
        refAccountStatuses
                .forEach(
                        status -> refAccountStatusDtos.add(
                                new RefAccountStatusDto(
                                        status.getId(),
                                        status.getCode(),
                                        status.getDescription(),
                                        status.getIsActive(),
                                        status.getIsClosed()
                                )));

        //  200 OK
        return ResponseEntity.ok().location(URI.create("/refAccountStatuses")).body(refAccountStatusDtos);
    }

    @GetMapping("/refAccountStatus/{id}")
    ResponseEntity<RefAccountStatusDto> refAccountStatusById(@PathVariable("id") Long id) {
        RefAccountStatus refAccountStatus = refAccountStatusService.getById(id);
        RefAccountStatusDto refAccountStatusDto = refAccountStatus == null
                ? null
                : new RefAccountStatusDto(
                refAccountStatus.getId(),
                refAccountStatus.getCode(),
                refAccountStatus.getDescription(),
                refAccountStatus.getIsActive(),
                refAccountStatus.getIsClosed());

        return refAccountStatusDto == null
                //  404 NOT FOUND
                ? ResponseEntity.notFound().build()
                //  200 OK
                : ResponseEntity.ok().location(URI.create("/refAccountStatuses/" + id)).body(refAccountStatusDto);
    }

    @GetMapping("/refAccountStatus")
    ResponseEntity<RefAccountStatusDto> refAccountStatusByDetails(@RequestParam("code") String code) {
        RefAccountStatus refAccountStatus = refAccountStatusService.getByCode(code);
        RefAccountStatusDto refAccountStatusDto = refAccountStatus == null
                ? null
                : new RefAccountStatusDto(
                refAccountStatus.getId(),
                refAccountStatus.getCode(),
                refAccountStatus.getDescription(),
                refAccountStatus.getIsActive(),
                refAccountStatus.getIsClosed());

        return refAccountStatusDto == null
                //  404 NOT FOUND
                ? ResponseEntity.notFound().build()
                //  200 OK
                : ResponseEntity.ok().location(URI.create("/refAccountStatuses?code=" + code)).body(refAccountStatusDto);
    }
    //endregion

    //region HTTP POST
    @PostMapping("/refAccountStatus")
    ResponseEntity<RefAccountStatusDto> createRefAccountStatus(@RequestBody RefAccountStatusDto dto) {

        if(!UsersUtil.isAdmin()) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        RefAccountStatus result;
        try {
            result = refAccountStatusService.addRefAccountStatus(dto.getDBModel());
        } catch (Exception e) {
            e.printStackTrace();
            //  400 BAD REQUEST
            return ResponseEntity.badRequest().body(dto);
        }

        //  201 CREATED
        return ResponseEntity.created(URI.create("/refAccountStatus/" + result.getId())).body(result.getDto());
    }
    //endregion

    //region HTTP PUT
    @PutMapping("/refAccountStatus")
    ResponseEntity<RefAccountStatusDto> updateRefAccountStatus(@RequestBody RefAccountStatusDto dto) {

        if(!UsersUtil.isAdmin()) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        RefAccountStatus result;
        try {
            result= refAccountStatusService.updateRefAccountStatus(dto.getDBModel());
        } catch (Exception e) {
            e.printStackTrace();
            //  400 BAD REQUEST
            return ResponseEntity.badRequest().body(dto);
        }

        //  200 OK
        return ResponseEntity.ok().location(URI.create("/refAccountStatus/" + result.getId())).body(result.getDto());
    }
    //endregion

    //region HTTP DELETE
    @DeleteMapping("/refAccountStatus/{id}")
    ResponseEntity<RefAccountStatusDto> deleteRefAccountStatus(@PathVariable("id") Long id) {

        if(!UsersUtil.isAdmin()) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        try {
            refAccountStatusService.deleteRefAccountStatus(id);
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
