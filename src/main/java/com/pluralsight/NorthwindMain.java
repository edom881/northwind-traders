package com.pluralsight;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;


public class NorthwindMain {

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/northwind";
        String username = "root";
        String password = "yearup26";

        Scanner scanner = new Scanner(System.in);

        System.out.println("What do you want to do?");
        System.out.println("1) Display all products");
        System.out.println("2) Display all customers");
        System.out.println("3) Display all categories");
        System.out.println("0) Exit");
        System.out.print("Select an option: ");

        String choice = scanner.nextLine();

        if (choice.equals("1")) {
            displayProducts(url, username, password);
        } else if (choice.equals("2")) {
            displayCustomers(url, username, password);


        } else if (choice.equals("3")) {
            displayCategories(url, username, password, scanner);
        } else if (choice.equals("0")) {
            System.out.println("Goodbye!");
        } else {
            System.out.println("Invalid option. Please choose 1, 2, 3, or 0.");
        }

        scanner.close();

    }

    public static void displayProducts(String url, String username, String password) {

        String query = "SELECT ProductID, ProductName, UnitPrice, UnitsInStock FROM products";

        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                Statement statement = connection.createStatement();
                ResultSet results = statement.executeQuery(query)
        ) {
            System.out.println("Connected!");
            System.out.println();

            System.out.printf("%-5s %-35s %-10s %-10s%n", "Id", "Name", "Price", "Stock");
            System.out.println("----- ----------------------------------- ---------- ----------");

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

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void displayCustomers(String url, String username, String password) {

        String query = "SELECT ContactName, CompanyName, City, Country, Phone " +
                "FROM customers " +
                "ORDER BY Country";

        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                Statement statement = connection.createStatement();
                ResultSet results = statement.executeQuery(query)
        ) {
            System.out.println("Connected!");
            System.out.println();

            System.out.printf(
                    "%-25s %-40s %-20s %-20s %-20s%n",
                    "Contact Name",
                    "Company Name",
                    "City",
                    "Country",
                    "Phone"
            );

            System.out.println(
                    "------------------------- ---------------------------------------- -------------------- -------------------- --------------------"
            );

            while (results.next()) {
                String contactName = results.getString("ContactName");
                String companyName = results.getString("CompanyName");
                String city = results.getString("City");
                String country = results.getString("Country");
                String phone = results.getString("Phone");

                System.out.printf(
                        "%-25s %-40s %-20s %-20s %-20s%n",
                        contactName,
                        companyName,
                        city,
                        country,
                        phone
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void displayCategories(String url, String username, String password, Scanner scanner) {

        String categoryQuery = "SELECT CategoryID, CategoryName FROM categories ORDER BY CategoryID";

        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                Statement statement = connection.createStatement();
                ResultSet results = statement.executeQuery(categoryQuery)
        ) {
            System.out.println();
            System.out.println("Categories");
            System.out.printf("%-15s %-25s%n", "Category ID", "Category Name");
            System.out.println("--------------- -------------------------");

            while (results.next()) {
                int categoryId = results.getInt("CategoryID");
                String categoryName = results.getString("CategoryName");

                System.out.printf("%-15d %-25s%n", categoryId, categoryName);
            }
            System.out.println();
            System.out.print("Enter a category ID to see products in that category: ");
            String categoryId = scanner.nextLine();

            displayProductsByCategory(connection, categoryId);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void displayProductsByCategory(Connection connection, String categoryId) {

        String productQuery = "SELECT ProductID, ProductName, UnitPrice, UnitsInStock " +
                "FROM products " +
                "WHERE CategoryID = ?";

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(productQuery)
        ) {
            preparedStatement.setString(1, categoryId);

            try (
                    ResultSet results = preparedStatement.executeQuery()
            ) {
                System.out.println();
                System.out.println("Products in Category: " + categoryId);
                System.out.printf("%-5s %-35s %-10s %-10s%n", "Id", "Name", "Price", "Stock");
                System.out.println("----- ----------------------------------- ---------- ----------");

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
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}