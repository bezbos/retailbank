package com.codecentric.retailbank.web.controller.api.v1;

import com.codecentric.retailbank.model.domain.Address;
import com.codecentric.retailbank.model.domain.Bank;
import com.codecentric.retailbank.model.domain.Branch;
import com.codecentric.retailbank.model.domain.RefBranchType;
import com.codecentric.retailbank.model.dto.AddressDto;
import com.codecentric.retailbank.model.dto.BankDto;
import com.codecentric.retailbank.model.dto.BranchDto;
import com.codecentric.retailbank.model.dto.RefBranchTypeDto;
import com.codecentric.retailbank.service.BranchService;
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
class BranchApiControllerMockTest {

    private final String ADMIN_TOKEN = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwicm9sZXMiOlsiUk9MRV9BRE1JTiIsIlJPTEVfVVNFUiJdLCJpZCI6MSwiZXhwIjoxNTU0NTQ3MTQwLCJpYXQiOjE1NTM5NDIzNDAsImVtYWlsIjoiYWRtaW5AZ21haWwuY29tIn0.nMCPTeGHD1mK0l2G7M3jRt5JAoBnt6YQhBA1e6u9lovzMuunFzerZxcI5fhuL_P_EpEF3x-gCTpZ_a1SyF_wDQ";

    @MockBean
    private BranchService branchService;

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
    @DisplayName("GET /branch/1 - Found")
    void testGetBranchByIdFound() throws Exception {
        // Setup our mocked service
        Address address = new Address();
        Bank bank = new Bank();
        RefBranchType type = new RefBranchType();

        Branch mockBranch = new Branch(1L, address, bank, type, "Mock Branch");
        doReturn(mockBranch).when(branchService).getById(1L);

        // Execute the GET request
        mockMvc.perform(get("/api/v1/branch/{id}", 1L)
                .header("Authorization", ADMIN_TOKEN))

                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))

                // Validate the headers
                .andExpect(header().string(HttpHeaders.LOCATION, "/branch/1"))

                // Validate the returned fields
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.details", is("Mock Branch")));

    }

    @Test
    @DisplayName("GET /branch/1 - Not Found")
    void testGetBranchByIdNotFound() throws Exception {

        // Setup our mocked service
        doReturn(null).when(branchService).getById(1L);

        // Execute the GET request
        mockMvc.perform(get("/api/v1/branch/{id}", 1L)
                .header("Authorization", ADMIN_TOKEN))

                // Validate that we get a 404 Not Found response
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /branch - Success")
    void testCreateBranch() throws Exception {

        // Setup our mocked service
        AddressDto address = new AddressDto();
        BankDto bank = new BankDto();
        RefBranchTypeDto type = new RefBranchTypeDto();

        BranchDto postBranch = new BranchDto(null, address, bank, type, "Posted Branch");
        Branch mockBranch = new Branch(1L, "Posted Branch");
        doReturn(mockBranch).when(branchService).addBranch(any());

        // Execute the POST request
        mockMvc.perform(post("/api/v1/branch")
                .header("Authorization", ADMIN_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(postBranch)))

                // Validate the response code and content type
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))

                // Validate the headers
                .andExpect(header().string(HttpHeaders.LOCATION, "/branch/1"))

                // Validate the returned fields
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.details", is("Posted Branch")));
    }

    @Test
    @DisplayName("PUT /branch/1 - Success")
    void testBranchPutSuccess() throws Exception {

        // Setup mocked service
        Address address = new Address();
        Bank bank = new Bank();
        RefBranchType type = new RefBranchType();

        Branch putBranch = new Branch(1L, address, bank, type, "Updated Branch");
        doReturn(putBranch).when(branchService).updateBranch(putBranch);

        // Execute the PUT request
        mockMvc.perform(put("/api/v1/branch")
                .header("Authorization", ADMIN_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(putBranch)))

                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))

                // Validate the headers
                .andExpect(header().string(HttpHeaders.LOCATION, "/branch/1"))

                // Validate the returned fields
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.details", is("Updated Branch")));
    }

    @Test
    @DisplayName("DELETE /branch/1 - Success")
    void testBranchDeleteSuccess() throws Exception {

        // Setup mocked branch
        Branch mockBranch = new Branch(1L, "Branch Name");

        // Setup mocked service
        doReturn(mockBranch).when(branchService).getById(1L);
        doNothing().when(branchService).deleteBranch(1L);

        // Execute our DELETE request
        mockMvc.perform(delete("/api/v1/branch/{id}", 1)
                .header("Authorization", ADMIN_TOKEN))

                // Validate the response code
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /branch/1 - Failure")
    void testBranchDeleteFailure() throws Exception {

        // Setup mocked service
        doThrow(NullPointerException.class).when(branchService).deleteBranch(1L);

        // Execute our DELETE request
        mockMvc.perform(delete("/api/v1/branch/{id}", 1)
                .header("Authorization", ADMIN_TOKEN))

                // Validate the response code
                .andExpect(status().isBadRequest());
    }
}