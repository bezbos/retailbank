package com.codecentric.retailbank.service;

import com.codecentric.retailbank.model.domain.Customer;
import com.codecentric.retailbank.repository.CustomerRepository;
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
class CustomerServiceMockTest {

    @Autowired
    private CustomerService service;

    @MockBean
    private CustomerRepository repository;


    @Test
    @DisplayName("Test getById - Success")
    void test_GetById_success() {
        // Setup our mock
        Customer mockCustomer = new Customer(1L, "Customer Name");
        doReturn(mockCustomer).when(repository).single(1L);

        // Execute the service call
        Customer returnedCustomer = service.getById(1L);

        // Assert the response
        Assertions.assertNotNull(returnedCustomer, "Customer wasn't found.");
        Assertions.assertSame(returnedCustomer, mockCustomer, "Customers should be the same.");
    }

    @Test
    @DisplayName("Test getByDetails - Success")
    void test_GetByDetails_success() {
        // Setup our mock
        Customer mockCustomer = new Customer(1L, "Customer Name");
        doReturn(mockCustomer).when(repository).getSingleByPersonalDetails("Customer Name");

        // Execute the service call
        Customer returnedCustomer = service.getByPersonalDetails("Customer Name");

        // Assert the response
        Assertions.assertNotNull(returnedCustomer, "Customer wasn't found.");
        Assertions.assertSame(returnedCustomer, mockCustomer, "Customers should be the same");
    }


    @Test
    @DisplayName("Test getAll - Success")
    void test_GetAll_success() {
        // Setup our mock
        List<Customer> mockCustomers = new ArrayList<>();
        mockCustomers.add(new Customer(1L, "Customer 1"));
        mockCustomers.add(new Customer(2L, "Customer 2"));
        mockCustomers.add(new Customer(3L, "Customer 3"));
        doReturn(mockCustomers).when(repository).all();

        // Execute the service call
        List<Customer> returnedCustomers = service.getAllCustomers();

        // Assert the response
        Assertions.assertIterableEquals(mockCustomers, returnedCustomers, "Customer lists are not the same!");
    }

    @Test
    @DisplayName("Test getAllByDetails - Success")
    void test_GetAllByDetails_success() {
        // Setup our mock
        Customer mockCustomer1 = new Customer(1L, "Customer 1");
        Customer mockCustomer2 = new Customer(1L, "Customer 2");
        Customer mockCustomer3 = new Customer(1L, "Customer 3");

        List<Customer> mockCustomerList = new ArrayList<>();
        mockCustomerList.add(mockCustomer1);
        mockCustomerList.add(mockCustomer2);
        mockCustomerList.add(mockCustomer3);
        doReturn(mockCustomerList).when(repository).allByPersonalDetails("Customer");

        // Execute the service call
        List<Customer> returnedCustomers = service.getAllByPersonalDetails("Customer");

        // Assert the response
        Assertions.assertIterableEquals(mockCustomerList, returnedCustomers, "Customer lists are not the same.");
        Assertions.assertTrue(returnedCustomers.contains(mockCustomer1), "Returned list doesn't contain mockCustomer1.");
        Assertions.assertTrue(returnedCustomers.contains(mockCustomer2), "Returned list doesn't contain mockCustomer2.");
        Assertions.assertTrue(returnedCustomers.contains(mockCustomer3), "Returned list doesn't contain mockCustomer3.");
    }


    @Test
    @DisplayName("Test getAllCustomersByPage - Success")
    void test_GetAllCustomersByPage_success() {
        // Setup our mock
        Customer mockCustomer1 = new Customer(1L, "Customer 1");
        Customer mockCustomer2 = new Customer(1L, "Customer 2");
        Customer mockCustomer3 = new Customer(1L, "Customer 3");
        Customer mockCustomer4 = new Customer(1L, "Customer 4");

        ListPage<Customer> secondPageMock = new ListPage<>();
        List<Customer> customerModels = new ArrayList<>();
        customerModels.add(mockCustomer3);
        customerModels.add(mockCustomer4);
        secondPageMock.setModels(customerModels);

        doReturn(secondPageMock).when(repository).allRange(1, 2);

        // Execute the service call
        List<Customer> returnedCustomers = service.getAllCustomers(1, 2).getModels();

        // Assert the response
        Assertions.assertIterableEquals(secondPageMock.getModels(), returnedCustomers, "Customer lists are not the same!");
    }

    @Test
    @DisplayName("Test addCustomer - Success")
    void test_addCustomer_success() {
        // Setup our mock
        Customer mockCustomer = new Customer(1L, "Customer Name");
        doReturn(mockCustomer).when(repository).add(mockCustomer);

        // Execute the service call
        Customer returnedCustomer = service.addCustomer(mockCustomer);

        // Assert the response
        Assertions.assertNotNull(returnedCustomer, "Customer shouldn't be null.");
        Assertions.assertSame(returnedCustomer, mockCustomer, "Customers should be the same");
    }

    @Test
    @DisplayName("Test updateCustomer - Success")
    void test_updateCustomer_success() {
        // Setup our mock
        Customer mockCustomer = new Customer(1L, "Customer Name");
        doReturn(mockCustomer).when(repository).update(mockCustomer);

        // Execute the service call
        Customer returnedCustomer = service.updateCustomer(mockCustomer);

        // Assert the response
        Assertions.assertNotNull(returnedCustomer, "Customer shouldn't be null.");
        Assertions.assertSame(returnedCustomer, mockCustomer, "Customers should be the same");
    }
}