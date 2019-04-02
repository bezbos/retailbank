package com.codecentric.retailbank.repository;

import com.codecentric.retailbank.model.domain.Address;
import com.codecentric.retailbank.model.domain.Branch;
import com.codecentric.retailbank.model.domain.Customer;
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
class CustomerRepositoryIntegrationTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private BranchRepository branchRepository;

    @Test
    @DisplayName("Test allCustomers - Success")
    void test_allCustomers_success() {
        // Execute the customerRepository call
        List<Customer> returnedCustomers = customerRepository.all();

        // Assert the response
        Assertions.assertTrue(returnedCustomers.size() > 0, "Returned list shouldn't be empty.");
    }

    @Test
    @DisplayName("Test singleCustomer - Success")
    void test_singleCustomer_success() {
        // Execute the customerRepository call
        Customer returnedCustomer = customerRepository.single(1L);

        // Assert the response
        Assertions.assertNotNull(returnedCustomer, "Returned customer shouldn't be null.");
    }

    @Test
    @DisplayName("Test addCustomer - Success")
    void test_addCustomer_success() {
        // Prepare the arguments
        Address address = addressRepository.single(1L);
        Branch branch = branchRepository.single(1L);

        // Execute the customerRepository call
        customerRepository.add(new Customer(1L, address, branch, "CUSTOMER INSERT - REPO INTEGRATION TEST", ""));

        // Retrieve the newly added item
        Customer addedCustomer = customerRepository.getSingleByPersonalDetails("CUSTOMER INSERT - REPO INTEGRATION TEST");

        // Assert the response
        Assertions.assertNotNull(addedCustomer, "Returned customer shouldn't be null.");

        // Cleanup
        Customer cleanupTarget = customerRepository.getSingleByPersonalDetails("CUSTOMER INSERT - REPO INTEGRATION TEST");
        customerRepository.delete(cleanupTarget);
    }

    @Test
    @DisplayName("Test updateCustomer - Success")
    void test_updateCustomer_success() {
        // Prepare the arguments
        Address address = addressRepository.single(1L);
        Branch branch = branchRepository.single(1L);

        // Execute the customerRepository call
        customerRepository.add(new Customer(1L, address, branch, "CUSTOMER TO UPDATE - REPO INTEGRATION TEST", ""));

        // Retrieve the newly added item and modify it
        Customer addedCustomer = customerRepository.getSingleByPersonalDetails("CUSTOMER TO UPDATE - REPO INTEGRATION TEST");
        addedCustomer.setPersonalDetails("CUSTOMER UPDATED - REPO INTEGRATION TEST");

        // Update the existing item
        customerRepository.update(addedCustomer);

        // Retrieve the updated item
        Customer updatedCustomer = customerRepository.getSingleByPersonalDetails("CUSTOMER UPDATED - REPO INTEGRATION TEST");

        // Assert the response
        Assertions.assertNotNull(updatedCustomer, "Returned customer shouldn't be null.");

        // Cleanup
        Customer cleanupTarget = customerRepository.getSingleByPersonalDetails("CUSTOMER UPDATED - REPO INTEGRATION TEST");
        customerRepository.delete(cleanupTarget);
    }

}