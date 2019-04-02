package com.codecentric.retailbank.web.controller.api.v1;

import com.codecentric.retailbank.model.domain.Bank;
import com.codecentric.retailbank.model.dto.BankDto;
import com.codecentric.retailbank.service.BankService;
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
class BankApiControllerMockTest {

    private final String ADMIN_TOKEN = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwicm9sZXMiOlsiUk9MRV9BRE1JTiIsIlJPTEVfVVNFUiJdLCJpZCI6MSwiZXhwIjoxNTU0NTQ3MTQwLCJpYXQiOjE1NTM5NDIzNDAsImVtYWlsIjoiYWRtaW5AZ21haWwuY29tIn0.nMCPTeGHD1mK0l2G7M3jRt5JAoBnt6YQhBA1e6u9lovzMuunFzerZxcI5fhuL_P_EpEF3x-gCTpZ_a1SyF_wDQ";

    @MockBean
    private BankService bankService;

    @Autowired
    private MockMvc mockMvc;

    //region Helper Methods
    static String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    //endregion

    @Test
    @DisplayName("GET /bank/1 - Found")
    void testGetBankByIdFound() throws Exception {

        // Setup our mocked service
        Bank mockBank = new Bank(1L, "Mock Bank of Test Land");
        doReturn(mockBank).when(bankService).getById(1L);

        // Execute the GET request
        mockMvc.perform(get("/api/v1/bank/{id}", 1L)
                .header("Authorization", ADMIN_TOKEN))

                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))

                // Validate the headers
                .andExpect(header().string(HttpHeaders.LOCATION, "/bank/1"))

                // Validate the returned fields
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.details", is("Mock Bank of Test Land")));

    }

    @Test
    @DisplayName("GET /bank/1 - Not Found")
    void testGetBankByIdNotFound() throws Exception {

        // Setup our mocked service
        doReturn(null).when(bankService).getById(1L);

        // Execute the GET request
        mockMvc.perform(get("/api/v1/bank/{id}", 1L)
                .header("Authorization", ADMIN_TOKEN))

                // Validate that we get a 404 Not Found response
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /bank - Success")
    void testCreateBank() throws Exception {

        // Setup our mocked service
        BankDto postBank = new BankDto("Posted Bank");
        Bank mockBank = new Bank(1L, "Posted Bank");
        doReturn(mockBank).when(bankService).addBank(any());

        // Execute the POST request
        mockMvc.perform(post("/api/v1/bank")
                .header("Authorization", ADMIN_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(postBank)))

                // Validate the response code and content type
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))

                // Validate the headers
                .andExpect(header().string(HttpHeaders.LOCATION, "/bank/1"))

                // Validate the returned fields
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.details", is("Posted Bank")));
    }

    @Test
    @DisplayName("PUT /bank/1 - Success")
    void testBankPutSuccess() throws Exception{

        // Setup mocked service
        Bank putBank = new Bank(1L, "Updated Bank");
        doReturn(putBank).when(bankService).updateBank(putBank);

        // Execute the PUT request
        mockMvc.perform(put("/api/v1/bank")
                .header("Authorization", ADMIN_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(putBank)))

                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))

                // Validate the headers
                .andExpect(header().string(HttpHeaders.LOCATION, "/bank/1"))

                // Validate the returned fields
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.details", is("Updated Bank")));
    }

    @Test
    @DisplayName("DELETE /bank/1 - Success")
    void testBankDeleteSuccess() throws Exception{

        // Setup mocked bank
        Bank mockBank = new Bank(1L, "Bank Name");

        // Setup mocked service
        doReturn(mockBank).when(bankService).getById(1L);
        doNothing().when(bankService).deleteBank(1L);

        // Execute our DELETE request
        mockMvc.perform(delete("/api/v1/bank/{id}", 1)
                .header("Authorization", ADMIN_TOKEN))

                // Validate the response code
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /bank/1 - Failure")
    void testBankDeleteFailure() throws Exception{

        // Setup mocked service
        doThrow(NullPointerException.class).when(bankService).deleteBank(1L);

        // Execute our DELETE request
        mockMvc.perform(delete("/api/v1/bank/{id}", 1)
                .header("Authorization", ADMIN_TOKEN))

                // Validate the response code
                .andExpect(status().isBadRequest());
    }
}

