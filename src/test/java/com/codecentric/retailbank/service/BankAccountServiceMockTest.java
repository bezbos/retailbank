package com.codecentric.retailbank.service;

import com.codecentric.retailbank.model.domain.BankAccount;
import com.codecentric.retailbank.model.domain.Customer;
import com.codecentric.retailbank.model.domain.RefAccountStatus;
import com.codecentric.retailbank.model.domain.RefAccountType;
import com.codecentric.retailbank.repository.BankAccountRepository;
import com.codecentric.retailbank.repository.helpers.ListPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doReturn;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class BankAccountServiceMockTest {

    @Autowired
    private BankAccountService bankAccountService;

    @MockBean
    private BankAccountRepository bankAccountRepository;

    @Test
    @DisplayName("Test getById - Success")
    void test_GetById_success() {
        // Setup our mock
        RefAccountStatus status = new RefAccountStatus(1L, "01-TEST", "Desc", "Y", "N");
        RefAccountType type = new RefAccountType(1L, "01-TEST", "Desc", "Y", "N", "N", "N", "N");
        Customer customer = new Customer(1L, null, null, "Personal", "Contact");

        BankAccount mockBankAccount = new BankAccount(1L, status, type, customer, BigDecimal.valueOf(1L), "BankAccount Name");
        doReturn(mockBankAccount).when(bankAccountRepository).single(1L);

        // Execute the bankAccountService call
        BankAccount returnedBankAccount = bankAccountService.getById(1L);

        // Assert the response
        Assertions.assertNotNull(returnedBankAccount, "BankAccount wasn't found.");
        Assertions.assertSame(returnedBankAccount, mockBankAccount, "BankAccounts should be the same.");
    }

    @Test
    @DisplayName("Test getByDetails - Success")
    void test_GetByDetails_success() {
        // Setup our mock
        RefAccountStatus status = new RefAccountStatus(1L, "01-TEST", "Desc", "Y", "N");
        RefAccountType type = new RefAccountType(1L, "01-TEST", "Desc", "Y", "N", "N", "N", "N");
        Customer customer = new Customer(1L, null, null, "Personal", "Contact");

        BankAccount mockBankAccount = new BankAccount(1L, status, type, customer, BigDecimal.valueOf(1L), "BankAccount Name");
        doReturn(mockBankAccount).when(bankAccountRepository).getSingleByDetails("BankAccount Name");

        // Execute the bankAccountService call
        BankAccount returnedBankAccount = bankAccountService.getByDetails("BankAccount Name");

        // Assert the response
        Assertions.assertNotNull(returnedBankAccount, "BankAccount wasn't found.");
        Assertions.assertSame(returnedBankAccount, mockBankAccount, "BankAccounts should be the same");
    }


    @Test
    @DisplayName("Test getAll - Success")
    void test_GetAll_success() {
        // Setup our mock
        RefAccountStatus status = new RefAccountStatus(1L, "01-TEST", "Desc", "Y", "N");
        RefAccountType type = new RefAccountType(1L, "01-TEST", "Desc", "Y", "N", "N", "N", "N");
        Customer customer = new Customer(1L, null, null, "Personal", "Contact");

        List<BankAccount> mockBankAccounts = new ArrayList<>();
        mockBankAccounts.add(new BankAccount(1L, status, type, customer, BigDecimal.valueOf(1L), "BankAccount 1"));
        mockBankAccounts.add(new BankAccount(2L, status, type, customer, BigDecimal.valueOf(1L), "BankAccount 2"));
        mockBankAccounts.add(new BankAccount(3L, status, type, customer, BigDecimal.valueOf(1L), "BankAccount 3"));
        doReturn(mockBankAccounts).when(bankAccountRepository).all();

        // Execute the bankAccountService call
        List<BankAccount> returnedBankAccounts = bankAccountService.getAllAccounts();

        // Assert the response
        Assertions.assertIterableEquals(mockBankAccounts, returnedBankAccounts, "BankAccount lists are not the same!");
    }

    @Test
    @DisplayName("Test getAllByDetails - Success")
    void test_GetAllByDetails_success() {
        // Setup our mock
        RefAccountStatus status = new RefAccountStatus(1L, "01-TEST", "Desc", "Y", "N");
        RefAccountType type = new RefAccountType(1L, "01-TEST", "Desc", "Y", "N", "N", "N", "N");
        Customer customer = new Customer(1L, null, null, "Personal", "Contact");

        BankAccount mockBankAccount1 = new BankAccount(1L, status, type, customer, BigDecimal.valueOf(1L), "BankAccount 1");
        BankAccount mockBankAccount2 = new BankAccount(1L, status, type, customer, BigDecimal.valueOf(1L), "BankAccount 2");
        BankAccount mockBankAccount3 = new BankAccount(1L, status, type, customer, BigDecimal.valueOf(1L), "BankAccount 3");

        List<BankAccount> mockBankAccountList = new ArrayList<>();
        mockBankAccountList.add(mockBankAccount1);
        mockBankAccountList.add(mockBankAccount2);
        mockBankAccountList.add(mockBankAccount3);
        doReturn(mockBankAccountList).when(bankAccountRepository).allByDetails("BankAccount");

        // Execute the bankAccountService call
        List<BankAccount> returnedBankAccounts = bankAccountService.getAllAccountsByDetails("BankAccount");

        // Assert the response
        Assertions.assertIterableEquals(mockBankAccountList, returnedBankAccounts, "BankAccount lists are not the same.");
        Assertions.assertTrue(returnedBankAccounts.contains(mockBankAccount1), "Returned list doesn't contain mockBankAccount1.");
        Assertions.assertTrue(returnedBankAccounts.contains(mockBankAccount2), "Returned list doesn't contain mockBankAccount2.");
        Assertions.assertTrue(returnedBankAccounts.contains(mockBankAccount3), "Returned list doesn't contain mockBankAccount3.");
    }


    @Test
    @DisplayName("Test getAllBankAccountsByPage - Success")
    void test_GetAllBankAccountsByPage_success() {
        // Setup our mock
        RefAccountStatus status = new RefAccountStatus(1L, "01-TEST", "Desc", "Y", "N");
        RefAccountType type = new RefAccountType(1L, "01-TEST", "Desc", "Y", "N", "N", "N", "N");
        Customer customer = new Customer(1L, null, null, "Personal", "Contact");

        BankAccount mockBankAccount1 = new BankAccount(1L, status, type, customer, BigDecimal.valueOf(1L), "BankAccount 1");
        BankAccount mockBankAccount2 = new BankAccount(1L, status, type, customer, BigDecimal.valueOf(1L), "BankAccount 2");
        BankAccount mockBankAccount3 = new BankAccount(1L, status, type, customer, BigDecimal.valueOf(1L), "BankAccount 3");
        BankAccount mockBankAccount4 = new BankAccount(1L, status, type, customer, BigDecimal.valueOf(1L), "BankAccount 4");

        ListPage<BankAccount> secondPageMock = new ListPage<>();
        List<BankAccount> bankAccountModels = new ArrayList<>();
        bankAccountModels.add(mockBankAccount3);
        bankAccountModels.add(mockBankAccount4);
        secondPageMock.setModels(bankAccountModels);

        doReturn(secondPageMock).when(bankAccountRepository).allRange(1, 2);

        // Execute the bankAccountService call
        List<BankAccount> returnedBankAccounts = bankAccountService.getAllAccounts(1, 2).getModels();

        // Assert the response
        Assertions.assertIterableEquals(secondPageMock.getModels(), returnedBankAccounts, "BankAccount lists are not the same!");
    }

    @Test
    @DisplayName("Test addBankAccount - Success")
    void test_addBankAccount_success() {
        // Setup our mock
        RefAccountStatus status = new RefAccountStatus(1L, "01-TEST", "Desc", "Y", "N");
        RefAccountType type = new RefAccountType(1L, "01-TEST", "Desc", "Y", "N", "N", "N", "N");
        Customer customer = new Customer(1L, null, null, "Personal", "Contact");

        BankAccount mockBankAccount = new BankAccount(1L, status, type, customer, BigDecimal.valueOf(1L), "BankAccount Name");
        doReturn(mockBankAccount).when(bankAccountRepository).add(mockBankAccount);

        // Execute the bankAccountService call
        BankAccount returnedBankAccount = bankAccountService.addAccount(mockBankAccount);

        // Assert the response
        Assertions.assertNotNull(returnedBankAccount, "BankAccount shouldn't be null.");
        Assertions.assertSame(returnedBankAccount, mockBankAccount, "BankAccounts should be the same");
    }

    @Test
    @DisplayName("Test updateBankAccount - Success")
    void test_updateBankAccount_success() {
        // Setup our mock
        RefAccountStatus status = new RefAccountStatus(1L, "01-TEST", "Desc", "Y", "N");
        RefAccountType type = new RefAccountType(1L, "01-TEST", "Desc", "Y", "N", "N", "N", "N");
        Customer customer = new Customer(1L, null, null, "Personal", "Contact");

        BankAccount mockBankAccount = new BankAccount(1L, status, type, customer, BigDecimal.valueOf(1L), "BankAccount Name");
        doReturn(mockBankAccount).when(bankAccountRepository).update(mockBankAccount);

        // Execute the bankAccountService call
        BankAccount returnedBankAccount = bankAccountService.updateAccount(mockBankAccount);

        // Assert the response
        Assertions.assertNotNull(returnedBankAccount, "BankAccount shouldn't be null.");
        Assertions.assertSame(returnedBankAccount, mockBankAccount, "BankAccounts should be the same");
    }
}