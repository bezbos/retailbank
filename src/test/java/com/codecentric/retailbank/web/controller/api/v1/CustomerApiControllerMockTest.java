package com.codecentric.retailbank.web.controller.api.v1;

import com.codecentric.retailbank.model.domain.Address;
import com.codecentric.retailbank.model.domain.Branch;
import com.codecentric.retailbank.model.domain.Customer;
import com.codecentric.retailbank.model.dto.AddressDto;
import com.codecentric.retailbank.model.dto.BranchDto;
import com.codecentric.retailbank.model.dto.CustomerDto;
import com.codecentric.retailbank.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class CustomerApiControllerMockTest {

    private final String ADMIN_TOKEN = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwicm9sZXMiOlsiUk9MRV9BRE1JTiIsIlJPTEVfVVNFUiJdLCJpZCI6MSwiZXhwIjoxNTU0NTQ3MTQwLCJpYXQiOjE1NTM5NDIzNDAsImVtYWlsIjoiYWRtaW5AZ21haWwuY29tIn0.nMCPTeGHD1mK0l2G7M3jRt5JAoBnt6YQhBA1e6u9lovzMuunFzerZxcI5fhuL_P_EpEF3x-gCTpZ_a1SyF_wDQ";

    @MockBean
    private CustomerService customerService;

    @Autowired
    private MockMvc mockMvc;

    private static String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Test
    @DisplayName("GET /customer/1 - Found")
    void testGetCustomerByIdFound() throws Exception {
        // Setup our mocked service
        Address address = new Address();
        Branch branch = new Branch();

        Customer mockCustomer = new Customer(1L, address, branch, "Mock Customer", "");
        doReturn(mockCustomer).when(customerService).getById(1L);

        // Execute the GET request
        mockMvc.perform(get("/api/v1/customer/{id}", 1L)
                .header("Authorization", ADMIN_TOKEN))

                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))

                // Validate the headers
                .andExpect(header().string(HttpHeaders.LOCATION, "/customer/1"))

                // Validate the returned fields
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.personalDetails", is("Mock Customer")));

    }

    @Test
    @DisplayName("GET /customer/1 - Not Found")
    void testGetCustomerByIdNotFound() throws Exception {

        // Setup our mocked service
        doReturn(null).when(customerService).getById(1L);

        // Execute the GET request
        mockMvc.perform(get("/api/v1/customer/{id}", 1L)
                .header("Authorization", ADMIN_TOKEN))

                // Validate that we get a 404 Not Found response
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /customer - Success")
    void testCreateCustomer() throws Exception {
        // Setup our mocked service
        AddressDto address = new AddressDto();
        BranchDto branch = new BranchDto();

        CustomerDto postCustomer = new CustomerDto(address, branch, "Posted Customer", "");
        Customer mockCustomer = new Customer(1L, "Posted Customer");
        doReturn(mockCustomer).when(customerService).addCustomer(any());

        // Execute the POST request
        mockMvc.perform(post("/api/v1/customer")
                .header("Authorization", ADMIN_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(postCustomer)))

                // Validate the response code and content type
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))

                // Validate the headers
                .andExpect(header().string(HttpHeaders.LOCATION, "/customer/1"))

                // Validate the returned fields
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.personalDetails", is("Posted Customer")));
    }

    @Test
    @DisplayName("PUT /customer/1 - Success")
    void testCustomerPutSuccess() throws Exception {
        // Setup mocked service
        Address address = new Address();
        Branch branch = new Branch();

        Customer putCustomer = new Customer(1L, address, branch, "Updated Customer", "");
        doReturn(putCustomer).when(customerService).updateCustomer(putCustomer);

        // Execute the PUT request
        mockMvc.perform(put("/api/v1/customer")
                .header("Authorization", ADMIN_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(putCustomer)))

                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))

                // Validate the headers
                .andExpect(header().string(HttpHeaders.LOCATION, "/customer/1"))

                // Validate the returned fields
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.personalDetails", is("Updated Customer")));
    }

    @Test
    @DisplayName("DELETE /customer/1 - Success")
    void testCustomerDeleteSuccess() throws Exception {
        // Setup mocked service
        Address address = new Address();
        Branch branch = new Branch();

        Customer mockCustomer = new Customer(1L, address, branch, "Customer Name", "");
        doReturn(mockCustomer).when(customerService).getById(1L);
        doNothing().when(customerService).deleteCustomer(1L);

        // Execute our DELETE request
        mockMvc.perform(delete("/api/v1/customer/{id}", 1)
                .header("Authorization", ADMIN_TOKEN))

                // Validate the response code
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /customer/1 - Failure")
    void testCustomerDeleteFailure() throws Exception {
        // Setup mocked service
        doThrow(NullPointerException.class).when(customerService).deleteCustomer(1L);

        // Execute our DELETE request
        mockMvc.perform(delete("/api/v1/customer/{id}", 1)
                .header("Authorization", ADMIN_TOKEN))

                // Validate the response code
                .andExpect(status().isBadRequest());
    }
}