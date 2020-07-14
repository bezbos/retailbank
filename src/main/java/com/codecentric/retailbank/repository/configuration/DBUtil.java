package com.codecentric.retailbank.repository.configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

    //region ORACLE
    private static final String ORACLE_USERNAME = "hr";
    private static final String ORACLE_PASSWORD = "hr";
    private static final String ORACLE_CONNECTION_STRING = "ORACLE_DB_CONNECTION_STRING";
    //endregion

    //region MYSQL
    private static final String MY_SQL_USERNAME = "root";
    private static final String MY_SQL_PASSWORD = "root";
    private static final String MY_SQL_CONNECTION_STRING = "jdbc:mysql://host.docker.internal:3307/bankcentric";
    //endregion

    public static Connection getConnection(DBType dbType) throws SQLException {
        switch (dbType) {
            case ORACLE_DB:
                return DriverManager.getConnection(ORACLE_CONNECTION_STRING, ORACLE_USERNAME, ORACLE_PASSWORD);

            case MYSQL_DB:
                return DriverManager.getConnection(MY_SQL_CONNECTION_STRING, MY_SQL_USERNAME, MY_SQL_PASSWORD);
            default:
                return null;
        }
    }

    public static void showErrorMessage(SQLException ex) {
        System.err.println("Error: " + ex.getMessage());
        System.err.println("Error code: " + ex.getErrorCode());
    }
}
