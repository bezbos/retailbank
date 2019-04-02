package com.codecentric.retailbank.service;

import com.codecentric.retailbank.model.domain.Bank;
import com.codecentric.retailbank.repository.BankRepository;
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
class BankServiceMockTest {

    @Autowired
    private BankService service;

    @MockBean
    private BankRepository repository;


    @Test
    @DisplayName("Test getById - Success")
    void test_GetById_success() {
        // Setup our mock
        Bank mockBank = new Bank(1L, "Bank Name");
        doReturn(mockBank).when(repository).single(1L);

        // Execute the service call
        Bank returnedBank = service.getById(1L);

        // Assert the response
        Assertions.assertNotNull(returnedBank, "Bank wasn't found.");
        Assertions.assertSame(returnedBank, mockBank, "Banks should be the same.");
    }

    @Test
    @DisplayName("Test getByDetails - Success")
    void test_GetByDetails_success() {
        // Setup our mock
        Bank mockBank = new Bank(1L, "Bank Name");
        doReturn(mockBank).when(repository).singleByDetails("Bank Name");

        // Execute the service call
        Bank returnedBank = service.getByDetails("Bank Name");

        // Assert the response
        Assertions.assertNotNull(returnedBank, "Bank wasn't found.");
        Assertions.assertSame(returnedBank, mockBank, "Banks should be the same");
    }


    @Test
    @DisplayName("Test getAll - Success")
    void test_GetAll_success() {
        // Setup our mock
        List<Bank> mockBanks = new ArrayList<>();
        mockBanks.add(new Bank(1L, "Bank 1"));
        mockBanks.add(new Bank(2L, "Bank 2"));
        mockBanks.add(new Bank(3L, "Bank 3"));
        doReturn(mockBanks).when(repository).all();

        // Execute the service call
        List<Bank> returnedBanks = service.getAllBanks();

        // Assert the response
        Assertions.assertIterableEquals(mockBanks, returnedBanks, "Bank lists are not the same!");
    }

    @Test
    @DisplayName("Test getAllByDetails - Success")
    void test_GetAllByDetails_success() {
        // Setup our mock
        Bank mockBank1 = new Bank(1L, "Bank 1");
        Bank mockBank2 = new Bank(1L, "Bank 2");
        Bank mockBank3 = new Bank(1L, "Bank 3");

        List<Bank> mockBankList = new ArrayList<>();
        mockBankList.add(mockBank1);
        mockBankList.add(mockBank2);
        mockBankList.add(mockBank3);
        doReturn(mockBankList).when(repository).allByDetails("Bank");

        // Execute the service call
        List<Bank> returnedBanks = service.getAllBanksByDetails("Bank");

        // Assert the response
        Assertions.assertIterableEquals(mockBankList, returnedBanks, "Bank lists are not the same.");
        Assertions.assertTrue(returnedBanks.contains(mockBank1), "Returned list doesn't contain mockBank1.");
        Assertions.assertTrue(returnedBanks.contains(mockBank2), "Returned list doesn't contain mockBank2.");
        Assertions.assertTrue(returnedBanks.contains(mockBank3), "Returned list doesn't contain mockBank3.");
    }


    @Test
    @DisplayName("Test getAllBanksByPage - Success")
    void test_GetAllBanksByPage_success() {
        // Setup our mock
        Bank mockBank1 = new Bank(1L, "Bank 1");
        Bank mockBank2 = new Bank(1L, "Bank 2");
        Bank mockBank3 = new Bank(1L, "Bank 3");
        Bank mockBank4 = new Bank(1L, "Bank 4");

        ListPage<Bank> secondPageMock = new ListPage<>();
        List<Bank> bankModels = new ArrayList<>();
        bankModels.add(mockBank3);
        bankModels.add(mockBank4);
        secondPageMock.setModels(bankModels);

        doReturn(secondPageMock).when(repository).allRange(1, 2);

        // Execute the service call
        List<Bank> returnedBanks = service.getAllBanksByPage(1, 2).getModels();

        // Assert the response
        Assertions.assertIterableEquals(secondPageMock.getModels(), returnedBanks, "Bank lists are not the same!");
    }

    @Test
    @DisplayName("Test addBank - Success")
    void test_addBank_success() {
        // Setup our mock
        Bank mockBank = new Bank(1L, "Bank Name");
        doReturn(mockBank).when(repository).add(mockBank);

        // Execute the service call
        Bank returnedBank = service.addBank(mockBank);

        // Assert the response
        Assertions.assertNotNull(returnedBank, "Bank shouldn't be null.");
        Assertions.assertSame(returnedBank, mockBank, "Banks should be the same");
    }

    @Test
    @DisplayName("Test updateBank - Success")
    void test_updateBank_success() {
        // Setup our mock
        Bank mockBank = new Bank(1L, "Bank Name");
        doReturn(mockBank).when(repository).update(mockBank);

        // Execute the service call
        Bank returnedBank = service.updateBank(mockBank);

        // Assert the response
        Assertions.assertNotNull(returnedBank, "Bank shouldn't be null.");
        Assertions.assertSame(returnedBank, mockBank, "Banks should be the same");
    }
}