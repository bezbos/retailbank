package com.codecentric.retailbank.web.controller.api.v1;

import com.codecentric.retailbank.model.domain.BankAccount;
import com.codecentric.retailbank.model.domain.Customer;
import com.codecentric.retailbank.model.domain.RefAccountStatus;
import com.codecentric.retailbank.model.domain.RefAccountType;
import com.codecentric.retailbank.model.dto.BankAccountDto;
import com.codecentric.retailbank.model.dto.CustomerDto;
import com.codecentric.retailbank.model.dto.RefAccountStatusDto;
import com.codecentric.retailbank.model.dto.RefAccountTypeDto;
import com.codecentric.retailbank.service.BankAccountService;
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

import java.math.BigDecimal;

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
class BankAccountApiControllerMockTest {

    private final String ADMIN_TOKEN = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwicm9sZXMiOlsiUk9MRV9BRE1JTiIsIlJPTEVfVVNFUiJdLCJpZCI6MSwiZXhwIjoxNTU0NTQ3MTQwLCJpYXQiOjE1NTM5NDIzNDAsImVtYWlsIjoiYWRtaW5AZ21haWwuY29tIn0.nMCPTeGHD1mK0l2G7M3jRt5JAoBnt6YQhBA1e6u9lovzMuunFzerZxcI5fhuL_P_EpEF3x-gCTpZ_a1SyF_wDQ";

    @MockBean
    private BankAccountService bankAccountService;

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
    @DisplayName("GET /bankAccount/1 - Found")
    void testGetBankAccountByIdFound() throws Exception {
        // Setup our mocked service
        RefAccountStatus status = new RefAccountStatus();
        RefAccountType type = new RefAccountType();
        Customer customer = new Customer();

        BankAccount mockBankAccount = new BankAccount(1L, status, type, customer, BigDecimal.ZERO, "Mock BankAccount");
        doReturn(mockBankAccount).when(bankAccountService).getById(1L);

        // Execute the GET request
        mockMvc.perform(get("/api/v1/bankAccount/{id}", 1L)
                .header("Authorization", ADMIN_TOKEN))

                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))

                // Validate the headers
                .andExpect(header().string(HttpHeaders.LOCATION, "/bankAccount/1"))

                // Validate the returned fields
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.details", is("Mock BankAccount")));

    }

    @Test
    @DisplayName("GET /bankAccount/1 - Not Found")
    void testGetBankAccountByIdNotFound() throws Exception {
        // Setup our mocked service
        doReturn(null).when(bankAccountService).getById(1L);

        // Execute the GET request
        mockMvc.perform(get("/api/v1/bankAccount/{id}", 1L)
                .header("Authorization", ADMIN_TOKEN))

                // Validate that we get a 404 Not Found response
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /bankAccount - Success")
    void testCreateBankAccount() throws Exception {
        // Setup our mocked service
        RefAccountStatusDto status = new RefAccountStatusDto();
        RefAccountTypeDto type = new RefAccountTypeDto();
        CustomerDto customer = new CustomerDto();

        BankAccountDto postBankAccount = new BankAccountDto(null, status, type, customer, BigDecimal.ZERO, "Posted BankAccount");
        BankAccount mockBankAccount = new BankAccount(1L, BigDecimal.ZERO, "Posted BankAccount");
        doReturn(mockBankAccount).when(bankAccountService).addAccount(any());

        // Execute the POST request
        mockMvc.perform(post("/api/v1/bankAccount")
                .header("Authorization", ADMIN_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(postBankAccount)))

                // Validate the response code and content type
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))

                // Validate the headers
                .andExpect(header().string(HttpHeaders.LOCATION, "/bankAccount/1"))

                // Validate the returned fields
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.details", is("Posted BankAccount")));
    }

    @Test
    @DisplayName("PUT /bankAccount/1 - Success")
    void testBankAccountPutSuccess() throws Exception {
        // Setup mocked service
        RefAccountStatus status = new RefAccountStatus();
        RefAccountType type = new RefAccountType();
        Customer customer = new Customer();

        BankAccount putBankAccount = new BankAccount(1L, status, type, customer, BigDecimal.ZERO, "Updated BankAccount");
        doReturn(putBankAccount).when(bankAccountService).updateAccount(putBankAccount);

        // Execute the PUT request
        mockMvc.perform(put("/api/v1/bankAccount")
                .header("Authorization", ADMIN_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(putBankAccount)))

                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))

                // Validate the headers
                .andExpect(header().string(HttpHeaders.LOCATION, "/bankAccount/1"))

                // Validate the returned fields
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.details", is("Updated BankAccount")));
    }

    @Test
    @DisplayName("DELETE /bankAccount/1 - Success")
    void testBankAccountDeleteSuccess() throws Exception {
        // Setup mocked bankAccount
        RefAccountStatus status = new RefAccountStatus();
        RefAccountType type = new RefAccountType();
        Customer customer = new Customer();

        BankAccount mockBankAccount = new BankAccount(1L,status, type, customer, BigDecimal.ZERO, "BankAccount Name");

        // Setup mocked service
        doReturn(mockBankAccount).when(bankAccountService).getById(1L);
        doNothing().when(bankAccountService).deleteAccount(1L);

        // Execute our DELETE request
        mockMvc.perform(delete("/api/v1/bankAccount/{id}", 1)
                .header("Authorization", ADMIN_TOKEN))

                // Validate the response code
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /bankAccount/1 - Failure")
    void testBankAccountDeleteFailure() throws Exception {
        // Setup mocked service
        doThrow(NullPointerException.class).when(bankAccountService).deleteAccount(1L);

        // Execute our DELETE request
        mockMvc.perform(delete("/api/v1/bankAccount/{id}", 1)
                .header("Authorization", ADMIN_TOKEN))

                // Validate the response code
                .andExpect(status().isBadRequest());
    }
}