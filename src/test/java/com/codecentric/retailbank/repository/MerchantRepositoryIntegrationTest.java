package com.codecentric.retailbank.repository;

import com.codecentric.retailbank.model.domain.Merchant;
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
class MerchantRepositoryIntegrationTest {

    @Autowired
    private MerchantRepository repository;

    @Test
    @DisplayName("Test allMerchants - Success")
    void test_allMerchants_success() {
        // Execute the repository call
        List<Merchant> returnedMerchants = repository.all();

        // Assert the response
        Assertions.assertTrue(returnedMerchants.size() > 0, "Returned list shouldn't be empty.");
    }

    @Test
    @DisplayName("Test singleMerchant - Success")
    void test_singleMerchant_success() {
        // Execute the repository call
        Merchant returnedMerchant = repository.single(1L);

        // Assert the response
        Assertions.assertNotNull(returnedMerchant, "Returned merchant shouldn't be null.");
    }

    @Test
    @DisplayName("Test addMerchant - Success")
    void test_addMerchant_success() {
        // Execute the repository call
        repository.add(new Merchant(1L, "MERCHANT INSERT - REPO INTEGRATION TEST"));

        // Retrieve the newly added item
        Merchant addedMerchant = repository.singleByDetails("MERCHANT INSERT - REPO INTEGRATION TEST");

        // Assert the response
        Assertions.assertNotNull(addedMerchant, "Returned merchant shouldn't be null.");

        // Cleanup
        Merchant cleanupTarget = repository.singleByDetails("MERCHANT INSERT - REPO INTEGRATION TEST");
        repository.delete(cleanupTarget);
    }

    @Test
    @DisplayName("Test updateMerchant - Success")
    void test_updateMerchant_success() {
        // Execute the repository call
        repository.add(new Merchant(1L, "MERCHANT TO UPDATE - REPO INTEGRATION TEST"));

        // Retrieve the newly added item and modify it
        Merchant addedMerchant = repository.singleByDetails("MERCHANT TO UPDATE - REPO INTEGRATION TEST");
        addedMerchant.setDetails("MERCHANT UPDATED - REPO INTEGRATION TEST");

        // Update the existing item
        repository.update(addedMerchant);

        // Retrieve the updated item
        Merchant updatedMerchant = repository.singleByDetails("MERCHANT UPDATED - REPO INTEGRATION TEST");

        // Assert the response
        Assertions.assertNotNull(updatedMerchant, "Returned merchant shouldn't be null.");

        // Cleanup
        Merchant cleanupTarget = repository.singleByDetails("MERCHANT UPDATED - REPO INTEGRATION TEST");
        repository.delete(cleanupTarget);
    }
}