package org.education.hospitalmanagementapp.services;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class AuthService {
    final String MYSQL_SERVER_URL = "jdbc:mysql://hospitalappserver.mysql.database.azure.com/";
    final String DB_URL = "jdbc:mysql://hospitalappserver.mysql.database.azure.com/DBname";
    final String USERNAME = "hospitaladmin";
    final String PASSWORD = "Manager1!";

    public boolean connectToDatabase() {
        boolean hasRegisteredUsers = false;

        try {
            // First, connect to MYSQL server and create the database if not created
            Connection conn = DriverManager.getConnection(MYSQL_SERVER_URL, USERNAME, PASSWORD);
            Statement statement = conn.createStatement();
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS DBname");
            statement.close();
            conn.close();

            // Second, connect to the database and create the table "users" if not created
            conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            statement = conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS users ("
                    + "id INT(10) NOT NULL PRIMARY KEY AUTO_INCREMENT,"
                    + "email VARCHAR(200) NOT NULL UNIQUE,"
                    + "user_name VARCHAR(200) NOT NULL UNIQUE,"
                    + "password VARCHAR(200) NOT NULL"
                    + ")";
            statement.executeUpdate(sql);

            // Check if we have user in the table
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM users");

            if (resultSet.next()) {
                int numusers = resultSet.getInt(1);
                if (numusers > 0) {
                    hasRegisteredUsers = true;
                }
            }

            statement.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return hasRegisteredUsers;
    }

    public boolean registerUser(String email, String username, String password) {
        boolean success = false;
        String sql = "INSERT INTO users (email, user_name, password) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            pstmt.setString(2, username);
            pstmt.setString(3, password); // In a production application, ensure you hash the password before storing it.
            pstmt.executeUpdate();
            success = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }

    public boolean loginUser(String username, String password) {
        boolean success = false;
        String sql = "SELECT * FROM users WHERE user_name = ? AND password = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password); // Use hashed password in production
            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                success = true; // User exists
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }
}
