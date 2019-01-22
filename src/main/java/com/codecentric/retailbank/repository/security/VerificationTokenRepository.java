package com.codecentric.retailbank.repository.security;

import com.codecentric.retailbank.model.security.User;
import com.codecentric.retailbank.model.security.VerificationToken;
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
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class VerificationTokenRepository extends JDBCRepositoryUtilities implements JDBCRepositoryBase<VerificationToken, Long> {

    @Override public List<VerificationToken> findAll() {
        ResultSet resultSet = null;
        List<VerificationToken> verificationTokens = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement csAllVerificationTokens = conn.prepareCall("{call allVerificationTokens()}")) {

            // Retrieve findAll verificationTokens
            csAllVerificationTokens.execute();

            // Transform each ResultSet row into VerificationToken model and add to "verificationTokens" list
            resultSet = csAllVerificationTokens.getResultSet();
            while (resultSet.next()) {

                User user = new User(
                        resultSet.getLong("user_account.id"),
                        resultSet.getString("user_account.email")
                );

                verificationTokens.add(
                        new VerificationToken(
                                resultSet.getString("verification_token.token"),
                                user,
                                resultSet.getDate("verification_token.expiry_date")
                        )
                );
            }

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            closeConnections(resultSet);
        }

        return verificationTokens.size() < 1 ? null : verificationTokens;
    }

    @Override public ListPage<VerificationToken> findAllRange(int pageIndex, int pageSize) {
        ResultSet resultSet = null;
        ListPage<VerificationToken> verificationTokenListPage = new ListPage<>();

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement csAllVerificationTokensRange = conn.prepareCall("{call allVerificationTokensRange(?,?)}");
             CallableStatement csVerificationTokensCount = conn.prepareCall("{call allVerificationTokensCount()}")) {

            // Retrieve verificationTokens in a certain range
            csAllVerificationTokensRange.setInt(1, Math.abs(pageIndex * pageSize));
            csAllVerificationTokensRange.setInt(2, Math.abs(pageSize));
            csAllVerificationTokensRange.execute();

            // Transform ResultSet rows into VerificationToken models and add them into the verificationTokenListPage
            List<VerificationToken> verificationTokensList = new ArrayList<>();
            resultSet = csAllVerificationTokensRange.getResultSet();
            while (resultSet.next()) {
                User user = new User(
                        resultSet.getLong("user_account.id"),
                        resultSet.getString("user_account.email")
                );

                verificationTokensList.add(
                        new VerificationToken(
                                resultSet.getString("verification_token.token"),
                                user,
                                resultSet.getDate("verification_token.expiry_date")
                        )
                );
            }

            // Get the total number of verificationTokens in DB
            csVerificationTokensCount.execute();
            resultSet = csVerificationTokensCount.getResultSet();
            while (resultSet.next())
                verificationTokenListPage.setPageCount(resultSet.getLong(1), pageSize);

            // Add verificationTokens to ListPage transfer object
            verificationTokenListPage.setModels(verificationTokensList);

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            closeConnections(resultSet);
        }

        return verificationTokenListPage.getModels().size() < 1 ? null : verificationTokenListPage;
    }

    @Override public VerificationToken getSingle(Long id) {
        if (id == null)
            throw new ArgumentNullException("The id argument must have a value/cannot be null.");

        VerificationToken verificationToken = null;
        ResultSet resultSet = null;

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement csSingleVerificationToken = conn.prepareCall("{call singleVerificationToken(?)}")) {

            // Retrieve a getSingle verificationToken
            csSingleVerificationToken.setLong(1, id);
            csSingleVerificationToken.execute();

            // Transform ResultSet row into a VerificationToken model
            resultSet = csSingleVerificationToken.getResultSet();
            byte rowCounter = 0;
            while (resultSet.next()) {

                // Check if more than one element matches id parameter
                ++rowCounter;
                if (rowCounter > 1)
                    throw new InvalidOperationException("The ResultSet does not contain exactly one row.");

                // Transform ResultSet row into a VerificationToken object
                User user = new User(
                        resultSet.getLong("user_account.id"),
                        resultSet.getString("user_account.email")
                );


                verificationToken = new VerificationToken(
                        resultSet.getString("verification_token.token"),
                        user,
                        resultSet.getDate("verification_token.expiry_date")
                );

            }
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            closeConnections(resultSet);
        }

        return verificationToken;
    }

    public VerificationToken getSingleByEmail(String email) {
        if (email == null)
            throw new ArgumentNullException("The email argument must have a value/cannot be null.");

        VerificationToken verificationToken = null;
        ResultSet resultSet = null;

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement csSingleVerificationToken = conn.prepareCall("{call singleVerificationTokenByEmail(?)}")) {

            // Retrieve a getSingle verificationToken
            csSingleVerificationToken.setString(1, email);
            csSingleVerificationToken.execute();

            // Transform ResultSet row into a VerificationToken model
            resultSet = csSingleVerificationToken.getResultSet();
            byte rowCounter = 0;
            while (resultSet.next()) {

                // Check if more than one element matches id parameter
                ++rowCounter;
                if (rowCounter > 1)
                    throw new InvalidOperationException("The ResultSet does not contain exactly one row.");

                // Transform ResultSet row into a VerificationToken object
                User user = new User(
                        resultSet.getLong("user_account.id"),
                        resultSet.getString("user_account.email")
                );

                verificationToken = new VerificationToken(
                        resultSet.getString("verification_token.token"),
                        user,
                        resultSet.getDate("verification_token.expiry_date")
                );

            }
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            closeConnections(resultSet);
        }

        return verificationToken;
    }

    public VerificationToken getSingleByToken(String token) {
        if (token == null)
            throw new ArgumentNullException("The token argument must have a value/cannot be null.");

        VerificationToken verificationToken = null;
        ResultSet resultSet = null;

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement csSingleVerificationToken = conn.prepareCall("{call singleVerificationTokenByToken(?)}")) {

            // Retrieve a getSingle verificationToken
            csSingleVerificationToken.setString(1, token);
            csSingleVerificationToken.execute();

            // Transform ResultSet row into a VerificationToken model
            resultSet = csSingleVerificationToken.getResultSet();
            byte rowCounter = 0;
            while (resultSet.next()) {

                // Check if more than one element matches id parameter
                ++rowCounter;
                if (rowCounter > 1)
                    throw new InvalidOperationException("The ResultSet does not contain exactly one row.");

                // Transform ResultSet row into a VerificationToken object
                User user = new User(
                        resultSet.getLong("user_account.id"),
                        resultSet.getString("user_account.token")
                );

                verificationToken = new VerificationToken(
                        resultSet.getString("verification_token.token"),
                        user,
                        resultSet.getDate("verification_token.expiry_date")
                );

            }
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            closeConnections(resultSet);
        }

        return verificationToken;
    }


    @Override public VerificationToken add(VerificationToken model) {
        if (model == null)
            throw new ArgumentNullException("The model argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_addVerificationToken = conn.prepareCall("{call addVerificationToken(?,?,?)}")) {

            // Add a verificationToken to DB
            cs_addVerificationToken.setString(1, model.getToken());
            cs_addVerificationToken.setLong(2, model.getUser().getId());
            cs_addVerificationToken.setDate(3, new Date(model.getExpiryDate().getTime()));
            cs_addVerificationToken.execute();

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }

        return model;
    }

    @Override public VerificationToken update(VerificationToken model) {
        if (model == null)
            throw new ArgumentNullException("The model argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement csSingleVerificationToken = conn.prepareCall("{call updateVerificationToken(?,?,?,?)}")) {

            // Update an existing verificationToken in DB
            csSingleVerificationToken.setLong(1, model.getId());
            csSingleVerificationToken.setString(2, model.getToken());
            csSingleVerificationToken.setLong(3, model.getUser().getId());
            csSingleVerificationToken.setDate(4, new Date(model.getExpiryDate().getTime()));
            csSingleVerificationToken.execute();

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }

        return model;
    }


    @Override public void delete(VerificationToken model) {
        if (model == null)
            throw new ArgumentNullException("The model argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement csDeleteVerificationToken = conn.prepareCall("{call deleteVerificationToken(?)}")) {

            // Delete verificationToken
            csDeleteVerificationToken.setLong(1, model.getId());
            csDeleteVerificationToken.execute();

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }

    @Override public void deleteById(Long id) {
        if (id == null)
            throw new ArgumentNullException("The id argument must have a value/cannot be null.");

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement csDeleteVerificationToken = conn.prepareCall("{call deleteVerificationToken(?)}")) {

            // Delete verificationToken
            csDeleteVerificationToken.setLong(1, id);
            csDeleteVerificationToken.execute();

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }

    @Override public void insertBatch(Iterable<VerificationToken> models) {
        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_addVerificationToken = conn.prepareCall("{call addVerificationToken(?)}")) {

            // Add calls to batch
            for (VerificationToken model : models) {
                try {
                    cs_addVerificationToken.setString(1, model.getToken());
                    cs_addVerificationToken.setLong(2, model.getUser().getId());
                    cs_addVerificationToken.setDate(3, new Date(model.getExpiryDate().getTime()));
                    cs_addVerificationToken.addBatch();
                } catch (SQLException ex) {
                    DBUtil.showErrorMessage(ex);
                }
            }

            // Execute batch!
            cs_addVerificationToken.executeBatch();
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }

    @Override public void updateBatch(Iterable<VerificationToken> models) {
        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_UpdateVerificationToken = conn.prepareCall("{call updateVerificationToken(?,?)}")) {

            // Add calls to batch
            for (VerificationToken model : models) {
                try {
                    cs_UpdateVerificationToken.setLong(1, model.getId());
                    cs_UpdateVerificationToken.setString(2, model.getToken());
                    cs_UpdateVerificationToken.setLong(3, model.getUser().getId());
                    cs_UpdateVerificationToken.setDate(4, new Date(model.getExpiryDate().getTime()));
                    cs_UpdateVerificationToken.addBatch();
                } catch (SQLException ex) {
                    DBUtil.showErrorMessage(ex);
                }
            }

            // Execute batch!
            cs_UpdateVerificationToken.executeBatch();
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }

    @Override public void deleteBatch(Iterable<VerificationToken> models) {
        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_UpdateVerificationToken = conn.prepareCall("{call deleteVerificationToken(?)}")) {

            // Add calls to batch
            for (VerificationToken model : models) {
                try {
                    cs_UpdateVerificationToken.setLong(1, model.getId());
                    cs_UpdateVerificationToken.addBatch();
                } catch (SQLException ex) {
                    DBUtil.showErrorMessage(ex);
                }
            }

            // Execute batch!
            cs_UpdateVerificationToken.executeBatch();
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }

    @Override public void deleteBatchByIds(Iterable<Long> ids) {
        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement cs_UpdateVerificationToken = conn.prepareCall("{call deleteVerificationToken(?)}")) {

            // Add calls to batch
            for (Long id : ids) {
                try {
                    cs_UpdateVerificationToken.setLong(1, id);
                    cs_UpdateVerificationToken.addBatch();
                } catch (SQLException ex) {
                    DBUtil.showErrorMessage(ex);
                }
            }

            // Execute batch!
            cs_UpdateVerificationToken.executeBatch();
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }
}