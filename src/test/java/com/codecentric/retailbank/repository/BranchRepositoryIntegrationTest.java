package com.codecentric.retailbank.repository;

import com.codecentric.retailbank.model.domain.Address;
import com.codecentric.retailbank.model.domain.Bank;
import com.codecentric.retailbank.model.domain.Branch;
import com.codecentric.retailbank.model.domain.RefBranchType;
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
class BranchRepositoryIntegrationTest {

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private BankRepository bankRepository;

    @Autowired
    private RefBranchTypeRepository typeRepository;

    @Test
    @DisplayName("Test allBranches - Success")
    void test_allBranches_success() {
        // Execute the branchRepository call
        List<Branch> returnedBranches = branchRepository.all();

        // Assert the response
        Assertions.assertTrue(returnedBranches.size() > 0, "Returned list shouldn't be empty.");
    }

    @Test
    @DisplayName("Test singleBranch - Success")
    void test_singleBranch_success() {
        // Execute the branchRepository call
        Branch returnedBranch = branchRepository.single(1L);

        // Assert the response
        Assertions.assertNotNull(returnedBranch, "Returned branch shouldn't be null.");
    }

    @Test
    @DisplayName("Test addBranch - Success")
    void test_addBranch_success() {
        //Prepare the arguments
        Address address = addressRepository.single(1L);
        Bank bank = bankRepository.single(1L);
        RefBranchType type = typeRepository.single(1L);

        // Execute the branchRepository call
        branchRepository.add(new Branch(1L, address, bank, type, "BRANCH INSERT - REPO INTEGRATION TEST"));

        // Retrieve the newly added item
        Branch addedBranch = branchRepository.singleByDetails("BRANCH INSERT - REPO INTEGRATION TEST");

        // Assert the response
        Assertions.assertNotNull(addedBranch, "Returned branch shouldn't be null.");

        // Cleanup
        Branch cleanupTarget = branchRepository.singleByDetails("BRANCH INSERT - REPO INTEGRATION TEST");
        branchRepository.delete(cleanupTarget);
    }

    @Test
    @DisplayName("Test updateBranch - Success")
    void test_updateBranch_success() {
        //Prepare the arguments
        Address address = addressRepository.single(1L);
        Bank bank = bankRepository.single(1L);
        RefBranchType type = typeRepository.single(1L);

        // Execute the branchRepository call
        branchRepository.add(new Branch(1L, address, bank, type, "BRANCH TO UPDATE - REPO INTEGRATION TEST"));

        // Retrieve the newly added item and modify it
        Branch addedBranch = branchRepository.singleByDetails("BRANCH TO UPDATE - REPO INTEGRATION TEST");
        addedBranch.setDetails("BRANCH UPDATED - REPO INTEGRATION TEST");

        // Update the existing item
        branchRepository.update(addedBranch);

        // Retrieve the updated item
        Branch updatedBranch = branchRepository.singleByDetails("BRANCH UPDATED - REPO INTEGRATION TEST");

        // Assert the response
        Assertions.assertNotNull(updatedBranch, "Returned branch shouldn't be null.");

        // Cleanup
        Branch cleanupTarget = branchRepository.singleByDetails("BRANCH UPDATED - REPO INTEGRATION TEST");
        branchRepository.delete(cleanupTarget);
    }
}