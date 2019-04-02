package com.codecentric.retailbank.repository;

import com.codecentric.retailbank.model.domain.Bank;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class BankRepositoryIntegrationTest {

    @Autowired
    private BankRepository repository;

    @Test
    @DisplayName("Test allBanks - Success")
    void test_allBanks_success() {
        // Execute the repository call
        List<Bank> returnedBanks = repository.all();

        // Assert the response
        Assertions.assertTrue(returnedBanks.size() > 0, "Returned list shouldn't be empty.");
    }

    @Test
    @DisplayName("Test singleBank - Success")
    void test_singleBank_success() {
        // Execute the repository call
        Bank returnedBank = repository.single(1L);

        // Assert the response
        Assertions.assertNotNull(returnedBank, "Returned bank shouldn't be null.");
    }

    @Test
    @DisplayName("Test addBank - Success")
    void test_addBank_success() {
        // Execute the repository call
        repository.add(new Bank("BANK INSERT - REPO INTEGRATION TEST"));

        // Retrieve the newly added item
        Bank addedBank = repository.singleByDetails("BANK INSERT - REPO INTEGRATION TEST");

        // Assert the response
        Assertions.assertNotNull(addedBank, "Returned bank shouldn't be null.");

        // Cleanup
        Bank cleanupTarget = repository.singleByDetails("BANK INSERT - REPO INTEGRATION TEST");
        repository.delete(cleanupTarget);
    }

    @Test
    @DisplayName("Test updateBank - Success")
    void test_updateBank_success() {
        // Execute the repository call
        repository.add(new Bank("BANK TO UPDATE - REPO INTEGRATION TEST"));

        // Retrieve the newly added item and modify it
        Bank addedBank = repository.singleByDetails("BANK TO UPDATE - REPO INTEGRATION TEST");
        addedBank.setDetails("BANK UPDATED - REPO INTEGRATION TEST");

        // Update the existing item
        repository.update(addedBank);

        // Retrieve the updated item
        Bank updatedBank = repository.singleByDetails("BANK UPDATED - REPO INTEGRATION TEST");

        // Assert the response
        Assertions.assertNotNull(updatedBank, "Returned bank shouldn't be null.");

        // Cleanup
        Bank cleanupTarget = repository.singleByDetails("BANK UPDATED - REPO INTEGRATION TEST");
        repository.delete(cleanupTarget);
    }


}