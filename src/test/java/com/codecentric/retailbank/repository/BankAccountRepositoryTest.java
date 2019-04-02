package com.codecentric.retailbank.repository;

import com.codecentric.retailbank.model.domain.BankAccount;
import com.codecentric.retailbank.model.domain.Customer;
import com.codecentric.retailbank.model.domain.RefAccountStatus;
import com.codecentric.retailbank.model.domain.RefAccountType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class BankAccountRepositoryTest {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private RefAccountStatusRepository statusRepository;

    @Autowired
    private RefAccountTypeRepository typeRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    @DisplayName("Test allBankAccounts - Success")
    void test_allBankAccounts_success() {
        // Execute the bankAccountRepository call
        List<BankAccount> returnedBankAccounts = bankAccountRepository.all();

        // Assert the response
        Assertions.assertTrue(returnedBankAccounts.size() > 0, "Returned list shouldn't be empty.");
    }

    @Test
    @DisplayName("Test singleBankAccount - Success")
    void test_singleBankAccount_success() {
        // Execute the bankAccountRepository call
        BankAccount returnedBankAccount = bankAccountRepository.single(1L);

        // Assert the response
        Assertions.assertNotNull(returnedBankAccount, "Returned bank account shouldn't be null.");
    }

    @Test
    @DisplayName("Test addBankAccount - Success")
    void test_addBankAccount_success() {
        // Prepare arguments
        RefAccountStatus status = statusRepository.single(1L);
        RefAccountType type = typeRepository.single(1L);
        Customer customer = customerRepository.single(1L);

        // Execute the bankAccountRepository call
        bankAccountRepository.add(new BankAccount(1L, status, type, customer, BigDecimal.ZERO, "BANK ACCOUNT INSERT - REPO INTEGRATION TEST"));

        // Retrieve the newly added item
        BankAccount addedBankAccount = bankAccountRepository.getSingleByDetails("BANK ACCOUNT INSERT - REPO INTEGRATION TEST");

        // Assert the response
        Assertions.assertNotNull(addedBankAccount, "Returned bank account shouldn't be null.");

        // Cleanup
        BankAccount cleanupTarget = bankAccountRepository.getSingleByDetails("BANK ACCOUNT INSERT - REPO INTEGRATION TEST");
        bankAccountRepository.delete(cleanupTarget);
    }

    @Test
    @DisplayName("Test updateBankAccount - Success")
    void test_updateBankAccount_success() {
        // Prepare arguments
        RefAccountStatus status = statusRepository.single(1L);
        RefAccountType type = typeRepository.single(1L);
        Customer customer = customerRepository.single(1L);

        // Execute the bankAccountRepository call
        bankAccountRepository.add(new BankAccount(1L, status, type, customer, BigDecimal.ZERO, "BANK ACCOUNT TO UPDATE - REPO INTEGRATION TEST"));

        // Retrieve the newly added item and modify it
        BankAccount addedBankAccount = bankAccountRepository.getSingleByDetails("BANK ACCOUNT TO UPDATE - REPO INTEGRATION TEST");
        addedBankAccount.setDetails("BANK ACCOUNT UPDATED - REPO INTEGRATION TEST");

        // Update the existing item
        bankAccountRepository.update(addedBankAccount);

        // Retrieve the updated item
        BankAccount updatedBankAccount = bankAccountRepository.getSingleByDetails("BANK ACCOUNT UPDATED - REPO INTEGRATION TEST");

        // Assert the response
        Assertions.assertNotNull(updatedBankAccount, "Returned bank account shouldn't be null.");

        // Cleanup
        BankAccount cleanupTarget = bankAccountRepository.getSingleByDetails("BANK ACCOUNT UPDATED - REPO INTEGRATION TEST");
        bankAccountRepository.delete(cleanupTarget);
    }
}