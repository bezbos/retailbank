package com.codecentric.retailbank.repository.JDBC;

import com.codecentric.retailbank.model.domain.OLD.BranchOLD;
import com.codecentric.retailbank.repository.JDBC.configuration.DBType;
import com.codecentric.retailbank.repository.JDBC.configuration.DBUtil;
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
public class BranchRepositoryJDBC extends JDBCRepositoryUtilities implements JDBCRepositoryBase<BranchOLD, Long> {

    @Override public List<BranchOLD> findAllOrDefault() {
        ResultSet resultSet = null;
        List<BranchOLD> branches = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_allAddresses = conn.prepareCall("{call allBranches()}")) {

            // Retrieve findAll branches
            cs_allAddresses.execute();

            // Transform each ResultSet row into Branches model and add to "branches" list
            resultSet = cs_allAddresses.getResultSet();
            while (resultSet.next()) {
                BranchOLD branch = new BranchOLD();
//                branch.setFields(
//                        resultSet.getLong(1),
//                        resultSet.getLong(2),
//                        resultSet.getLong(3),
//                        resultSet.getLong(4),
//                        resultSet.getString(5)
//                );
                branches.add(branch);
            }

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            closeConnections(resultSet);
        }

        return branches.size() < 1 ? null : branches;
    }

    @Override public List<BranchOLD> findAll() {
        return null;
    }

    @Override public ListPage<BranchOLD> findAllRangeOrDefault(int pageIndex, int pageSize) {
        return null;
    }

    @Override public ListPage<BranchOLD> findAllRange(int pageIndex, int pageSize) {
        return null;
    }

    @Override public BranchOLD getSingleOrDefault(Long aLong) {
        return null;
    }

    @Override public BranchOLD getSingle(Long aLong) {
        return null;
    }

    @Override public BranchOLD add(BranchOLD model) {
        return null;
    }

    @Override public BranchOLD update(BranchOLD model) {
        return null;
    }

    @Override public void deleteOrDefault(BranchOLD model) {

    }

    @Override public void delete(BranchOLD model) {

    }

    @Override public void deleteByIdOrDefault(Long id) {

    }

    @Override public void deleteById(Long id) {

    }

    @Override public void insertBatch(Iterable<BranchOLD> models) {

    }

    @Override public void updateBatch(Iterable<BranchOLD> models) {

    }

    @Override public void deleteBatch(Iterable<BranchOLD> models) {

    }

    @Override public void deleteBatchByIds(Iterable<Long> longs) {

    }
}
