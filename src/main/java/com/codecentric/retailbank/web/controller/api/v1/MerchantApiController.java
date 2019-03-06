package com.codecentric.retailbank.web.controller.api.v1;

import com.codecentric.retailbank.model.domain.Merchant;
import com.codecentric.retailbank.model.dto.MerchantDto;
import com.codecentric.retailbank.repository.helpers.ListPage;
import com.codecentric.retailbank.service.MerchantService;
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
public class MerchantApiController {

    //region FIELDS
    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final MerchantService merchantService;
    //endregion

    //region CONSTRUCTOR
    @Autowired public MerchantApiController(MerchantService merchantService) {
        this.merchantService = merchantService;
    }
    //endregion

    //region HTTP GET
    @GetMapping({"/merchants", "/merchants/{page}"})
    ResponseEntity<PageableList<MerchantDto>> merchants(@PathVariable("page") Optional<Integer> page) {

        // If pageIndex is less than 1 set it to 1.
        Integer pageIndex = page.isPresent() ? page.get() : 0;
        pageIndex = pageIndex == 0 || pageIndex < 0 || pageIndex == null ?
                0 : pageIndex;

        ListPage<Merchant> merchants = merchantService.getAllMerchants(pageIndex, PAGE_SIZE);
        List<MerchantDto> merchantDtos = new ArrayList<>();
        merchants.getModels()
                .forEach(
                        b -> merchantDtos.add(new MerchantDto(b.getId(), b.getDetails()))
                );

        PageableList<MerchantDto> pageableMerchantDtos = new PageableList<>(pageIndex, merchantDtos, merchants.getPageCount());

        return pageableMerchantDtos.currentPage == null
                //  404 NOT FOUND
                ? new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE)
                //  200 OK
                : new ResponseEntity<>(pageableMerchantDtos, HttpStatus.OK);
    }

    @GetMapping("/merchant/{id}")
    ResponseEntity<MerchantDto> merchantById(@PathVariable("id") Long id) {
        Merchant merchant = merchantService.getById(id);
        MerchantDto merchantDto = merchant == null
                ? null
                : new MerchantDto(merchant.getId(), merchant.getDetails());

        return merchant == null
                //  404 NOT FOUND
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                //  200 OK
                : new ResponseEntity<>(merchantDto, HttpStatus.OK);
    }

    @GetMapping("/merchant")
    ResponseEntity<MerchantDto> merchantByDetails(@RequestParam("details") String details) {
        Merchant merchant = merchantService.getByDetails(details);
        MerchantDto merchantDto = merchant == null
                ? null
                : new MerchantDto(merchant.getId(), merchant.getDetails());

        return merchant == null
                //  404 NOT FOUND
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                //  200 OK
                : new ResponseEntity<>(merchantDto, HttpStatus.OK);
    }
    //endregion

    //region HTTP POST
    @PostMapping("/merchant")
    ResponseEntity<MerchantDto> createMerchant(@RequestBody MerchantDto clientDto) {
        try {
            merchantService.addMerchant(clientDto.getDBModel());
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
    @PutMapping("/merchant")
    ResponseEntity<MerchantDto> updateMerchant(@RequestBody MerchantDto clientDto) {
        try {
            merchantService.updateMerchant(clientDto.getDBModel());
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
    @DeleteMapping("/merchant/{id}")
    ResponseEntity<MerchantDto> deleteMerchant(@PathVariable("id") Long id) {
        try {
            merchantService.deleteMerchant(id);
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
