package com.codecentric.retailbank.web.controller.api.v1;

import com.codecentric.retailbank.model.domain.Customer;
import com.codecentric.retailbank.model.dto.CustomerDto;
import com.codecentric.retailbank.repository.helpers.ListPage;
import com.codecentric.retailbank.service.CustomerService;
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
public class CustomerApiController {

    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private CustomerService customerService;

    //region HTTP GET
    @GetMapping(value = {"/customers", "/customers/{page}"})
    ResponseEntity<PageableList<CustomerDto>> customers(@PathVariable("page") Optional<Integer> page) {

        // If pageIndex is less than 1 set it to 1.
        Integer pageIndex = page.isPresent() ? page.get() : 0;
        pageIndex = pageIndex == 0 || pageIndex < 0 || pageIndex == null ?
                0 : pageIndex;

        ListPage<Customer> customers = customerService.getAllCustomers(pageIndex, PAGE_SIZE);
        List<CustomerDto> customerDtos = new ArrayList<>();
        customers.getModels()
                .forEach(
                        b -> customerDtos.add(
                                new CustomerDto(
                                        b.getId(),
                                        b.getAddress().getDto(),
                                        b.getBranch().getDto(),
                                        b.getPersonalDetails(),
                                        b.getContactDetails()
                                )));


        PageableList<CustomerDto> pageableCustomerDtos = new PageableList<>(pageIndex, customerDtos, customers.getPageCount());

        return pageableCustomerDtos.currentPage == null
                //  404 NOT FOUND
                ? new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE)
                //  200 OK
                : new ResponseEntity<>(pageableCustomerDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/customer/{id}")
    ResponseEntity<CustomerDto> customerById(@PathVariable("id") Long id) {
        Customer customer = customerService.getById(id);
        CustomerDto customerDto = customer == null
                ? null
                : new CustomerDto(
                customer.getId(),
                customer.getAddress().getDto(),
                customer.getBranch().getDto(),
                customer.getPersonalDetails(),
                customer.getContactDetails());

        return customer == null
                //  404 NOT FOUND
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                //  200 OK
                : new ResponseEntity<>(customerDto, HttpStatus.OK);
    }

    @GetMapping(value = "/customer")
    ResponseEntity<CustomerDto> customerByDetails(@RequestParam("personalDetails") String personalDetails) {
        Customer customer = customerService.getByPersonalDetails(personalDetails);
        CustomerDto customerDto = customer == null
                ? null
                : new CustomerDto(
                customer.getId(),
                customer.getAddress().getDto(),
                customer.getBranch().getDto(),
                customer.getPersonalDetails(),
                customer.getContactDetails());

        return customer == null
                //  404 NOT FOUND
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                //  200 OK
                : new ResponseEntity<>(customerDto, HttpStatus.OK);
    }
    //endregion

    //region HTTP POST
    @PostMapping(value = "/customer")
    ResponseEntity<CustomerDto> createCustomer(@RequestBody CustomerDto clientDto) {
        try {
            customerService.addCustomer(clientDto.getDBModel());
        } catch (Exception e) {
            //  400 BAD REQUEST
            return new ResponseEntity<>(clientDto, HttpStatus.BAD_REQUEST);
        }

        //  201 CREATED
        return new ResponseEntity<>(clientDto, HttpStatus.CREATED);
    }
    //endregion

    //region HTTP PUT
    @PutMapping(value = "/customer")
    ResponseEntity<CustomerDto> updateCustomer(@RequestBody CustomerDto clientDto) {
        try {
            customerService.updateCustomer(clientDto.getDBModel());
        } catch (Exception e) {
            //  400 BAD REQUEST
            return new ResponseEntity<>(clientDto, HttpStatus.BAD_REQUEST);
        }

        //  200 OK
        return new ResponseEntity<>(clientDto, HttpStatus.OK);
    }
    //endregion

    //region HTTP DELETE
    @DeleteMapping(value = "/customer/{id}")
    ResponseEntity<CustomerDto> deleteCustomer(@PathVariable("id") Long id) {
        try {
            customerService.deleteCustomer(id);
        } catch (Exception e) {
            //  400 BAD REQUEST
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        //  204 NO CONTENT
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    //endregion
}
