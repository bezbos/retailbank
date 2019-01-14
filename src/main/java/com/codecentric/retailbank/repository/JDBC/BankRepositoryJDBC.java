package com.codecentric.retailbank.repository.JDBC;

import com.codecentric.retailbank.model.domain.Bank;
import com.codecentric.retailbank.repository.JDBC.configuration.DBType;
import com.codecentric.retailbank.repository.JDBC.configuration.DBUtil;
import com.codecentric.retailbank.repository.JDBC.exceptions.ArgumentNullException;
import com.codecentric.retailbank.repository.JDBC.exceptions.InvalidOperationException;
import com.codecentric.retailbank.repository.JDBC.exceptions.SourceCollectionIsEmptyException;
import com.codecentric.retailbank.repository.JDBC.interfaces.JDBCRepositoryBase;
import com.codecentric.retailbank.repository.JDBC.interfaces.JDBCRepositoryUtilities;
import com.codecentric.retailbank.repository.JDBC.wrappers.ListPage;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BankRepositoryJDBC extends JDBCRepositoryUtilities
        implements JDBCRepositoryBase<Bank, Long> {

    @Override
    public List<Bank> all() {
        ResultSet resultSet = null;
        List<Bank> banks = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement csAllBanks = conn.prepareCall("{call allBanks()}")) {

            // Retrieve all banks
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

        return banks;
    }

    @Override
    public ListPage<Bank> allByPage(int pageIndex, int pageSize) {
        ResultSet resultSet = null;
        ListPage<Bank> bankListPage = new ListPage<>();

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement csAllBanksRange = conn.prepareCall("{call allBanksRange(?,?)}");
             CallableStatement csBanksCount = conn.prepareCall("{call allBanksCount()}")) {

            // Retrieve all banks by page
            csAllBanksRange.setInt(1, Math.abs(pageIndex * pageSize));
            csAllBanksRange.setInt(2, Math.abs(pageSize));
            csAllBanksRange.execute();

            // Create bank models and add them into a list
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

        return bankListPage;
    }

    @Override
    public Bank singleOrDefault(Long id) {
        if (id == null)
            throw new ArgumentNullException("The id argument must have a value/cannot be null.");

        Bank bank = null;
        ResultSet resultSet = null;

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement csSingleBank = conn.prepareCall("{call singleBank(?)}")) {

            // Retrieve a single bank
            csSingleBank.setLong(1, id);
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
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            closeConnections(resultSet);
        }

        return bank;
    }

    @Override
    public Bank single(Long id) {
        if (id == null)
            throw new ArgumentNullException("The id argument must have a value/cannot be null.");

        Bank bank = null;
        ResultSet resultSet = null;

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement csSingleBank = conn.prepareCall("{call singleBank(?)}")) {

            // Retrieve a single bank
            csSingleBank.setLong(1, id);
            csSingleBank.execute();

            // Transform ResultSet row into a Bank model
            byte rowCounter = 0;
            resultSet = csSingleBank.getResultSet();
            while (resultSet.next()) {

                // Check if more than one element matches id parameter
                ++rowCounter;
                if (rowCounter > 1)
                    throw new NullPointerException("The ResultSet does not contain exactly one row.");

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

    @Override
    public Bank insert(Bank model) {
        if (model == null)
            throw new ArgumentNullException("The model argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement csSingleBank = conn.prepareCall("{call addBank(?)}")) {

            // Add a bank to DB
            csSingleBank.setString(1, model.getDetails());
            csSingleBank.execute();

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }

        return model;
    }

    @Override
    public Bank update(Bank model) {
        if (model == null)
            throw new ArgumentNullException("The model argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement csSingleBank = conn.prepareCall("{call updateBank(?,?)}")) {

            // Add a bank to DB
            csSingleBank.setLong(1, model.getId());
            csSingleBank.setString(2, model.getDetails());
            csSingleBank.execute();

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }

        return model;
    }

    @Override
    public void delete(Bank model) {

    }

    @Override
    public void deleteById(Bank model) {

    }

    @Override
    public Bank insertMany(Iterable<Bank> model) {
        return null;
    }

    @Override
    public Bank updateMany(Iterable<Bank> model) {
        return null;
    }

    @Override
    public void deleteMany(Iterable<Bank> model) {

    }

    @Override
    public void deleteManyById(Iterable<Long> model) {

    }
}
