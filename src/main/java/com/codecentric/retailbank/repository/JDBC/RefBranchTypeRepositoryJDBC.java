package com.codecentric.retailbank.repository.JDBC;

import com.codecentric.retailbank.model.domain.OLD.RefBranchTypeOLD;
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
public class RefBranchTypeRepositoryJDBC extends JDBCRepositoryUtilities implements JDBCRepositoryBase<RefBranchTypeOLD, Long> {

    @Override public List<RefBranchTypeOLD> findAllOrDefault() {
        ResultSet resultSet = null;
        List<RefBranchTypeOLD> branchTypes = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_allRefBranchTypes = conn.prepareCall("{call allRefBranchTypes()}")) {

            // Retrieve findAll branchTypes
            cs_allRefBranchTypes.execute();

            // Transform each ResultSet row into RefBranchTypeOLD model and add to "branchTypes" list
            resultSet = cs_allRefBranchTypes.getResultSet();
            while (resultSet.next()) {
                branchTypes.add(
                        new RefBranchTypeOLD(
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

    @Override public List<RefBranchTypeOLD> findAll() {
        ResultSet resultSet = null;
        List<RefBranchTypeOLD> branchTypes = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_allRefBranchTypes = conn.prepareCall("{call allRefBranchTypes()}")) {

            // Retrieve findAll branchTypes
            cs_allRefBranchTypes.execute();

            // Transform each ResultSet row into RefBranchTypeOLD model and add to "branchTypes" list
            resultSet = cs_allRefBranchTypes.getResultSet();
            byte rowCounter = 0;
            while (resultSet.next()) {
                branchTypes.add(
                        new RefBranchTypeOLD(
                                resultSet.getLong(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getString(5),
                                resultSet.getString(6)
                        )
                );
            }

            if (rowCounter < 1)
                throw new SourceCollectionIsEmptyException("The ResultSet does contain not any rows.");

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            closeConnections(resultSet);
        }

        return branchTypes.size() < 1 ? null : branchTypes;
    }


    @Override public ListPage<RefBranchTypeOLD> findAllRangeOrDefault(int pageIndex, int pageSize) {
        ResultSet resultSet = null;
        ListPage<RefBranchTypeOLD> refBranchTypeListPage = new ListPage<>();

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_allRefBranchTypesRange = conn.prepareCall("{call allRefBranchTypesRange(?,?)}");
             CallableStatement cs_allRefBranchTypesCount = conn.prepareCall("{call allRefBranchTypesCount()}")) {

            // Retrieve findAll RefBranchTypes
            cs_allRefBranchTypesRange.setInt(1, Math.abs(pageIndex * pageSize));
            cs_allRefBranchTypesRange.setInt(2, Math.abs(pageSize));
            cs_allRefBranchTypesRange.execute();

            // Transform each ResultSet row into RefBranchTypeOLD model and add to "refBranchTypes" list
            resultSet = cs_allRefBranchTypesRange.getResultSet();
            List<RefBranchTypeOLD> refBranchTypes = new ArrayList<>();
            while (resultSet.next()) {
                refBranchTypes.add(
                        new RefBranchTypeOLD(
                                resultSet.getLong(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getString(5),
                                resultSet.getString(6)
                        )
                );
            }

            // Get the total number of RefBranchTypeOLD-s in DB
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

    @Override public ListPage<RefBranchTypeOLD> findAllRange(int pageIndex, int pageSize) {
        ResultSet resultSet = null;
        ListPage<RefBranchTypeOLD> refBranchTypeListPage = new ListPage<>();

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_allRefBranchTypesRange = conn.prepareCall("{call allRefBranchTypesRange(?,?)}");
             CallableStatement cs_allRefBranchTypesCount = conn.prepareCall("{call allRefBranchTypesCount()}")) {

            // Retrieve findAll RefBranchTypes
            cs_allRefBranchTypesRange.setInt(1, Math.abs(pageIndex * pageSize));
            cs_allRefBranchTypesRange.setInt(2, Math.abs(pageSize));
            cs_allRefBranchTypesRange.execute();

            // Transform each ResultSet row into RefBranchTypeOLD model and add to "refBranchTypes" list
            resultSet = cs_allRefBranchTypesRange.getResultSet();
            List<RefBranchTypeOLD> refBranchTypes = new ArrayList<>();
            byte rowCounter = 0;
            while (resultSet.next()) {
                refBranchTypes.add(
                        new RefBranchTypeOLD(
                                resultSet.getLong(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getString(5),
                                resultSet.getString(6)
                        )
                );
            }
            if (rowCounter < 1)
                throw new SourceCollectionIsEmptyException("The ResultSet does contain not any rows.");

            // Get the total number of RefBranchTypeOLD-s in DB
            cs_allRefBranchTypesCount.execute();
            resultSet = cs_allRefBranchTypesCount.getResultSet();
            rowCounter = 0;
            while (resultSet.next())
                refBranchTypeListPage.setPageCount(resultSet.getLong(1), pageSize);
            if (rowCounter < 1)
                throw new SourceCollectionIsEmptyException("The ResultSet does contain not any rows.");

            // Add refBranchTypes to ListPage transfer object
            refBranchTypeListPage.setModels(refBranchTypes);

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            closeConnections(resultSet);
        }

        return refBranchTypeListPage.getModels().size() < 1 ? null : refBranchTypeListPage;
    }


    @Override public RefBranchTypeOLD getSingleOrDefault(Long id) {
        if (id == null)
            throw new ArgumentNullException("The id argument must have a value/cannot be null.");

        RefBranchTypeOLD refBranchType = null;
        ResultSet resultSet = null;

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_singleRefBranchType = conn.prepareCall("{call singleRefBranchType(?)}")) {

            // Retrieve a getSingle RefBranchTypeOLD
            cs_singleRefBranchType.setLong(1, id);
            cs_singleRefBranchType.execute();

            // Transform ResultSet row into a RefBranchTypeOLD model
            byte rowCounter = 0;
            resultSet = cs_singleRefBranchType.getResultSet();
            while (resultSet.next()) {

                // Check if more than one element matches id parameter
                ++rowCounter;
                if (rowCounter > 1)
                    throw new InvalidOperationException("The ResultSet does not contain exactly one row.");

                // Transform ResultSet row into a RefBranchTypeOLD object
                refBranchType = new RefBranchTypeOLD(
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

    @Override public RefBranchTypeOLD getSingle(Long id) {
        if (id == null)
            throw new ArgumentNullException("The id argument must have a value/cannot be null.");

        RefBranchTypeOLD refBranchType = null;
        ResultSet resultSet = null;

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_singleRefBranchType = conn.prepareCall("{call singleRefBranchType(?)}")) {

            // Retrieve a getSingle RefBranchTypeOLD
            cs_singleRefBranchType.setLong(1, id);
            cs_singleRefBranchType.execute();

            // Transform ResultSet row into a RefBranchTypeOLD model
            resultSet = cs_singleRefBranchType.getResultSet();
            byte rowCounter = 0;
            while (resultSet.next()) {

                // Check if more than one element matches id parameter
                ++rowCounter;
                if (rowCounter > 1)
                    throw new InvalidOperationException("The ResultSet does not contain exactly one row.");

                // Transform ResultSet row into a RefBranchTypeOLD object
                refBranchType = new RefBranchTypeOLD(
                        resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6)
                );
            }
            if (rowCounter < 1)
                throw new SourceCollectionIsEmptyException("The ResultSet does not contain any rows.");

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            closeConnections(resultSet);
        }

        return refBranchType;
    }


    public RefBranchTypeOLD getSingleByCodeOrDefault(String code) {
        if (code == null)
            throw new ArgumentNullException("The code argument must have a value/cannot be null.");

        RefBranchTypeOLD refBranchType = null;
        ResultSet resultSet = null;

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_singleRefBranchType = conn.prepareCall("{call singleRefBranchTypeByCode(?)}")) {

            // Retrieve a getSingle RefBranchTypeOLD
            cs_singleRefBranchType.setString(1, code);
            cs_singleRefBranchType.execute();

            // Transform ResultSet row into a RefBranchTypeOLD model
            byte rowCounter = 0;
            resultSet = cs_singleRefBranchType.getResultSet();
            while (resultSet.next()) {

                // Check if more than one element matches id parameter
                ++rowCounter;
                if (rowCounter > 1)
                    throw new InvalidOperationException("The ResultSet does not contain exactly one row.");

                // Transform ResultSet row into a RefBranchTypeOLD object
                refBranchType = new RefBranchTypeOLD(
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

    public RefBranchTypeOLD getSingleByCode(String code) {
        if (code == null)
            throw new ArgumentNullException("The code argument must have a value/cannot be null.");

        RefBranchTypeOLD refBranchType = null;
        ResultSet resultSet = null;

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_singleRefBranchType = conn.prepareCall("{call singleRefBranchType(?)}")) {

            // Retrieve a getSingle RefBranchTypeOLD
            cs_singleRefBranchType.setString(1, code);
            cs_singleRefBranchType.execute();

            // Transform ResultSet row into a RefBranchTypeOLD model
            resultSet = cs_singleRefBranchType.getResultSet();
            byte rowCounter = 0;
            while (resultSet.next()) {

                // Check if more than one element matches id parameter
                ++rowCounter;
                if (rowCounter > 1)
                    throw new InvalidOperationException("The ResultSet does not contain exactly one row.");

                // Transform ResultSet row into a RefBranchTypeOLD object
                refBranchType = new RefBranchTypeOLD(
                        resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6)
                );
            }
            if (rowCounter < 1)
                throw new SourceCollectionIsEmptyException("The ResultSet does not contain any rows.");

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            closeConnections(resultSet);
        }

        return refBranchType;
    }


    @Override public RefBranchTypeOLD add(RefBranchTypeOLD model) {
        if (model == null)
            throw new ArgumentNullException("The model argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_addRefBranchType = conn.prepareCall("{call addRefBranchType(?,?,?,?,?)}")) {

            // Add a new RefBranchTypeOLD to DB
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

    @Override public RefBranchTypeOLD update(RefBranchTypeOLD model) {
        if (model == null)
            throw new ArgumentNullException("The model argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_updateRefBranchType = conn.prepareCall("{call updateRefBranchType(?,?,?,?,?,?)}")) {

            // Add a new RefBranchTypeOLD to DB
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


    @Override public void deleteOrDefault(RefBranchTypeOLD model) {
        if (model == null)
            throw new ArgumentNullException("The model argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_deleteRefBranchType = conn.prepareCall("{call deleteRefBranchType(?)}")) {

            // Delete an existing RefBranchTypeOLD
            cs_deleteRefBranchType.setLong(1, model.getId());
            cs_deleteRefBranchType.execute();

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }

    @Override public void delete(RefBranchTypeOLD model) {
        if (model == null)
            throw new ArgumentNullException("The model argument must have a value/cannot be null.");

        ResultSet resultSet = null;

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_deleteRefBranchType = conn.prepareCall("{call deleteRefBranchType(?)}");
             CallableStatement cs_singleRefBranchType = conn.prepareCall("{call singleRefBranchType(?)}")) {

            // Check if the RefBranchTypeOLD exists
            cs_singleRefBranchType.setLong(1, model.getId());
            cs_singleRefBranchType.execute();

            // Validate the result set
            resultSet = cs_singleRefBranchType.getResultSet();
            byte rowCounter = 0;
            while (resultSet.next()) {

                // Check if more than one element matches id parameter
                ++rowCounter;
                if (rowCounter > 1)
                    throw new InvalidOperationException("The ResultSet does not contain exactly one row.");
            }

            if (rowCounter < 1)
                throw new SourceCollectionIsEmptyException("The ResultSet does contain not any rows.");

            // Delete an existing RefBranchTypeOLD
            cs_deleteRefBranchType.setLong(1, model.getId());
            cs_deleteRefBranchType.execute();

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
             CallableStatement cs_deleteRefBranchType = conn.prepareCall("{call deleteRefBranchType(?)}")) {

            // Delete an existing RefBranchTypeOLD
            cs_deleteRefBranchType.setLong(1, id);
            cs_deleteRefBranchType.execute();

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }

    @Override public void deleteById(Long id) {
        if (id == null)
            throw new ArgumentNullException("The id argument must have a value/cannot be null.");

        ResultSet resultSet = null;

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_deleteRefBranchType = conn.prepareCall("{call deleteRefBranchType(?)}");
             CallableStatement cs_singleRefBranchType = conn.prepareCall("{call singleRefBranchType(?)}")) {

            // Check if the RefBranchTypeOLD exists
            cs_singleRefBranchType.setLong(1, id);
            cs_singleRefBranchType.execute();

            // Validate the result set
            resultSet = cs_singleRefBranchType.getResultSet();
            byte rowCounter = 0;
            while (resultSet.next()) {

                // Check if more than one element matches id parameter
                ++rowCounter;
                if (rowCounter > 1)
                    throw new InvalidOperationException("The ResultSet does not contain exactly one row.");
            }

            if (rowCounter < 1)
                throw new SourceCollectionIsEmptyException("The ResultSet does contain not any rows.");

            // Delete an existing RefBranchTypeOLD
            cs_deleteRefBranchType.setLong(1, id);
            cs_deleteRefBranchType.execute();

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            closeConnections(resultSet);
        }
    }


    @Override public void insertBatch(Iterable<RefBranchTypeOLD> models) {
        if (models == null)
            throw new ArgumentNullException("The models argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_addRefBranchType = conn.prepareCall("{call addRefBranchType(?,?,?,?,?)}")) {

            // Add calls to batch
            for (RefBranchTypeOLD model : models) {
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

    @Override public void updateBatch(Iterable<RefBranchTypeOLD> models) {
        if (models == null)
            throw new ArgumentNullException("The models argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_updateRefBranchType = conn.prepareCall("{call updateRefBranchType(?,?,?,?,?,?)}")) {

            // Add calls to batch
            for (RefBranchTypeOLD model : models) {
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

    @Override public void deleteBatch(Iterable<RefBranchTypeOLD> models) {
        if (models == null)
            throw new ArgumentNullException("The models argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_deleteRefBranchTypes = conn.prepareCall("{call deleteRefBranchTypes(?)}")) {

            // Add calls to batch
            for (RefBranchTypeOLD model : models) {
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
