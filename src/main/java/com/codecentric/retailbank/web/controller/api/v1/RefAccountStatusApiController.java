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

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class RefAccountStatusApiController {

    //region FIELDS
    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final RefAccountStatusService refAccountStatusService;
    //endregion

    //region CONSTRUCTOR
    @Autowired public RefAccountStatusApiController(RefAccountStatusService refAccountStatusService) {
        this.refAccountStatusService = refAccountStatusService;
    }
    //endregion


    //region HTTP GET
    @GetMapping({"/refAccountStatuses"})
    ResponseEntity<List<RefAccountStatusDto>> refAccountStatuses() {

        List<RefAccountStatus> refAccountStatuses = refAccountStatusService.getAllRefAccountStatus();
        List<RefAccountStatusDto> refAccountStatusDtos = new ArrayList<>();
        refAccountStatuses
                .forEach(
                        x -> refAccountStatusDtos.add(
                                new RefAccountStatusDto(
                                        x.getId(),
                                        x.getCode(),
                                        x.getDescription(),
                                        x.getIsActive(),
                                        x.getIsClosed()
                                )));

        //  200 OK
        return new ResponseEntity<>(refAccountStatusDtos, HttpStatus.OK);
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

        return refAccountStatus == null
                //  404 NOT FOUND
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                //  200 OK
                : new ResponseEntity<>(refAccountStatusDto, HttpStatus.OK);
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

        return refAccountStatus == null
                //  404 NOT FOUND
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                //  200 OK
                : new ResponseEntity<>(refAccountStatusDto, HttpStatus.OK);
    }
    //endregion

    //region HTTP POST
    @PostMapping("/refAccountStatus")
    ResponseEntity<RefAccountStatusDto> createRefAccountStatus(@RequestBody RefAccountStatusDto clientDto) {

        if(!UsersUtil.isAdmin()) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        try {
            refAccountStatusService.addRefAccountStatus(clientDto.getDBModel());
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
    @PutMapping("/refAccountStatus")
    ResponseEntity<RefAccountStatusDto> updateRefAccountStatus(@RequestBody RefAccountStatusDto clientDto) {

        if(!UsersUtil.isAdmin()) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        try {
            refAccountStatusService.updateRefAccountStatus(clientDto.getDBModel());
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
    @DeleteMapping("/refAccountStatus/{id}")
    ResponseEntity<RefAccountStatusDto> deleteRefAccountStatus(@PathVariable("id") Long id) {

        if(!UsersUtil.isAdmin()) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        try {
            refAccountStatusService.deleteRefAccountStatus(id);
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
