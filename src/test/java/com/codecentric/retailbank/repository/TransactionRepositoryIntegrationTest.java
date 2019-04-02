package com.codecentric.retailbank.repository;

import com.codecentric.retailbank.model.domain.BankAccount;
import com.codecentric.retailbank.model.domain.Merchant;
import com.codecentric.retailbank.model.domain.RefTransactionType;
import com.codecentric.retailbank.model.domain.Transaction;
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
class TransactionRepositoryIntegrationTest {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private RefTransactionTypeRepository typeRepository;

    @Test
    @DisplayName("Test allTransactions - Success")
    void test_allTransactions_success() {
        // Execute the transactionRepository call
        List<Transaction> returnedTransactions = transactionRepository.all();

        // Assert the response
        Assertions.assertTrue(returnedTransactions.size() > 0, "Returned list shouldn't be empty.");
    }

    @Test
    @DisplayName("Test singleTransaction - Success")
    void test_singleTransaction_success() {
        // Execute the transactionRepository call
        Transaction returnedTransaction = transactionRepository.single(1L);

        // Assert the response
        Assertions.assertNotNull(returnedTransaction, "Returned transaction shouldn't be null.");
    }

    @Test
    @DisplayName("Test createPayment - Success")
    void test_createPayment_success() {
        // Prepare the arguments
        BankAccount sender = bankAccountRepository.single(1L);
        BankAccount receiver = bankAccountRepository.single(2L);
        Merchant merchant = merchantRepository.single(1L);
        RefTransactionType type = typeRepository.single(1L);

        // Prepare the transaction argument
        Transaction transaction = new Transaction(1L, sender, receiver, merchant, type, null, BigDecimal.valueOf(1L), "TRANSACTION INSERT - REPO INTEGRATION TEST");

        // Execute the transactionRepository call
        transactionRepository.createPayment(transaction);

        // Retrieve the newly created transaction
        Transaction newlyCreatedTransaction = transactionRepository.singleByDetails("TRANSACTION INSERT - REPO INTEGRATION TEST");

        // Assert the response
        Assertions.assertNotNull(newlyCreatedTransaction, "Returned transaction shouldn't be null.");
    }
}