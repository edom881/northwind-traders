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

            // Get Product ID, Product Name, Unit Price, and Units in Stock
            ResultSet results = statement.executeQuery(
                    "SELECT ProductID, ProductName, UnitPrice, UnitsInStock FROM products"
            );

            // Print table header
            System.out.printf("%-5s %-35s %-10s %-10s%n", "Id", "Name", "Price", "Stock");
            System.out.println("----- ----------------------------------- ---------- ----------");

            // Print each product in table format
            while (results.next()) {
                int productId = results.getInt("ProductID");
                String productName = results.getString("ProductName");
                double unitPrice = results.getDouble("UnitPrice");
                int unitsInStock = results.getInt("UnitsInStock");

                System.out.printf(
                        "%-5d %-35s %-10.2f %-10d%n",
                        productId,
                        productName,
                        unitPrice,
                        unitsInStock
                );
            }

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}