package com.codecentric.retailbank.repository.JDBC;

import com.codecentric.retailbank.model.domain.Address;
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
public class AddressRepositoryJDBC extends JDBCRepositoryUtilities implements JDBCRepositoryBase<Address, Long> {

    @Override public List findAllOrDefault() {
        ResultSet resultSet = null;
        List<Address> addresses = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_allAddresses = conn.prepareCall("{call allAddresses()}")) {

            // Retrieve findAll addresses
            cs_allAddresses.execute();

            // Transform each ResultSet row into Address model and add to "addresses" list
            resultSet = cs_allAddresses.getResultSet();
            while (resultSet.next()) {
                addresses.add(
                        new Address(
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

        return addresses.size() < 1 ? null : addresses;
    }

    @Override public List findAll() {
        ResultSet resultSet = null;
        List<Address> addresses = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_allAddresses = conn.prepareCall("{call allAddresses()}")) {

            // Retrieve findAll addresses
            cs_allAddresses.execute();

            // Transform each ResultSet row into Address model and add to "addresses" list
            resultSet = cs_allAddresses.getResultSet();
            byte rowCounter = 0;
            while (resultSet.next()) {
                ++rowCounter;
                addresses.add(
                        new Address(
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

            if (rowCounter < 1)
                throw new SourceCollectionIsEmptyException("The ResultSet does contain not any rows.");

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            closeConnections(resultSet);
        }

        return addresses.size() < 1 ? null : addresses;
    }


    @Override public ListPage findAllRangeOrDefault(int pageIndex, int pageSize) {
        ResultSet resultSet = null;
        ListPage<Address> addressListPage = new ListPage<>();

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_allAddressesRange = conn.prepareCall("{call allAddressesRange(?,?)}");
             CallableStatement cs_allAddressesCount = conn.prepareCall("{call allAddressesCount()}")) {

            // Retrieve findAll addresses
            cs_allAddressesRange.setInt(1, Math.abs(pageIndex * pageSize));
            cs_allAddressesRange.setInt(2, Math.abs(pageSize));
            cs_allAddressesRange.execute();

            // Transform each ResultSet row into Address model and add to "addresses" list
            resultSet = cs_allAddressesRange.getResultSet();
            List<Address> addressList = new ArrayList<>();
            while (resultSet.next()) {
                addressList.add(
                        new Address(
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

            // Get the total number of addresses in DB
            cs_allAddressesCount.execute();
            resultSet = cs_allAddressesCount.getResultSet();
            while (resultSet.next())
                addressListPage.setPageCount(resultSet.getLong(1), pageSize);

            // Add addresses to ListPage transfer object
            addressListPage.setModels(addressList);

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            closeConnections(resultSet);
        }

        return addressListPage.getModels().size() < 1 ? null : addressListPage;
    }

    @Override public ListPage findAllRange(int pageIndex, int pageSize) {
        ResultSet resultSet = null;
        ListPage<Address> addressListPage = new ListPage<>();

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_allAddressesRange = conn.prepareCall("{call allAddressesRange(?, ?)}");
             CallableStatement cs_allAddressesCount = conn.prepareCall("{call allAddressesCount()}")) {

            // Retrieve findAll addresses
            cs_allAddressesRange.setInt(1, Math.abs(pageIndex * pageSize));
            cs_allAddressesRange.setInt(2, Math.abs(pageSize));
            cs_allAddressesRange.execute();

            // Transform each ResultSet row into Address model and add to "addresses" list
            resultSet = cs_allAddressesRange.getResultSet();
            List<Address> addressList = new ArrayList<>();
            byte rowCounter = 0;
            while (resultSet.next()) {
                ++rowCounter;
                addressList.add(
                        new Address(
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
            if (rowCounter < 1)
                throw new SourceCollectionIsEmptyException("The ResultSet does contain not any rows.");

            // Get the total number of addresses in DB
            cs_allAddressesCount.execute();
            resultSet = cs_allAddressesCount.getResultSet();
            rowCounter = 0;
            while (resultSet.next()) {
                ++rowCounter;
                addressListPage.setPageCount(resultSet.getLong(1), pageSize);
            }
            if (rowCounter < 1)
                throw new SourceCollectionIsEmptyException("The ResultSet does contain not any rows.");

            // Add addresses to ListPage transfer object
            addressListPage.setModels(addressList);

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            closeConnections(resultSet);
        }

        return addressListPage;
    }


    @Override public Address getSingleOrDefault(Long id) {
        if (id == null)
            throw new ArgumentNullException("The id argument must have a value/cannot be null.");

        Address address = null;
        ResultSet resultSet = null;

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_singleAddress = conn.prepareCall("{call singleAddress(?)}")) {

            // Retrieve a getSingle address
            cs_singleAddress.setLong(1, id);
            cs_singleAddress.execute();

            // Transform ResultSet row into a Address model
            byte rowCounter = 0;
            resultSet = cs_singleAddress.getResultSet();
            while (resultSet.next()) {

                // Check if more than one element matches id parameter
                ++rowCounter;
                if (rowCounter > 1)
                    throw new InvalidOperationException("The ResultSet does not contain exactly one row.");

                // Transform ResultSet row into a Address object
                address = new Address(
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

        return address;
    }

    @Override public Address getSingle(Long id) {
        if (id == null)
            throw new ArgumentNullException("The id argument must have a value/cannot be null.");

        Address address = null;
        ResultSet resultSet = null;

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_singleAddress = conn.prepareCall("{call singleAddress(?)}")) {

            // Retrieve a getSingle address
            cs_singleAddress.setLong(1, id);
            cs_singleAddress.execute();

            // Transform ResultSet row into a Address model
            byte rowCounter = 0;
            resultSet = cs_singleAddress.getResultSet();
            while (resultSet.next()) {

                // Check if more than one element matches id parameter
                ++rowCounter;
                if (rowCounter > 1)
                    throw new InvalidOperationException("The ResultSet does not contain exactly one row.");

                // Transform ResultSet row into a Address object
                address = new Address(
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
            if (rowCounter < 1)
                throw new SourceCollectionIsEmptyException("The ResultSet does not contain any rows.");

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            closeConnections(resultSet);
        }

        return address;
    }


    public Address getSingleByLine1OrDefault(String line1) {
        if (line1 == null)
            throw new ArgumentNullException("The id argument must have a value/cannot be null.");

        Address address = null;
        ResultSet resultSet = null;

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_singleAddress = conn.prepareCall("{call singleAddressByLine1(?)}")) {

            // Retrieve a getSingle address
            cs_singleAddress.setString(1, line1);
            cs_singleAddress.execute();

            // Transform ResultSet row into a Address model
            byte rowCounter = 0;
            resultSet = cs_singleAddress.getResultSet();
            while (resultSet.next()) {

                // Check if more than one element matches id parameter
                ++rowCounter;
                if (rowCounter > 1)
                    throw new InvalidOperationException("The ResultSet does not contain exactly one row.");

                // Transform ResultSet row into a Address object
                address = new Address(
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

        return address;
    }

    public Address getSingleByLine1(String line1) {
        if (line1 == null)
            throw new ArgumentNullException("The id argument must have a value/cannot be null.");

        Address address = null;
        ResultSet resultSet = null;

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_singleAddress = conn.prepareCall("{call singleAddressByLine1(?)}")) {

            // Retrieve a getSingle address
            cs_singleAddress.setString(1, line1);
            cs_singleAddress.execute();

            // Transform ResultSet row into a Address model
            byte rowCounter = 0;
            resultSet = cs_singleAddress.getResultSet();
            while (resultSet.next()) {

                // Check if more than one element matches id parameter
                ++rowCounter;
                if (rowCounter > 1)
                    throw new InvalidOperationException("The ResultSet does not contain exactly one row.");

                // Transform ResultSet row into a Address object
                address = new Address(
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
            if (rowCounter < 1)
                throw new SourceCollectionIsEmptyException("The ResultSet does not contain any rows.");

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            closeConnections(resultSet);
        }

        return address;
    }


    @Override public Address add(Address model) {
        if (model == null)
            throw new ArgumentNullException("The model argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_addAddress = conn.prepareCall("{call addAddress(?,?,?,?,?,?,?)}")) {

            // Add an address to DB
            cs_addAddress.setString(1, model.getLine1());
            cs_addAddress.setString(2, model.getLine2());
            cs_addAddress.setString(3, model.getTownCity());
            cs_addAddress.setString(4, model.getZipPostcode());
            cs_addAddress.setString(5, model.getStateProvinceCountry());
            cs_addAddress.setString(6, model.getCountry());
            cs_addAddress.setString(7, model.getOtherDetails());
            cs_addAddress.execute();

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }

        return model;
    }

    @Override public Address update(Address model) {
        if (model == null)
            throw new ArgumentNullException("The model argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_updateAddress = conn.prepareCall("{call updateAddress(?,?,?,?,?,?,?,?)}")) {

            // Update an existing address in DB
            cs_updateAddress.setLong(1, model.getId());
            cs_updateAddress.setString(2, model.getLine1());
            cs_updateAddress.setString(3, model.getLine2());
            cs_updateAddress.setString(4, model.getTownCity());
            cs_updateAddress.setString(5, model.getZipPostcode());
            cs_updateAddress.setString(6, model.getStateProvinceCountry());
            cs_updateAddress.setString(7, model.getCountry());
            cs_updateAddress.setString(8, model.getOtherDetails());
            cs_updateAddress.execute();

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }

        return model;
    }


    @Override public void deleteOrDefault(Address model) {
        if (model == null)
            throw new ArgumentNullException("The model argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_deleteAddress = conn.prepareCall("{call deleteAddress(?)}")) {

            // Delete an existing address
            cs_deleteAddress.setLong(1, model.getId());
            cs_deleteAddress.execute();

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }

    @Override public void delete(Address model) {
        if (model == null)
            throw new ArgumentNullException("The model argument must have a value/cannot be null.");

        ResultSet resultSet = null;

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_deleteAddress = conn.prepareCall("{call deleteAddress(?)}");
             CallableStatement cs_singleAddress = conn.prepareCall("{call singleAddress(?)}")) {

            // Check if the address exists
            cs_singleAddress.setLong(1, model.getId());
            cs_singleAddress.execute();

            // Validate the result set
            resultSet = cs_singleAddress.getResultSet();
            byte rowCounter = 0;
            while (resultSet.next()) {

                // Check if more than one element matches id parameter
                ++rowCounter;
                if (rowCounter > 1)
                    throw new InvalidOperationException("The ResultSet does not contain exactly one row.");
            }

            if (rowCounter < 1)
                throw new SourceCollectionIsEmptyException("The ResultSet does contain not any rows.");

            // Delete the existing address
            cs_deleteAddress.setLong(1, model.getId());
            cs_deleteAddress.execute();

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
             CallableStatement cs_deleteAddress = conn.prepareCall("{call deleteAddress(?)}")) {

            // Delete an existing address
            cs_deleteAddress.setLong(1, id);
            cs_deleteAddress.execute();

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }

    @Override public void deleteById(Long id) {
        if (id == null)
            throw new ArgumentNullException("The id argument must have a value/cannot be null.");

        ResultSet resultSet = null;

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_deleteAddress = conn.prepareCall("{call deleteAddress(?)}");
             CallableStatement cs_singleAddress = conn.prepareCall("{call singleAddress(?)}")) {

            // Check if the address exists
            cs_singleAddress.setLong(1, id);
            cs_singleAddress.execute();

            // Validate the result set
            resultSet = cs_singleAddress.getResultSet();
            byte rowCounter = 0;
            while (resultSet.next()) {

                // Check if more than one element matches id parameter
                ++rowCounter;
                if (rowCounter > 1)
                    throw new InvalidOperationException("The ResultSet does not contain exactly one row.");
            }

            if (rowCounter < 1)
                throw new SourceCollectionIsEmptyException("The ResultSet does contain not any rows.");

            // Delete the existing address
            cs_deleteAddress.setLong(1, id);
            cs_deleteAddress.execute();

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            closeConnections(resultSet);
        }
    }


    @Override public void insertBatch(Iterable<Address> models) {
        if (models == null)
            throw new ArgumentNullException("The models argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_addAddress = conn.prepareCall("{call addAddress(?,?,?,?,?,?,?)}")) {

            // Add calls to batch
            for (Address model : models) {
                try {
                    cs_addAddress.setString(1, model.getLine1());
                    cs_addAddress.setString(2, model.getLine2());
                    cs_addAddress.setString(3, model.getTownCity());
                    cs_addAddress.setString(4, model.getZipPostcode());
                    cs_addAddress.setString(5, model.getStateProvinceCountry());
                    cs_addAddress.setString(6, model.getCountry());
                    cs_addAddress.setString(7, model.getOtherDetails());
                    cs_addAddress.addBatch();
                } catch (SQLException ex) {
                    DBUtil.showErrorMessage(ex);
                }
            }

            // Execute batch!
            cs_addAddress.executeBatch();
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }

    @Override public void updateBatch(Iterable<Address> models) {
        if (models == null)
            throw new ArgumentNullException("The models argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_updateAddress = conn.prepareCall("{call updateAddress(?,?,?,?,?,?,?,?)}")) {

            // Add calls to batch
            for (Address model : models) {
                try {
                    cs_updateAddress.setLong(1, model.getId());
                    cs_updateAddress.setString(2, model.getLine1());
                    cs_updateAddress.setString(3, model.getLine2());
                    cs_updateAddress.setString(4, model.getTownCity());
                    cs_updateAddress.setString(5, model.getZipPostcode());
                    cs_updateAddress.setString(6, model.getStateProvinceCountry());
                    cs_updateAddress.setString(7, model.getCountry());
                    cs_updateAddress.setString(8, model.getOtherDetails());
                    cs_updateAddress.addBatch();
                } catch (SQLException ex) {
                    DBUtil.showErrorMessage(ex);
                }
            }

            // Execute batch!
            cs_updateAddress.executeBatch();
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }

    @Override public void deleteBatch(Iterable<Address> models) {
        if (models == null)
            throw new ArgumentNullException("The models argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cstmtUpdateBank = conn.prepareCall("{call deleteAddresses(?)}")) {

            // Add calls to batch
            for (Address model : models) {
                try {
                    cstmtUpdateBank.setLong(1, model.getId());
                    cstmtUpdateBank.addBatch();
                } catch (SQLException ex) {
                    DBUtil.showErrorMessage(ex);
                }
            }

            // Execute batch!
            cstmtUpdateBank.executeBatch();
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }

    @Override public void deleteBatchByIds(Iterable<Long> ids) {
        if (ids == null)
            throw new ArgumentNullException("The ids argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_deleteAddress = conn.prepareCall("{call deleteAddress(?)}")) {

            // Add calls to batch
            for (Long id : ids) {
                try {
                    cs_deleteAddress.setLong(1, id);
                    cs_deleteAddress.addBatch();
                } catch (SQLException ex) {
                    DBUtil.showErrorMessage(ex);
                }
            }

            // Execute batch!
            cs_deleteAddress.executeBatch();
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }
}
