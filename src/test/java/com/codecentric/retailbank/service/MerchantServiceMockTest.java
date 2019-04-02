package com.codecentric.retailbank.service;

import com.codecentric.retailbank.model.domain.Merchant;
import com.codecentric.retailbank.repository.MerchantRepository;
import com.codecentric.retailbank.repository.helpers.ListPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doReturn;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class MerchantServiceMockTest {

    @Autowired
    private MerchantService service;

    @MockBean
    private MerchantRepository repository;


    @Test
    @DisplayName("Test getById - Success")
    void test_GetById_success() {
        // Setup our mock
        Merchant mockMerchant = new Merchant(1L, "Merchant Name");
        doReturn(mockMerchant).when(repository).single(1L);

        // Execute the service call
        Merchant returnedMerchant = service.getById(1L);

        // Assert the response
        Assertions.assertNotNull(returnedMerchant, "Merchant wasn't found.");
        Assertions.assertSame(returnedMerchant, mockMerchant, "Merchants should be the same.");
    }

    @Test
    @DisplayName("Test getByDetails - Success")
    void test_GetByDetails_success() {
        // Setup our mock
        Merchant mockMerchant = new Merchant(1L, "Merchant Name");
        doReturn(mockMerchant).when(repository).singleByDetails("Merchant Name");

        // Execute the service call
        Merchant returnedMerchant = service.getByDetails("Merchant Name");

        // Assert the response
        Assertions.assertNotNull(returnedMerchant, "Merchant wasn't found.");
        Assertions.assertSame(returnedMerchant, mockMerchant, "Merchants should be the same");
    }


    @Test
    @DisplayName("Test getAll - Success")
    void test_GetAll_success() {
        // Setup our mock
        List<Merchant> mockMerchants = new ArrayList<>();
        mockMerchants.add(new Merchant(1L, "Merchant 1"));
        mockMerchants.add(new Merchant(2L, "Merchant 2"));
        mockMerchants.add(new Merchant(3L, "Merchant 3"));
        doReturn(mockMerchants).when(repository).all();

        // Execute the service call
        List<Merchant> returnedMerchants = service.getAllMerchants();

        // Assert the response
        Assertions.assertIterableEquals(mockMerchants, returnedMerchants, "Merchant lists are not the same!");
    }

    @Test
    @DisplayName("Test getAllByDetails - Success")
    void test_GetAllByDetails_success() {
        // Setup our mock
        Merchant mockMerchant1 = new Merchant(1L, "Merchant 1");
        Merchant mockMerchant2 = new Merchant(1L, "Merchant 2");
        Merchant mockMerchant3 = new Merchant(1L, "Merchant 3");

        List<Merchant> mockMerchantList = new ArrayList<>();
        mockMerchantList.add(mockMerchant1);
        mockMerchantList.add(mockMerchant2);
        mockMerchantList.add(mockMerchant3);
        doReturn(mockMerchantList).when(repository).allByDetails("Merchant");

        // Execute the service call
        List<Merchant> returnedMerchants = service.getAllMerchantsByDetails("Merchant");

        // Assert the response
        Assertions.assertIterableEquals(mockMerchantList, returnedMerchants, "Merchant lists are not the same.");
        Assertions.assertTrue(returnedMerchants.contains(mockMerchant1), "Returned list doesn't contain mockMerchant1.");
        Assertions.assertTrue(returnedMerchants.contains(mockMerchant2), "Returned list doesn't contain mockMerchant2.");
        Assertions.assertTrue(returnedMerchants.contains(mockMerchant3), "Returned list doesn't contain mockMerchant3.");
    }


    @Test
    @DisplayName("Test getAllMerchantsByPage - Success")
    void test_GetAllMerchantsByPage_success() {
        // Setup our mock
        Merchant mockMerchant1 = new Merchant(1L, "Merchant 1");
        Merchant mockMerchant2 = new Merchant(1L, "Merchant 2");
        Merchant mockMerchant3 = new Merchant(1L, "Merchant 3");
        Merchant mockMerchant4 = new Merchant(1L, "Merchant 4");

        ListPage<Merchant> secondPageMock = new ListPage<>();
        List<Merchant> merchantModels = new ArrayList<>();
        merchantModels.add(mockMerchant3);
        merchantModels.add(mockMerchant4);
        secondPageMock.setModels(merchantModels);

        doReturn(secondPageMock).when(repository).allRange(1, 2);

        // Execute the service call
        List<Merchant> returnedMerchants = service.getAllMerchants(1, 2).getModels();

        // Assert the response
        Assertions.assertIterableEquals(secondPageMock.getModels(), returnedMerchants, "Merchant lists are not the same!");
    }

    @Test
    @DisplayName("Test addMerchant - Success")
    void test_addMerchant_success() {
        // Setup our mock
        Merchant mockMerchant = new Merchant(1L, "Merchant Name");
        doReturn(mockMerchant).when(repository).add(mockMerchant);

        // Execute the service call
        Merchant returnedMerchant = service.addMerchant(mockMerchant);

        // Assert the response
        Assertions.assertNotNull(returnedMerchant, "Merchant shouldn't be null.");
        Assertions.assertSame(returnedMerchant, mockMerchant, "Merchants should be the same");
    }

    @Test
    @DisplayName("Test updateMerchant - Success")
    void test_updateMerchant_success() {
        // Setup our mock
        Merchant mockMerchant = new Merchant(1L, "Merchant Name");
        doReturn(mockMerchant).when(repository).update(mockMerchant);

        // Execute the service call
        Merchant returnedMerchant = service.updateMerchant(mockMerchant);

        // Assert the response
        Assertions.assertNotNull(returnedMerchant, "Merchant shouldn't be null.");
        Assertions.assertSame(returnedMerchant, mockMerchant, "Merchants should be the same");
    }
}