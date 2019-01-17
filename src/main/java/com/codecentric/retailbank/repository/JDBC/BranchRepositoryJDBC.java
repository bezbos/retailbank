package com.codecentric.retailbank.repository.JDBC;

import com.codecentric.retailbank.model.domain.Branch;
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
public class BranchRepositoryJDBC extends JDBCRepositoryUtilities implements JDBCRepositoryBase<Branch, Long> {

    @Override public List<Branch> findAllOrDefault() {
        ResultSet resultSet = null;
        List<Branch> branches = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_allAddresses = conn.prepareCall("{call allBranches()}")) {

            // Retrieve findAll branches
            cs_allAddresses.execute();

            // Transform each ResultSet row into Branches model and add to "branches" list
            resultSet = cs_allAddresses.getResultSet();
            while (resultSet.next()) {
                Branch branch = new Branch();
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

    @Override public List<Branch> findAll() {
        return null;
    }

    @Override public ListPage<Branch> findAllRangeOrDefault(int pageIndex, int pageSize) {
        return null;
    }

    @Override public ListPage<Branch> findAllRange(int pageIndex, int pageSize) {
        return null;
    }

    @Override public Branch getSingleOrDefault(Long aLong) {
        return null;
    }

    @Override public Branch getSingle(Long aLong) {
        return null;
    }

    @Override public Branch add(Branch model) {
        return null;
    }

    @Override public Branch update(Branch model) {
        return null;
    }

    @Override public void deleteOrDefault(Branch model) {

    }

    @Override public void delete(Branch model) {

    }

    @Override public void deleteByIdOrDefault(Long id) {

    }

    @Override public void deleteById(Long id) {

    }

    @Override public void insertBatch(Iterable<Branch> models) {

    }

    @Override public void updateBatch(Iterable<Branch> models) {

    }

    @Override public void deleteBatch(Iterable<Branch> models) {

    }

    @Override public void deleteBatchByIds(Iterable<Long> longs) {

    }
}
