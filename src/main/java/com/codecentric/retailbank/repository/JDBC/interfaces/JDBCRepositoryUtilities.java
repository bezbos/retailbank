package com.codecentric.retailbank.repository.JDBC.interfaces;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCRepositoryUtilities {

    protected void closeConnections(ResultSet resultSet) throws SQLException {
        if(resultSet != null)
            resultSet.close();
    }

    protected void closeConnections(Connection connection) throws SQLException {
        if(connection != null)
            connection.close();
    }

    protected void closeConnections(Connection connection, ResultSet resultSet) throws SQLException {
        if(connection != null)
            connection.close();
        if(resultSet != null)
            resultSet.close();
    }
}
