package com.codecentric.retailbank.web.controller.api.v1;

import com.codecentric.retailbank.model.domain.Merchant;
import com.codecentric.retailbank.model.dto.MerchantDto;
import com.codecentric.retailbank.service.MerchantService;
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
class MerchantApiControllerMockTest {

    private final String ADMIN_TOKEN = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwicm9sZXMiOlsiUk9MRV9BRE1JTiIsIlJPTEVfVVNFUiJdLCJpZCI6MSwiZXhwIjoxNTU0NTQ3MTQwLCJpYXQiOjE1NTM5NDIzNDAsImVtYWlsIjoiYWRtaW5AZ21haWwuY29tIn0.nMCPTeGHD1mK0l2G7M3jRt5JAoBnt6YQhBA1e6u9lovzMuunFzerZxcI5fhuL_P_EpEF3x-gCTpZ_a1SyF_wDQ";

    @MockBean
    private MerchantService merchantService;

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
    @DisplayName("GET /merchant/1 - Found")
    void testGetMerchantByIdFound() throws Exception {

        // Setup our mocked service
        Merchant mockMerchant = new Merchant(1L, "Mock Merchant of Test Land");
        doReturn(mockMerchant).when(merchantService).getById(1L);

        // Execute the GET request
        mockMvc.perform(get("/api/v1/merchant/{id}", 1L)
                .header("Authorization", ADMIN_TOKEN))

                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))

                // Validate the headers
                .andExpect(header().string(HttpHeaders.LOCATION, "/merchant/1"))

                // Validate the returned fields
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.details", is("Mock Merchant of Test Land")));

    }

    @Test
    @DisplayName("GET /merchant/1 - Not Found")
    void testGetMerchantByIdNotFound() throws Exception {

        // Setup our mocked service
        doReturn(null).when(merchantService).getById(1L);

        // Execute the GET request
        mockMvc.perform(get("/api/v1/merchant/{id}", 1L)
                .header("Authorization", ADMIN_TOKEN))

                // Validate that we get a 404 Not Found response
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /merchant - Success")
    void testCreateMerchant() throws Exception {

        // Setup our mocked service
        MerchantDto postMerchant = new MerchantDto(null, "Posted Merchant");
        Merchant mockMerchant = new Merchant(1L, "Posted Merchant");
        doReturn(mockMerchant).when(merchantService).addMerchant(any());

        // Execute the POST request
        mockMvc.perform(post("/api/v1/merchant")
                .header("Authorization", ADMIN_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(postMerchant)))

                // Validate the response code and content type
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))

                // Validate the headers
                .andExpect(header().string(HttpHeaders.LOCATION, "/merchant/1"))

                // Validate the returned fields
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.details", is("Posted Merchant")));
    }

    @Test
    @DisplayName("PUT /merchant/1 - Success")
    void testMerchantPutSuccess() throws Exception {

        // Setup mocked service
        Merchant putMerchant = new Merchant(1L, "Updated Merchant");
        doReturn(putMerchant).when(merchantService).updateMerchant(putMerchant);

        // Execute the PUT request
        mockMvc.perform(put("/api/v1/merchant")
                .header("Authorization", ADMIN_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(putMerchant)))

                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))

                // Validate the headers
                .andExpect(header().string(HttpHeaders.LOCATION, "/merchant/1"))

                // Validate the returned fields
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.details", is("Updated Merchant")));
    }

    @Test
    @DisplayName("DELETE /merchant/1 - Success")
    void testMerchantDeleteSuccess() throws Exception {

        // Setup mocked merchant
        Merchant mockMerchant = new Merchant(1L, "Merchant Name");

        // Setup mocked service
        doReturn(mockMerchant).when(merchantService).getById(1L);
        doNothing().when(merchantService).deleteMerchant(1L);

        // Execute our DELETE request
        mockMvc.perform(delete("/api/v1/merchant/{id}", 1)
                .header("Authorization", ADMIN_TOKEN))

                // Validate the response code
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /merchant/1 - Failure")
    void testMerchantDeleteFailure() throws Exception {

        // Setup mocked service
        doThrow(NullPointerException.class).when(merchantService).deleteMerchant(1L);

        // Execute our DELETE request
        mockMvc.perform(delete("/api/v1/merchant/{id}", 1)
                .header("Authorization", ADMIN_TOKEN))

                // Validate the response code
                .andExpect(status().isBadRequest());
    }
}