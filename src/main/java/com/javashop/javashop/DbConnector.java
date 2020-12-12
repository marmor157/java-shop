package com.javashop.javashop;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnector {
    private static String URL = "jdbc:mysql://localhost:3306/java-shop-mysql";
    private static String USER = "java-shop-mysql";
    private static String PASSWROD = "java-shop-mysql";

    public Connection connect() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWROD);
            System.out.println("Database COnnected");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
