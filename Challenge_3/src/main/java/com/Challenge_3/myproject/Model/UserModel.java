package com.Challenge_3.myproject.Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class UserModel {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/binarfud";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "dewa1234";

    public void createTables() {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement()) {

            // Create user table
            String createUserTableSQL = "CREATE TABLE users (" +
                    "userId SERIAL PRIMARY KEY, " +
                    "username VARCHAR(255) NOT NULL, " +
                    "email_address VARCHAR(255) NOT NULL, " +
                    "password VARCHAR(255) NOT NULL)";
            statement.execute(createUserTableSQL);

            // Create Order table
            String createOrderTableSQL = "CREATE TABLE orders (" +
                    "orderId SERIAL PRIMARY KEY, " +
                    "order_time TIMESTAMP NOT NULL, " +
                    "destination_address VARCHAR(255) NOT NULL, " +
                    "user_id INT NOT NULL, " +
                    "FOREIGN KEY (user_id) REFERENCES users(userId))";
            statement.execute(createOrderTableSQL);

            // Create merchant table
            String createMerchantTableSQL = "CREATE TABLE merchant (" +
                    "merchantId SERIAL PRIMARY KEY, " +
                    "merchant_name VARCHAR(255) NOT NULL, " +
                    "merchant_location VARCHAR(255) NOT NULL)";
            statement.execute(createMerchantTableSQL);

            // Create product table
            String createProductTableSQL = "CREATE TABLE product (" +
                    "productId SERIAL PRIMARY KEY, " +
                    "product_name VARCHAR(255) NOT NULL, " +
                    "price DECIMAL(10, 2) NOT NULL, " +
                    "merchant_id INT NOT NULL, " +
                    "FOREIGN KEY (merchant_id) REFERENCES merchant(merchantId))";
            statement.execute(createProductTableSQL);

            // Create orderdetail table
            String createOrderDetailTableSQL = "CREATE TABLE orderdetail (" +
                    "orderdetailId SERIAL PRIMARY KEY, " +
                    "order_id INT NOT NULL, " +
                    "product_id INT NOT NULL, " +
                    "quantity INT NOT NULL, " +
                    "total_price DECIMAL(10, 2) NOT NULL, " +
                    "FOREIGN KEY (order_id) REFERENCES orders(orderId), " +
                    "FOREIGN KEY (product_id) REFERENCES product(productId))";
            statement.execute(createOrderDetailTableSQL);

            System.out.println("Tables created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

