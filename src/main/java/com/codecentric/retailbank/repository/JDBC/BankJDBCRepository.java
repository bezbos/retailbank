package com.codecentric.retailbank.repository.JDBC;

import java.math.BigDecimal;
import java.sql.*;

public class BankJDBCRepository {

    private static void prepareStatement(PreparedStatement pstmt, BigDecimal current_balance, String details) throws SQLException {
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
        System.out.println("Total Bank Accounts: " + rs.getRow());
    }

    @SuppressWarnings("UnusedAssignment")
    public static void test5() throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection(DBType.MYSQL_DB);

            String sql = "SELECT * FROM accounts WHERE current_balance < ? AND other_details = ?";
            pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            prepareStatement(pstmt, BigDecimal.valueOf(100000.0), "No additional details.");
            System.out.println("------------------------------");
            prepareStatement(pstmt, BigDecimal.valueOf(50000.0), "No additional details.");

        } catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
    }


    public static void test4() throws SQLException {
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


    public static void test3() throws SQLException {
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
