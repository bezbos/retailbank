package com.codecentric.retailbank.repository.JDBC;

import com.codecentric.retailbank.model.domain.Bank;
import com.codecentric.retailbank.repository.JDBC.configuration.DBType;
import com.codecentric.retailbank.repository.JDBC.configuration.DBUtil;
import com.codecentric.retailbank.repository.JDBC.exceptions.ArgumentNullException;
import com.codecentric.retailbank.repository.JDBC.exceptions.InvalidOperationException;
import com.codecentric.retailbank.repository.JDBC.exceptions.SourceCollectionIsEmptyException;
import com.codecentric.retailbank.repository.JDBC.helpers.JDBCRepositoryBase;
import com.codecentric.retailbank.repository.JDBC.helpers.JDBCRepositoryUtilities;
import com.codecentric.retailbank.repository.JDBC.wrappers.ListPage;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BankRepositoryJDBC extends JDBCRepositoryUtilities implements JDBCRepositoryBase<Bank, Long> {

    @Override public List<Bank> findAllOrDefault() {
        ResultSet resultSet = null;
        List<Bank> banks = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement csAllBanks = conn.prepareCall("{call allBanks()}")) {

            // Retrieve findAll banks
            csAllBanks.execute();

            // Transform each ResultSet row into Bank model and add to "banks" list
            resultSet = csAllBanks.getResultSet();
            while (resultSet.next()) {
                banks.add(
                        new Bank(resultSet.getLong(1), resultSet.getString(2))
                );
            }

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            closeConnections(resultSet);
        }

        return banks.size() < 1 ? null : banks;
    }

    @Override public List<Bank> findAll() {
        ResultSet resultSet = null;
        List<Bank> banks = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement csAllBanks = conn.prepareCall("{call allBanks()}")) {

            // Retrieve findAll banks
            csAllBanks.execute();

            // Transform each ResultSet row into Bank model and add to "banks" list
            resultSet = csAllBanks.getResultSet();
            byte rowCounter = 0;
            while (resultSet.next()) {
                ++rowCounter;
                banks.add(
                        new Bank(resultSet.getLong(1), resultSet.getString(2))
                );
            }

            if (rowCounter < 1)
                throw new SourceCollectionIsEmptyException("The ResultSet does contain not any rows.");

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            closeConnections(resultSet);
        }

        return banks;
    }


    @Override public ListPage<Bank> findAllRangeOrDefault(int pageIndex, int pageSize) {
        ResultSet resultSet = null;
        ListPage<Bank> bankListPage = new ListPage<>();

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement csAllBanksRange = conn.prepareCall("{call allBanksRange(?,?)}");
             CallableStatement csBanksCount = conn.prepareCall("{call allBanksCount()}")) {

            // Retrieve banks in a certain range
            csAllBanksRange.setInt(1, Math.abs(pageIndex * pageSize));
            csAllBanksRange.setInt(2, Math.abs(pageSize));
            csAllBanksRange.execute();

            // Transform ResultSet rows into Bank models and add them into the bankListPage
            List<Bank> banksList = new ArrayList<>();
            resultSet = csAllBanksRange.getResultSet();
            while (resultSet.next()) {
                banksList.add(
                        new Bank(resultSet.getLong(1), resultSet.getString(2))
                );
            }

            // Get the total number of banks in DB
            csBanksCount.execute();
            resultSet = csBanksCount.getResultSet();
            while (resultSet.next())
                bankListPage.setPageCount(resultSet.getLong(1), pageSize);

            // Add banks to ListPage transfer object
            bankListPage.setModels(banksList);

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            closeConnections(resultSet);
        }

        return bankListPage.getModels().size() < 1 ? null : bankListPage;
    }

    @Override public ListPage<Bank> findAllRange(int pageIndex, int pageSize) {
        ResultSet resultSet = null;
        ListPage<Bank> bankListPage = new ListPage<>();

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement csAllBanksRange = conn.prepareCall("{call allBanksRange(?,?)}");
             CallableStatement csBanksCount = conn.prepareCall("{call allBanksCount()}")) {

            // Retrieve banks in a certain range
            csAllBanksRange.setInt(1, Math.abs(pageIndex * pageSize));
            csAllBanksRange.setInt(2, Math.abs(pageSize));
            csAllBanksRange.execute();

            // Transform ResultSet rows into Bank models and add them into the bankListPage
            resultSet = csAllBanksRange.getResultSet();
            List<Bank> banksList = new ArrayList<>();
            byte rowCounter = 0;
            while (resultSet.next()) {
                ++rowCounter;
                banksList.add(
                        new Bank(resultSet.getLong(1), resultSet.getString(2))
                );
            }
            if (rowCounter < 1)
                throw new SourceCollectionIsEmptyException("The ResultSet does contain not any rows.");

            // Get the total number of banks in DB
            csBanksCount.execute();
            resultSet = csBanksCount.getResultSet();
            rowCounter = 0;
            while (resultSet.next()) {
                ++rowCounter;
                bankListPage.setPageCount(resultSet.getLong(1), pageSize);
            }
            if (rowCounter < 1)
                throw new SourceCollectionIsEmptyException("The ResultSet does contain not any rows.");

            // Add banks to ListPage transfer object
            bankListPage.setModels(banksList);

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            closeConnections(resultSet);
        }

        return bankListPage;
    }


    public List<Bank> findAllByDetailsOrDefault(String details) {
        if (details == null)
            throw new ArgumentNullException("The details argument must have a value/cannot be null.");

        ResultSet resultSet = null;
        List<Bank> banks = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement callableStatement = conn.prepareCall("{call allBanksByDetails(?)}")) {

            // Retrieve findAll banks
            callableStatement.setString(1, details);
            callableStatement.execute();

            // Transform each ResultSet row into Bank model and add to "banks" list
            resultSet = callableStatement.getResultSet();
            while (resultSet.next()) {
                banks.add(
                        new Bank(resultSet.getLong(1), resultSet.getString(2))
                );
            }

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            closeConnections(resultSet);
        }

        return banks.size() < 1 ? null : banks;
    }

    public List<Bank> findAllByDetails(String details) {
        if (details == null)
            throw new ArgumentNullException("The details argument must have a value/cannot be null.");

        ResultSet resultSet = null;
        List<Bank> banks = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement callableStatement = conn.prepareCall("{call allBanksByDetails(?)}")) {

            // Retrieve findAll banks
            callableStatement.setString(1, details);
            callableStatement.execute();

            // Transform each ResultSet row into Bank model and add to "banks" list
            resultSet = callableStatement.getResultSet();
            byte rowCounter = 0;
            while (resultSet.next()) {
                banks.add(
                        new Bank(resultSet.getLong(1), resultSet.getString(2))
                );
            }

            if (rowCounter < 1)
                throw new SourceCollectionIsEmptyException("The ResultSet does contain not any rows.");

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            closeConnections(resultSet);
        }

        return banks;
    }


    @Override public Bank getSingleOrDefault(Long id) {
        if (id == null)
            throw new ArgumentNullException("The id argument must have a value/cannot be null.");

        Bank bank = null;
        ResultSet resultSet = null;

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement csSingleBank = conn.prepareCall("{call singleBank(?)}")) {

            // Retrieve a getSingle bank
            csSingleBank.setLong(1, id);
            csSingleBank.execute();

            // Transform ResultSet row into a Bank model
            resultSet = csSingleBank.getResultSet();
            byte rowCounter = 0;
            while (resultSet.next()) {

                // Check if more than one element matches id parameter
                ++rowCounter;
                if (rowCounter > 1)
                    throw new InvalidOperationException("The ResultSet does not contain exactly one row.");

                // Transform ResultSet row into a Bank object
                bank = new Bank(resultSet.getLong(1), resultSet.getString(2));
            }
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            closeConnections(resultSet);
        }

        return bank;
    }

    @Override public Bank getSingle(Long id) {
        if (id == null)
            throw new ArgumentNullException("The id argument must have a value/cannot be null.");

        Bank bank = null;
        ResultSet resultSet = null;

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement csSingleBank = conn.prepareCall("{call singleBank(?)}")) {

            // Retrieve a getSingle bank
            csSingleBank.setLong(1, id);
            csSingleBank.execute();

            // Transform ResultSet row into a Bank model
            resultSet = csSingleBank.getResultSet();
            byte rowCounter = 0;
            while (resultSet.next()) {

                // Check if more than one element matches id parameter
                ++rowCounter;
                if (rowCounter > 1)
                    throw new InvalidOperationException("The ResultSet does not contain exactly one row.");

                // Transform ResultSet row into a Bank object
                bank = new Bank(resultSet.getLong(1), resultSet.getString(2));
            }

            if (rowCounter < 1)
                throw new SourceCollectionIsEmptyException("The ResultSet does not contain any rows.");

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            closeConnections(resultSet);
        }

        return bank;
    }


    public Bank getSingleByDetailsOrDefault(String details) {
        if (details == null)
            throw new ArgumentNullException("The details argument must have a value/cannot be null.");

        Bank bank = null;
        ResultSet resultSet = null;

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement csSingleBank = conn.prepareCall("{call singleBankByDetails(?)}")) {

            // Retrieve a getSingle bank
            csSingleBank.setString(1, details);
            csSingleBank.execute();

            // Transform ResultSet row into a Bank model
            resultSet = csSingleBank.getResultSet();
            byte rowCounter = 0;
            while (resultSet.next()) {

                // Check if more than one element matches id parameter
                ++rowCounter;
                if (rowCounter > 1)
                    throw new InvalidOperationException("The ResultSet does not contain exactly one row.");

                // Transform ResultSet row into a Bank object
                bank = new Bank(resultSet.getLong(1), resultSet.getString(2));
            }
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            closeConnections(resultSet);
        }

        return bank;
    }

    public Bank getSingleByDetails(String details) {
        if (details == null)
            throw new ArgumentNullException("The details argument must have a value/cannot be null.");

        Bank bank = null;
        ResultSet resultSet = null;

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement csSingleBank = conn.prepareCall("{call singleBankByDetails(?)}")) {

            // Retrieve a getSingle bank
            csSingleBank.setString(1, details);
            csSingleBank.execute();

            // Transform ResultSet row into a Bank model
            byte rowCounter = 0;
            resultSet = csSingleBank.getResultSet();
            while (resultSet.next()) {

                // Check if more than one element matches id parameter
                ++rowCounter;
                if (rowCounter > 1)
                    throw new InvalidOperationException("The ResultSet does not contain exactly one row.");

                // Transform ResultSet row into a Bank object
                bank = new Bank(resultSet.getLong(1), resultSet.getString(2));
            }

            if (rowCounter < 1)
                throw new SourceCollectionIsEmptyException("The ResultSet does contain not any rows.");

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            closeConnections(resultSet);
        }

        return bank;
    }


    @Override public Bank add(Bank model) {
        if (model == null)
            throw new ArgumentNullException("The model argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_addBank = conn.prepareCall("{call addBank(?)}")) {

            // Add a bank to DB
            cs_addBank.setString(1, model.getDetails());
            cs_addBank.execute();

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }

        return model;
    }

    @Override public Bank update(Bank model) {
        if (model == null)
            throw new ArgumentNullException("The model argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement csSingleBank = conn.prepareCall("{call updateBank(?,?)}")) {

            // Update an existing bank in DB
            csSingleBank.setLong(1, model.getId());
            csSingleBank.setString(2, model.getDetails());
            csSingleBank.execute();

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }

        return model;
    }


    @Override public void deleteOrDefault(Bank model) {
        if (model == null)
            throw new ArgumentNullException("The model argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement csDeleteBank = conn.prepareCall("{call deleteBank(?)}")) {

            // Delete bank
            csDeleteBank.setLong(1, model.getId());
            csDeleteBank.execute();

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }

    @Override public void delete(Bank model) {
        if (model == null)
            throw new ArgumentNullException("The model argument must have a value/cannot be null.");

        ResultSet resultSet = null;

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement csDeleteBank = conn.prepareCall("{call deleteBank(?)}");
             CallableStatement csSingleBank = conn.prepareCall("{call singleBank(?)}")) {

            // Check if bank exists
            csSingleBank.setLong(1, model.getId());
            csSingleBank.execute();

            // Validate result set
            byte rowCounter = 0;
            resultSet = csSingleBank.getResultSet();
            while (resultSet.next()) {

                // Check if more than one element matches id parameter
                ++rowCounter;
                if (rowCounter > 1)
                    throw new InvalidOperationException("The ResultSet does not contain exactly one row.");
            }

            if (rowCounter < 1)
                throw new SourceCollectionIsEmptyException("The ResultSet does contain not any rows.");

            // Delete bank
            csDeleteBank.setLong(1, model.getId());
            csDeleteBank.execute();

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            closeConnections(resultSet);
        }
    }


    @Override public void deleteByIdOrDefault(Long id) {
        if (id == null)
            throw new ArgumentNullException("The id argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement csDeleteBank = conn.prepareCall("{call deleteBank(?)}")) {

            // Delete bank
            csDeleteBank.setLong(1, id);
            csDeleteBank.execute();

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }

    @Override public void deleteById(Long id) {
        if (id == null)
            throw new ArgumentNullException("The id argument must have a value/cannot be null.");

        ResultSet resultSet = null;

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement csDeleteBank = conn.prepareCall("{call deleteBank(?)}");
             CallableStatement csSingleBank = conn.prepareCall("{call singleBank(?)}")) {

            // Check if bank exists
            csSingleBank.setLong(1, id);
            csSingleBank.execute();

            // Validate result set
            byte rowCounter = 0;
            resultSet = csSingleBank.getResultSet();
            while (resultSet.next()) {

                // Check if more than one row matches the id parameter
                ++rowCounter;
                if (rowCounter > 1)
                    throw new InvalidOperationException("The ResultSet does not contain exactly one row.");
            }

            // Check if ResultSet is empty.
            if (rowCounter < 1)
                throw new SourceCollectionIsEmptyException("The ResultSet does contain not any rows.");

            // Delete bank
            csDeleteBank.setLong(1, id);
            csDeleteBank.execute();

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            closeConnections(resultSet);
        }
    }

    public List<Bank> getBatchByIds(Iterable<Long> ids) {
        if (ids == null)
            throw new ArgumentNullException("The ids argument must have a value/cannot be null.");

        ResultSet resultSet = null;
        List<Bank> banks = null;

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_getBanks = conn.prepareCall("{call singleBank(?)}")) {

            // Add calls to batch
            for (Long id : ids) {
                try {
                    cs_getBanks.setLong(1, id);
                    cs_getBanks.addBatch();
                } catch (SQLException ex) {
                    DBUtil.showErrorMessage(ex);
                }
            }

            // Execute batch!
            cs_getBanks.executeBatch();

            // Transform ResultSet rows into banks
            resultSet = cs_getBanks.getResultSet();
            banks = new ArrayList<>();
            while (resultSet.next()) {
                banks.add(
                        new Bank(
                                resultSet.getLong(1),
                                resultSet.getString(2)
                        )
                );
            }

            return banks;
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            closeConnections(resultSet);
        }

        return banks;
    }

    @Override public void insertBatch(Iterable<Bank> models) {
        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_addBank = conn.prepareCall("{call addBank(?)}")) {

            // Add calls to batch
            for (Bank model : models) {
                try {
                    cs_addBank.setString(1, model.getDetails());
                    cs_addBank.addBatch();
                } catch (SQLException ex) {
                    DBUtil.showErrorMessage(ex);
                }
            }

            // Execute batch!
            cs_addBank.executeBatch();
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }

    @Override public void updateBatch(Iterable<Bank> models) {
        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cstmtUpdateBank = conn.prepareCall("{call updateBank(?,?)}")) {

            // Add calls to batch
            for (Bank model : models) {
                try {
                    cstmtUpdateBank.setLong(1, model.getId());
                    cstmtUpdateBank.setString(2, model.getDetails());
                    cstmtUpdateBank.addBatch();
                } catch (SQLException ex) {
                    DBUtil.showErrorMessage(ex);
                }
            }

            // Execute batch!
            cstmtUpdateBank.executeBatch();
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }

    @Override public void deleteBatch(Iterable<Bank> models) {
        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cstmtUpdateBank = conn.prepareCall("{call deleteBank(?)}")) {

            // Add calls to batch
            for (Bank model : models) {
                try {
                    cstmtUpdateBank.setLong(1, model.getId());
                    cstmtUpdateBank.addBatch();
                } catch (SQLException ex) {
                    DBUtil.showErrorMessage(ex);
                }
            }

            // Execute batch!
            cstmtUpdateBank.executeBatch();
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }

    @Override public void deleteBatchByIds(Iterable<Long> ids) {
        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cstmtUpdateBank = conn.prepareCall("{call deleteBank(?)}")) {

            // Add calls to batch
            for (Long id : ids) {
                try {
                    cstmtUpdateBank.setLong(1, id);
                    cstmtUpdateBank.addBatch();
                } catch (SQLException ex) {
                    DBUtil.showErrorMessage(ex);
                }
            }

            // Execute batch!
            cstmtUpdateBank.executeBatch();
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }
}
