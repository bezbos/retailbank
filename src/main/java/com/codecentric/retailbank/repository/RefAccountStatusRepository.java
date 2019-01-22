package com.codecentric.retailbank.repository;

import com.codecentric.retailbank.model.domain.RefAccountStatus;
import com.codecentric.retailbank.repository.configuration.DBType;
import com.codecentric.retailbank.repository.configuration.DBUtil;
import com.codecentric.retailbank.repository.exceptions.ArgumentNullException;
import com.codecentric.retailbank.repository.exceptions.InvalidOperationException;
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
public class RefAccountStatusRepository extends JDBCRepositoryUtilities implements JDBCRepositoryBase<RefAccountStatus, Long> {

    @Override public List<RefAccountStatus> findAll() {
        ResultSet resultSet = null;
        List<RefAccountStatus> branchTypes = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_allRefAccountStatuses = conn.prepareCall("{call allRefAccountStatus()}")) {

            // Retrieve findAll branchTypes
            cs_allRefAccountStatuses.execute();

            // Transform each ResultSet row into RefAccountStatus model and add to "branchTypes" list
            resultSet = cs_allRefAccountStatuses.getResultSet();
            while (resultSet.next()) {
                branchTypes.add(
                        new RefAccountStatus(
                                resultSet.getLong(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getString(5)
                        )
                );
            }

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            closeConnections(resultSet);
        }

        return branchTypes.size() < 1 ? null : branchTypes;
    }

    @Override public ListPage<RefAccountStatus> findAllRange(int pageIndex, int pageSize) {
        ResultSet resultSet = null;
        ListPage<RefAccountStatus> refBranchTypeListPage = new ListPage<>();

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_allRefAccountStatusesRange = conn.prepareCall("{call allRefAccountStatusRange(?,?)}");
             CallableStatement cs_allRefAccountStatusesCount = conn.prepareCall("{call allRefAccountStatusCount()}")) {

            // Retrieve findAll RefAccountStatuses
            cs_allRefAccountStatusesRange.setInt(1, Math.abs(pageIndex * pageSize));
            cs_allRefAccountStatusesRange.setInt(2, Math.abs(pageSize));
            cs_allRefAccountStatusesRange.execute();

            // Transform each ResultSet row into RefAccountStatus model and add to "refBranchTypes" list
            resultSet = cs_allRefAccountStatusesRange.getResultSet();
            List<RefAccountStatus> refBranchTypes = new ArrayList<>();
            while (resultSet.next()) {
                refBranchTypes.add(
                        new RefAccountStatus(
                                resultSet.getLong(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getString(5)
                        )
                );
            }

            // Get the total number of RefAccountStatus-s in DB
            cs_allRefAccountStatusesCount.execute();
            resultSet = cs_allRefAccountStatusesCount.getResultSet();
            while (resultSet.next())
                refBranchTypeListPage.setPageCount(resultSet.getLong(1), pageSize);

            // Add refBranchTypes to ListPage transfer object
            refBranchTypeListPage.setModels(refBranchTypes);

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            closeConnections(resultSet);
        }

        return refBranchTypeListPage.getModels().size() < 1 ? null : refBranchTypeListPage;
    }

    @Override public RefAccountStatus getSingle(Long id) {
        if (id == null)
            throw new ArgumentNullException("The id argument must have a value/cannot be null.");

        RefAccountStatus refBranchType = null;
        ResultSet resultSet = null;

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_singleRefAccountStatus = conn.prepareCall("{call singleRefAccountStatus(?)}")) {

            // Retrieve a getSingle RefAccountStatus
            cs_singleRefAccountStatus.setLong(1, id);
            cs_singleRefAccountStatus.execute();

            // Transform ResultSet row into a RefAccountStatus model
            byte rowCounter = 0;
            resultSet = cs_singleRefAccountStatus.getResultSet();
            while (resultSet.next()) {

                // Check if more than one element matches id parameter
                ++rowCounter;
                if (rowCounter > 1)
                    throw new InvalidOperationException("The ResultSet does not contain exactly one row.");

                // Transform ResultSet row into a RefAccountStatus object
                refBranchType = new RefAccountStatus(
                                resultSet.getLong(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getString(5)
                        );
            }
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            closeConnections(resultSet);
        }

        return refBranchType;
    }

    public RefAccountStatus getSingleByCode(String code) {
        if (code == null)
            throw new ArgumentNullException("The code argument must have a value/cannot be null.");

        RefAccountStatus refBranchType = null;
        ResultSet resultSet = null;

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_singleRefAccountStatus = conn.prepareCall("{call singleRefAccountStatusByCode(?)}")) {

            // Retrieve a getSingle RefAccountStatus
            cs_singleRefAccountStatus.setString(1, code);
            cs_singleRefAccountStatus.execute();

            // Transform ResultSet row into a RefAccountStatus model
            byte rowCounter = 0;
            resultSet = cs_singleRefAccountStatus.getResultSet();
            while (resultSet.next()) {

                // Check if more than one element matches id parameter
                ++rowCounter;
                if (rowCounter > 1)
                    throw new InvalidOperationException("The ResultSet does not contain exactly one row.");

                // Transform ResultSet row into a RefAccountStatus object
                refBranchType = new RefAccountStatus(
                                resultSet.getLong(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getString(5)
                        );
            }
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            closeConnections(resultSet);
        }

        return refBranchType;
    }

    @Override public RefAccountStatus add(RefAccountStatus model) {
        if (model == null)
            throw new ArgumentNullException("The model argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_addRefAccountStatus = conn.prepareCall("{call addRefAccountStatus(?,?,?,?)}")) {

            // Add a new RefAccountStatus to DB
            cs_addRefAccountStatus.setString(1, model.getCode());
            cs_addRefAccountStatus.setString(2, model.getDescription());
            cs_addRefAccountStatus.setString(3, model.getIsActive());
            cs_addRefAccountStatus.setString(4, model.getIsClosed());
            cs_addRefAccountStatus.execute();

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }

        return model;
    }

    @Override public RefAccountStatus update(RefAccountStatus model) {
        if (model == null)
            throw new ArgumentNullException("The model argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_updateRefAccountStatus = conn.prepareCall("{call updateRefAccountStatus(?,?,?,?,?)}")) {

            // Add a new RefAccountStatus to DB
            cs_updateRefAccountStatus.setLong(1, model.getId());
            cs_updateRefAccountStatus.setString(2, model.getCode());
            cs_updateRefAccountStatus.setString(3, model.getDescription());
            cs_updateRefAccountStatus.setString(4, model.getIsActive());
            cs_updateRefAccountStatus.setString(5, model.getIsClosed());
            cs_updateRefAccountStatus.execute();

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }

        return model;
    }

    @Override public void delete(RefAccountStatus model) {
        if (model == null)
            throw new ArgumentNullException("The model argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_deleteRefAccountStatus = conn.prepareCall("{call deleteRefAccountStatus(?)}")) {

            // Delete an existing RefAccountStatus
            cs_deleteRefAccountStatus.setLong(1, model.getId());
            cs_deleteRefAccountStatus.execute();

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }

    @Override public void deleteById(Long id) {
        if (id == null)
            throw new ArgumentNullException("The id argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_deleteRefAccountStatus = conn.prepareCall("{call deleteRefAccountStatus(?)}")) {

            // Delete an existing RefAccountStatus
            cs_deleteRefAccountStatus.setLong(1, id);
            cs_deleteRefAccountStatus.execute();

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }

    public List<RefAccountStatus> getBatchByIds(Iterable<Long> ids) {
        if (ids == null)
            throw new ArgumentNullException("The ids argument must have a value/cannot be null.");

        ResultSet resultSet = null;
        List<RefAccountStatus> refBranchTypes = null;

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_getRefAccountStatuses = conn.prepareCall("{call singleRefAccountStatus(?)}")) {

            // Add calls to batch
            for (Long id : ids) {
                try {
                    cs_getRefAccountStatuses.setLong(1, id);
                    cs_getRefAccountStatuses.addBatch();
                } catch (SQLException ex) {
                    DBUtil.showErrorMessage(ex);
                }
            }

            // Execute batch!
            cs_getRefAccountStatuses.executeBatch();

            // Transform ResultSet rows into refBranchTypes
            resultSet = cs_getRefAccountStatuses.getResultSet();
            refBranchTypes = new ArrayList<>();
            while (resultSet.next()) {
                refBranchTypes.add(
                        new RefAccountStatus(
                                resultSet.getLong(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getString(5)
                        )
                );
            }

            return refBranchTypes;
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            closeConnections(resultSet);
        }

        return refBranchTypes;
    }

    @Override public void insertBatch(Iterable<RefAccountStatus> models) {
        if (models == null)
            throw new ArgumentNullException("The models argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_addRefAccountStatus = conn.prepareCall("{call addRefAccountStatus(?,?,?,?)}")) {

            // Add calls to batch
            for (RefAccountStatus model : models) {
                try {
                    cs_addRefAccountStatus.setString(1, model.getCode());
                    cs_addRefAccountStatus.setString(2, model.getDescription());
                    cs_addRefAccountStatus.setString(3, model.getIsActive());
                    cs_addRefAccountStatus.setString(4, model.getIsClosed());
                    cs_addRefAccountStatus.addBatch();
                } catch (SQLException ex) {
                    DBUtil.showErrorMessage(ex);
                }
            }

            // Execute batch!
            cs_addRefAccountStatus.executeBatch();
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }

    @Override public void updateBatch(Iterable<RefAccountStatus> models) {
        if (models == null)
            throw new ArgumentNullException("The models argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_updateRefAccountStatus = conn.prepareCall("{call updateRefAccountStatus(?,?,?,?,?)}")) {

            // Add calls to batch
            for (RefAccountStatus model : models) {
                try {
                    cs_updateRefAccountStatus.setLong(1, model.getId());
                    cs_updateRefAccountStatus.setString(2, model.getCode());
                    cs_updateRefAccountStatus.setString(3, model.getDescription());
                    cs_updateRefAccountStatus.setString(4, model.getIsActive());
                    cs_updateRefAccountStatus.setString(5, model.getIsClosed());
                    cs_updateRefAccountStatus.addBatch();
                } catch (SQLException ex) {
                    DBUtil.showErrorMessage(ex);
                }
            }

            // Execute batch!
            cs_updateRefAccountStatus.executeBatch();
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }

    @Override public void deleteBatch(Iterable<RefAccountStatus> models) {
        if (models == null)
            throw new ArgumentNullException("The models argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_deleteRefAccountStatuses = conn.prepareCall("{call deleteRefAccountStatus(?)}")) {

            // Add calls to batch
            for (RefAccountStatus model : models) {
                try {
                    cs_deleteRefAccountStatuses.setLong(1, model.getId());
                    cs_deleteRefAccountStatuses.addBatch();
                } catch (SQLException ex) {
                    DBUtil.showErrorMessage(ex);
                }
            }

            // Execute batch!
            cs_deleteRefAccountStatuses.executeBatch();
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }

    @Override public void deleteBatchByIds(Iterable<Long> ids) {
        if (ids == null)
            throw new ArgumentNullException("The ids argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_deleteRefAccountStatuses = conn.prepareCall("{call deleteRefAccountStatus(?)}")) {

            // Add calls to batch
            for (Long id : ids) {
                try {
                    cs_deleteRefAccountStatuses.setLong(1, id);
                    cs_deleteRefAccountStatuses.addBatch();
                } catch (SQLException ex) {
                    DBUtil.showErrorMessage(ex);
                }
            }

            // Execute batch!
            cs_deleteRefAccountStatuses.executeBatch();
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }
}
