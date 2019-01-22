package com.codecentric.retailbank.repository;

import com.codecentric.retailbank.exception.nullpointer.ArgumentNullException;
import com.codecentric.retailbank.exception.nullpointer.InvalidOperationException;
import com.codecentric.retailbank.model.domain.BankAccount;
import com.codecentric.retailbank.model.domain.Customer;
import com.codecentric.retailbank.model.domain.RefAccountStatus;
import com.codecentric.retailbank.model.domain.RefAccountType;
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
public class BankAccountRepository extends JDBCRepositoryUtilities implements JDBCRepositoryBase<BankAccount, Long> {

    //region READ
    @Override public List<BankAccount> all() {
        ResultSet resultSet = null;
        List<BankAccount> bankAccounts = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_allBankAccounts = conn.prepareCall("{call allAccounts()}")) {

            // Retrieve all bankAccounts
            cs_allBankAccounts.execute();

            // Transform each ResultSet row into BankAccount model and add to "bankAccounts" list
            resultSet = cs_allBankAccounts.getResultSet();
            while (resultSet.next()) {

                RefAccountStatus refAccountStatus = new RefAccountStatus(
                        resultSet.getLong("accounts.account_status_id"),
                        resultSet.getString("ref_account_status.account_status_code")
                );

                RefAccountType refAccountType = new RefAccountType(
                        resultSet.getLong("accounts.account_type_id"),
                        resultSet.getString("ref_account_types.account_type_code")
                );

                Customer customer = new Customer(
                        resultSet.getLong("accounts.customer_id"),
                        resultSet.getString("customers.personal_details")
                );

                BankAccount bankAccount = new BankAccount(
                        resultSet.getLong("accounts.account_number"),
                        refAccountStatus,
                        refAccountType,
                        customer,
                        resultSet.getBigDecimal("accounts.current_balance"),
                        resultSet.getString("accounts.other_details")
                );

                bankAccounts.add(bankAccount);
            }

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            closeConnections(resultSet);
        }

        return bankAccounts;
    }

    @Override public ListPage<BankAccount> allRange(int pageIndex, int pageSize) {
        ResultSet resultSet = null;
        ListPage<BankAccount> bankAccountListPage = new ListPage<>();

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_allBankAccountsRange = conn.prepareCall("{call allAccountsRange(?,?)}");
             CallableStatement cs_allBankAccountsCount = conn.prepareCall("{call allAccountsCount()}")) {

            // Retrieve all BankAccounts
            cs_allBankAccountsRange.setInt(1, Math.abs(pageIndex * pageSize));
            cs_allBankAccountsRange.setInt(2, Math.abs(pageSize));
            cs_allBankAccountsRange.execute();

            // Transform each ResultSet row into BankAccount model and add to "bankAccounts" list
            resultSet = cs_allBankAccountsRange.getResultSet();
            List<BankAccount> bankAccounts = new ArrayList<>();
            while (resultSet.next()) {
                RefAccountStatus refAccountStatus = new RefAccountStatus(
                        resultSet.getLong("accounts.account_status_id"),
                        resultSet.getString("ref_account_status.account_status_code")
                );

                RefAccountType refAccountType = new RefAccountType(
                        resultSet.getLong("accounts.account_type_id"),
                        resultSet.getString("ref_account_types.account_type_code")
                );

                Customer customer = new Customer(
                        resultSet.getLong("accounts.customer_id"),
                        resultSet.getString("customers.personal_details")
                );

                BankAccount bankAccount = new BankAccount(
                        resultSet.getLong("accounts.account_number"),
                        refAccountStatus,
                        refAccountType,
                        customer,
                        resultSet.getBigDecimal("accounts.current_balance"),
                        resultSet.getString("accounts.other_details")
                );

                bankAccounts.add(bankAccount);
            }

            // Get the total number of BankAccount-s in DB
            cs_allBankAccountsCount.execute();
            resultSet = cs_allBankAccountsCount.getResultSet();
            while (resultSet.next())
                bankAccountListPage.setPageCount(resultSet.getLong(1), pageSize);

            // Add bankAccounts to ListPage transfer object
            bankAccountListPage.setModels(bankAccounts);

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            closeConnections(resultSet);
        }

        return bankAccountListPage;
    }

    @Override public BankAccount single(Long id) {
        if (id == null)
            throw new ArgumentNullException("The id argument must have a value/cannot be null.");

        BankAccount bankAccount = null;
        ResultSet resultSet = null;

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_singleBankAccount = conn.prepareCall("{call singleAccount(?)}")) {

            // Retrieve a single BankAccount
            cs_singleBankAccount.setLong(1, id);
            cs_singleBankAccount.execute();

            // Transform ResultSet row into a BankAccount model
            byte rowCounter = 0;
            resultSet = cs_singleBankAccount.getResultSet();
            while (resultSet.next()) {

                // Check if more than one element matches id parameter
                ++rowCounter;
                if (rowCounter > 1)
                    throw new InvalidOperationException("The ResultSet does not contain exactly one row.");

                // Transform ResultSet row into a BankAccount object
                RefAccountStatus refAccountStatus = new RefAccountStatus(
                        resultSet.getLong("accounts.account_status_id"),
                        resultSet.getString("ref_account_status.account_status_code")
                );

                RefAccountType refAccountType = new RefAccountType(
                        resultSet.getLong("accounts.account_type_id"),
                        resultSet.getString("ref_account_types.account_type_code")
                );

                Customer customer = new Customer(
                        resultSet.getLong("accounts.customer_id"),
                        resultSet.getString("customers.personal_details")
                );

                bankAccount = new BankAccount(
                        resultSet.getLong("accounts.account_number"),
                        refAccountStatus,
                        refAccountType,
                        customer,
                        resultSet.getBigDecimal("accounts.current_balance"),
                        resultSet.getString("accounts.other_details")
                );
            }
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            closeConnections(resultSet);
        }

        return bankAccount;
    }

    public BankAccount getSingleByDetails(String details) {
        if (details == null)
            throw new ArgumentNullException("The details argument must have a value/cannot be null.");

        BankAccount bankAccount = null;
        ResultSet resultSet = null;

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_singleBankAccount = conn.prepareCall("{call singleBankAccountByDetails(?)}")) {

            // Retrieve a single BankAccount
            cs_singleBankAccount.setString(1, details);
            cs_singleBankAccount.execute();

            // Transform ResultSet row into a BankAccount model
            byte rowCounter = 0;
            resultSet = cs_singleBankAccount.getResultSet();
            while (resultSet.next()) {

                // Check if more than one element matches details parameter
                ++rowCounter;
                if (rowCounter > 1)
                    throw new InvalidOperationException("The ResultSet does not contain exactly one row.");

                // Transform ResultSet row into a BankAccount object
                RefAccountStatus refAccountStatus = new RefAccountStatus(
                        resultSet.getLong("ref_account_status.account_status_id"),
                        resultSet.getString("ref_account_status.account_status_code")
                );

                RefAccountType refAccountType = new RefAccountType(
                        resultSet.getLong("ref_account_types.account_type_id"),
                        resultSet.getString("ref_account_types.account_type_code")
                );

                Customer customer = new Customer(
                        resultSet.getLong("customers.customer_id"),
                        resultSet.getString("customers.personal_details")
                );

                bankAccount = new BankAccount(
                        resultSet.getLong("accounts.account_number"),
                        refAccountStatus,
                        refAccountType,
                        customer,
                        resultSet.getBigDecimal("accounts.current_balance"),
                        resultSet.getString("accounts.other_details")
                );
            }
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            closeConnections(resultSet);
        }

        return bankAccount;
    }
    //endregion

    //region WRITE
    @Override public BankAccount add(BankAccount model) {
        if (model == null)
            throw new ArgumentNullException("The model argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_addBankAccount = conn.prepareCall("{call addAccount(?,?,?,?,?)}")) {

            // Add a new BankAccount to DB
            cs_addBankAccount.setLong("p_account_status_id", model.getStatus().getId());
            cs_addBankAccount.setLong("p_account_type_id", model.getType().getId());
            cs_addBankAccount.setLong("p_customer_id", model.getCustomer().getId());
            cs_addBankAccount.setBigDecimal("p_current_balance", model.getBalance());
            cs_addBankAccount.setString("p_other_details", model.getDetails());
            cs_addBankAccount.execute();

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }

        return model;
    }

    @Override public BankAccount update(BankAccount model) {
        if (model == null)
            throw new ArgumentNullException("The model argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_updateBankAccount = conn.prepareCall("{call updateAccount(?,?,?,?,?,?)}")) {

            // Update an existing BankAccount
            cs_updateBankAccount.setLong("p_account_number", model.getId());
            cs_updateBankAccount.setLong("p_account_status_id", model.getStatus().getId());
            cs_updateBankAccount.setLong("p_account_type_id", model.getType().getId());
            cs_updateBankAccount.setLong("p_customer_id", model.getCustomer().getId());
            cs_updateBankAccount.setBigDecimal("p_current_balance", model.getBalance());
            cs_updateBankAccount.setString("p_other_details", model.getDetails());
            cs_updateBankAccount.execute();

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }

        return model;
    }

    @Override public void insertBatch(Iterable<BankAccount> models) {
        if (models == null)
            throw new ArgumentNullException("The models argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_addBankAccount = conn.prepareCall("{call addAccount(?,?,?,?,?)}")) {

            // Add calls to batch
            for (BankAccount model : models) {
                try {
                    cs_addBankAccount.setLong("p_account_status_id", model.getStatus().getId());
                    cs_addBankAccount.setLong("p_account_type_id", model.getType().getId());
                    cs_addBankAccount.setLong("p_customer_id", model.getCustomer().getId());
                    cs_addBankAccount.setBigDecimal("p_current_balance", model.getBalance());
                    cs_addBankAccount.setString("p_other_details", model.getDetails());
                    cs_addBankAccount.addBatch();
                } catch (SQLException ex) {
                    DBUtil.showErrorMessage(ex);
                }
            }

            // Execute batch!
            cs_addBankAccount.executeBatch();
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }

    @Override public void updateBatch(Iterable<BankAccount> models) {
        if (models == null)
            throw new ArgumentNullException("The models argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_updateBankAccount = conn.prepareCall("{call updateAccount(?,?,?,?,?,?)}")) {

            // Add calls to batch
            for (BankAccount model : models) {
                try {
                    cs_updateBankAccount.setLong("p_account_number", model.getId());
                    cs_updateBankAccount.setLong("p_account_status_id", model.getStatus().getId());
                    cs_updateBankAccount.setLong("p_account_type_id", model.getType().getId());
                    cs_updateBankAccount.setLong("p_customer_id", model.getCustomer().getId());
                    cs_updateBankAccount.setBigDecimal("p_current_balance", model.getBalance());
                    cs_updateBankAccount.setString("p_other_details", model.getDetails());
                    cs_updateBankAccount.addBatch();
                } catch (SQLException ex) {
                    DBUtil.showErrorMessage(ex);
                }
            }

            // Execute batch!
            cs_updateBankAccount.executeBatch();
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }
    //endregion

    //region DELETE
    @Override public void delete(BankAccount model) {
        if (model == null)
            throw new ArgumentNullException("The model argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_deleteBankAccount = conn.prepareCall("{call deleteAccount(?)}")) {

            // Delete an existing BankAccount
            cs_deleteBankAccount.setLong(1, model.getId());
            cs_deleteBankAccount.execute();

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }

    @Override public void deleteById(Long id) {
        if (id == null)
            throw new ArgumentNullException("The id argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_deleteBankAccount = conn.prepareCall("{call deleteAccount(?)}")) {

            // Delete an existing BankAccount
            cs_deleteBankAccount.setLong(1, id);
            cs_deleteBankAccount.execute();

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }

    @Override public void deleteBatch(Iterable<BankAccount> models) {
        if (models == null)
            throw new ArgumentNullException("The models argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_deleteBankAccounts = conn.prepareCall("{call deleteAccounts(?)}")) {

            // Add calls to batch
            for (BankAccount model : models) {
                try {
                    cs_deleteBankAccounts.setLong(1, model.getId());
                    cs_deleteBankAccounts.addBatch();
                } catch (SQLException ex) {
                    DBUtil.showErrorMessage(ex);
                }
            }

            // Execute batch!
            cs_deleteBankAccounts.executeBatch();
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }

    @Override public void deleteBatchByIds(Iterable<Long> ids) {
        if (ids == null)
            throw new ArgumentNullException("The ids argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_deleteBankAccounts = conn.prepareCall("{call deleteAccounts(?)}")) {

            // Add calls to batch
            for (Long id : ids) {
                try {
                    cs_deleteBankAccounts.setLong(1, id);
                    cs_deleteBankAccounts.addBatch();
                } catch (SQLException ex) {
                    DBUtil.showErrorMessage(ex);
                }
            }

            // Execute batch!
            cs_deleteBankAccounts.executeBatch();
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }
    //endregion
}
