package com.codecentric.retailbank.web.controller.api.v1;

import com.codecentric.retailbank.model.domain.BankAccount;
import com.codecentric.retailbank.model.domain.Merchant;
import com.codecentric.retailbank.model.domain.RefTransactionType;
import com.codecentric.retailbank.model.domain.Transaction;
import com.codecentric.retailbank.model.dto.BankAccountDto;
import com.codecentric.retailbank.model.dto.MerchantDto;
import com.codecentric.retailbank.model.dto.RefTransactionTypeDto;
import com.codecentric.retailbank.model.dto.TransactionDto;
import com.codecentric.retailbank.service.TransactionService;
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
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class TransactionApiControllerTest {

    private final String ADMIN_TOKEN = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwicm9sZXMiOlsiUk9MRV9BRE1JTiIsIlJPTEVfVVNFUiJdLCJpZCI6MSwiZXhwIjoxNTU0NTQ3MTQwLCJpYXQiOjE1NTM5NDIzNDAsImVtYWlsIjoiYWRtaW5AZ21haWwuY29tIn0.nMCPTeGHD1mK0l2G7M3jRt5JAoBnt6YQhBA1e6u9lovzMuunFzerZxcI5fhuL_P_EpEF3x-gCTpZ_a1SyF_wDQ";

    @MockBean
    private TransactionService transactionService;

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
    @DisplayName("GET /transaction/1 - Found")
    void testGetTransactionByIdFound() throws Exception {
        // Setup our mocked service
        BankAccount sender = new BankAccount();
        BankAccount receiver = new BankAccount();
        Merchant merchant = new Merchant(1L);
        RefTransactionType type = new RefTransactionType();

        Transaction mockTransaction = new Transaction(1L, sender, receiver, merchant, type, null, BigDecimal.valueOf(1L), "Mock Transaction of Test Land");
        doReturn(mockTransaction).when(transactionService).getById(1L);

        // Execute the GET request
        mockMvc.perform(get("/api/v1/transaction/{id}", 1L)
                .header("Authorization", ADMIN_TOKEN))

                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))

                // Validate the headers
                .andExpect(header().string(HttpHeaders.LOCATION, "/transaction/1"))

                // Validate the returned fields
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.details", is("Mock Transaction of Test Land")));

    }

    @Test
    @DisplayName("GET /transaction/1 - Not Found")
    void testGetTransactionByIdNotFound() throws Exception {

        // Setup our mocked service
        doReturn(null).when(transactionService).getById(1L);

        // Execute the GET request
        mockMvc.perform(get("/api/v1/transaction/{id}", 1L)
                .header("Authorization", ADMIN_TOKEN))

                // Validate that we get a 404 Not Found response
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /transaction - Success")
    void testCreateTransaction() throws Exception {
        // Setup our mocked service
        BankAccountDto sender = new BankAccountDto();
        BankAccountDto receiver = new BankAccountDto();
        MerchantDto merchant = new MerchantDto(1L);
        RefTransactionTypeDto type = new RefTransactionTypeDto();

        TransactionDto postTransaction = new TransactionDto(null, sender, receiver, merchant, type, null, BigDecimal.valueOf(1L), "Posted Transaction");
        Transaction mockTransaction = new Transaction(1L, sender.getDBModel(), receiver.getDBModel(), merchant.getDBModel(), type.getDBModel(), null, BigDecimal.valueOf(1L),"Posted Transaction");
        doReturn(mockTransaction).when(transactionService).createPayment(any());

        // Execute the POST request
        mockMvc.perform(post("/api/v1/transaction")
                .header("Authorization", ADMIN_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(postTransaction)))

                // Validate the response code and content type
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))

                // Validate the headers
                .andExpect(header().string(HttpHeaders.LOCATION, "/transaction"))

                // Validate the returned fields
                .andExpect(jsonPath("$.details", is("Posted Transaction")));
    }
}