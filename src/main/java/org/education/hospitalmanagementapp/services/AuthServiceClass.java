package org.education.hospitalmanagementapp.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.education.hospitalmanagementapp.Patient;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * AuthServiceClass provides database-related authentication and user/patient management services.
 * It handles database connections, user registration, validation, and update operations.
 */
public class AuthServiceClass {
    final String MYSQL_SERVER_URL = "jdbc:mysql://hospitalmanagement.mysql.database.azure.com/";
    final String DB_URL = "jdbc:mysql://hospitalmanagement.mysql.database.azure.com/hospital-management";
    final String USERNAME = "hospitaladmin";
    final String PASSWORD = "Manager1!";
    private final ObservableList<Patient> data = FXCollections.observableArrayList();



    /**
     * Establishes connection to the database and ensures the "users" table exists.
     * Checks if any registered users exist in the database.
     *
     * @return true if there are registered users; false otherwise.
     */
    public  boolean connectToDatabase() {
        boolean hasRegistredUsers = false;



        try {
            Connection conn = DriverManager.getConnection(MYSQL_SERVER_URL, USERNAME, PASSWORD);
            Statement statement = conn.createStatement();
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS DBname");
            statement.close();
            conn.close();

            //Second, connect to the database and create the table "users" if cot created
            conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            statement = conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS users (" +
                    "id INT(10) NOT NULL PRIMARY KEY AUTO_INCREMENT," +
                    "username VARCHAR(200) NOT NULL," +
                    "email VARCHAR(200) NOT NULL UNIQUE," +
                    "password VARCHAR(200) NOT NULL," +
                    "profile_picture LONGBLOB" +
                    ")";
            statement.executeUpdate(sql);

            //check if we have users in the table users
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM users");

            if (resultSet.next()) {
                int numUsers = resultSet.getInt(1);
                if (numUsers > 0) {
                    hasRegistredUsers = true;
                }
            }

            statement.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return hasRegistredUsers;
    }

    /**
     * Establishes a connection to the database.
     *
     * @return Connection object for database interaction.
     * @throws SQLException if the connection fails.
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
    }


    public String stringAllUsers() {
        connectToDatabase();
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "SELECT * FROM users ";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();
            String toReturn = "";
            while (resultSet.next()) {
                int id = resultSet.getInt("PatientID");
                String first_name = resultSet.getString("FirstName");
                String last_name = resultSet.getString("LastName");
                String DateOfBirth = resultSet.getString("DateOfBirth");
                String ContactNumber = resultSet.getString("ContactNumber");
                String Address = resultSet.getString("Address");
                int Cost = resultSet.getInt("cost");
                String Services = resultSet.getString("services");

                toReturn = toReturn + (id + "," + first_name + "," + last_name + "," + DateOfBirth + "," + ContactNumber + "," + Address + "," + Cost + "," + Services + "\n");
            }

            preparedStatement.close();
            conn.close();
            //toReturn = toReturn.substring(0, toReturn.lastIndexOf("\n"));
            return toReturn;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public ObservableList<Patient> getPatientInfo(){
        connectToDatabase();
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "SELECT * FROM patients ";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("PatientID");
                String first_name = resultSet.getString("FirstName");
                String last_name = resultSet.getString("LastName");
                String DateOfBirth = resultSet.getString("DateOfBirth");
                String ContactNumber = resultSet.getString("ContactNumber");
                String Address = resultSet.getString("Address");
                int Cost = resultSet.getInt("cost");
                String Services = resultSet.getString("services");
                data.add(new Patient(id, first_name, last_name, DateOfBirth, ContactNumber, Address, Cost, Services));
            }
            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * Inserts a new user into the "users" table with profile picture support.
     * @param username the username of the new user
     * @param email the email of the new user
     * @param password the password of the new user
     * @param profilePicture the profile picture as byte array
     * @return true if insertion was successful, false otherwise
     */
    public boolean insertUser(String username, String email, String password, byte[] profilePicture) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "INSERT INTO users (username, email, password, profile_picture) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);

            if (profilePicture != null) {
                preparedStatement.setBytes(4, profilePicture);
            } else {
                preparedStatement.setNull(4, java.sql.Types.BLOB);
            }

            int row = preparedStatement.executeUpdate();
            preparedStatement.close();
            conn.close();
            return row > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * Updates the profile picture for a specific user.
     * @param username the username of the user
     * @param profilePicture the new profile picture as byte array
     * @return true if update was successful, false otherwise
     */
    public boolean updateProfilePicture(String username, byte[] profilePicture) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "UPDATE users SET profile_picture = ? WHERE username = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            if (profilePicture != null) {
                preparedStatement.setBytes(1, profilePicture);
            } else {
                preparedStatement.setNull(1, java.sql.Types.BLOB);
            }
            preparedStatement.setString(2, username);

            int row = preparedStatement.executeUpdate();
            preparedStatement.close();
            conn.close();
            return row > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * Retrieves the profile picture for a specific user.
     * @param username the username of the user
     * @return byte array containing the profile picture, or null if not found
     */
    public byte[] getProfilePicture(String username) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "SELECT profile_picture FROM users WHERE username = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();
            byte[] profilePicture = null;
            if (resultSet.next()) {
                profilePicture = resultSet.getBytes("profile_picture");
            }

            preparedStatement.close();
            conn.close();
            return profilePicture;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Inserts a new patient into the "patients" table.
     *
     * @param fname        patient's first name.
     * @param lname       patient's last name.
     * @param dob         patient's date of birth.
     * @param phoneNumber patient's contact number.
     * @param address     patient's address.
     */
    public  void insertPatient(String fname, String lname, String dob, String phoneNumber, String address) {

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "INSERT INTO patients (FirstName, LastName, DateOfBirth, ContactNumber, Address) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, fname);
            preparedStatement.setString(2, lname);
            preparedStatement.setString(3, dob);
            preparedStatement.setString(4, phoneNumber);
            preparedStatement.setString(5, address);

            int row = preparedStatement.executeUpdate();

            if (row > 0) {
                System.out.println("A new patient was inserted successfully.");
            }

            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Validates user credentials against the database records.
     *
     * @param username the username to validate.
     * @param email    the email to validate.
     * @param password the password to validate.
     * @return true if the user is valid; false otherwise.
     */
    public boolean validateUser(String username, String email, String password) {
        boolean isValidUser = false;

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            String sql = "SELECT COUNT(*) FROM users WHERE username = ? AND email = ? AND password = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                isValidUser = count > 0;
            }

            resultSet.close();
            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isValidUser;
    }

    /**
     * Checks if a username already exists in the "users" table.
     *
     * @param username the username to check.
     * @return true if the username exists; false otherwise.
     */
    public boolean usernameExists(String username) {
        boolean exists = false;

        String query = "SELECT COUNT(*) FROM users WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                exists = rs.getInt(1) > 0; // If count > 0, the username exists
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exists;
    }
    public boolean patientExists(String firstName, String lastName) {
        boolean exists = false;

        String query = "SELECT COUNT(*) FROM patients WHERE FirstName = ? AND LastName = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                exists = rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exists;
    }

    /**
     * Updates a user's username and password in the "users" table.
     *
     * @param email       the user's email.
     * @param newUsername the new username.
     * @param newPassword the new password.
     */
    public void updateUser(String email, String newUsername, String newPassword) {
        String updateQuery = "UPDATE users SET username = ?, password = ? WHERE email = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(updateQuery)) {

            stmt.setString(1, newUsername);
            stmt.setString(2, newPassword);
            stmt.setString(3, email);

            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("User information updated successfully.");
            } else {
                System.out.println("Failed to update user information.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the contact number of a patient based on their first and last name.
     * @param fname The first name of the patient.
     * @param lname The last name of the patient.
     * @param newNumber The new contact number to be set.
     */
    public void updatePatientNumber(String fname, String lname, String newNumber) {
        String updateQuery = "UPDATE patients SET ContactNumber = ? WHERE FirstName = ? AND LastName = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(updateQuery)) {

            stmt.setString(1, newNumber);
            stmt.setString(2, fname);
            stmt.setString(3, lname);

            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Patient phone number updated successfully.");
            } else {
                System.out.println("Failed to update patient phone number information.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the address of a patient based on their first and last name.
     * @param fname The first name of the patient.
     * @param lname The last name of the patient.
     * @param newAddress The new address to be set.
     */
    public void updatePatientAddress(String fname, String lname, String newAddress) {
        String updateQuery = "UPDATE patients SET Address = ? WHERE FirstName = ? AND LastName = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(updateQuery)) {

            stmt.setString(1, newAddress);
            stmt.setString(2, fname);
            stmt.setString(3, lname);

            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Patient address updated successfully.");
            } else {
                System.out.println("Failed to update patient address information.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
