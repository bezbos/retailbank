package com.codecentric.retailbank.repository;

import com.codecentric.retailbank.model.domain.Address;
import com.codecentric.retailbank.model.domain.Bank;
import com.codecentric.retailbank.model.domain.Branch;
import com.codecentric.retailbank.model.domain.RefBranchType;
import com.codecentric.retailbank.repository.configuration.DBType;
import com.codecentric.retailbank.repository.configuration.DBUtil;
import com.codecentric.retailbank.repository.exceptions.ArgumentNullException;
import com.codecentric.retailbank.repository.exceptions.InvalidOperationException;
import com.codecentric.retailbank.repository.exceptions.SourceCollectionIsEmptyException;
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
public class BranchRepository extends JDBCRepositoryUtilities implements JDBCRepositoryBase<Branch, Long> {

    @Override public List<Branch> findAll() {
        ResultSet resultSet = null;
        List<Branch> branches = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_allBranches = conn.prepareCall("{call allBranches()}")) {

            // Retrieve findAll branches
            cs_allBranches.execute();

            // Transform each ResultSet row into Branches model and add to "branches" list
            resultSet = cs_allBranches.getResultSet();
            while (resultSet.next()) {

                Address address = new Address(
                        resultSet.getLong("branches.address_id"),
                        resultSet.getString("addresses.line_1"),
                        resultSet.getString("addresses.line_2"),
                        resultSet.getString("addresses.town_city"),
                        resultSet.getString("addresses.zip_postcode"),
                        resultSet.getString("addresses.state_province_country"),
                        resultSet.getString("addresses.country"),
                        resultSet.getString("addresses.other_details")
                );

                Bank bank = new Bank(
                        resultSet.getLong("branches.bank_id"),
                        resultSet.getString("banks.bank_details")
                );

                RefBranchType refBranchType = new RefBranchType(
                        resultSet.getLong("branches.branch_type_id"),
                        resultSet.getString("ref_branch_types.branch_type_code"),
                        resultSet.getString("ref_branch_types.branch_type_description"),
                        resultSet.getString("ref_branch_types.large_urban"),
                        resultSet.getString("ref_branch_types.small_rural"),
                        resultSet.getString("ref_branch_types.medium_suburban")
                );

                Branch branch = new Branch(
                        resultSet.getLong("branches.branch_id"),
                        address,
                        bank,
                        refBranchType,
                        resultSet.getString("branches.branch_details")
                );

                branches.add(branch);
            }

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            closeConnections(resultSet);
        }

        return branches.size() < 1 ? null : branches;
    }

    @Override public ListPage<Branch> findAllRange(int pageIndex, int pageSize) {
        ResultSet resultSet = null;
        ListPage<Branch> branchListPage = new ListPage<>();

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_allBranchesRange = conn.prepareCall("{call allBranchesRange(?,?)}");
             CallableStatement cs_allBranchesCount = conn.prepareCall("{call allBranchesCount()}")) {

            // Retrieve findAll branches
            cs_allBranchesRange.setInt(1, Math.abs(pageIndex * pageSize));
            cs_allBranchesRange.setInt(2, Math.abs(pageSize));
            cs_allBranchesRange.execute();

            // Transform each ResultSet row into Branch model and add to "branches" list
            resultSet = cs_allBranchesRange.getResultSet();
            List<Branch> branches = new ArrayList<>();
            while (resultSet.next()) {

                Address address = new Address(
                        resultSet.getLong("branches.address_id"),
                        resultSet.getString("addresses.line_1"),
                        resultSet.getString("addresses.line_2"),
                        resultSet.getString("addresses.town_city"),
                        resultSet.getString("addresses.zip_postcode"),
                        resultSet.getString("addresses.state_province_country"),
                        resultSet.getString("addresses.country"),
                        resultSet.getString("addresses.other_details")
                );

                Bank bank = new Bank(
                        resultSet.getLong("branches.bank_id"),
                        resultSet.getString("banks.bank_details")
                );

                RefBranchType refBranchType = new RefBranchType(
                        resultSet.getLong("branches.branch_type_id"),
                        resultSet.getString("ref_branch_types.branch_type_code"),
                        resultSet.getString("ref_branch_types.branch_type_description"),
                        resultSet.getString("ref_branch_types.large_urban"),
                        resultSet.getString("ref_branch_types.small_rural"),
                        resultSet.getString("ref_branch_types.medium_suburban")
                );

                Branch branch = new Branch(
                        resultSet.getLong("branches.branch_id"),
                        address,
                        bank,
                        refBranchType,
                        resultSet.getString("branches.branch_details")
                );

                branches.add(branch);
            }

            // Get the total number of branches in DB
            cs_allBranchesCount.execute();
            resultSet = cs_allBranchesCount.getResultSet();
            while (resultSet.next())
                branchListPage.setPageCount(resultSet.getLong(1), pageSize);

            // Add branches to ListPage transfer object
            branchListPage.setModels(branches);

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            closeConnections(resultSet);
        }

        return branchListPage.getModels().size() < 1 ? null : branchListPage;
    }

    @Override public Branch getSingle(Long id) {
        if (id == null)
            throw new ArgumentNullException("The id argument must have a value/cannot be null.");

        Branch branch = null;
        ResultSet resultSet = null;

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_singleBranch = conn.prepareCall("{call singleBranch(?)}")) {

            // Retrieve a getSingle branch
            cs_singleBranch.setLong(1, id);
            cs_singleBranch.execute();

            // Transform ResultSet row into a Branch model
            byte rowCounter = 0;
            resultSet = cs_singleBranch.getResultSet();
            while (resultSet.next()) {

                // Check if more than one element matches id parameter
                ++rowCounter;
                if (rowCounter > 1)
                    throw new InvalidOperationException("The ResultSet does not contain exactly one row.");

                // Transform ResultSet row into a Branch object
                Address address = new Address(
                        resultSet.getLong("branches.address_id"),
                        resultSet.getString("addresses.line_1"),
                        resultSet.getString("addresses.line_2"),
                        resultSet.getString("addresses.town_city"),
                        resultSet.getString("addresses.zip_postcode"),
                        resultSet.getString("addresses.state_province_country"),
                        resultSet.getString("addresses.country"),
                        resultSet.getString("addresses.other_details")
                );

                Bank bank = new Bank(
                        resultSet.getLong("branches.bank_id"),
                        resultSet.getString("banks.bank_details")
                );

                RefBranchType refBranchType = new RefBranchType(
                        resultSet.getLong("branches.branch_type_id"),
                        resultSet.getString("ref_branch_types.branch_type_code"),
                        resultSet.getString("ref_branch_types.branch_type_description"),
                        resultSet.getString("ref_branch_types.large_urban"),
                        resultSet.getString("ref_branch_types.small_rural"),
                        resultSet.getString("ref_branch_types.medium_suburban")
                );

                branch = new Branch(
                        resultSet.getLong("branches.branch_id"),
                        address,
                        bank,
                        refBranchType,
                        resultSet.getString("branches.branch_details")
                );
            }
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            closeConnections(resultSet);
        }

        return branch;
    }

    public Branch getSingleByDetails(String details) {
        if (details == null)
            throw new ArgumentNullException("The details argument must have a value/cannot be null.");

        Branch branch = null;
        ResultSet resultSet = null;

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_singleBranch = conn.prepareCall("{call singleBranchByDetails(?)}")) {

            // Retrieve a getSingle branch
            cs_singleBranch.setString(1, details);
            cs_singleBranch.execute();

            // Transform ResultSet row into a Branch model
            byte rowCounter = 0;
            resultSet = cs_singleBranch.getResultSet();
            while (resultSet.next()) {

                // Check if more than one element matches details parameter
                ++rowCounter;
                if (rowCounter > 1)
                    throw new InvalidOperationException("The ResultSet does not contain exactly one row.");

                // Transform ResultSet row into a Branch object
                Address address = new Address(
                        resultSet.getLong("branches.address_id"),
                        resultSet.getString("addresses.line_1"),
                        resultSet.getString("addresses.line_2"),
                        resultSet.getString("addresses.town_city"),
                        resultSet.getString("addresses.zip_postcode"),
                        resultSet.getString("addresses.state_province_country"),
                        resultSet.getString("addresses.country"),
                        resultSet.getString("addresses.other_details")
                );

                Bank bank = new Bank(
                        resultSet.getLong("branches.bank_id"),
                        resultSet.getString("banks.bank_details")
                );

                RefBranchType refBranchType = new RefBranchType(
                        resultSet.getLong("branches.branch_type_id"),
                        resultSet.getString("ref_branch_types.branch_type_code"),
                        resultSet.getString("ref_branch_types.branch_type_description"),
                        resultSet.getString("ref_branch_types.large_urban"),
                        resultSet.getString("ref_branch_types.small_rural"),
                        resultSet.getString("ref_branch_types.medium_suburban")
                );

                branch = new Branch(
                        resultSet.getLong("branches.branch_id"),
                        address,
                        bank,
                        refBranchType,
                        resultSet.getString("branches.branch_details")
                );
            }
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            closeConnections(resultSet);
        }

        return branch;
    }

    @Override public Branch add(Branch model) {
        if (model == null)
            throw new ArgumentNullException("The model argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_addBranch = conn.prepareCall("{call addBranch(?,?,?,?)}")) {

            // Add an branch to DB
            if (model.getAddress() == null)
                cs_addBranch.setNull("p_address_id", Types.BIGINT);
            else
                cs_addBranch.setLong("p_address_id", model.getAddress().getId());
            cs_addBranch.setLong("p_bank_id", model.getBank().getId());
            cs_addBranch.setLong("p_branch_type_id", model.getRefBranchType().getId());
            cs_addBranch.setString("p_branch_details", model.getDetails());
            cs_addBranch.execute();

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }

        return model;
    }

    @Override public Branch update(Branch model) {
        if (model == null)
            throw new ArgumentNullException("The model argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_addBranch = conn.prepareCall("{call updateBranch(?,?,?,?,?)}")) {

            // Add a branch to DB
            cs_addBranch.setLong("p_branch_id", model.getId());
            if (model.getAddress() == null)
                cs_addBranch.setNull("p_address_id", Types.BIGINT);
            else
                cs_addBranch.setLong("p_address_id", model.getAddress().getId());
            cs_addBranch.setLong("p_bank_id", model.getBank().getId());
            cs_addBranch.setLong("p_branch_type_id", model.getRefBranchType().getId());
            cs_addBranch.setString("p_branch_details", model.getDetails());
            cs_addBranch.execute();

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }

        return model;
    }

    @Override public void delete(Branch model) {
        if (model == null)
            throw new ArgumentNullException("The model argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_deleteBranch = conn.prepareCall("{call deleteBranch(?)}")) {

            // Delete an existing branch
            cs_deleteBranch.setLong(1, model.getId());
            cs_deleteBranch.execute();

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }

    @Override public void deleteById(Long id) {
        if (id == null)
            throw new ArgumentNullException("The id argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_deleteBranch = conn.prepareCall("{call deleteBranch(?)}")) {

            // Delete an existing branch
            cs_deleteBranch.setLong(1, id);
            cs_deleteBranch.execute();

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }

    @Override public void insertBatch(Iterable<Branch> models) {
        if (models == null)
            throw new ArgumentNullException("The models argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_addBranch = conn.prepareCall("{call addBranch(?,?,?,?)}")) {

            // Add calls to batch
            for (Branch model : models) {
                try {
                    if (model.getAddress() == null)
                        cs_addBranch.setNull("p_address_id", Types.BIGINT);
                    else
                        cs_addBranch.setLong("p_address_id", model.getAddress().getId());
                    cs_addBranch.setLong("p_bank_id", model.getBank().getId());
                    cs_addBranch.setLong("p_branch_type_id", model.getRefBranchType().getId());
                    cs_addBranch.setString("p_branch_details", model.getDetails());
                    cs_addBranch.addBatch();
                } catch (SQLException ex) {
                    DBUtil.showErrorMessage(ex);
                }
            }

            // Execute batch!
            cs_addBranch.executeBatch();
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }

    @Override public void updateBatch(Iterable<Branch> models) {
        if (models == null)
            throw new ArgumentNullException("The models argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_addBranch = conn.prepareCall("{call updateBranch(?,?,?,?,?)}")) {

            // Add calls to batch
            for (Branch model : models) {
                try {
                    cs_addBranch.setLong("p_branch_id", model.getId());
                    if (model.getAddress() == null)
                        cs_addBranch.setNull("p_address_id", Types.BIGINT);
                    else
                        cs_addBranch.setLong("p_address_id", model.getAddress().getId());
                    cs_addBranch.setLong("p_bank_id", model.getBank().getId());
                    cs_addBranch.setLong("p_branch_type_id", model.getRefBranchType().getId());
                    cs_addBranch.setString("p_branch_details", model.getDetails());
                    cs_addBranch.addBatch();
                } catch (SQLException ex) {
                    DBUtil.showErrorMessage(ex);
                }
            }

            // Execute batch!
            cs_addBranch.executeBatch();
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }

    @Override public void deleteBatch(Iterable<Branch> models) {
        if (models == null)
            throw new ArgumentNullException("The models argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_deleteBranches = conn.prepareCall("{call deleteBranches(?)}")) {

            // Add calls to batch
            for (Branch model : models) {
                try {
                    cs_deleteBranches.setLong(1, model.getId());
                    cs_deleteBranches.addBatch();
                } catch (SQLException ex) {
                    DBUtil.showErrorMessage(ex);
                }
            }

            // Execute batch!
            cs_deleteBranches.executeBatch();
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }

    @Override public void deleteBatchByIds(Iterable<Long> ids) {
        if (ids == null)
            throw new ArgumentNullException("The ids argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_deleteBranches = conn.prepareCall("{call deleteBranches(?)}")) {

            // Add calls to batch
            for (Long id : ids) {
                try {
                    cs_deleteBranches.setLong(1, id);
                    cs_deleteBranches.addBatch();
                } catch (SQLException ex) {
                    DBUtil.showErrorMessage(ex);
                }
            }

            // Execute batch!
            cs_deleteBranches.executeBatch();
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }

    public List<Branch> getAllByAddressId(Long id) {
        if (id == null)
            throw new ArgumentNullException("The id argument must have a value/cannot be null.");

        List<Branch> branches = null;
        ResultSet resultSet = null;

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_allBranchesByAddressId = conn.prepareCall("{call allBranchesByAddressId(?)}")) {

            // Retrieve a branches
            cs_allBranchesByAddressId.setLong(1, id);
            cs_allBranchesByAddressId.execute();

            // Transform ResultSet row into a Branch model
            resultSet = cs_allBranchesByAddressId.getResultSet();
            branches = new ArrayList<>();
            byte rowCounter = 0;
            while (resultSet.next()) {
                ++rowCounter;

                // Transform ResultSet row into a Branch object
                Address address = new Address(
                        resultSet.getLong("branches.address_id"),
                        resultSet.getString("addresses.line_1"),
                        resultSet.getString("addresses.line_2"),
                        resultSet.getString("addresses.town_city"),
                        resultSet.getString("addresses.zip_postcode"),
                        resultSet.getString("addresses.state_province_country"),
                        resultSet.getString("addresses.country"),
                        resultSet.getString("addresses.other_details")
                );

                Bank bank = new Bank(
                        resultSet.getLong("branches.bank_id"),
                        resultSet.getString("banks.bank_details")
                );

                RefBranchType refBranchType = new RefBranchType(
                        resultSet.getLong("branches.branch_type_id"),
                        resultSet.getString("ref_branch_types.branch_type_code"),
                        resultSet.getString("ref_branch_types.branch_type_description"),
                        resultSet.getString("ref_branch_types.large_urban"),
                        resultSet.getString("ref_branch_types.small_rural"),
                        resultSet.getString("ref_branch_types.medium_suburban")
                );

                Branch branch = new Branch(
                        resultSet.getLong("branches.branch_id"),
                        address,
                        bank,
                        refBranchType,
                        resultSet.getString("branches.branch_details")
                );

                branches.add(branch);
            }
            if (rowCounter < 1)
                throw new SourceCollectionIsEmptyException("The ResultSet does not contain any rows.");

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            closeConnections(resultSet);
        }

        return branches;
    }
}
