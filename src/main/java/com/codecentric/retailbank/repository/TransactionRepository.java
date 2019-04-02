package com.codecentric.retailbank.repository;

import com.codecentric.retailbank.exception.nullpointer.ArgumentNullException;
import com.codecentric.retailbank.exception.nullpointer.InvalidOperationException;
import com.codecentric.retailbank.model.domain.BankAccount;
import com.codecentric.retailbank.model.domain.Merchant;
import com.codecentric.retailbank.model.domain.RefTransactionType;
import com.codecentric.retailbank.model.domain.Transaction;
import com.codecentric.retailbank.repository.configuration.DBType;
import com.codecentric.retailbank.repository.configuration.DBUtil;
import com.codecentric.retailbank.repository.helpers.JDBCRepositoryBase;
import com.codecentric.retailbank.repository.helpers.JDBCRepositoryUtilities;
import com.codecentric.retailbank.repository.helpers.ListPage;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TransactionRepository extends JDBCRepositoryUtilities implements JDBCRepositoryBase<Transaction, Long> {

    //region READ
    @Override public List<Transaction> all() {
        ResultSet resultSet = null;
        List<Transaction> transactions = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_allTransactions = conn.prepareCall("{call allTransactions()}")) {

            // Retrieve all transactions
            cs_allTransactions.execute();

            // Transform each ResultSet row into Transaction model and add to "transactions" list
            resultSet = cs_allTransactions.getResultSet();
            while (resultSet.next()) {

                BankAccount senderAccount = new BankAccount(
                        resultSet.getLong("transactions.sender_account_number")
                );

                BankAccount receiverAccount = new BankAccount(
                        resultSet.getLong("transactions.receiver_account_number")
                );

                Merchant merchant = new Merchant(
                        resultSet.getLong("transactions.merchant_id"),
                        resultSet.getString("merchants.merchant_details")
                );

                RefTransactionType refTransactionType = new RefTransactionType(
                        resultSet.getLong("ref_transaction_types.transaction_type_id"),
                        resultSet.getString("ref_transaction_types.transaction_type_code")
                );

                Transaction transaction = new Transaction(
                        resultSet.getLong("transactions.transaction_id"),
                        senderAccount,
                        receiverAccount,
                        merchant,
                        refTransactionType,
                        resultSet.getDate("transactions.transaction_date_time"),
                        resultSet.getBigDecimal("transactions.transaction_amount"),
                        resultSet.getString("transactions.other_details")
                );

                transactions.add(transaction);
            }

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            closeConnections(resultSet);
        }

        return transactions;
    }

    @Override public ListPage<Transaction> allRange(int pageIndex, int pageSize) {
        ResultSet resultSet = null;
        ListPage<Transaction> transactionListPage = new ListPage<>();

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_allTransactionsRange = conn.prepareCall("{call allTransactionsRange(?,?)}");
             CallableStatement cs_allTransactionsCount = conn.prepareCall("{call allTransactionsCount()}")) {

            // Retrieve all Transactions
            cs_allTransactionsRange.setInt(1, Math.abs(pageIndex * pageSize));
            cs_allTransactionsRange.setInt(2, Math.abs(pageSize));
            cs_allTransactionsRange.execute();

            // Transform each ResultSet row into Transaction model and add to "transactions" list
            resultSet = cs_allTransactionsRange.getResultSet();
            List<Transaction> transactions = new ArrayList<>();
            while (resultSet.next()) {
                BankAccount senderAccount = new BankAccount(
                        resultSet.getLong("transactions.sender_account_number")
                );

                BankAccount receiverAccount = new BankAccount(
                        resultSet.getLong("transactions.receiver_account_number")
                );

                Merchant merchant = new Merchant(
                        resultSet.getLong("transactions.merchant_id"),
                        resultSet.getString("merchants.merchant_details")
                );

                RefTransactionType refTransactionType = new RefTransactionType(
                        resultSet.getLong("ref_transaction_types.transaction_type_id"),
                        resultSet.getString("ref_transaction_types.transaction_type_code")
                );

                Transaction transaction = new Transaction(
                        resultSet.getLong("transactions.transaction_id"),
                        senderAccount,
                        receiverAccount,
                        merchant,
                        refTransactionType,
                        resultSet.getDate("transactions.transaction_date_time"),
                        resultSet.getBigDecimal("transactions.transaction_amount"),
                        resultSet.getString("transactions.other_details")
                );

                transactions.add(transaction);
            }

            // Get the total number of Transaction-s in DB
            cs_allTransactionsCount.execute();
            resultSet = cs_allTransactionsCount.getResultSet();
            while (resultSet.next())
                transactionListPage.setPageCount(resultSet.getLong(1), pageSize);

            // Add transactions to ListPage transfer object
            transactionListPage.setModels(transactions);

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            closeConnections(resultSet);
        }

        return transactionListPage;
    }

    @Override public Transaction single(Long id) {
        if (id == null)
            throw new ArgumentNullException("The id argument must have a value/cannot be null.");

        Transaction transaction = null;
        ResultSet resultSet = null;

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_singleTransaction = conn.prepareCall("{call singleTransaction(?)}")) {

            // Retrieve a single Transaction
            cs_singleTransaction.setLong(1, id);
            cs_singleTransaction.execute();

            // Transform ResultSet row into a Transaction model
            byte rowCounter = 0;
            resultSet = cs_singleTransaction.getResultSet();
            while (resultSet.next()) {

                // Check if more than one element matches id parameter
                ++rowCounter;
                if (rowCounter > 1)
                    throw new InvalidOperationException("The ResultSet does not contain exactly one row.");

                // Transform ResultSet row into a Transaction object
                BankAccount senderAccount = new BankAccount(
                        resultSet.getLong("transactions.sender_account_number")
                );

                BankAccount receiverAccount = new BankAccount(
                        resultSet.getLong("transactions.receiver_account_number")
                );


                Merchant merchant = new Merchant(
                        resultSet.getLong("transactions.merchant_id"),
                        resultSet.getString("merchants.merchant_details")
                );

                RefTransactionType refTransactionType = new RefTransactionType(
                        resultSet.getLong("ref_transaction_types.transaction_type_id"),
                        resultSet.getString("ref_transaction_types.transaction_type_code")
                );

                transaction = new Transaction(
                        resultSet.getLong("transactions.transaction_id"),
                        senderAccount,
                        receiverAccount,
                        merchant,
                        refTransactionType,
                        resultSet.getDate("transactions.transaction_date_time"),
                        resultSet.getBigDecimal("transactions.transaction_amount"),
                        resultSet.getString("transactions.other_details")
                );
            }
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            closeConnections(resultSet);
        }

        return transaction;
    }

    public Transaction singleByDetails(String details) throws ArgumentNullException, InvalidOperationException{
        if (details == null)
            throw new ArgumentNullException("The details argument must have a value/cannot be null.");

        Transaction transaction = null;
        ResultSet resultSet = null;

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_singleTransaction = conn.prepareCall("{call singleTransactionByDetails(?)}")) {

            // Retrieve a single Transaction
            cs_singleTransaction.setString(1, details);
            cs_singleTransaction.execute();

            // Transform ResultSet row into a Transaction model
            byte rowCounter = 0;
            resultSet = cs_singleTransaction.getResultSet();
            while (resultSet.next()) {

                // Transform ResultSet row into a Transaction object
                BankAccount senderAccount = new BankAccount(
                        resultSet.getLong("transactions.sender_account_number")
                );

                BankAccount receiverAccount = new BankAccount(
                        resultSet.getLong("transactions.receiver_account_number")
                );

                Merchant merchant = new Merchant(
                        resultSet.getLong("transactions.merchant_id"),
                        resultSet.getString("merchants.merchant_details")
                );

                RefTransactionType refTransactionType = new RefTransactionType(
                        resultSet.getLong("ref_transaction_types.transaction_type_id"),
                        resultSet.getString("ref_transaction_types.transaction_type_code")
                );

                transaction = new Transaction(
                        resultSet.getLong("transactions.transaction_id"),
                        senderAccount,
                        receiverAccount,
                        merchant,
                        refTransactionType,
                        resultSet.getDate("transactions.transaction_date_time"),
                        resultSet.getBigDecimal("transactions.transaction_amount"),
                        resultSet.getString("transactions.other_details")
                );

                // Check if more than one element matches details parameter
                ++rowCounter;
                if (rowCounter > 1)
                    throw new InvalidOperationException("The ResultSet does not contain exactly one row.", transaction);

            }
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            closeConnections(resultSet);
        }

        return transaction;
    }
    //endregion

    //region WRITE
    public Transaction createPayment(Transaction model){
        if (model == null)
            throw new ArgumentNullException("The model argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_addTransaction = conn.prepareCall("{call create_payment(?,?,?,?,?)}")) {

            // Create new payment
            cs_addTransaction.setLong("p_sender_account_number", model.getSenderAccount().getId());
            cs_addTransaction.setLong("p_receiver_account_number", model.getReceiverAccount().getId());
            cs_addTransaction.setLong("p_merchant_id", model.getMerchant().getId());
            cs_addTransaction.setBigDecimal("p_transaction_amount", model.getAmount());
            cs_addTransaction.setString("p_other_details", model.getDetails());
            cs_addTransaction.execute();

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }

        return model;
    }
    //endregion


    //region DELETE
    @Override public void delete(Transaction model) {
        if (model == null)
            throw new ArgumentNullException("The model argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_deleteTransaction = conn.prepareCall("{call deleteTransaction(?)}")) {

            // Delete an existing Transaction
            cs_deleteTransaction.setLong(1, model.getId());
            cs_deleteTransaction.execute();

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }

    @Override public void deleteById(Long id) {
        if (id == null)
            throw new ArgumentNullException("The id argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_deleteTransaction = conn.prepareCall("{call deleteTransaction(?)}")) {

            // Delete an existing Transaction
            cs_deleteTransaction.setLong(1, id);
            cs_deleteTransaction.execute();

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }

    @Override public void deleteBatch(Iterable<Transaction> models) {
        if (models == null)
            throw new ArgumentNullException("The models argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_deleteTransactions = conn.prepareCall("{call deleteTransactions(?)}")) {

            // Add calls to batch
            for (Transaction model : models) {
                try {
                    cs_deleteTransactions.setLong(1, model.getId());
                    cs_deleteTransactions.addBatch();
                } catch (SQLException ex) {
                    DBUtil.showErrorMessage(ex);
                }
            }

            // Execute batch!
            cs_deleteTransactions.executeBatch();
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }

    @Override public void deleteBatchByIds(Iterable<Long> ids) {
        if (ids == null)
            throw new ArgumentNullException("The ids argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_deleteTransactions = conn.prepareCall("{call deleteTransactions(?)}")) {

            // Add calls to batch
            for (Long id : ids) {
                try {
                    cs_deleteTransactions.setLong(1, id);
                    cs_deleteTransactions.addBatch();
                } catch (SQLException ex) {
                    DBUtil.showErrorMessage(ex);
                }
            }

            // Execute batch!
            cs_deleteTransactions.executeBatch();
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }
    //endregion

    //region Unused Overrides
    /**
     * Use <code>createPayment()</code> instead of this!
     */
    @Deprecated
    @Override
    public Transaction add(Transaction model) {
        return null;
    }

    /**
     * Use <code>createPayment()</code> instead of this!
     */
    @Deprecated
    @Override public Transaction update(Transaction model) {
        return null;
    }

    /**
     * Use <code>createPayment()</code> instead of this!
     */
    @Deprecated
    @Override public void insertBatch(Iterable<Transaction> models) { }

    /**
     * Use <code>createPayment()</code> instead of this!
     */
    @Deprecated
    @Override public void updateBatch(Iterable<Transaction> models) { }
    //endregion

}
