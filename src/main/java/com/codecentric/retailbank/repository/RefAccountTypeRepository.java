package com.codecentric.retailbank.repository;

import com.codecentric.retailbank.exception.nullpointer.ArgumentNullException;
import com.codecentric.retailbank.exception.nullpointer.InvalidOperationException;
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
public class RefAccountTypeRepository extends JDBCRepositoryUtilities implements JDBCRepositoryBase<RefAccountType, Long> {

    //region READ
    @Override public List<RefAccountType> all() {
        ResultSet resultSet = null;
        List<RefAccountType> branchTypes = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_allRefAccountTypes = conn.prepareCall("{call allRefAccountTypes()}")) {

            // Retrieve all branchTypes
            cs_allRefAccountTypes.execute();

            // Transform each ResultSet row into RefAccountType model and add to "branchTypes" list
            resultSet = cs_allRefAccountTypes.getResultSet();
            while (resultSet.next()) {
                branchTypes.add(
                        new RefAccountType(
                                resultSet.getLong(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getString(5),
                                resultSet.getString(6),
                                resultSet.getString(7),
                                resultSet.getString(8)
                        )
                );
            }

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            closeConnections(resultSet);
        }

        return branchTypes;
    }

    @Override public ListPage<RefAccountType> allRange(int pageIndex, int pageSize) {
        ResultSet resultSet = null;
        ListPage<RefAccountType> refBranchTypeListPage = new ListPage<>();

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_allRefAccountTypesRange = conn.prepareCall("{call allRefAccountTypesRange(?,?)}");
             CallableStatement cs_allRefAccountTypesCount = conn.prepareCall("{call allRefAccountTypesCount()}")) {

            // Retrieve all RefAccountTypes
            cs_allRefAccountTypesRange.setInt(1, Math.abs(pageIndex * pageSize));
            cs_allRefAccountTypesRange.setInt(2, Math.abs(pageSize));
            cs_allRefAccountTypesRange.execute();

            // Transform each ResultSet row into RefAccountType model and add to "refBranchTypes" list
            resultSet = cs_allRefAccountTypesRange.getResultSet();
            List<RefAccountType> refBranchTypes = new ArrayList<>();
            while (resultSet.next()) {
                refBranchTypes.add(
                        new RefAccountType(
                                resultSet.getLong(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getString(5),
                                resultSet.getString(6),
                                resultSet.getString(7),
                                resultSet.getString(8)
                        )
                );
            }

            // Get the total number of RefAccountType-s in DB
            cs_allRefAccountTypesCount.execute();
            resultSet = cs_allRefAccountTypesCount.getResultSet();
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

    @Override public RefAccountType single(Long id) {
        if (id == null)
            throw new ArgumentNullException("The id argument must have a value/cannot be null.");

        RefAccountType refAccountType = null;
        ResultSet resultSet = null;

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_singleRefAccountType = conn.prepareCall("{call singleRefAccountType(?)}")) {

            // Retrieve a single RefAccountType
            cs_singleRefAccountType.setLong(1, id);
            cs_singleRefAccountType.execute();

            // Transform ResultSet row into a RefAccountType model
            byte rowCounter = 0;
            resultSet = cs_singleRefAccountType.getResultSet();
            while (resultSet.next()) {

                // Check if more than one element matches id parameter
                ++rowCounter;
                if (rowCounter > 1)
                    throw new InvalidOperationException("The ResultSet does not contain exactly one row.");

                // Transform ResultSet row into a RefAccountType object
                refAccountType = new RefAccountType(
                        resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getString(8)
                );
            }
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            closeConnections(resultSet);
        }

        return refAccountType;
    }

    public RefAccountType singleByCode(String code) throws ArgumentNullException, InvalidOperationException {
        if (code == null)
            throw new ArgumentNullException("The code argument must have a value/cannot be null.");

        RefAccountType refBranchType = null;
        ResultSet resultSet = null;

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_singleRefAccountType = conn.prepareCall("{call singleRefAccountTypeByCode(?)}")) {

            // Retrieve a single RefAccountType
            cs_singleRefAccountType.setString(1, code);
            cs_singleRefAccountType.execute();

            // Transform ResultSet row into a RefAccountType model
            byte rowCounter = 0;
            resultSet = cs_singleRefAccountType.getResultSet();
            while (resultSet.next()) {

                // Transform ResultSet row into a RefAccountType object
                refBranchType = new RefAccountType(
                        resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getString(8)
                );

                // Check if more than one element matches id parameter
                ++rowCounter;
                if (rowCounter > 1)
                    throw new InvalidOperationException("The ResultSet does not contain exactly one row.", refBranchType);

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
    @Override public RefAccountType add(RefAccountType model) {
        if (model == null)
            throw new ArgumentNullException("The model argument must have a value/cannot be null.");

        RefAccountType refAccountType = null;
        ResultSet resultSet = null;

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_addRefAccountType = conn.prepareCall("{call addRefAccountType(?,?,?,?,?,?,?)}")) {

            // Add a new RefAccountType to DB
            cs_addRefAccountType.setString(1, model.getCode());
            cs_addRefAccountType.setString(2, model.getDescription());
            cs_addRefAccountType.setString(3, model.getIsCheckingType());
            cs_addRefAccountType.setString(4, model.getIsSavingsType());
            cs_addRefAccountType.setString(5, model.getIsCertificateOfDepositType());
            cs_addRefAccountType.setString(6, model.getIsMoneyMarketType());
            cs_addRefAccountType.setString(7, model.getIsIndividualRetirementType());
            cs_addRefAccountType.execute();

            // Transform ResultSet row into a RefAccountType model
            byte rowCounter = 0;
            resultSet = cs_addRefAccountType.getResultSet();
            while (resultSet.next()) {

                // Check if more than one element matches id parameter
                ++rowCounter;
                if (rowCounter > 1)
                    throw new InvalidOperationException("The ResultSet does not contain exactly one row.");

                // Transform ResultSet row into a RefAccountType object
                refAccountType = new RefAccountType(
                        resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getString(8)
                );
            }
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }

        return refAccountType;
    }

    @Override public RefAccountType update(RefAccountType model) {
        if (model == null)
            throw new ArgumentNullException("The model argument must have a value/cannot be null.");

        RefAccountType refAccountType = null;
        ResultSet resultSet = null;

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_updateRefAccountType = conn.prepareCall("{call updateRefAccountType(?,?,?,?,?,?,?,?)}")) {

            // Add a new RefAccountType to DB
            cs_updateRefAccountType.setLong(1, model.getId());
            cs_updateRefAccountType.setString(2, model.getCode());
            cs_updateRefAccountType.setString(3, model.getDescription());
            cs_updateRefAccountType.setString(4, model.getIsCheckingType());
            cs_updateRefAccountType.setString(5, model.getIsSavingsType());
            cs_updateRefAccountType.setString(6, model.getIsCertificateOfDepositType());
            cs_updateRefAccountType.setString(7, model.getIsMoneyMarketType());
            cs_updateRefAccountType.setString(8, model.getIsIndividualRetirementType());
            cs_updateRefAccountType.execute();

            // Transform ResultSet row into a RefAccountType model
            byte rowCounter = 0;
            resultSet = cs_updateRefAccountType.getResultSet();
            while (resultSet.next()) {

                // Check if more than one element matches id parameter
                ++rowCounter;
                if (rowCounter > 1)
                    throw new InvalidOperationException("The ResultSet does not contain exactly one row.");

                // Transform ResultSet row into a RefAccountType object
                refAccountType = new RefAccountType(
                        resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getString(8)
                );
            }
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }

        return refAccountType;
    }

    @Override public void insertBatch(Iterable<RefAccountType> models) {
        if (models == null)
            throw new ArgumentNullException("The models argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_addRefAccountType = conn.prepareCall("{call addRefAccountType(?,?,?,?,?)}")) {

            // Add calls to batch
            for (RefAccountType model : models) {
                try {
                    cs_addRefAccountType.setString(1, model.getCode());
                    cs_addRefAccountType.setString(2, model.getDescription());
                    cs_addRefAccountType.setString(3, model.getIsCheckingType());
                    cs_addRefAccountType.setString(4, model.getIsSavingsType());
                    cs_addRefAccountType.setString(5, model.getIsCertificateOfDepositType());
                    cs_addRefAccountType.setString(6, model.getIsMoneyMarketType());
                    cs_addRefAccountType.setString(7, model.getIsIndividualRetirementType());
                    cs_addRefAccountType.addBatch();
                } catch (SQLException ex) {
                    DBUtil.showErrorMessage(ex);
                }
            }

            // Execute batch!
            cs_addRefAccountType.executeBatch();
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }

    @Override public void updateBatch(Iterable<RefAccountType> models) {
        if (models == null)
            throw new ArgumentNullException("The models argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_updateRefAccountType = conn.prepareCall("{call updateRefAccountType(?,?,?,?,?,?)}")) {

            // Add calls to batch
            for (RefAccountType model : models) {
                try {
                    cs_updateRefAccountType.setLong(1, model.getId());
                    cs_updateRefAccountType.setString(2, model.getCode());
                    cs_updateRefAccountType.setString(3, model.getDescription());
                    cs_updateRefAccountType.setString(4, model.getIsCheckingType());
                    cs_updateRefAccountType.setString(5, model.getIsSavingsType());
                    cs_updateRefAccountType.setString(6, model.getIsCertificateOfDepositType());
                    cs_updateRefAccountType.setString(7, model.getIsMoneyMarketType());
                    cs_updateRefAccountType.setString(8, model.getIsIndividualRetirementType());
                    cs_updateRefAccountType.addBatch();
                } catch (SQLException ex) {
                    DBUtil.showErrorMessage(ex);
                }
            }

            // Execute batch!
            cs_updateRefAccountType.executeBatch();
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }
    //endregion

    //region DELETE
    @Override public void delete(RefAccountType model) {
        if (model == null)
            throw new ArgumentNullException("The model argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_deleteRefAccountType = conn.prepareCall("{call deleteRefAccountType(?)}")) {

            // Delete an existing RefAccountType
            cs_deleteRefAccountType.setLong(1, model.getId());
            cs_deleteRefAccountType.execute();

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }

    @Override public void deleteById(Long id) {
        if (id == null)
            throw new ArgumentNullException("The id argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_deleteRefAccountType = conn.prepareCall("{call deleteRefAccountType(?)}")) {

            // Delete an existing RefAccountType
            cs_deleteRefAccountType.setLong(1, id);
            cs_deleteRefAccountType.execute();

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }

    @Override public void deleteBatch(Iterable<RefAccountType> models) {
        if (models == null)
            throw new ArgumentNullException("The models argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_deleteRefAccountTypes = conn.prepareCall("{call deleteRefAccountTypes(?)}")) {

            // Add calls to batch
            for (RefAccountType model : models) {
                try {
                    cs_deleteRefAccountTypes.setLong(1, model.getId());
                    cs_deleteRefAccountTypes.addBatch();
                } catch (SQLException ex) {
                    DBUtil.showErrorMessage(ex);
                }
            }

            // Execute batch!
            cs_deleteRefAccountTypes.executeBatch();
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }

    @Override public void deleteBatchByIds(Iterable<Long> ids) {
        if (ids == null)
            throw new ArgumentNullException("The ids argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_deleteRefAccountTypes = conn.prepareCall("{call deleteRefAccountTypes(?)}")) {

            // Add calls to batch
            for (Long id : ids) {
                try {
                    cs_deleteRefAccountTypes.setLong(1, id);
                    cs_deleteRefAccountTypes.addBatch();
                } catch (SQLException ex) {
                    DBUtil.showErrorMessage(ex);
                }
            }

            // Execute batch!
            cs_deleteRefAccountTypes.executeBatch();
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }
    //endregion
}
