package com.pluralsight;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class NorthwindMain {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/northwind";
        String username = "root";
        String password = "yearup26";
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected!");
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT * FROM products");
            while (results.next()) {
                System.out.println(results.getString("ProductName"));
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}