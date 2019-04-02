package com.codecentric.retailbank.service;

import com.codecentric.retailbank.model.domain.BankAccount;
import com.codecentric.retailbank.model.domain.Merchant;
import com.codecentric.retailbank.model.domain.RefTransactionType;
import com.codecentric.retailbank.model.domain.Transaction;
import com.codecentric.retailbank.repository.TransactionRepository;
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
class TransactionServiceMockTest {

    @Autowired
    private TransactionService service;

    @MockBean
    private TransactionRepository repository;


    @Test
    @DisplayName("Test getById - Success")
    void test_GetById_success() {
        // Setup our mock
        BankAccount sender = new BankAccount();
        BankAccount receiver = new BankAccount();
        Merchant merchant = new Merchant(1L);
        RefTransactionType type = new RefTransactionType();

        Transaction mockTransaction = new Transaction(1L,sender, receiver, merchant, type, null, BigDecimal.valueOf(1L), "Transaction Name");
        doReturn(mockTransaction).when(repository).single(1L);

        // Execute the service call
        Transaction returnedTransaction = service.getById(1L);

        // Assert the response
        Assertions.assertNotNull(returnedTransaction, "Transaction wasn't found.");
        Assertions.assertSame(returnedTransaction, mockTransaction, "Transactions should be the same.");
    }

    @Test
    @DisplayName("Test getByDetails - Success")
    void test_GetByDetails_success() {
        // Setup our mock
        BankAccount sender = new BankAccount();
        BankAccount receiver = new BankAccount();
        Merchant merchant = new Merchant(1L);
        RefTransactionType type = new RefTransactionType();

        Transaction mockTransaction = new Transaction(1L,sender, receiver, merchant, type, null, BigDecimal.valueOf(1L), "Transaction Name");
        doReturn(mockTransaction).when(repository).singleByDetails("Transaction Name");

        // Execute the service call
        Transaction returnedTransaction = service.getByDetails("Transaction Name");

        // Assert the response
        Assertions.assertNotNull(returnedTransaction, "Transaction wasn't found.");
        Assertions.assertSame(returnedTransaction, mockTransaction, "Transactions should be the same");
    }


    @Test
    @DisplayName("Test getAll - Success")
    void test_GetAll_success() {
        // Setup our mock
        BankAccount sender = new BankAccount();
        BankAccount receiver = new BankAccount();
        Merchant merchant = new Merchant(1L);
        RefTransactionType type = new RefTransactionType();

        List<Transaction> mockTransactions = new ArrayList<>();
        mockTransactions.add(new Transaction(1L,sender, receiver, merchant, type, null, BigDecimal.valueOf(1L), "Transaction 1"));
        mockTransactions.add(new Transaction(2L,sender, receiver, merchant, type, null, BigDecimal.valueOf(1L), "Transaction 2"));
        mockTransactions.add(new Transaction(3L,sender, receiver, merchant, type, null, BigDecimal.valueOf(1L), "Transaction 3"));
        doReturn(mockTransactions).when(repository).all();

        // Execute the service call
        List<Transaction> returnedTransactions = service.getAllTransactions();

        // Assert the response
        Assertions.assertIterableEquals(mockTransactions, returnedTransactions, "Transaction lists are not the same!");
    }

    @Test
    @DisplayName("Test getAllTransactionsByPage - Success")
    void test_GetAllTransactionsByPage_success() {
        // Setup our mock
        BankAccount sender = new BankAccount();
        BankAccount receiver = new BankAccount();
        Merchant merchant = new Merchant(1L);
        RefTransactionType type = new RefTransactionType();

        Transaction mockTransaction1 = new Transaction(1L,sender, receiver, merchant, type, null, BigDecimal.valueOf(1L), "Transaction 1");
        Transaction mockTransaction2 = new Transaction(1L,sender, receiver, merchant, type, null, BigDecimal.valueOf(1L), "Transaction 2");
        Transaction mockTransaction3 = new Transaction(1L,sender, receiver, merchant, type, null, BigDecimal.valueOf(1L), "Transaction 3");
        Transaction mockTransaction4 = new Transaction(1L,sender, receiver, merchant, type, null, BigDecimal.valueOf(1L), "Transaction 4");

        ListPage<Transaction> secondPageMock = new ListPage<>();
        List<Transaction> bankModels = new ArrayList<>();
        bankModels.add(mockTransaction3);
        bankModels.add(mockTransaction4);
        secondPageMock.setModels(bankModels);

        doReturn(secondPageMock).when(repository).allRange(1, 2);

        // Execute the service call
        List<Transaction> returnedTransactions = service.getAllTransactions(1, 2).getModels();

        // Assert the response
        Assertions.assertIterableEquals(secondPageMock.getModels(), returnedTransactions, "Transaction lists are not the same!");
    }

    @Test
    @DisplayName("Test addTransaction - Success")
    void test_addTransaction_success() {
        // Setup our mock
        BankAccount sender = new BankAccount();
        BankAccount receiver = new BankAccount();
        Merchant merchant = new Merchant(1L);
        RefTransactionType type = new RefTransactionType();

        Transaction mockTransaction = new Transaction(1L, sender, receiver, merchant, type, null, BigDecimal.valueOf(1L), "Transaction Name");
        doReturn(mockTransaction).when(repository).createPayment(mockTransaction);

        // Execute the service call
        Transaction returnedTransaction = service.createPayment(mockTransaction);

        // Assert the response
        Assertions.assertNotNull(returnedTransaction, "Transaction shouldn't be null.");
        Assertions.assertSame(returnedTransaction, mockTransaction, "Transactions should be the same");
    }

}