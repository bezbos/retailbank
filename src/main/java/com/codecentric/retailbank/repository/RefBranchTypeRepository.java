package com.codecentric.retailbank.repository;

import com.codecentric.retailbank.model.domain.RefBranchType;
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
public class RefBranchTypeRepository extends JDBCRepositoryUtilities implements JDBCRepositoryBase<RefBranchType, Long> {

    @Override public List<RefBranchType> findAll() {
        ResultSet resultSet = null;
        List<RefBranchType> branchTypes = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_allRefBranchTypes = conn.prepareCall("{call allRefBranchTypes()}")) {

            // Retrieve findAll branchTypes
            cs_allRefBranchTypes.execute();

            // Transform each ResultSet row into RefBranchType model and add to "branchTypes" list
            resultSet = cs_allRefBranchTypes.getResultSet();
            while (resultSet.next()) {
                branchTypes.add(
                        new RefBranchType(
                                resultSet.getLong(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getString(5),
                                resultSet.getString(6)
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

    @Override public ListPage<RefBranchType> findAllRange(int pageIndex, int pageSize) {
        ResultSet resultSet = null;
        ListPage<RefBranchType> refBranchTypeListPage = new ListPage<>();

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_allRefBranchTypesRange = conn.prepareCall("{call allRefBranchTypesRange(?,?)}");
             CallableStatement cs_allRefBranchTypesCount = conn.prepareCall("{call allRefBranchTypesCount()}")) {

            // Retrieve findAll RefBranchTypes
            cs_allRefBranchTypesRange.setInt(1, Math.abs(pageIndex * pageSize));
            cs_allRefBranchTypesRange.setInt(2, Math.abs(pageSize));
            cs_allRefBranchTypesRange.execute();

            // Transform each ResultSet row into RefBranchType model and add to "refBranchTypes" list
            resultSet = cs_allRefBranchTypesRange.getResultSet();
            List<RefBranchType> refBranchTypes = new ArrayList<>();
            while (resultSet.next()) {
                refBranchTypes.add(
                        new RefBranchType(
                                resultSet.getLong(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getString(5),
                                resultSet.getString(6)
                        )
                );
            }

            // Get the total number of RefBranchType-s in DB
            cs_allRefBranchTypesCount.execute();
            resultSet = cs_allRefBranchTypesCount.getResultSet();
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

    @Override public RefBranchType getSingle(Long id) {
        if (id == null)
            throw new ArgumentNullException("The id argument must have a value/cannot be null.");

        RefBranchType refBranchType = null;
        ResultSet resultSet = null;

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_singleRefBranchType = conn.prepareCall("{call singleRefBranchType(?)}")) {

            // Retrieve a getSingle RefBranchType
            cs_singleRefBranchType.setLong(1, id);
            cs_singleRefBranchType.execute();

            // Transform ResultSet row into a RefBranchType model
            byte rowCounter = 0;
            resultSet = cs_singleRefBranchType.getResultSet();
            while (resultSet.next()) {

                // Check if more than one element matches id parameter
                ++rowCounter;
                if (rowCounter > 1)
                    throw new InvalidOperationException("The ResultSet does not contain exactly one row.");

                // Transform ResultSet row into a RefBranchType object
                refBranchType = new RefBranchType(
                        resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6)
                );
            }
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            closeConnections(resultSet);
        }

        return refBranchType;
    }

    public RefBranchType getSingleByCode(String code) {
        if (code == null)
            throw new ArgumentNullException("The code argument must have a value/cannot be null.");

        RefBranchType refBranchType = null;
        ResultSet resultSet = null;

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_singleRefBranchType = conn.prepareCall("{call singleRefBranchTypeByCode(?)}")) {

            // Retrieve a getSingle RefBranchType
            cs_singleRefBranchType.setString(1, code);
            cs_singleRefBranchType.execute();

            // Transform ResultSet row into a RefBranchType model
            byte rowCounter = 0;
            resultSet = cs_singleRefBranchType.getResultSet();
            while (resultSet.next()) {

                // Check if more than one element matches id parameter
                ++rowCounter;
                if (rowCounter > 1)
                    throw new InvalidOperationException("The ResultSet does not contain exactly one row.");

                // Transform ResultSet row into a RefBranchType object
                refBranchType = new RefBranchType(
                        resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6)
                );
            }
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            closeConnections(resultSet);
        }

        return refBranchType;
    }

    @Override public RefBranchType add(RefBranchType model) {
        if (model == null)
            throw new ArgumentNullException("The model argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_addRefBranchType = conn.prepareCall("{call addRefBranchType(?,?,?,?,?)}")) {

            // Add a new RefBranchType to DB
            cs_addRefBranchType.setString(1, model.getCode());
            cs_addRefBranchType.setString(2, model.getDescription());
            cs_addRefBranchType.setString(3, model.getIsLargeUrban());
            cs_addRefBranchType.setString(4, model.getIsSmallRural());
            cs_addRefBranchType.setString(5, model.getIsMediumSuburban());
            cs_addRefBranchType.execute();

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }

        return model;
    }

    @Override public RefBranchType update(RefBranchType model) {
        if (model == null)
            throw new ArgumentNullException("The model argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_updateRefBranchType = conn.prepareCall("{call updateRefBranchType(?,?,?,?,?,?)}")) {

            // Add a new RefBranchType to DB
            cs_updateRefBranchType.setLong(1, model.getId());
            cs_updateRefBranchType.setString(2, model.getCode());
            cs_updateRefBranchType.setString(3, model.getDescription());
            cs_updateRefBranchType.setString(4, model.getIsLargeUrban());
            cs_updateRefBranchType.setString(5, model.getIsSmallRural());
            cs_updateRefBranchType.setString(6, model.getIsMediumSuburban());
            cs_updateRefBranchType.execute();

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }

        return model;
    }

    @Override public void delete(RefBranchType model) {
        if (model == null)
            throw new ArgumentNullException("The model argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_deleteRefBranchType = conn.prepareCall("{call deleteRefBranchType(?)}")) {

            // Delete an existing RefBranchType
            cs_deleteRefBranchType.setLong(1, model.getId());
            cs_deleteRefBranchType.execute();

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }

    @Override public void deleteById(Long id) {
        if (id == null)
            throw new ArgumentNullException("The id argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_deleteRefBranchType = conn.prepareCall("{call deleteRefBranchType(?)}")) {

            // Delete an existing RefBranchType
            cs_deleteRefBranchType.setLong(1, id);
            cs_deleteRefBranchType.execute();

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }

    public List<RefBranchType> getBatchByIds(Iterable<Long> ids) {
        if (ids == null)
            throw new ArgumentNullException("The ids argument must have a value/cannot be null.");

        ResultSet resultSet = null;
        List<RefBranchType> refBranchTypes = null;

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_getRefBranchTypes = conn.prepareCall("{call singleRefBranchType(?)}")) {

            // Add calls to batch
            for (Long id : ids) {
                try {
                    cs_getRefBranchTypes.setLong(1, id);
                    cs_getRefBranchTypes.addBatch();
                } catch (SQLException ex) {
                    DBUtil.showErrorMessage(ex);
                }
            }

            // Execute batch!
            cs_getRefBranchTypes.executeBatch();

            // Transform ResultSet rows into refBranchTypes
            resultSet = cs_getRefBranchTypes.getResultSet();
            refBranchTypes = new ArrayList<>();
            while (resultSet.next()) {
                refBranchTypes.add(
                        new RefBranchType(
                                resultSet.getLong(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getString(5),
                                resultSet.getString(6)
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

    @Override public void insertBatch(Iterable<RefBranchType> models) {
        if (models == null)
            throw new ArgumentNullException("The models argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_addRefBranchType = conn.prepareCall("{call addRefBranchType(?,?,?,?,?)}")) {

            // Add calls to batch
            for (RefBranchType model : models) {
                try {
                    cs_addRefBranchType.setString(1, model.getCode());
                    cs_addRefBranchType.setString(2, model.getDescription());
                    cs_addRefBranchType.setString(3, model.getIsLargeUrban());
                    cs_addRefBranchType.setString(4, model.getIsSmallRural());
                    cs_addRefBranchType.setString(5, model.getIsMediumSuburban());
                    cs_addRefBranchType.addBatch();
                } catch (SQLException ex) {
                    DBUtil.showErrorMessage(ex);
                }
            }

            // Execute batch!
            cs_addRefBranchType.executeBatch();
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }

    @Override public void updateBatch(Iterable<RefBranchType> models) {
        if (models == null)
            throw new ArgumentNullException("The models argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_updateRefBranchType = conn.prepareCall("{call updateRefBranchType(?,?,?,?,?,?)}")) {

            // Add calls to batch
            for (RefBranchType model : models) {
                try {
                    cs_updateRefBranchType.setLong(1, model.getId());
                    cs_updateRefBranchType.setString(2, model.getCode());
                    cs_updateRefBranchType.setString(3, model.getDescription());
                    cs_updateRefBranchType.setString(4, model.getIsLargeUrban());
                    cs_updateRefBranchType.setString(5, model.getIsSmallRural());
                    cs_updateRefBranchType.setString(6, model.getIsMediumSuburban());
                    cs_updateRefBranchType.addBatch();
                } catch (SQLException ex) {
                    DBUtil.showErrorMessage(ex);
                }
            }

            // Execute batch!
            cs_updateRefBranchType.executeBatch();
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }

    @Override public void deleteBatch(Iterable<RefBranchType> models) {
        if (models == null)
            throw new ArgumentNullException("The models argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_deleteRefBranchTypes = conn.prepareCall("{call deleteRefBranchTypes(?)}")) {

            // Add calls to batch
            for (RefBranchType model : models) {
                try {
                    cs_deleteRefBranchTypes.setLong(1, model.getId());
                    cs_deleteRefBranchTypes.addBatch();
                } catch (SQLException ex) {
                    DBUtil.showErrorMessage(ex);
                }
            }

            // Execute batch!
            cs_deleteRefBranchTypes.executeBatch();
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }

    @Override public void deleteBatchByIds(Iterable<Long> ids) {
        if (ids == null)
            throw new ArgumentNullException("The ids argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_deleteRefBranchTypes = conn.prepareCall("{call deleteRefBranchTypes(?)}")) {

            // Add calls to batch
            for (Long id : ids) {
                try {
                    cs_deleteRefBranchTypes.setLong(1, id);
                    cs_deleteRefBranchTypes.addBatch();
                } catch (SQLException ex) {
                    DBUtil.showErrorMessage(ex);
                }
            }

            // Execute batch!
            cs_deleteRefBranchTypes.executeBatch();
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }
}
