package com.codecentric.retailbank.repository.JDBC.security;

import com.codecentric.retailbank.model.security.PasswordResetToken;
import com.codecentric.retailbank.model.security.User;
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
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PasswordResetTokenRepositoryJDBC extends JDBCRepositoryUtilities implements JDBCRepositoryBase<PasswordResetToken, Long> {

    @Override public List<PasswordResetToken> findAll() {
        ResultSet resultSet = null;
        List<PasswordResetToken> passwordResetTokens = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement csAllPasswordResetTokens = conn.prepareCall("{call allPasswordResetTokens()}")) {

            // Retrieve findAll passwordResetTokens
            csAllPasswordResetTokens.execute();

            // Transform each ResultSet row into PasswordResetToken model and add to "passwordResetTokens" list
            resultSet = csAllPasswordResetTokens.getResultSet();
            while (resultSet.next()) {

                User user = new User(
                        resultSet.getLong("user_account.id"),
                        resultSet.getString("user_account.email")
                );

                passwordResetTokens.add(
                        new PasswordResetToken(
                                resultSet.getString("password_reset_token.token"),
                                user,
                                resultSet.getDate("password_reset_token.expiry_date")
                        )
                );
            }

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            closeConnections(resultSet);
        }

        return passwordResetTokens.size() < 1 ? null : passwordResetTokens;
    }

    @Override public ListPage<PasswordResetToken> findAllRange(int pageIndex, int pageSize) {
        ResultSet resultSet = null;
        ListPage<PasswordResetToken> passwordResetTokenListPage = new ListPage<>();

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement csAllPasswordResetTokensRange = conn.prepareCall("{call allPasswordResetTokensRange(?,?)}");
             CallableStatement csPasswordResetTokensCount = conn.prepareCall("{call allPasswordResetTokensCount()}")) {

            // Retrieve passwordResetTokens in a certain range
            csAllPasswordResetTokensRange.setInt(1, Math.abs(pageIndex * pageSize));
            csAllPasswordResetTokensRange.setInt(2, Math.abs(pageSize));
            csAllPasswordResetTokensRange.execute();

            // Transform ResultSet rows into PasswordResetToken models and add them into the passwordResetTokenListPage
            List<PasswordResetToken> passwordResetTokensList = new ArrayList<>();
            resultSet = csAllPasswordResetTokensRange.getResultSet();
            while (resultSet.next()) {
                User user = new User(
                        resultSet.getLong("user_account.id"),
                        resultSet.getString("user_account.email")
                );

                passwordResetTokensList.add(
                        new PasswordResetToken(
                                resultSet.getString("password_reset_token.token"),
                                user,
                                resultSet.getDate("password_reset_token.expiry_date")
                        )
                );
            }

            // Get the total number of passwordResetTokens in DB
            csPasswordResetTokensCount.execute();
            resultSet = csPasswordResetTokensCount.getResultSet();
            while (resultSet.next())
                passwordResetTokenListPage.setPageCount(resultSet.getLong(1), pageSize);

            // Add passwordResetTokens to ListPage transfer object
            passwordResetTokenListPage.setModels(passwordResetTokensList);

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            closeConnections(resultSet);
        }

        return passwordResetTokenListPage.getModels().size() < 1 ? null : passwordResetTokenListPage;
    }

    @Override public PasswordResetToken getSingle(Long id) {
        if (id == null)
            throw new ArgumentNullException("The id argument must have a value/cannot be null.");

        PasswordResetToken passwordResetToken = null;
        ResultSet resultSet = null;

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement csSinglePasswordResetToken = conn.prepareCall("{call singlePasswordResetToken(?)}")) {

            // Retrieve a getSingle passwordResetToken
            csSinglePasswordResetToken.setLong(1, id);
            csSinglePasswordResetToken.execute();

            // Transform ResultSet row into a PasswordResetToken model
            resultSet = csSinglePasswordResetToken.getResultSet();
            byte rowCounter = 0;
            while (resultSet.next()) {

                // Check if more than one element matches id parameter
                ++rowCounter;
                if (rowCounter > 1)
                    throw new InvalidOperationException("The ResultSet does not contain exactly one row.");

                // Transform ResultSet row into a PasswordResetToken object
                User user = new User(
                        resultSet.getLong("user_account.id"),
                        resultSet.getString("user_account.email")
                );


                passwordResetToken = new PasswordResetToken(
                        resultSet.getString("password_reset_token.token"),
                        user,
                        resultSet.getDate("password_reset_token.expiry_date")
                );

            }
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            closeConnections(resultSet);
        }

        return passwordResetToken;
    }

    public PasswordResetToken getSingleByEmail(String email) {
        if (email == null)
            throw new ArgumentNullException("The email argument must have a value/cannot be null.");

        PasswordResetToken passwordResetToken = null;
        ResultSet resultSet = null;

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement csSinglePasswordResetToken = conn.prepareCall("{call singlePasswordResetTokenByEmail(?)}")) {

            // Retrieve a getSingle passwordResetToken
            csSinglePasswordResetToken.setString(1, email);
            csSinglePasswordResetToken.execute();

            // Transform ResultSet row into a PasswordResetToken model
            resultSet = csSinglePasswordResetToken.getResultSet();
            byte rowCounter = 0;
            while (resultSet.next()) {

                // Check if more than one element matches id parameter
                ++rowCounter;
                if (rowCounter > 1)
                    throw new InvalidOperationException("The ResultSet does not contain exactly one row.");

                // Transform ResultSet row into a PasswordResetToken object
                User user = new User(
                        resultSet.getLong("user_account.id"),
                        resultSet.getString("user_account.email")
                );

                passwordResetToken = new PasswordResetToken(
                        resultSet.getString("password_reset_token.token"),
                        user,
                        resultSet.getDate("password_reset_token.expiry_date")
                );

            }
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            closeConnections(resultSet);
        }

        return passwordResetToken;
    }

    public PasswordResetToken getSingleByToken(String token) {
        if (token == null)
            throw new ArgumentNullException("The token argument must have a value/cannot be null.");

        PasswordResetToken passwordResetToken = null;
        ResultSet resultSet = null;

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement csSinglePasswordResetToken = conn.prepareCall("{call singlePasswordResetTokenByToken(?)}")) {

            // Retrieve a getSingle passwordResetToken
            csSinglePasswordResetToken.setString(1, token);
            csSinglePasswordResetToken.execute();

            // Transform ResultSet row into a PasswordResetToken model
            resultSet = csSinglePasswordResetToken.getResultSet();
            byte rowCounter = 0;
            while (resultSet.next()) {

                // Check if more than one element matches id parameter
                ++rowCounter;
                if (rowCounter > 1)
                    throw new InvalidOperationException("The ResultSet does not contain exactly one row.");

                // Transform ResultSet row into a PasswordResetToken object
                User user = new User(
                        resultSet.getLong("user_account.id"),
                        resultSet.getString("user_account.token")
                );

                passwordResetToken = new PasswordResetToken(
                        resultSet.getString("password_reset_token.token"),
                        user,
                        resultSet.getDate("password_reset_token.expiry_date")
                );

            }
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            closeConnections(resultSet);
        }

        return passwordResetToken;
    }


    @Override public PasswordResetToken add(PasswordResetToken model) {
        if (model == null)
            throw new ArgumentNullException("The model argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_addPasswordResetToken = conn.prepareCall("{call addPasswordResetToken(?,?,?)}")) {

            // Add a passwordResetToken to DB
            cs_addPasswordResetToken.setString(1, model.getToken());
            cs_addPasswordResetToken.setLong(2, model.getUser().getId());
            cs_addPasswordResetToken.setDate(3, new Date(model.getExpiryDate().getTime()));
            cs_addPasswordResetToken.execute();

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }

        return model;
    }

    @Override public PasswordResetToken update(PasswordResetToken model) {
        if (model == null)
            throw new ArgumentNullException("The model argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement csSinglePasswordResetToken = conn.prepareCall("{call updatePasswordResetToken(?,?,?,?)}")) {

            // Update an existing passwordResetToken in DB
            csSinglePasswordResetToken.setLong(1, model.getId());
            csSinglePasswordResetToken.setString(2, model.getToken());
            csSinglePasswordResetToken.setLong(3, model.getUser().getId());
            csSinglePasswordResetToken.setDate(4, new Date(model.getExpiryDate().getTime()));
            csSinglePasswordResetToken.execute();

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }

        return model;
    }


    @Override public void delete(PasswordResetToken model) {
        if (model == null)
            throw new ArgumentNullException("The model argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement csDeletePasswordResetToken = conn.prepareCall("{call deletePasswordResetToken(?)}")) {

            // Delete passwordResetToken
            csDeletePasswordResetToken.setLong(1, model.getId());
            csDeletePasswordResetToken.execute();

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }

    @Override public void deleteById(Long id) {
        if (id == null)
            throw new ArgumentNullException("The id argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement csDeletePasswordResetToken = conn.prepareCall("{call deletePasswordResetToken(?)}")) {

            // Delete passwordResetToken
            csDeletePasswordResetToken.setLong(1, id);
            csDeletePasswordResetToken.execute();

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }

    @Override public void insertBatch(Iterable<PasswordResetToken> models) {
        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_addPasswordResetToken = conn.prepareCall("{call addPasswordResetToken(?)}")) {

            // Add calls to batch
            for (PasswordResetToken model : models) {
                try {
                    cs_addPasswordResetToken.setString(1, model.getToken());
                    cs_addPasswordResetToken.setLong(2, model.getUser().getId());
                    cs_addPasswordResetToken.setDate(3, new Date(model.getExpiryDate().getTime()));
                    cs_addPasswordResetToken.addBatch();
                } catch (SQLException ex) {
                    DBUtil.showErrorMessage(ex);
                }
            }

            // Execute batch!
            cs_addPasswordResetToken.executeBatch();
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }

    @Override public void updateBatch(Iterable<PasswordResetToken> models) {
        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_UpdatePasswordResetToken = conn.prepareCall("{call updatePasswordResetToken(?,?)}")) {

            // Add calls to batch
            for (PasswordResetToken model : models) {
                try {
                    cs_UpdatePasswordResetToken.setLong(1, model.getId());
                    cs_UpdatePasswordResetToken.setString(2, model.getToken());
                    cs_UpdatePasswordResetToken.setLong(3, model.getUser().getId());
                    cs_UpdatePasswordResetToken.setDate(4, new Date(model.getExpiryDate().getTime()));
                    cs_UpdatePasswordResetToken.addBatch();
                } catch (SQLException ex) {
                    DBUtil.showErrorMessage(ex);
                }
            }

            // Execute batch!
            cs_UpdatePasswordResetToken.executeBatch();
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }

    @Override public void deleteBatch(Iterable<PasswordResetToken> models) {
        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_UpdatePasswordResetToken = conn.prepareCall("{call deletePasswordResetToken(?)}")) {

            // Add calls to batch
            for (PasswordResetToken model : models) {
                try {
                    cs_UpdatePasswordResetToken.setLong(1, model.getId());
                    cs_UpdatePasswordResetToken.addBatch();
                } catch (SQLException ex) {
                    DBUtil.showErrorMessage(ex);
                }
            }

            // Execute batch!
            cs_UpdatePasswordResetToken.executeBatch();
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }

    @Override public void deleteBatchByIds(Iterable<Long> ids) {
        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_UpdatePasswordResetToken = conn.prepareCall("{call deletePasswordResetToken(?)}")) {

            // Add calls to batch
            for (Long id : ids) {
                try {
                    cs_UpdatePasswordResetToken.setLong(1, id);
                    cs_UpdatePasswordResetToken.addBatch();
                } catch (SQLException ex) {
                    DBUtil.showErrorMessage(ex);
                }
            }

            // Execute batch!
            cs_UpdatePasswordResetToken.executeBatch();
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }
}