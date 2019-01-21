package com.codecentric.retailbank.repository.JDBC.security;

import com.codecentric.retailbank.model.security.Role;
import com.codecentric.retailbank.repository.JDBC.configuration.DBType;
import com.codecentric.retailbank.repository.JDBC.configuration.DBUtil;
import com.codecentric.retailbank.repository.JDBC.exceptions.ArgumentNullException;
import com.codecentric.retailbank.repository.JDBC.exceptions.InvalidOperationException;
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
public class RoleRepositoryJDBC extends JDBCRepositoryUtilities implements JDBCRepositoryBase<Role, Long> {

    @Override public List<Role> findAll() {
        ResultSet resultSet = null;
        List<Role> roles = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement csAllRoles = conn.prepareCall("{call allRoles()}")) {

            // Retrieve findAll roles
            csAllRoles.execute();

            // Transform each ResultSet row into Role model and add to "roles" list
            resultSet = csAllRoles.getResultSet();
            while (resultSet.next()) {
                roles.add(
                        new Role(resultSet.getLong(1), resultSet.getString(2))
                );
            }

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            closeConnections(resultSet);
        }

        return roles.size() < 1 ? null : roles;
    }

    @Override public ListPage<Role> findAllRange(int pageIndex, int pageSize) {
        ResultSet resultSet = null;
        ListPage<Role> roleListPage = new ListPage<>();

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement csAllRolesRange = conn.prepareCall("{call allRolesRange(?,?)}");
             CallableStatement csRolesCount = conn.prepareCall("{call allRolesCount()}")) {

            // Retrieve roles in a certain range
            csAllRolesRange.setInt(1, Math.abs(pageIndex * pageSize));
            csAllRolesRange.setInt(2, Math.abs(pageSize));
            csAllRolesRange.execute();

            // Transform ResultSet rows into Role models and add them into the roleListPage
            List<Role> rolesList = new ArrayList<>();
            resultSet = csAllRolesRange.getResultSet();
            while (resultSet.next()) {
                rolesList.add(
                        new Role(resultSet.getLong(1), resultSet.getString(2))
                );
            }

            // Get the total number of roles in DB
            csRolesCount.execute();
            resultSet = csRolesCount.getResultSet();
            while (resultSet.next())
                roleListPage.setPageCount(resultSet.getLong(1), pageSize);

            // Add roles to ListPage transfer object
            roleListPage.setModels(rolesList);

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            closeConnections(resultSet);
        }

        return roleListPage.getModels().size() < 1 ? null : roleListPage;
    }

    @Override public Role getSingle(Long id) {
        if (id == null)
            throw new ArgumentNullException("The id argument must have a value/cannot be null.");

        Role role = null;
        ResultSet resultSet = null;

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement csSingleRole = conn.prepareCall("{call singleRole(?)}")) {

            // Retrieve a getSingle role
            csSingleRole.setLong(1, id);
            csSingleRole.execute();

            // Transform ResultSet row into a Role model
            resultSet = csSingleRole.getResultSet();
            byte rowCounter = 0;
            while (resultSet.next()) {

                // Check if more than one element matches id parameter
                ++rowCounter;
                if (rowCounter > 1)
                    throw new InvalidOperationException("The ResultSet does not contain exactly one row.");

                // Transform ResultSet row into a Role object
                role = new Role(resultSet.getLong(1), resultSet.getString(2));
            }
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            closeConnections(resultSet);
        }

        return role;
    }

    public Role getSingleByName(String name) {
        if (name == null)
            throw new ArgumentNullException("The name argument must have a value/cannot be null.");

        Role role = null;
        ResultSet resultSet = null;

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement csSingleRole = conn.prepareCall("{call singleRoleByName(?)}")) {

            // Retrieve a getSingle role
            csSingleRole.setString(1, name);
            csSingleRole.execute();

            // Transform ResultSet row into a Role model
            resultSet = csSingleRole.getResultSet();
            byte rowCounter = 0;
            while (resultSet.next()) {

                // Check if more than one element matches id parameter
                ++rowCounter;
                if (rowCounter > 1)
                    throw new InvalidOperationException("The ResultSet does not contain exactly one row.");

                // Transform ResultSet row into a Role object
                role = new Role(resultSet.getLong(1), resultSet.getString(2));
            }
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            closeConnections(resultSet);
        }

        return role;
    }

    @Override public Role add(Role model) {
        if (model == null)
            throw new ArgumentNullException("The model argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_addRole = conn.prepareCall("{call addRole(?)}")) {

            // Add a role to DB
            cs_addRole.setString(1, model.getName());
            cs_addRole.execute();

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }

        return model;
    }

    @Override public Role update(Role model) {
        if (model == null)
            throw new ArgumentNullException("The model argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement csSingleRole = conn.prepareCall("{call updateRole(?,?)}")) {

            // Update an existing role in DB
            csSingleRole.setLong(1, model.getId());
            csSingleRole.setString(2, model.getName());
            csSingleRole.execute();

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }

        return model;
    }


    @Override public void delete(Role model) {
        if (model == null)
            throw new ArgumentNullException("The model argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement csDeleteRole = conn.prepareCall("{call deleteRole(?)}")) {

            // Delete role
            csDeleteRole.setLong(1, model.getId());
            csDeleteRole.execute();

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }

    @Override public void deleteById(Long id) {
        if (id == null)
            throw new ArgumentNullException("The id argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement csDeleteRole = conn.prepareCall("{call deleteRole(?)}")) {

            // Delete role
            csDeleteRole.setLong(1, id);
            csDeleteRole.execute();

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }

    public List<Role> getBatchByIds(Iterable<Long> ids) {
        if (ids == null)
            throw new ArgumentNullException("The ids argument must have a value/cannot be null.");

        ResultSet resultSet = null;
        List<Role> roles = null;

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_getRoles = conn.prepareCall("{call singleRole(?)}")) {

            // Add calls to batch
            for (Long id : ids) {
                try {
                    cs_getRoles.setLong(1, id);
                    cs_getRoles.addBatch();
                } catch (SQLException ex) {
                    DBUtil.showErrorMessage(ex);
                }
            }

            // Execute batch!
            cs_getRoles.executeBatch();

            // Transform ResultSet rows into roles
            resultSet = cs_getRoles.getResultSet();
            roles = new ArrayList<>();
            while (resultSet.next()) {
                roles.add(
                        new Role(
                                resultSet.getLong(1),
                                resultSet.getString(2)
                        )
                );
            }

            return roles;
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            closeConnections(resultSet);
        }

        return roles;
    }

    @Override public void insertBatch(Iterable<Role> models) {
        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_addRole = conn.prepareCall("{call addRole(?)}")) {

            // Add calls to batch
            for (Role model : models) {
                try {
                    cs_addRole.setString(1, model.getName());
                    cs_addRole.addBatch();
                } catch (SQLException ex) {
                    DBUtil.showErrorMessage(ex);
                }
            }

            // Execute batch!
            cs_addRole.executeBatch();
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }

    @Override public void updateBatch(Iterable<Role> models) {
        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_UpdateRole = conn.prepareCall("{call updateRole(?,?)}")) {

            // Add calls to batch
            for (Role model : models) {
                try {
                    cs_UpdateRole.setLong(1, model.getId());
                    cs_UpdateRole.setString(2, model.getName());
                    cs_UpdateRole.addBatch();
                } catch (SQLException ex) {
                    DBUtil.showErrorMessage(ex);
                }
            }

            // Execute batch!
            cs_UpdateRole.executeBatch();
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }

    @Override public void deleteBatch(Iterable<Role> models) {
        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_UpdateRole = conn.prepareCall("{call deleteRole(?)}")) {

            // Add calls to batch
            for (Role model : models) {
                try {
                    cs_UpdateRole.setLong(1, model.getId());
                    cs_UpdateRole.addBatch();
                } catch (SQLException ex) {
                    DBUtil.showErrorMessage(ex);
                }
            }

            // Execute batch!
            cs_UpdateRole.executeBatch();
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }

    @Override public void deleteBatchByIds(Iterable<Long> ids) {
        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_UpdateRole = conn.prepareCall("{call deleteRole(?)}")) {

            // Add calls to batch
            for (Long id : ids) {
                try {
                    cs_UpdateRole.setLong(1, id);
                    cs_UpdateRole.addBatch();
                } catch (SQLException ex) {
                    DBUtil.showErrorMessage(ex);
                }
            }

            // Execute batch!
            cs_UpdateRole.executeBatch();
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }
}