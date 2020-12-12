package com.javashop.javashop;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QueryExecutor {
    private static DbConnector connector = new DbConnector();
    private static Connection connection = connector.connect();


    public static ResultSet executeSelect(String selectQuery) {
        try{
            Statement statement = connection.createStatement();
            return statement.executeQuery(selectQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    public static void executeQuery(String query) {
        try{
            Statement statement = connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
