package com.codecentric.retailbank.service;

import com.codecentric.retailbank.model.domain.Branch;
import com.codecentric.retailbank.repository.BranchRepository;
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
class BranchServiceMockTest {

    @Autowired
    private BranchService service;

    @MockBean
    private BranchRepository repository;


    @Test
    @DisplayName("Test getById - Success")
    void test_GetById_success() {
        // Setup our mock
        Branch mockBranch = new Branch(1L, "Branch Name");
        doReturn(mockBranch).when(repository).single(1L);

        // Execute the service call
        Branch returnedBranch = service.getById(1L);

        // Assert the response
        Assertions.assertNotNull(returnedBranch, "Branch wasn't found.");
        Assertions.assertSame(returnedBranch, mockBranch, "Branches should be the same.");
    }

    @Test
    @DisplayName("Test getByDetails - Success")
    void test_GetByDetails_success() {
        // Setup our mock
        Branch mockBranch = new Branch(1L, "Branch Name");
        doReturn(mockBranch).when(repository).singleByDetails("Branch Name");

        // Execute the service call
        Branch returnedBranch = service.getByDetails("Branch Name");

        // Assert the response
        Assertions.assertNotNull(returnedBranch, "Branch wasn't found.");
        Assertions.assertSame(returnedBranch, mockBranch, "Branches should be the same");
    }


    @Test
    @DisplayName("Test getAll - Success")
    void test_GetAll_success() {
        // Setup our mock
        List<Branch> mockBranches = new ArrayList<>();
        mockBranches.add(new Branch(1L, "Branch 1"));
        mockBranches.add(new Branch(2L, "Branch 2"));
        mockBranches.add(new Branch(3L, "Branch 3"));
        doReturn(mockBranches).when(repository).all();

        // Execute the service call
        List<Branch> returnedBranches = service.getAllBranches();

        // Assert the response
        Assertions.assertIterableEquals(mockBranches, returnedBranches, "Branch lists are not the same!");
    }

    @Test
    @DisplayName("Test getAllByDetails - Success")
    void test_GetAllByDetails_success() {
        // Setup our mock
        Branch mockBranch1 = new Branch(1L, "Branch 1");
        Branch mockBranch2 = new Branch(1L, "Branch 2");
        Branch mockBranch3 = new Branch(1L, "Branch 3");

        List<Branch> mockBranchList = new ArrayList<>();
        mockBranchList.add(mockBranch1);
        mockBranchList.add(mockBranch2);
        mockBranchList.add(mockBranch3);
        doReturn(mockBranchList).when(repository).allByDetails("Branch");

        // Execute the service call
        List<Branch> returnedBranches = service.getAllBranchesByDetails("Branch");

        // Assert the response
        Assertions.assertIterableEquals(mockBranchList, returnedBranches, "Branch lists are not the same.");
        Assertions.assertTrue(returnedBranches.contains(mockBranch1), "Returned list doesn't contain mockBranch1.");
        Assertions.assertTrue(returnedBranches.contains(mockBranch2), "Returned list doesn't contain mockBranch2.");
        Assertions.assertTrue(returnedBranches.contains(mockBranch3), "Returned list doesn't contain mockBranch3.");
    }


    @Test
    @DisplayName("Test getAllBranchesByPage - Success")
    void test_GetAllBranchesByPage_success() {
        // Setup our mock
        Branch mockBranch1 = new Branch(1L, "Branch 1");
        Branch mockBranch2 = new Branch(1L, "Branch 2");
        Branch mockBranch3 = new Branch(1L, "Branch 3");
        Branch mockBranch4 = new Branch(1L, "Branch 4");

        ListPage<Branch> secondPageMock = new ListPage<>();
        List<Branch> branchModels = new ArrayList<>();
        branchModels.add(mockBranch3);
        branchModels.add(mockBranch4);
        secondPageMock.setModels(branchModels);

        doReturn(secondPageMock).when(repository).allRange(1, 2);

        // Execute the service call
        List<Branch> returnedBranches = service.getAllBranchesByPage(1, 2).getModels();

        // Assert the response
        Assertions.assertIterableEquals(secondPageMock.getModels(), returnedBranches, "Branch lists are not the same!");
    }

    @Test
    @DisplayName("Test addBranch - Success")
    void test_addBranch_success() {
        // Setup our mock
        Branch mockBranch = new Branch(1L, "Branch Name");
        doReturn(mockBranch).when(repository).add(mockBranch);

        // Execute the service call
        Branch returnedBranch = service.addBranch(mockBranch);

        // Assert the response
        Assertions.assertNotNull(returnedBranch, "Branch shouldn't be null.");
        Assertions.assertSame(returnedBranch, mockBranch, "Branches should be the same");
    }

    @Test
    @DisplayName("Test updateBranch - Success")
    void test_updateBranch_success() {
        // Setup our mock
        Branch mockBranch = new Branch(1L, "Branch Name");
        doReturn(mockBranch).when(repository).update(mockBranch);

        // Execute the service call
        Branch returnedBranch = service.updateBranch(mockBranch);

        // Assert the response
        Assertions.assertNotNull(returnedBranch, "Branch shouldn't be null.");
        Assertions.assertSame(returnedBranch, mockBranch, "Branches should be the same");
    }
}