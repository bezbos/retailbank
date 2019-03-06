package com.codecentric.retailbank.web.controller.api.v1;

import com.codecentric.retailbank.model.domain.Address;
import com.codecentric.retailbank.model.dto.AddressDto;
import com.codecentric.retailbank.repository.helpers.ListPage;
import com.codecentric.retailbank.service.AddressService;
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
public class AddressApiController {

    //region FIELDS
    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final AddressService addressService;
    //endregion

    //region CONSTRUCTOR
    @Autowired public AddressApiController(AddressService addressService) {
        this.addressService = addressService;
    }
    //endregion

    //region HTTP GET
    @GetMapping({"/addresses", "/addresses/{page}"})
    ResponseEntity<PageableList<AddressDto>> addresses(@PathVariable("page") Optional<Integer> page) {

        // If pageIndex is less than 1 set it to 1.
        Integer pageIndex = page.isPresent() ? page.get() : 0;
        pageIndex = pageIndex == 0 || pageIndex < 0 || pageIndex == null ?
                0 : pageIndex;

        ListPage<Address> addresses = addressService.getAllAddressesByPage(pageIndex, PAGE_SIZE);
        List<AddressDto> addressDtos = new ArrayList<>();
        addresses.getModels()
                .forEach(x -> addressDtos.add(
                        new AddressDto(
                                x.getId(),
                                x.getLine1(),
                                x.getLine2(),
                                x.getTownCity(),
                                x.getZipPostcode(),
                                x.getStateProvinceCountry(),
                                x.getCountry(),
                                x.getOtherDetails())
                ));

        PageableList<AddressDto> pageableAddressDtos = new PageableList<>(pageIndex, addressDtos, addresses.getPageCount());

        return pageableAddressDtos.currentPage == null
                //  404 NOT FOUND
                ? new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE)
                //  200 OK
                : new ResponseEntity<>(pageableAddressDtos, HttpStatus.OK);
    }

    @GetMapping("/address/{id}")
    ResponseEntity<AddressDto> addressById(@PathVariable("id") Long id) {
        Address address = addressService.getById(id);
        AddressDto addressDto = address == null
                ? null
                : new AddressDto(
                address.getId(),
                address.getLine1(),
                address.getLine2(),
                address.getTownCity(),
                address.getZipPostcode(),
                address.getStateProvinceCountry(),
                address.getCountry(),
                address.getOtherDetails());

        return address == null
                //  404 NOT FOUND
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                //  200 OK
                : new ResponseEntity<>(addressDto, HttpStatus.OK);
    }

    @GetMapping("/address")
    ResponseEntity<AddressDto> addressByDetails(@RequestParam("line1") String line1) {
        Address address = addressService.getByLine1(line1);
        AddressDto addressDto = address == null
                ? null
                : new AddressDto(address.getId(),
                address.getLine1(),
                address.getLine2(),
                address.getTownCity(),
                address.getZipPostcode(),
                address.getStateProvinceCountry(),
                address.getCountry(),
                address.getOtherDetails());

        return address == null
                //  404 NOT FOUND
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                //  200 OK
                : new ResponseEntity<>(addressDto, HttpStatus.OK);
    }
    //endregion

    //region HTTP POST
    @PostMapping("/address")
    ResponseEntity<AddressDto> createBank(@RequestBody AddressDto clientDto) {
        try {
            addressService.addAddress(clientDto.getDBModel());
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
    @PutMapping("/address")
    ResponseEntity<AddressDto> updateBank(@RequestBody AddressDto clientDto) {
        try {
            addressService.updateAddress(clientDto.getDBModel());
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
    @DeleteMapping("/address/{id}")
    ResponseEntity<AddressDto> deleteBank(@PathVariable("id") Long id) {
        try {
            addressService.deleteAddress(id);
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
