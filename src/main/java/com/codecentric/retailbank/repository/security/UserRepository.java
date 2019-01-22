package com.codecentric.retailbank.repository.security;

import com.codecentric.retailbank.model.security.Role;
import com.codecentric.retailbank.model.security.User;
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
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository extends JDBCRepositoryUtilities implements JDBCRepositoryBase<User, Long> {

    @Override public List<User> findAll() {
        ResultSet resultSet = null;
        ResultSet rolesResultSet = null;
        List<User> users = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_allUserAccounts = conn.prepareCall("{call allUserAccounts()}");
             CallableStatement cs_userRoles = conn.prepareCall("{call getUserRoles(?)}")) {

            // Retrieve all user accounts
            cs_allUserAccounts.execute();

            // Transform each ResultSet row into User model and add to "users" list
            resultSet = cs_allUserAccounts.getResultSet();
            while (resultSet.next()) {

                // Create a new roles list every iteration
                List<Role> roles = new ArrayList<>();

                // Retrieve all user roles
                cs_userRoles.setLong("p_user_account_id", resultSet.getLong("user_account.id"));
                cs_userRoles.execute();

                // Transform each ResultSet row into Role model and add to "roles" list
                rolesResultSet = cs_userRoles.getResultSet();
                while (resultSet.next()) {
                    roles.add(
                            new Role(
                                    rolesResultSet.getLong("user_role.id"),
                                    rolesResultSet.getString("user_role.role_name")
                            )
                    );
                }

                users.add(
                        new User(
                                resultSet.getLong("user_account.id"),
                                resultSet.getString("user_account.first_name"),
                                resultSet.getString("user_account.last_name"),
                                resultSet.getString("user_account.email"),
                                resultSet.getString("user_account.user_password"),
                                resultSet.getBoolean("user_account.enabled"),
                                resultSet.getBoolean("user_account.is_using2fa"),
                                resultSet.getString("user_account.secret"),
                                roles
                        )
                );
            }
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            closeConnections(resultSet, rolesResultSet);
        }

        return users.size() < 1 ? null : users;
    }

    @Override public ListPage<User> findAllRange(int pageIndex, int pageSize) {
        ResultSet resultSet = null;
        ResultSet rolesResultSet = null;
        ListPage<User> userListPage = new ListPage<>();

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_allUsersRange = conn.prepareCall("{call allUserAccountsRange(?,?)}");
             CallableStatement cs_usersCount = conn.prepareCall("{call allUserAccountsCount()}");
             CallableStatement cs_userRoles = conn.prepareCall("{call getUserRoles(?)}")) {

            // Retrieve users in a certain range
            cs_allUsersRange.setInt(1, Math.abs(pageIndex * pageSize));
            cs_allUsersRange.setInt(2, Math.abs(pageSize));
            cs_allUsersRange.execute();

            // Transform ResultSet rows into User models and add them into the userListPage
            List<User> usersList = new ArrayList<>();
            resultSet = cs_allUsersRange.getResultSet();
            while (resultSet.next()) {
                // Create a new roles list every iteration
                List<Role> roles = new ArrayList<>();

                // Retrieve all user roles
                cs_userRoles.setLong("p_user_account_id", resultSet.getLong("user_account.id"));
                cs_userRoles.execute();

                // Transform each ResultSet row into Role model and add to "roles" list
                rolesResultSet = cs_userRoles.getResultSet();
                while (rolesResultSet.next()) {
                    roles.add(
                            new Role(
                                    rolesResultSet.getLong("user_role.id"),
                                    rolesResultSet.getString("user_role.role_name")
                            )
                    );
                }

                usersList.add(
                        new User(
                                resultSet.getLong("user_account.id"),
                                resultSet.getString("user_account.first_name"),
                                resultSet.getString("user_account.last_name"),
                                resultSet.getString("user_account.email"),
                                resultSet.getString("user_account.user_password"),
                                resultSet.getBoolean("user_account.enabled"),
                                resultSet.getBoolean("user_account.is_using2fa"),
                                resultSet.getString("user_account.secret"),
                                roles
                        )
                );
            }

            // Get the total number of users in DB
            cs_usersCount.execute();
            resultSet = cs_usersCount.getResultSet();
            while (resultSet.next())
                userListPage.setPageCount(resultSet.getLong(1), pageSize);

            // Add users to ListPage transfer object
            userListPage.setModels(usersList);

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            closeConnections(resultSet, rolesResultSet);
        }

        return userListPage.getModels().size() < 1 ? null : userListPage;
    }

    @Override public User getSingle(Long id) {
        if (id == null)
            throw new ArgumentNullException("The id argument must have a value/cannot be null.");

        User user = null;
        ResultSet rolesResultSet = null;
        ResultSet resultSet = null;

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_singleUserAccount = conn.prepareCall("{call singleUserAccount(?)}");
             CallableStatement cs_userRoles = conn.prepareCall("{call getUserRoles(?)}")) {

            // Retrieve a getSingle user
            cs_singleUserAccount.setLong(1, id);
            cs_singleUserAccount.execute();

            // Transform ResultSet row into a User model
            resultSet = cs_singleUserAccount.getResultSet();
            byte rowCounter = 0;
            while (resultSet.next()) {

                // Check if more than one element matches id parameter
                ++rowCounter;
                if (rowCounter > 1)
                    throw new InvalidOperationException("The ResultSet does not contain exactly one row.");

                // Create a new roles list every iteration
                List<Role> roles = new ArrayList<>();

                // Retrieve all user roles
                cs_userRoles.setLong("p_user_account_id", resultSet.getLong("user_account.id"));
                cs_userRoles.execute();

                // Transform each ResultSet row into Role model and add to "roles" list
                rolesResultSet = cs_userRoles.getResultSet();
                while (rolesResultSet.next()) {
                    roles.add(
                            new Role(
                                    rolesResultSet.getLong("user_role.id"),
                                    rolesResultSet.getString("user_role.role_name")
                            )
                    );
                }

                user = new User(
                        resultSet.getLong("user_account.id"),
                        resultSet.getString("user_account.first_name"),
                        resultSet.getString("user_account.last_name"),
                        resultSet.getString("user_account.email"),
                        resultSet.getString("user_account.user_password"),
                        resultSet.getBoolean("user_account.enabled"),
                        resultSet.getBoolean("user_account.is_using2fa"),
                        resultSet.getString("user_account.secret"),
                        roles
                );
            }


        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            closeConnections(resultSet, rolesResultSet);
        }

        return user;
    }

    public User getSingleByUsername(String username) {
        if (username == null)
            throw new ArgumentNullException("The username argument must have a value/cannot be null.");

        User user = null;
        ResultSet resultSet = null;
        ResultSet rolesResultSet = null;

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement csSingleUser = conn.prepareCall("{call singleUserAccountByUsername(?)}");
             CallableStatement cs_userRoles = conn.prepareCall("{call getUserRoles(?)}")) {

            // Retrieve a getSingle user
            csSingleUser.setString(1, username);
            csSingleUser.execute();

            // Transform ResultSet row into a User model
            resultSet = csSingleUser.getResultSet();
            byte rowCounter = 0;
            while (resultSet.next()) {

                // Check if more than one element matches id parameter
                ++rowCounter;
                if (rowCounter > 1)
                    throw new InvalidOperationException("The ResultSet does not contain exactly one row.");

                // Create a new roles list every iteration
                List<Role> roles = new ArrayList<>();

                // Retrieve all user roles
                cs_userRoles.setLong("p_user_account_id", resultSet.getLong("user_account.id"));
                cs_userRoles.execute();

                // Transform each ResultSet row into Role model and add to "roles" list
                rolesResultSet = cs_userRoles.getResultSet();
                while (rolesResultSet.next()) {
                    roles.add(
                            new Role(
                                    rolesResultSet.getLong("user_role.id"),
                                    rolesResultSet.getString("user_role.role_name")
                            )
                    );
                }

                user = new User(
                        resultSet.getLong("user_account.id"),
                        resultSet.getString("user_account.first_name"),
                        resultSet.getString("user_account.last_name"),
                        resultSet.getString("user_account.email"),
                        resultSet.getString("user_account.user_password"),
                        resultSet.getBoolean("user_account.enabled"),
                        resultSet.getBoolean("user_account.is_using2fa"),
                        resultSet.getString("user_account.secret"),
                        roles
                );
            }
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            closeConnections(resultSet, rolesResultSet);
        }

        return user;
    }

    @Override public User add(User model) {
        if (model == null)
            throw new ArgumentNullException("The model argument must have a value/cannot be null.");

        ResultSet resultSet = null;

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_addUser = conn.prepareCall("{call addUserAccount(?,?,?,?,?,?,?,?)}");
             CallableStatement csSingleUser = conn.prepareCall("{call singleUserAccountByUsername(?)}");
             CallableStatement cs_setRoleToUser = conn.prepareCall("{call setRoleToUser(?,?)}")) {

            // Add a user to DB
            cs_addUser.setString("p_first_name", model.getFirstName());
            cs_addUser.setString("p_last_name", model.getLastName());
            cs_addUser.setString("p_email", model.getEmail());
            cs_addUser.setString("p_user_password", model.getPassword());
            cs_addUser.setBoolean("p_enabled", model.isEnabled());
            cs_addUser.setBoolean("p_is_using2fa", model.isUsing2FA());
            cs_addUser.setString("p_secret", model.getSecret());
            cs_addUser.setNull("p_last_login", Types.TIMESTAMP);
            cs_addUser.execute();

            // Retrieve the newly created user's ID
            csSingleUser.setString(1, model.getEmail());
            csSingleUser.execute();
            Long userId = null;
            resultSet = csSingleUser.getResultSet();
            while(resultSet.next())
                userId = resultSet.getLong(1);

            // Add roles to user
            cs_setRoleToUser.setLong("p_user_id", userId);
            cs_setRoleToUser.setLong("p_role_id", 2L);
            cs_setRoleToUser.execute();


        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
        finally {
            closeConnections(resultSet);
        }

        return model;
    }

    public User addAdmin(User model) {
        if (model == null)
            throw new ArgumentNullException("The model argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_addUser = conn.prepareCall("{call addUserAccount(?,?,?,?,?,?,?,?)}");
             CallableStatement cs_setRoleToUser = conn.prepareCall("{call setRoleToUser(?,?)}")) {

            // Add a user to DB
            cs_addUser.setString("p_first_name", model.getFirstName());
            cs_addUser.setString("p_last_name", model.getLastName());
            cs_addUser.setString("p_email", model.getEmail());
            cs_addUser.setString("p_user_password", model.getPassword());
            cs_addUser.setBoolean("p_enabled", true);
            cs_addUser.setBoolean("p_is_using2fa", model.isUsing2FA());
            cs_addUser.setString("p_secret", model.getSecret());
            cs_addUser.setNull("p_last_login", Types.TIMESTAMP);
            cs_addUser.execute();

            // Add roles to user
            cs_setRoleToUser.setLong("user_account", model.getId());
            cs_setRoleToUser.setLong("user_role", 1L);
            cs_setRoleToUser.execute();

            cs_setRoleToUser.setLong("p_user_id", model.getId());
            cs_setRoleToUser.setLong("p_role_id", 2L);
            cs_setRoleToUser.execute();

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }

        return model;
    }

    @Override public User update(User model) {
        if (model == null)
            throw new ArgumentNullException("The model argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_addUser = conn.prepareCall("{call updateUserAccount(?,?,?,?,?,?,?,?,?)}");
             CallableStatement cs_setRoleToUser = conn.prepareCall("{call setRoleToUser(?,?)}")) {

            // Add a user to DB
            cs_addUser.setLong("p_user_account_id", model.getId());
            cs_addUser.setString("p_first_name", model.getFirstName());
            cs_addUser.setString("p_last_name", model.getLastName());
            cs_addUser.setString("p_email", model.getEmail());
            cs_addUser.setString("p_user_password", model.getPassword());
            cs_addUser.setBoolean("p_enabled", model.isEnabled());
            cs_addUser.setBoolean("p_is_using2fa", model.isUsing2FA());
            cs_addUser.setString("p_secret", model.getSecret());
            cs_addUser.setNull("p_last_login", Types.TIMESTAMP);
            cs_addUser.execute();

            cs_setRoleToUser.setLong("p_user_id", model.getId());
            cs_setRoleToUser.setLong("p_role_id", 2L);
            cs_setRoleToUser.execute();

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }

        return model;
    }

    @Override public void delete(User model) {
        if (model == null)
            throw new ArgumentNullException("The model argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_deleteUser = conn.prepareCall("{call deleteUserAccount(?)}")) {

            // Delete user
            cs_deleteUser.setLong(1, model.getId());
            cs_deleteUser.execute();

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }

    @Override public void deleteById(Long id) {
        if (id == null)
            throw new ArgumentNullException("The id argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement csDeleteUser = conn.prepareCall("{call deleteUserAccount(?)}")) {

            // Delete user
            csDeleteUser.setLong(1, id);
            csDeleteUser.execute();

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }

    @Override public void insertBatch(Iterable<User> models) {
        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_addUser = conn.prepareCall("{call addUserAccount(?,?,?,?,?,?,?,?)}");
             CallableStatement cs_setRoleToUser = conn.prepareCall("{call setRoleToUser(?,?)}")) {

            // Add calls to batch
            for (User model : models) {
                try {
                    // Add a user to DB
                    cs_addUser.setLong("p_user_account_id", model.getId());
                    cs_addUser.setString("p_first_name", model.getFirstName());
                    cs_addUser.setString("p_last_name", model.getLastName());
                    cs_addUser.setString("p_email", model.getEmail());
                    cs_addUser.setString("p_user_password", model.getPassword());
                    cs_addUser.setBoolean("p_enabled", model.isEnabled());
                    cs_addUser.setBoolean("p_is_using2fa", model.isUsing2FA());
                    cs_addUser.setString("p_secret", model.getSecret());
                    cs_addUser.setNull("p_last_login", Types.TIMESTAMP);

                    // Add role to user
                    cs_setRoleToUser.setLong("p_user_id", model.getId());
                    cs_setRoleToUser.setLong("p_role_id", 2L);

                    cs_addUser.addBatch();
                    cs_setRoleToUser.addBatch();
                } catch (SQLException ex) {
                    DBUtil.showErrorMessage(ex);
                }
            }

            // Execute batch!
            cs_addUser.executeBatch();
            cs_setRoleToUser.executeBatch();
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }

    @Override public void updateBatch(Iterable<User> models) {
        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_updateUser = conn.prepareCall("{call addUserAccount(?,?,?,?,?,?,?,?)}")) {

            // Add calls to batch
            for (User model : models) {
                try {
                    // Add a user to DB
                    cs_updateUser.setLong("p_user_account_id", model.getId());
                    cs_updateUser.setString("p_first_name", model.getFirstName());
                    cs_updateUser.setString("p_last_name", model.getLastName());
                    cs_updateUser.setString("p_email", model.getEmail());
                    cs_updateUser.setString("p_user_password", model.getPassword());
                    cs_updateUser.setBoolean("p_enabled", model.isEnabled());
                    cs_updateUser.setBoolean("p_is_using2fa", model.isUsing2FA());
                    cs_updateUser.setString("p_secret", model.getSecret());
                    cs_updateUser.setNull("p_last_login", Types.TIMESTAMP);

                    cs_updateUser.addBatch();
                } catch (SQLException ex) {
                    DBUtil.showErrorMessage(ex);
                }
            }

            // Execute batch!
            cs_updateUser.executeBatch();
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }

    @Override public void deleteBatch(Iterable<User> models) {
        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_updateUser = conn.prepareCall("{call deleteUserAccount(?)}")) {

            // Add calls to batch
            for (User model : models) {
                try {
                    cs_updateUser.setLong(1, model.getId());
                    cs_updateUser.addBatch();
                } catch (SQLException ex) {
                    DBUtil.showErrorMessage(ex);
                }
            }

            // Execute batch!
            cs_updateUser.executeBatch();
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }

    @Override public void deleteBatchByIds(Iterable<Long> ids) {
        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_updateUser = conn.prepareCall("{call deleteUserAccount(?)}")) {

            // Add calls to batch
            for (Long id : ids) {
                try {
                    cs_updateUser.setLong(1, id);
                    cs_updateUser.addBatch();
                } catch (SQLException ex) {
                    DBUtil.showErrorMessage(ex);
                }
            }

            // Execute batch!
            cs_updateUser.executeBatch();
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }
}
