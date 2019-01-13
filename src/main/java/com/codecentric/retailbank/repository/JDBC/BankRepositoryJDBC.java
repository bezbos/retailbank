package com.codecentric.retailbank.repository.JDBC;

import com.codecentric.retailbank.model.domain.Bank;
import com.codecentric.retailbank.repository.JDBC.configuration.DBType;
import com.codecentric.retailbank.repository.JDBC.configuration.DBUtil;
import com.codecentric.retailbank.repository.JDBC.interfaces.JDBCRepositoryBase;
import com.codecentric.retailbank.repository.JDBC.interfaces.JDBCRepositoryUtilities;
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
        List<Bank> banks = new ArrayList<Bank>();

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement csAllBanks = conn.prepareCall("{call allBanks()}")) {

            csAllBanks.execute();
            ResultSet resultSet = csAllBanks.getResultSet();
            while (resultSet.next()) {
                banks.add(
                        new Bank(resultSet.getLong(1), resultSet.getString(2))
                );
            }


            closeConnections(resultSet);
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
            return null;
        }

        return banks;
    }

    @Override
    public ListPage<Bank> allByPage(int pageIndex, int pageSize) {
        ListPage<Bank> banks = new ListPage<>();

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement csAllBanksRange = conn.prepareCall("{call allBanksRange(?,?)}");
             CallableStatement csBanksCount = conn.prepareCall("{call allBanksCount()}")) {

            csAllBanksRange.setInt(1, Math.abs(pageIndex * pageSize));
            csAllBanksRange.setInt(2, Math.abs(pageSize));
            csAllBanksRange.execute();

            List<Bank> banksList = new ArrayList<>();
            ResultSet resultSet = csAllBanksRange.getResultSet();
            while (resultSet.next()) {
                banksList.add(
                        new Bank(resultSet.getLong(1), resultSet.getString(2))
                );
            }

            csBanksCount.execute();
            resultSet = csBanksCount.getResultSet();
            while (resultSet.next())
                banks.setPageCount(resultSet.getLong(1), pageSize);

            banks.setModels(banksList);

            closeConnections(resultSet);
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
            return null;
        }

        return banks;
    }

    @Override
    public Bank singleOrDefault(Long aLong) {
        return null;
    }

    @Override
    public Bank single(Long aLong) {
        return null;
    }

    @Override
    public Bank firstOrDefault(Long aLong) {
        return null;
    }

    @Override
    public Bank first(Long aLong) {
        return null;
    }

    @Override
    public Bank lastOrDefault(Long aLong) {
        return null;
    }

    @Override
    public Bank last(Long aLong) {
        return null;
    }

    @Override
    public Bank insert(Bank model) {
        return null;
    }

    @Override
    public Bank update(Bank model) {
        return null;
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
