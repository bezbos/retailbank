package com.codecentric.retailbank.repository;

import com.codecentric.retailbank.exception.nullpointer.ArgumentNullException;
import com.codecentric.retailbank.exception.nullpointer.InvalidOperationException;
import com.codecentric.retailbank.model.domain.Address;
import com.codecentric.retailbank.model.domain.Branch;
import com.codecentric.retailbank.model.domain.Customer;
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
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomerRepository extends JDBCRepositoryUtilities implements JDBCRepositoryBase<Customer, Long> {

    //region READ
    @Override public List<Customer> all() {
        ResultSet resultSet = null;
        List<Customer> customers = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_allCustomers = conn.prepareCall("{call allCustomers()}")) {

            // Retrieve all customers
            cs_allCustomers.execute();

            // Transform each ResultSet row into a Customer model and add to "customers" list
            resultSet = cs_allCustomers.getResultSet();
            while (resultSet.next()) {

                Address address = new Address(
                        resultSet.getLong("customers.address_id"),
                        resultSet.getString("addresses.line_1")
                );

                Branch branch = new Branch(
                        resultSet.getLong("customers.branch_id"),
                        resultSet.getString("branches.branch_details")
                );

                Customer customer = new Customer(
                        resultSet.getLong("customers.customer_id"),
                        address,
                        branch,
                        resultSet.getString("customers.personal_details"),
                        resultSet.getString("customers.contact_details")
                );

                customers.add(customer);
            }

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            closeConnections(resultSet);
        }

        return customers;
    }

    @Override public ListPage<Customer> allRange(int pageIndex, int pageSize) {
        ResultSet resultSet = null;
        ListPage<Customer> customerListPage = new ListPage<>();

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_allCustomersRange = conn.prepareCall("{call allCustomersRange(?,?)}");
             CallableStatement cs_allCustomersCount = conn.prepareCall("{call allCustomersCount()}")) {

            // Retrieve all customers
            cs_allCustomersRange.setInt(1, Math.abs(pageIndex * pageSize));
            cs_allCustomersRange.setInt(2, Math.abs(pageSize));
            cs_allCustomersRange.execute();

            // Transform each ResultSet row into a Customer model and add to "customers" list
            resultSet = cs_allCustomersRange.getResultSet();
            List<Customer> customers = new ArrayList<>();
            while (resultSet.next()) {

                Address address = new Address(
                        resultSet.getLong("customers.address_id"),
                        resultSet.getString("addresses.line_1")
                );

                Branch branch = new Branch(
                        resultSet.getLong("customers.branch_id"),
                        resultSet.getString("branches.branch_details")
                );

                Customer customer = new Customer(
                        resultSet.getLong("customers.customer_id"),
                        address,
                        branch,
                        resultSet.getString("customers.personal_details"),
                        resultSet.getString("customers.contact_details")
                );

                customers.add(customer);
            }

            // Get the total number of customers in DB
            cs_allCustomersCount.execute();
            resultSet = cs_allCustomersCount.getResultSet();
            while (resultSet.next())
                customerListPage.setPageCount(resultSet.getLong(1), pageSize);

            // Add customers to ListPage transfer object
            customerListPage.setModels(customers);

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            closeConnections(resultSet);
        }

        return customerListPage;
    }

    @Override public Customer single(Long id) {
        if (id == null)
            throw new ArgumentNullException("The id argument must have a value/cannot be null.");

        Customer customer = null;
        ResultSet resultSet = null;

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_singleCustomer = conn.prepareCall("{call singleCustomer(?)}")) {

            // Retrieve a single customer
            cs_singleCustomer.setLong(1, id);
            cs_singleCustomer.execute();

            // Transform ResultSet row into a Branch model
            byte rowCounter = 0;
            resultSet = cs_singleCustomer.getResultSet();
            while (resultSet.next()) {

                // Check if more than one element matches id parameter
                ++rowCounter;
                if (rowCounter > 1)
                    throw new InvalidOperationException("The ResultSet does not contain exactly one row.");

                // Transform ResultSet row into a Branch object
                Address address = new Address(
                        resultSet.getLong("customers.address_id"),
                        resultSet.getString("addresses.line_1")
                );

                Branch branch = new Branch(
                        resultSet.getLong("customers.branch_id"),
                        resultSet.getString("branches.branch_details")
                );

                customer = new Customer(
                        resultSet.getLong("customers.customer_id"),
                        address,
                        branch,
                        resultSet.getString("customers.personal_details"),
                        resultSet.getString("customers.contact_details")
                );

            }
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            closeConnections(resultSet);
        }

        return customer;
    }

    public Customer getSingleByPersonalDetails(String personalDetails) {
        if (personalDetails == null)
            throw new ArgumentNullException("The personalDetails argument must have a value/cannot be null.");

        Customer customer = null;
        ResultSet resultSet = null;

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_singleCustomer = conn.prepareCall("{call singleCustomerByPersonalDetails(?)}")) {

            // Retrieve a single customer
            cs_singleCustomer.setString(1, personalDetails);
            cs_singleCustomer.execute();

            // Transform ResultSet row into a Branch model
            resultSet = cs_singleCustomer.getResultSet();
            byte rowCounter = 0;
            while (resultSet.next()) {

                // Transform ResultSet row into a Branch object
                Address address = new Address(
                        resultSet.getLong("customers.address_id"),
                        resultSet.getString("addresses.line_1")
                );

                Branch branch = new Branch(
                        resultSet.getLong("customers.branch_id"),
                        resultSet.getString("branches.branch_details")
                );

                customer = new Customer(
                        resultSet.getLong("customers.customer_id"),
                        address,
                        branch,
                        resultSet.getString("customers.personal_details"),
                        resultSet.getString("customers.contact_details")
                );

                // Check if more than one element matches id parameter
                ++rowCounter;
                if (rowCounter > 1)
                    throw new InvalidOperationException("The ResultSet does not contain exactly one row.", customer);

            }
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            closeConnections(resultSet);
        }

        return customer;
    }
    //endregion

    //region WRITE
    @Override public Customer add(Customer model) {
        if (model == null)
            throw new ArgumentNullException("The model argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_addCustomer = conn.prepareCall("{call addCustomer(?,?,?,?)}")) {

            // Add a customer to DB
            if (model.getAddress() == null)
                cs_addCustomer.setNull("p_address_id", Types.BIGINT);
            else
                cs_addCustomer.setLong("p_address_id", model.getAddress().getId());
            cs_addCustomer.setLong("p_branch_id", model.getBranch().getId());
            cs_addCustomer.setString("p_personal_details", model.getPersonalDetails());
            cs_addCustomer.setString("p_contact_details", model.getContactDetails());
            cs_addCustomer.execute();

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }

        return model;
    }

    @Override public Customer update(Customer model) {
        if (model == null)
            throw new ArgumentNullException("The model argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_updateCustomer = conn.prepareCall("{call updateCustomer(?,?,?,?,?)}")) {

            // Update an existing customer
            cs_updateCustomer.setLong("p_customer_id", model.getId());
            if (model.getAddress() == null)
                cs_updateCustomer.setNull("p_address_id", Types.BIGINT);
            else
                cs_updateCustomer.setLong("p_address_id", model.getAddress().getId());
            cs_updateCustomer.setLong("p_branch_id", model.getBranch().getId());
            cs_updateCustomer.setString("p_personal_details", model.getPersonalDetails());
            cs_updateCustomer.setString("p_contact_details", model.getContactDetails());
            cs_updateCustomer.execute();

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }

        return model;
    }

    @Override public void insertBatch(Iterable<Customer> models) {
        if (models == null)
            throw new ArgumentNullException("The models argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_addCustomer = conn.prepareCall("{call addCustomer(?,?,?,?)}")) {

            // Add calls to batch
            for (Customer model : models) {
                try {
                    if (model.getAddress() == null)
                        cs_addCustomer.setNull("p_address_id", Types.BIGINT);
                    else
                        cs_addCustomer.setLong("p_address_id", model.getAddress().getId());
                    cs_addCustomer.setLong("p_branch_id", model.getBranch().getId());
                    cs_addCustomer.setString("p_personal_details", model.getPersonalDetails());
                    cs_addCustomer.setString("p_contact_details", model.getContactDetails());
                    cs_addCustomer.addBatch();
                } catch (SQLException ex) {
                    DBUtil.showErrorMessage(ex);
                }
            }

            // Execute batch!
            cs_addCustomer.executeBatch();
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }

    @Override public void updateBatch(Iterable<Customer> models) {
        if (models == null)
            throw new ArgumentNullException("The models argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_updateCustomer = conn.prepareCall("{call updateCustomer(?,?,?,?,?)}")) {

            // Add calls to batch
            for (Customer model : models) {
                try {
                    cs_updateCustomer.setLong("p_customer_id", model.getId());
                    if (model.getAddress() == null)
                        cs_updateCustomer.setNull("p_address_id", Types.BIGINT);
                    else
                        cs_updateCustomer.setLong("p_address_id", model.getAddress().getId());
                    cs_updateCustomer.setLong("p_branch_id", model.getBranch().getId());
                    cs_updateCustomer.setString("p_personal_details", model.getPersonalDetails());
                    cs_updateCustomer.setString("p_contact_details", model.getContactDetails());
                    cs_updateCustomer.addBatch();
                } catch (SQLException ex) {
                    DBUtil.showErrorMessage(ex);
                }
            }

            // Execute batch!
            cs_updateCustomer.executeBatch();
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }
    //endregion

    //region DELETE
    @Override public void delete(Customer model) {
        if (model == null)
            throw new ArgumentNullException("The model argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_deleteCustomer = conn.prepareCall("{call deleteCustomer(?)}")) {

            // Delete an existing customer
            cs_deleteCustomer.setLong(1, model.getId());
            cs_deleteCustomer.execute();

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }

    @Override public void deleteById(Long id) {
        if (id == null)
            throw new ArgumentNullException("The id argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_deleteCustomer = conn.prepareCall("{call deleteCustomer(?)}")) {

            // Delete an existing customer
            cs_deleteCustomer.setLong(1, id);
            cs_deleteCustomer.execute();

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }

    @Override public void deleteBatch(Iterable<Customer> models) {
        if (models == null)
            throw new ArgumentNullException("The models argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_deleteCustomers = conn.prepareCall("{call deleteCustomers(?)}")) {

            // Add calls to batch
            for (Customer model : models) {
                try {
                    cs_deleteCustomers.setLong(1, model.getId());
                    cs_deleteCustomers.addBatch();
                } catch (SQLException ex) {
                    DBUtil.showErrorMessage(ex);
                }
            }

            // Execute batch!
            cs_deleteCustomers.executeBatch();
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }

    @Override public void deleteBatchByIds(Iterable<Long> ids) {
        if (ids == null)
            throw new ArgumentNullException("The ids argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_deleteCustomers = conn.prepareCall("{call deleteCustomers(?)}")) {

            // Add calls to batch
            for (Long id : ids) {
                try {
                    cs_deleteCustomers.setLong(1, id);
                    cs_deleteCustomers.addBatch();
                } catch (SQLException ex) {
                    DBUtil.showErrorMessage(ex);
                }
            }

            // Execute batch!
            cs_deleteCustomers.executeBatch();
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }
    //endregion
}
