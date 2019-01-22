package com.codecentric.retailbank.repository;

import com.codecentric.retailbank.exception.nullpointer.ArgumentNullException;
import com.codecentric.retailbank.exception.nullpointer.InvalidOperationException;
import com.codecentric.retailbank.model.domain.RefTransactionType;
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
public class RefTransactionTypeRepository extends JDBCRepositoryUtilities implements JDBCRepositoryBase<RefTransactionType, Long> {

    //region READ
    @Override public List<RefTransactionType> all() {
        ResultSet resultSet = null;
        List<RefTransactionType> transactionTypes = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_allRefTransactionTypes = conn.prepareCall("{call allRefTransactionTypes()}")) {

            // Retrieve all transactionTypes
            cs_allRefTransactionTypes.execute();

            // Transform each ResultSet row into RefTransactionType model and add to "transactionTypes" list
            resultSet = cs_allRefTransactionTypes.getResultSet();
            while (resultSet.next()) {
                transactionTypes.add(
                        new RefTransactionType(
                                resultSet.getLong(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getString(4)
                        )
                );
            }

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            closeConnections(resultSet);
        }

        return transactionTypes;
    }

    @Override public ListPage<RefTransactionType> allRange(int pageIndex, int pageSize) {
        ResultSet resultSet = null;
        ListPage<RefTransactionType> refBranchTypeListPage = new ListPage<>();

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_allRefTransactionTypesRange = conn.prepareCall("{call allRefTransactionTypesRange(?,?)}");
             CallableStatement cs_allRefTransactionTypesCount = conn.prepareCall("{call allRefTransactionTypesCount()}")) {

            // Retrieve all RefTransactionTypes
            cs_allRefTransactionTypesRange.setInt(1, Math.abs(pageIndex * pageSize));
            cs_allRefTransactionTypesRange.setInt(2, Math.abs(pageSize));
            cs_allRefTransactionTypesRange.execute();

            // Transform each ResultSet row into RefTransactionType model and add to "refBranchTypes" list
            resultSet = cs_allRefTransactionTypesRange.getResultSet();
            List<RefTransactionType> refBranchTypes = new ArrayList<>();
            while (resultSet.next()) {
                refBranchTypes.add(
                        new RefTransactionType(
                                resultSet.getLong(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getString(4)
                        )
                );
            }

            // Get the total number of RefTransactionType-s in DB
            cs_allRefTransactionTypesCount.execute();
            resultSet = cs_allRefTransactionTypesCount.getResultSet();
            while (resultSet.next())
                refBranchTypeListPage.setPageCount(resultSet.getLong(1), pageSize);

            // Add refBranchTypes to ListPage transfer object
            refBranchTypeListPage.setModels(refBranchTypes);

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            closeConnections(resultSet);
        }

        return refBranchTypeListPage;
    }

    @Override public RefTransactionType single(Long id) {
        if (id == null)
            throw new ArgumentNullException("The id argument must have a value/cannot be null.");

        RefTransactionType refBranchType = null;
        ResultSet resultSet = null;

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_singleRefTransactionType = conn.prepareCall("{call singleRefTransactionType(?)}")) {

            // Retrieve a single RefTransactionType
            cs_singleRefTransactionType.setLong(1, id);
            cs_singleRefTransactionType.execute();

            // Transform ResultSet row into a RefTransactionType model
            byte rowCounter = 0;
            resultSet = cs_singleRefTransactionType.getResultSet();
            while (resultSet.next()) {

                // Check if more than one element matches id parameter
                ++rowCounter;
                if (rowCounter > 1)
                    throw new InvalidOperationException("The ResultSet does not contain exactly one row.");

                // Transform ResultSet row into a RefTransactionType object
                refBranchType = new RefTransactionType(
                        resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(4)
                );
            }
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            closeConnections(resultSet);
        }

        return refBranchType;
    }

    public RefTransactionType singleByCode(String code) {
        if (code == null)
            throw new ArgumentNullException("The code argument must have a value/cannot be null.");

        RefTransactionType refBranchType = null;
        ResultSet resultSet = null;

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_singleRefTransactionType = conn.prepareCall("{call singleRefTransactionTypeByCode(?)}")) {

            // Retrieve a single RefTransactionType
            cs_singleRefTransactionType.setString(1, code);
            cs_singleRefTransactionType.execute();

            // Transform ResultSet row into a RefTransactionType model
            byte rowCounter = 0;
            resultSet = cs_singleRefTransactionType.getResultSet();
            while (resultSet.next()) {

                // Check if more than one element matches id parameter
                ++rowCounter;
                if (rowCounter > 1)
                    throw new InvalidOperationException("The ResultSet does not contain exactly one row.");

                // Transform ResultSet row into a RefTransactionType object
                refBranchType = new RefTransactionType(
                        resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(4)
                );
            }
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            closeConnections(resultSet);
        }

        return refBranchType;
    }
    //endregion

    //region WRITE
    @Override public RefTransactionType add(RefTransactionType model) {
        if (model == null)
            throw new ArgumentNullException("The model argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_addRefTransactionType = conn.prepareCall("{call addRefTransactionType(?,?,?,?)}")) {

            // Add a new RefTransactionType to DB
            cs_addRefTransactionType.setString(1, model.getCode());
            cs_addRefTransactionType.setString(2, model.getDescription());
            cs_addRefTransactionType.setString(3, model.getIsDeposit());
            cs_addRefTransactionType.setString(4, model.getIsWithdrawal());
            cs_addRefTransactionType.execute();

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }

        return model;
    }

    @Override public RefTransactionType update(RefTransactionType model) {
        if (model == null)
            throw new ArgumentNullException("The model argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_updateRefTransactionType = conn.prepareCall("{call updateRefTransactionType(?,?,?,?,?)}")) {

            // Add a new RefTransactionType to DB
            cs_updateRefTransactionType.setLong(1, model.getId());
            cs_updateRefTransactionType.setString(2, model.getCode());
            cs_updateRefTransactionType.setString(3, model.getDescription());
            cs_updateRefTransactionType.setString(4, model.getIsDeposit());
            cs_updateRefTransactionType.setString(5, model.getIsWithdrawal());
            cs_updateRefTransactionType.execute();

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }

        return model;
    }

    @Override public void insertBatch(Iterable<RefTransactionType> models) {
        if (models == null)
            throw new ArgumentNullException("The models argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_addRefTransactionType = conn.prepareCall("{call addRefTransactionType(?,?,?,?)}")) {

            // Add calls to batch
            for (RefTransactionType model : models) {
                try {
                    cs_addRefTransactionType.setString(1, model.getCode());
                    cs_addRefTransactionType.setString(2, model.getDescription());
                    cs_addRefTransactionType.setString(3, model.getIsDeposit());
                    cs_addRefTransactionType.setString(4, model.getIsWithdrawal());
                    cs_addRefTransactionType.addBatch();
                } catch (SQLException ex) {
                    DBUtil.showErrorMessage(ex);
                }
            }

            // Execute batch!
            cs_addRefTransactionType.executeBatch();
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }

    @Override public void updateBatch(Iterable<RefTransactionType> models) {
        if (models == null)
            throw new ArgumentNullException("The models argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_updateRefTransactionType = conn.prepareCall("{call updateRefTransactionType(?,?,?,?,?)}")) {

            // Add calls to batch
            for (RefTransactionType model : models) {
                try {
                    cs_updateRefTransactionType.setLong(1, model.getId());
                    cs_updateRefTransactionType.setString(2, model.getCode());
                    cs_updateRefTransactionType.setString(3, model.getDescription());
                    cs_updateRefTransactionType.setString(4, model.getIsDeposit());
                    cs_updateRefTransactionType.setString(5, model.getIsWithdrawal());
                    cs_updateRefTransactionType.addBatch();
                } catch (SQLException ex) {
                    DBUtil.showErrorMessage(ex);
                }
            }

            // Execute batch!
            cs_updateRefTransactionType.executeBatch();
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }
    //endregion

    //region DELETE
    @Override public void delete(RefTransactionType model) {
        if (model == null)
            throw new ArgumentNullException("The model argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_deleteRefTransactionType = conn.prepareCall("{call deleteRefTransactionType(?)}")) {

            // Delete an existing RefTransactionType
            cs_deleteRefTransactionType.setLong(1, model.getId());
            cs_deleteRefTransactionType.execute();

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }

    @Override public void deleteById(Long id) {
        if (id == null)
            throw new ArgumentNullException("The id argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_deleteRefTransactionType = conn.prepareCall("{call deleteRefTransactionType(?)}")) {

            // Delete an existing RefTransactionType
            cs_deleteRefTransactionType.setLong(1, id);
            cs_deleteRefTransactionType.execute();

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }

    @Override public void deleteBatch(Iterable<RefTransactionType> models) {
        if (models == null)
            throw new ArgumentNullException("The models argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_deleteRefTransactionTypes = conn.prepareCall("{call deleteRefTransactionTypes(?)}")) {

            // Add calls to batch
            for (RefTransactionType model : models) {
                try {
                    cs_deleteRefTransactionTypes.setLong(1, model.getId());
                    cs_deleteRefTransactionTypes.addBatch();
                } catch (SQLException ex) {
                    DBUtil.showErrorMessage(ex);
                }
            }

            // Execute batch!
            cs_deleteRefTransactionTypes.executeBatch();
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }

    @Override public void deleteBatchByIds(Iterable<Long> ids) {
        if (ids == null)
            throw new ArgumentNullException("The ids argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_deleteRefTransactionTypes = conn.prepareCall("{call deleteRefTransactionTypes(?)}")) {

            // Add calls to batch
            for (Long id : ids) {
                try {
                    cs_deleteRefTransactionTypes.setLong(1, id);
                    cs_deleteRefTransactionTypes.addBatch();
                } catch (SQLException ex) {
                    DBUtil.showErrorMessage(ex);
                }
            }

            // Execute batch!
            cs_deleteRefTransactionTypes.executeBatch();
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }
    //endregion
}
