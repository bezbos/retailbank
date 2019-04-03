package com.codecentric.retailbank.web.controller.api.v1;

import com.codecentric.retailbank.model.domain.Customer;
import com.codecentric.retailbank.model.dto.CustomerDto;
import com.codecentric.retailbank.repository.helpers.ListPage;
import com.codecentric.retailbank.security.UsersUtil;
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

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.codecentric.retailbank.constants.Constant.PAGE_SIZE;

@RestController
@RequestMapping("/api/v1")
public class CustomerApiController {

    //region FIELDS
    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private CustomerService customerService;
    //endregion

    //region HTTP GET
    @GetMapping({"/customers", "/customers/{page}"})
    ResponseEntity<PageableList<CustomerDto>> customers(@PathVariable("page") Optional<Integer> page,
                                                        @RequestParam("personalDetails") Optional<String> personalDetails) {

        if (personalDetails.isPresent()) {
            List<Customer> customers = customerService.getAllByPersonalDetails(personalDetails.get());
            List<CustomerDto> customerDtos = new ArrayList<>();
            customers.forEach(
                    customer -> customerDtos.add(
                            new CustomerDto(
                                    customer.getId(),
                                    customer.getAddress().getDto(),
                                    customer.getBranch().getDto(),
                                    customer.getPersonalDetails(),
                                    customer.getContactDetails()
                            )));

            PageableList<CustomerDto> pagableCustomerDtoList = new PageableList<>(customerDtos);

            return customerDtos.size() == 0
                    //  404 NOT FOUND
                    ? ResponseEntity.notFound().build()
                    //  200 OK
                    : ResponseEntity.ok().location(URI.create("/customers?" + personalDetails.get())).body(pagableCustomerDtoList);
        }

        // If pageIndex is less than 1 set it to 1.
        Integer pageIndex = page.isPresent() ? page.get() : 0;
        pageIndex = pageIndex == 0 || pageIndex < 0 ?
                0 : pageIndex;

        ListPage<Customer> customers = customerService.getAllCustomers(pageIndex, PAGE_SIZE);
        List<CustomerDto> customerDtos = new ArrayList<>();
        customers.getModels()
                .forEach(
                        customer -> customerDtos.add(
                                new CustomerDto(
                                        customer.getId(),
                                        customer.getAddress().getDto(),
                                        customer.getBranch().getDto(),
                                        customer.getPersonalDetails(),
                                        customer.getContactDetails()
                                )));


        PageableList<CustomerDto> pageableCustomerDtos = new PageableList<>(pageIndex, customerDtos, customers.getPageCount(), customers.getModelsCount());

        return pageableCustomerDtos.currentPage == null
                //  404 NOT FOUND
                ? ResponseEntity.notFound().build()
                //  200 OK
                : ResponseEntity.ok().location(URI.create("/customers/" + pageIndex)).body(pageableCustomerDtos);
    }

    @GetMapping("/customer/{id}")
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

        return customerDto == null
                //  404 NOT FOUND
                ? ResponseEntity.notFound().build()
                //  200 OK
                : ResponseEntity.ok().location(URI.create("/customer/" + id)).body(customerDto);
    }

    @GetMapping("/customer")
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

        return customerDto == null
                //  404 NOT FOUND
                ? ResponseEntity.notFound().build()
                //  200 OK
                : ResponseEntity.ok().location(URI.create("/customer?personalDetails=" + personalDetails)).body(customerDto);
    }
    //endregion

    //region HTTP POST
    @PostMapping("/customer")
    ResponseEntity<CustomerDto> createCustomer(@RequestBody CustomerDto dto) {

        if (!UsersUtil.isAdmin()) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        Customer result;
        try {
            result = customerService.addCustomer(dto.getDBModel());
        } catch (Exception e) {
            e.printStackTrace();
            //  400 BAD REQUEST
            return ResponseEntity.badRequest().body(dto);
        }

        //  201 CREATED
        return ResponseEntity.created(URI.create("/customer/" + result.getId())).body(result.getDto());
    }
    //endregion

    //region HTTP PUT
    @PutMapping("/customer")
    ResponseEntity<CustomerDto> updateCustomer(@RequestBody CustomerDto dto) {

        if (!UsersUtil.isAdmin()) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        Customer result;
        try {
             result = customerService.updateCustomer(dto.getDBModel());
        } catch (Exception e) {
            e.printStackTrace();
            //  400 BAD REQUEST
            return ResponseEntity.badRequest().body(dto);
        }

        //  200 OK
        return ResponseEntity.ok().location(URI.create("/customer/" + result.getId())).body(result.getDto());
    }
    //endregion

    //region HTTP DELETE
    @DeleteMapping("/customer/{id}")
    ResponseEntity<CustomerDto> deleteCustomer(@PathVariable("id") Long id) {

        if (!UsersUtil.isAdmin()) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        try {
            customerService.deleteCustomer(id);
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
