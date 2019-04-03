package com.codecentric.retailbank.web.controller.api.v1;

import com.codecentric.retailbank.model.domain.Address;
import com.codecentric.retailbank.model.dto.AddressDto;
import com.codecentric.retailbank.service.AddressService;
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
class AddressApiControllerMockTest {

    private final String ADMIN_TOKEN = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwicm9sZXMiOlsiUk9MRV9BRE1JTiIsIlJPTEVfVVNFUiJdLCJpZCI6MSwiZXhwIjoxNTU0NTQ3MTQwLCJpYXQiOjE1NTM5NDIzNDAsImVtYWlsIjoiYWRtaW5AZ21haWwuY29tIn0.nMCPTeGHD1mK0l2G7M3jRt5JAoBnt6YQhBA1e6u9lovzMuunFzerZxcI5fhuL_P_EpEF3x-gCTpZ_a1SyF_wDQ";

    @MockBean
    private AddressService addressService;

    @Autowired
    private MockMvc mockMvc;

    private static String asJsonString(Object obj) {
        try {
            String test = new ObjectMapper().writeValueAsString(obj);
            return test;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Test
    @DisplayName("GET /address/1 - Found")
    void testGetAddressByIdFound() throws Exception {

        // Setup our mocked service
        Address mockAddress = new Address(1L, "Mock Address");
        doReturn(mockAddress).when(addressService).getById(1L);

        // Execute the GET request
        mockMvc.perform(get("/api/v1/address/{id}", 1L)
                .header("Authorization", ADMIN_TOKEN))

                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))

                // Validate the headers
                .andExpect(header().string(HttpHeaders.LOCATION, "/address/1"))

                // Validate the returned fields
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.line1", is("Mock Address")));

    }

    @Test
    @DisplayName("GET /address/1 - Not Found")
    void testGetAddressByIdNotFound() throws Exception {

        // Setup our mocked service
        doReturn(null).when(addressService).getById(1L);

        // Execute the GET request
        mockMvc.perform(get("/api/v1/address/{id}", 1L)
                .header("Authorization", ADMIN_TOKEN))

                // Validate that we get a 404 Not Found response
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /address - Success")
    void testCreateAddress() throws Exception {

        // Setup our mocked service
        AddressDto postAddress = new AddressDto("Posted Address");
        Address mockAddress = new Address(1L, "Posted Address");
        doReturn(mockAddress).when(addressService).addAddress(any());

        // Execute the POST request
        mockMvc.perform(post("/api/v1/address")
                .header("Authorization", ADMIN_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(postAddress)))

                // Validate the response code and content type
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))

                // Validate the headers
                .andExpect(header().string(HttpHeaders.LOCATION, "/address/1"))

                // Validate the returned fields
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.line1", is("Posted Address")));
    }

    @Test
    @DisplayName("PUT /address/1 - Success")
    void testAddressPutSuccess() throws Exception {

        // Setup mocked service
        Address putAddress = new Address(1L, "Updated Address");
        doReturn(putAddress).when(addressService).updateAddress(putAddress);

        // Execute the PUT request
        mockMvc.perform(put("/api/v1/address")
                .header("Authorization", ADMIN_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(putAddress)))

                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))

                // Validate the headers
                .andExpect(header().string(HttpHeaders.LOCATION, "/address/1"))

                // Validate the returned fields
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.line1", is("Updated Address")));
    }

    @Test
    @DisplayName("DELETE /address/1 - Success")
    void testAddressDeleteSuccess() throws Exception {

        // Setup mocked address
        Address mockAddress = new Address(1L, "Address Name");

        // Setup mocked service
        doReturn(mockAddress).when(addressService).getById(1L);
        doNothing().when(addressService).deleteAddress(1L);

        // Execute our DELETE request
        mockMvc.perform(delete("/api/v1/address/{id}", 1)
                .header("Authorization", ADMIN_TOKEN))

                // Validate the response code
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /address/1 - Failure")
    void testAddressDeleteFailure() throws Exception {

        // Setup mocked service
        doThrow(NullPointerException.class).when(addressService).deleteAddress(1L);

        // Execute our DELETE request
        mockMvc.perform(delete("/api/v1/address/{id}", 1)
                .header("Authorization", ADMIN_TOKEN))

                // Validate the response code
                .andExpect(status().isBadRequest());
    }
}