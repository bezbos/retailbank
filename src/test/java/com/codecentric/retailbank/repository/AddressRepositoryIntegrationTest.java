package com.codecentric.retailbank.repository;

import com.codecentric.retailbank.model.domain.Address;
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
class AddressRepositoryIntegrationTest {

    @Autowired
    private AddressRepository repository;

    @Test
    @DisplayName("Test allAddress - Success")
    void test_allAddress_success() {
        // Execute the repository call
        List<Address> returnedAddress = repository.all();

        // Assert the response
        Assertions.assertTrue(returnedAddress.size() > 0, "Returned list shouldn't be empty.");
    }

    @Test
    @DisplayName("Test singleAddress - Success")
    void test_singleAddress_success() {
        // Execute the repository call
        Address returnedAddress = repository.single(1L);

        // Assert the response
        Assertions.assertNotNull(returnedAddress, "Returned address shouldn't be null.");
    }

    @Test
    @DisplayName("Test addAddress - Success")
    void test_addAddress_success() {
        // Execute the repository call
        repository.add(new Address(1L, "ADDRESS INSERT - REPO INTEGRATION TEST"));

        // Retrieve the newly added item
        Address addedAddress = repository.singleByLine1("ADDRESS INSERT - REPO INTEGRATION TEST");

        // Assert the response
        Assertions.assertNotNull(addedAddress, "Returned address shouldn't be null.");

        // Cleanup
        Address cleanupTarget = repository.singleByLine1("ADDRESS INSERT - REPO INTEGRATION TEST");
        repository.delete(cleanupTarget);
    }

    @Test
    @DisplayName("Test updateAddress - Success")
    void test_updateAddress_success() {
        // Execute the repository call
        repository.add(new Address(1L, "ADDRESS TO UPDATE - REPO INTEGRATION TEST"));

        // Retrieve the newly added item and modify it
        Address addedAddress = repository.singleByLine1("ADDRESS TO UPDATE - REPO INTEGRATION TEST");
        addedAddress.setLine1("ADDRESS UPDATED - REPO INTEGRATION TEST");

        // Update the existing item
        repository.update(addedAddress);

        // Retrieve the updated item
        Address updatedAddress = repository.singleByLine1("ADDRESS UPDATED - REPO INTEGRATION TEST");

        // Assert the response
        Assertions.assertNotNull(updatedAddress, "Returned address shouldn't be null.");

        // Cleanup
        Address cleanupTarget = repository.singleByLine1("ADDRESS UPDATED - REPO INTEGRATION TEST");
        repository.delete(cleanupTarget);
    }

}