package com.codecentric.retailbank.repository.helpers;

import com.codecentric.retailbank.repository.configuration.DBType;
import com.codecentric.retailbank.repository.configuration.DBUtil;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class JDBCExampleDemos {

    public static void metadata_demo() throws SQLException {
        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB)) {

            DatabaseMetaData dbmd = conn.getMetaData();

            // Database Metadata
            System.out.println("Driver name: " + dbmd.getDriverName());
            System.out.println("Driver version: " + dbmd.getDriverVersion());
            System.out.println("Logged in user: " + dbmd.getUserName());
            System.out.println("Database product name: " + dbmd.getDatabaseProductName());
            System.out.println("Database product version: " + dbmd.getDatabaseProductVersion());

            // Table names from connected database
            String catalog = null;
            String schemaPattern = null;
            String tableNamePattern = null;
            String[] schemaTypes = {"TABLE"};
            ResultSet rs = dbmd.getTables(catalog, schemaPattern, tableNamePattern, schemaTypes);
            System.out.println("Tables");
            System.out.println("==============");
            while (rs.next()) {
                System.out.println(rs.getString("TABLE_NAME"));
            }

            rs.close();
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }


    public static void storedProceduresTransactions_demo() throws SQLException {
        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement callableStatementWithdrawFromAccount = conn.prepareCall("{call withdrawFromAccount(?,?,?,?)}");
             CallableStatement callableStatementDepositToAccount = conn.prepareCall("{call depositToAccount(?,?,?,?)}")) {

            conn.setAutoCommit(false);

            // WITHDRAWAL
            Long withdrawalAccountNumber = 1L;
            Long withdrawalMerchantId = 1L;
            BigDecimal withdrawalAmount = BigDecimal.valueOf(1000000.0);
            String withdrawalDetails = "JDBC STORED PROCEDURE!";

            callableStatementWithdrawFromAccount.setLong(1, withdrawalAccountNumber);
            callableStatementWithdrawFromAccount.setLong(2, withdrawalMerchantId);
            callableStatementWithdrawFromAccount.setBigDecimal(3, withdrawalAmount);
            callableStatementWithdrawFromAccount.setString(4, withdrawalDetails);
            callableStatementWithdrawFromAccount.execute();
            System.out.println("Successfully withdrew " + withdrawalAmount + " from account number: " + withdrawalAccountNumber);


            // DEPOSIT
            Long depositAccountNumber = 1L;
            Long depositMerchantId = 1L;
            BigDecimal depositAmount = BigDecimal.valueOf(150.0);
            String depositDetails = "JDBC STORED PROCEDURE!";

            callableStatementDepositToAccount.setLong(1, depositAccountNumber);
            callableStatementDepositToAccount.setLong(2, depositMerchantId);
            callableStatementDepositToAccount.setBigDecimal(3, depositAmount);
            callableStatementDepositToAccount.setString(4, depositDetails);
            callableStatementDepositToAccount.execute();
            System.out.println("Successfully deposited " + depositAmount + " from account number: " + depositAccountNumber);


            String sql = "SELECT current_balance FROM accounts WHERE account_number = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setLong(1, withdrawalAccountNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("PREPARED STATEMENT EXECUTED SUCCESSFULLY!");
            BigDecimal balanceAmount = BigDecimal.ZERO;
            if (resultSet.next()) {
                balanceAmount = resultSet.getBigDecimal("current_balance");
            }

            if (balanceAmount.compareTo(BigDecimal.ZERO) == 1) {
                conn.commit();
                System.out.println("TRANSACTION SUCCESSFUL!!!");
            } else {
                conn.rollback();
                System.out.println("INSUFFICIENT FUNDS. ROLLING BACK!");
            }

            preparedStatement.close();
            resultSet.close();
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }


    public static void storedProceduresBatch_demo() throws SQLException {
        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement callableStatement = conn.prepareCall("{call addCustomer(?,?,?,?)}")) {

            String option;

            for (int i = 0; i < 10; i++) {
                Long addressId = 1L;
                Long branchId = 1L;
                String personalDetails = "INSERTED VIA JDBC USING A MYSQL STORED PROCEDURE!!!1!1!!";
                String contactDetails = "INSERTED VIA JDBC USING A MYSQL STORED PROCEDURE!1!1!!1!";

                callableStatement.setLong(1, addressId);
                callableStatement.setLong(2, branchId);
                callableStatement.setString(3, personalDetails);
                callableStatement.setString(4, contactDetails);
                callableStatement.addBatch();
                System.out.println("Added customer to batch. Cycle: " + i);
            }
            int[] updateCounts = callableStatement.executeBatch();
            System.out.println("Executed batch! Total inserts: " + updateCounts.length);

            System.out.println("WORKS!!!!!!!!");

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }


    public static void storedProcedures_demo() throws SQLException {
        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             CallableStatement callableStatement = conn.prepareCall("{call addCustomer(?,?,?,?)}")) {

            Long addressId = 1L;
            Long branchId = 1L;
            String personalDetails = "INSERTED VIA JDBC USING A MYSQL STORED PROCEDURE!!!1!1!!";
            String contactDetails = "INSERTED VIA JDBC USING A MYSQL STORED PROCEDURE!1!1!!1!";

            callableStatement.setLong(1, addressId);
            callableStatement.setLong(2, branchId);
            callableStatement.setString(3, personalDetails);
            callableStatement.setString(4, contactDetails);

            callableStatement.execute();

            System.out.println("WORKS!!!!!!!!");

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }


    public static void deleteRecord_demo() throws SQLException {
        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB)) {

            String sql = "DELETE FROM transactions WHERE transaction_amount = ?";

            BigDecimal transaction_amount = BigDecimal.valueOf(9999.00);

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setBigDecimal(1, transaction_amount);

            int result = preparedStatement.executeUpdate();

            System.out.println("RECORD INSERTED SUCCESSFULLY!" + "     " + "result: " + result);

            preparedStatement.close();
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }


    public static void updateRecord_demo() throws SQLException {
        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB)) {

            String sql = "UPDATE transactions SET transaction_amount = ? WHERE transaction_amount = ?";

            BigDecimal new_transaction_amount = BigDecimal.valueOf(9999.00);
            BigDecimal old_transaction_amount = BigDecimal.valueOf(1000.00);

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setBigDecimal(1, new_transaction_amount);
            preparedStatement.setBigDecimal(2, old_transaction_amount);

            int result = preparedStatement.executeUpdate();

            System.out.println("RECORD INSERTED SUCCESSFULLY!" + "     " + "result: " + result);

            preparedStatement.close();
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }


    public static void insertRecord_demo() throws SQLException {
        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB)) {
            Long transaction_id = 0L;
            Long account_number = 1L;
            Long merchant_id = 1L;
            Long transaction_type_id = 1L;
            Date transaction_date_time = Date.valueOf(LocalDate.now());
            BigDecimal transaction_amount = BigDecimal.valueOf(1000.0);
            String other_details = "JDBC DATA INSERT!!!!!";

            String sql = "INSERT INTO transactions VALUES(?,?,?,?,?,?,?)";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setLong(1, transaction_id);
            preparedStatement.setLong(2, account_number);
            preparedStatement.setLong(3, merchant_id);
            preparedStatement.setLong(4, transaction_type_id);
            preparedStatement.setDate(5, transaction_date_time);
            preparedStatement.setBigDecimal(6, transaction_amount);
            preparedStatement.setString(7, other_details);

            int result = preparedStatement.executeUpdate();

            System.out.println("RECORD INSERTED SUCCESSFULLY!" + "     " + "result: " + result);

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }


    private static void prepareStatementExtracted(@NotNull PreparedStatement pstmt, BigDecimal current_balance, String details) throws SQLException {
        pstmt.setBigDecimal(1, current_balance);
        pstmt.setString(2, details);

        ResultSet rs;
        rs = pstmt.executeQuery();

        while (rs.next()) {
            System.out.println(
                    rs.getString("account_number") +
                            "    " +
                            rs.getString("current_balance") +
                            "    " +
                            rs.getString("other_details") + "\n"
            );
        }

        rs.last();
        System.out.println("Total BankOLD Accounts: " + rs.getRow());
    }

    @SuppressWarnings("UnusedAssignment")
    public static void selectRecordsWithExtractedPrepareStatement_demo() throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBUtil.getConnection(DBType.MYSQL_DB);

            String sql = "SELECT * FROM accounts WHERE current_balance < ? AND other_details = ?";
            pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            prepareStatementExtracted(pstmt, BigDecimal.valueOf(100000.0), "No additional details.");
            System.out.println("------------------------------");
            prepareStatementExtracted(pstmt, BigDecimal.valueOf(50000.0), "No additional details.");

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }


    public static void insertRecordOld_demo() throws SQLException {
        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
             // CANNOT USE "SELECT *..." WHEN DOING UPDATES AND INSERTS
             ResultSet rs = stmt.executeQuery("SELECT branch_id, address_id, bank_id, branch_type_id, branch_details FROM branches")) {

            rs.absolute(6);
            rs.updateString("branch_details", "Information Technology");
            rs.updateRow();
            System.out.println("Record updated successfully!");

            rs.moveToInsertRow();
            rs.updateLong("branch_id", 999);
            rs.updateLong("address_id", 1);
            rs.updateLong("bank_id", 1);
            rs.updateLong("branch_type_id", 1);
            rs.updateString("branch_details", "INSERTED VIA JDBC!!!!");
            rs.insertRow();

            System.out.println("RECORD INSERTED SUCCESSFULLY!");

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }


    public static void resultSet_demo() throws SQLException {
        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
             ResultSet rs = stmt.executeQuery("SELECT * FROM banks LIMIT 7")) {

            rs.beforeFirst();
            System.out.println("\nFirst 7 rows: ");
            while (rs.next()) {
                System.out.println(rs.getString("bank_id") + "    " + rs.getString("bank_details") + "\n");
            }

            rs.afterLast();
            System.out.println("\nLast 7 rows: ");
            while (rs.previous()) {
                System.out.println(rs.getString("bank_id") + "    " + rs.getString("bank_details") + "\n");
            }

            rs.first();
            System.out.println("\nFirst record: ");
            System.out.println(rs.getString("bank_id") + "    " + rs.getString("bank_details") + "\n");

            rs.last();
            System.out.println("\nLast record: ");
            System.out.println(rs.getString("bank_id") + "    " + rs.getString("bank_details") + "\n");

            rs.absolute(4);
            System.out.println("\nFourth record: ");
            System.out.println(rs.getString("bank_id") + "    " + rs.getString("bank_details") + "\n");

            rs.relative(2);
            System.out.println("\nSixth record: ");
            System.out.println(rs.getString("bank_id") + "    " + rs.getString("bank_details") + "\n");

            rs.relative(-4);
            System.out.println("\nSecond record: ");
            System.out.println(rs.getString("bank_id") + "    " + rs.getString("bank_details") + "\n");
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }


    public static void test2() throws SQLException {

        try (Connection conn = DBUtil.getConnection(DBType.MYSQL_DB);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM banks")) {

            int rowCount = 0;

            while (rs.next()) {
                System.out.print(rs.getString("bank_id") + "       " + rs.getString("bank_details") + "\n");
                rowCount++;
            }

            System.out.println("Total Rows: " + rowCount);

            System.out.println("WORKS!!!!!!");
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            System.out.println("CONN CLOSED!!!!!!");
        }
    }


    public static void test1() throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection(DBType.MYSQL_DB);
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery("SELECT * FROM banerks");
            rs.last();

            System.out.println("Total No. of Rows: " + rs.getRow());

            System.out.println("WORKS!!!!!!");
        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        } finally {
            conn.close();

            System.out.println("CONN CLOSED!!!!!!");
        }
    }
}
