package com.pluralsight;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class NorthwindMain {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/northwind";
        String username = "root";
        String password = "yearup26";
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected!");
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}