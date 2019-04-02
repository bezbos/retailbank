package com.codecentric.retailbank.service;

import com.codecentric.retailbank.model.domain.Address;
import com.codecentric.retailbank.repository.AddressRepository;
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
class AddressServiceMockTest {

    @Autowired
    private AddressService service;

    @MockBean
    private AddressRepository repository;


    @Test
    @DisplayName("Test getById - Success")
    void test_GetById_success() {
        // Setup our mock
        Address mockAddress = new Address(1L, "Address Name");
        doReturn(mockAddress).when(repository).single(1L);

        // Execute the service call
        Address returnedAddress = service.getById(1L);

        // Assert the response
        Assertions.assertNotNull(returnedAddress, "Address wasn't found.");
        Assertions.assertSame(returnedAddress, mockAddress, "Addresses should be the same.");
    }

    @Test
    @DisplayName("Test getByDetails - Success")
    void test_GetByDetails_success() {
        // Setup our mock
        Address mockAddress = new Address(1L, "Address Name");
        doReturn(mockAddress).when(repository).singleByLine1("Address Name");

        // Execute the service call
        Address returnedAddress = service.getByLine1("Address Name");

        // Assert the response
        Assertions.assertNotNull(returnedAddress, "Address wasn't found.");
        Assertions.assertSame(returnedAddress, mockAddress, "Addresses should be the same");
    }


    @Test
    @DisplayName("Test getAll - Success")
    void test_GetAll_success() {
        // Setup our mock
        List<Address> mockAddresses = new ArrayList<>();
        mockAddresses.add(new Address(1L, "Address 1"));
        mockAddresses.add(new Address(2L, "Address 2"));
        mockAddresses.add(new Address(3L, "Address 3"));
        doReturn(mockAddresses).when(repository).all();

        // Execute the service call
        List<Address> returnedAddresses = service.getAllAddress();

        // Assert the response
        Assertions.assertIterableEquals(mockAddresses, returnedAddresses, "Address lists are not the same!");
    }

    @Test
    @DisplayName("Test getAllByDetails - Success")
    void test_GetAllByDetails_success() {
        // Setup our mock
        Address mockAddress1 = new Address(1L, "Address 1");
        Address mockAddress2 = new Address(1L, "Address 2");
        Address mockAddress3 = new Address(1L, "Address 3");

        List<Address> mockAddressList = new ArrayList<>();
        mockAddressList.add(mockAddress1);
        mockAddressList.add(mockAddress2);
        mockAddressList.add(mockAddress3);
        doReturn(mockAddressList).when(repository).allByLine1("Address");

        // Execute the service call
        List<Address> returnedAddresses = service.getManyByLine1("Address");

        // Assert the response
        Assertions.assertIterableEquals(mockAddressList, returnedAddresses, "Address lists are not the same.");
        Assertions.assertTrue(returnedAddresses.contains(mockAddress1), "Returned list doesn't contain mockAddress1.");
        Assertions.assertTrue(returnedAddresses.contains(mockAddress2), "Returned list doesn't contain mockAddress2.");
        Assertions.assertTrue(returnedAddresses.contains(mockAddress3), "Returned list doesn't contain mockAddress3.");
    }


    @Test
    @DisplayName("Test getAllAddressesByPage - Success")
    void test_GetAllAddressesByPage_success() {
        // Setup our mock
        Address mockAddress1 = new Address(1L, "Address 1");
        Address mockAddress2 = new Address(1L, "Address 2");
        Address mockAddress3 = new Address(1L, "Address 3");
        Address mockAddress4 = new Address(1L, "Address 4");

        ListPage<Address> secondPageMock = new ListPage<>();
        List<Address> addressModels = new ArrayList<>();
        addressModels.add(mockAddress3);
        addressModels.add(mockAddress4);
        secondPageMock.setModels(addressModels);

        doReturn(secondPageMock).when(repository).allRange(1, 2);

        // Execute the service call
        List<Address> returnedAddresses = service.getAllAddressesByPage(1, 2).getModels();

        // Assert the response
        Assertions.assertIterableEquals(secondPageMock.getModels(), returnedAddresses, "Address lists are not the same!");
    }

    @Test
    @DisplayName("Test addAddress - Success")
    void test_addAddress_success() {
        // Setup our mock
        Address mockAddress = new Address(1L, "Address Name");
        doReturn(mockAddress).when(repository).add(mockAddress);

        // Execute the service call
        Address returnedAddress = service.addAddress(mockAddress);

        // Assert the response
        Assertions.assertNotNull(returnedAddress, "Address shouldn't be null.");
        Assertions.assertSame(returnedAddress, mockAddress, "Addresses should be the same");
    }

    @Test
    @DisplayName("Test updateAddress - Success")
    void test_updateAddress_success() {
        // Setup our mock
        Address mockAddress = new Address(1L, "Address Name");
        doReturn(mockAddress).when(repository).update(mockAddress);

        // Execute the service call
        Address returnedAddress = service.updateAddress(mockAddress);

        // Assert the response
        Assertions.assertNotNull(returnedAddress, "Address shouldn't be null.");
        Assertions.assertSame(returnedAddress, mockAddress, "Addresses should be the same");
    }
}