package com.codecentric.retailbank.repository;

import com.codecentric.retailbank.model.domain.Merchant;
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
public class MerchantRepository extends JDBCRepositoryUtilities implements JDBCRepositoryBase<Merchant, Long> {

    @Override public List<Merchant> findAll() {
        ResultSet resultSet = null;
        List<Merchant> merchants = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement csAllMerchants = conn.prepareCall("{call allMerchants()}")) {

            // Retrieve findAll merchants
            csAllMerchants.execute();

            // Transform each ResultSet row into Merchant model and add to "merchants" list
            resultSet = csAllMerchants.getResultSet();
            while (resultSet.next()) {
                merchants.add(
                        new Merchant(resultSet.getLong(1), resultSet.getString(2))
                );
            }

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            closeConnections(resultSet);
        }

        return merchants.size() < 1 ? null : merchants;
    }

    @Override public ListPage<Merchant> findAllRange(int pageIndex, int pageSize) {
        ResultSet resultSet = null;
        ListPage<Merchant> merchantListPage = new ListPage<>();

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement csAllMerchantsRange = conn.prepareCall("{call allMerchantsRange(?,?)}");
             CallableStatement csMerchantsCount = conn.prepareCall("{call allMerchantsCount()}")) {

            // Retrieve merchants in a certain range
            csAllMerchantsRange.setInt(1, Math.abs(pageIndex * pageSize));
            csAllMerchantsRange.setInt(2, Math.abs(pageSize));
            csAllMerchantsRange.execute();

            // Transform ResultSet rows into Merchant models and add them into the merchantListPage
            List<Merchant> merchantsList = new ArrayList<>();
            resultSet = csAllMerchantsRange.getResultSet();
            while (resultSet.next()) {
                merchantsList.add(
                        new Merchant(resultSet.getLong(1), resultSet.getString(2))
                );
            }

            // Get the total number of merchants in DB
            csMerchantsCount.execute();
            resultSet = csMerchantsCount.getResultSet();
            while (resultSet.next())
                merchantListPage.setPageCount(resultSet.getLong(1), pageSize);

            // Add merchants to ListPage transfer object
            merchantListPage.setModels(merchantsList);

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            closeConnections(resultSet);
        }

        return merchantListPage.getModels().size() < 1 ? null : merchantListPage;
    }

    public List<Merchant> findAllByDetails(String details) {
        if (details == null)
            throw new ArgumentNullException("The details argument must have a value/cannot be null.");

        ResultSet resultSet = null;
        List<Merchant> merchants = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement callableStatement = conn.prepareCall("{call allMerchantsByDetails(?)}")) {

            // Retrieve findAll merchants
            callableStatement.setString(1, details);
            callableStatement.execute();

            // Transform each ResultSet row into Merchant model and add to "merchants" list
            resultSet = callableStatement.getResultSet();
            while (resultSet.next()) {
                merchants.add(
                        new Merchant(resultSet.getLong(1), resultSet.getString(2))
                );
            }

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            closeConnections(resultSet);
        }

        return merchants.size() < 1 ? null : merchants;
    }

    @Override public Merchant getSingle(Long id) {
        if (id == null)
            throw new ArgumentNullException("The id argument must have a value/cannot be null.");

        Merchant merchant = null;
        ResultSet resultSet = null;

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement csSingleMerchant = conn.prepareCall("{call singleMerchant(?)}")) {

            // Retrieve a getSingle merchant
            csSingleMerchant.setLong(1, id);
            csSingleMerchant.execute();

            // Transform ResultSet row into a Merchant model
            resultSet = csSingleMerchant.getResultSet();
            byte rowCounter = 0;
            while (resultSet.next()) {

                // Check if more than one element matches id parameter
                ++rowCounter;
                if (rowCounter > 1)
                    throw new InvalidOperationException("The ResultSet does not contain exactly one row.");

                // Transform ResultSet row into a Merchant object
                merchant = new Merchant(resultSet.getLong(1), resultSet.getString(2));
            }
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            closeConnections(resultSet);
        }

        return merchant;
    }

    public Merchant getSingleByDetails(String details) {
        if (details == null)
            throw new ArgumentNullException("The details argument must have a value/cannot be null.");

        Merchant merchant = null;
        ResultSet resultSet = null;

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement csSingleMerchant = conn.prepareCall("{call singleMerchantByDetails(?)}")) {

            // Retrieve a getSingle merchant
            csSingleMerchant.setString(1, details);
            csSingleMerchant.execute();

            // Transform ResultSet row into a Merchant model
            resultSet = csSingleMerchant.getResultSet();
            byte rowCounter = 0;
            while (resultSet.next()) {

                // Check if more than one element matches id parameter
                ++rowCounter;
                if (rowCounter > 1)
                    throw new InvalidOperationException("The ResultSet does not contain exactly one row.");

                // Transform ResultSet row into a Merchant object
                merchant = new Merchant(resultSet.getLong(1), resultSet.getString(2));
            }
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            closeConnections(resultSet);
        }

        return merchant;
    }

    @Override public Merchant add(Merchant model) {
        if (model == null)
            throw new ArgumentNullException("The model argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_addMerchant = conn.prepareCall("{call addMerchant(?)}")) {

            // Add a merchant to DB
            cs_addMerchant.setString(1, model.getDetails());
            cs_addMerchant.execute();

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }

        return model;
    }

    @Override public Merchant update(Merchant model) {
        if (model == null)
            throw new ArgumentNullException("The model argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement csSingleMerchant = conn.prepareCall("{call updateMerchant(?,?)}")) {

            // Update an existing merchant in DB
            csSingleMerchant.setLong(1, model.getId());
            csSingleMerchant.setString(2, model.getDetails());
            csSingleMerchant.execute();

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }

        return model;
    }


    @Override public void delete(Merchant model) {
        if (model == null)
            throw new ArgumentNullException("The model argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement csDeleteMerchant = conn.prepareCall("{call deleteMerchant(?)}")) {

            // Delete merchant
            csDeleteMerchant.setLong(1, model.getId());
            csDeleteMerchant.execute();

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }

    @Override public void deleteById(Long id) {
        if (id == null)
            throw new ArgumentNullException("The id argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement csDeleteMerchant = conn.prepareCall("{call deleteMerchant(?)}")) {

            // Delete merchant
            csDeleteMerchant.setLong(1, id);
            csDeleteMerchant.execute();

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }

    public List<Merchant> getBatchByIds(Iterable<Long> ids) {
        if (ids == null)
            throw new ArgumentNullException("The ids argument must have a value/cannot be null.");

        ResultSet resultSet = null;
        List<Merchant> merchants = null;

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_getMerchants = conn.prepareCall("{call singleMerchant(?)}")) {

            // Add calls to batch
            for (Long id : ids) {
                try {
                    cs_getMerchants.setLong(1, id);
                    cs_getMerchants.addBatch();
                } catch (SQLException ex) {
                    DBUtil.showErrorMessage(ex);
                }
            }

            // Execute batch!
            cs_getMerchants.executeBatch();

            // Transform ResultSet rows into merchants
            resultSet = cs_getMerchants.getResultSet();
            merchants = new ArrayList<>();
            while (resultSet.next()) {
                merchants.add(
                        new Merchant(
                                resultSet.getLong(1),
                                resultSet.getString(2)
                        )
                );
            }

            return merchants;
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            closeConnections(resultSet);
        }

        return merchants;
    }

    @Override public void insertBatch(Iterable<Merchant> models) {
        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_addMerchant = conn.prepareCall("{call addMerchant(?)}")) {

            // Add calls to batch
            for (Merchant model : models) {
                try {
                    cs_addMerchant.setString(1, model.getDetails());
                    cs_addMerchant.addBatch();
                } catch (SQLException ex) {
                    DBUtil.showErrorMessage(ex);
                }
            }

            // Execute batch!
            cs_addMerchant.executeBatch();
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }

    @Override public void updateBatch(Iterable<Merchant> models) {
        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_UpdateMerchant = conn.prepareCall("{call updateMerchant(?,?)}")) {

            // Add calls to batch
            for (Merchant model : models) {
                try {
                    cs_UpdateMerchant.setLong(1, model.getId());
                    cs_UpdateMerchant.setString(2, model.getDetails());
                    cs_UpdateMerchant.addBatch();
                } catch (SQLException ex) {
                    DBUtil.showErrorMessage(ex);
                }
            }

            // Execute batch!
            cs_UpdateMerchant.executeBatch();
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }

    @Override public void deleteBatch(Iterable<Merchant> models) {
        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_UpdateMerchant = conn.prepareCall("{call deleteMerchant(?)}")) {

            // Add calls to batch
            for (Merchant model : models) {
                try {
                    cs_UpdateMerchant.setLong(1, model.getId());
                    cs_UpdateMerchant.addBatch();
                } catch (SQLException ex) {
                    DBUtil.showErrorMessage(ex);
                }
            }

            // Execute batch!
            cs_UpdateMerchant.executeBatch();
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }

    @Override public void deleteBatchByIds(Iterable<Long> ids) {
        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_UpdateMerchant = conn.prepareCall("{call deleteMerchant(?)}")) {

            // Add calls to batch
            for (Long id : ids) {
                try {
                    cs_UpdateMerchant.setLong(1, id);
                    cs_UpdateMerchant.addBatch();
                } catch (SQLException ex) {
                    DBUtil.showErrorMessage(ex);
                }
            }

            // Execute batch!
            cs_UpdateMerchant.executeBatch();
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }
}
