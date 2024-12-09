package org.education.hospitalmanagementapp.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.education.hospitalmanagementapp.AlertMessages;
import org.education.hospitalmanagementapp.services.AuthServiceClass;

import java.util.regex.Pattern;

/**
 * Controller class for handling user registration logic and navigation in the Hospital Management App.
 * Manages user input validation, account creation, and navigation back to the login page.
 */
public class RegistrationController {

    private static final Pattern FIRSTNAME_PATTERN = Pattern.compile("^[A-Z][a-zA-Z]+$");
    private static final Pattern LAST_NAME_PATTERN = Pattern.compile("^[A-Z][a-zA-Z]*(-[A-Z][a-zA-Z]*)?$");
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9.-]{5,20}$");
    private static final Pattern EMAILPATTERN = Pattern.compile(
            "^[A-Za-z0-9.%+-]+@(gmail|yahoo|hotmail|outlook|aol|icloud|protonmail|zoho|yandex|mail)\\.(com|edu|gov|org|net|io|co)$",
            Pattern.CASE_INSENSITIVE
    );
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%?&])[A-Za-z\\d@$!%?&]{8,20}$");
    private AlertMessages alert = new AlertMessages();
    AuthServiceClass asc = new AuthServiceClass();

    @FXML
    private TextField usernameField, emailField;

    @FXML
    private PasswordField passwordField, confirmPassField;

    /**
     * Handles the user registration process. Validates input, checks for existing usernames,
     * creates a new user, and navigates to the main menu upon successful registration.
     * @param event the action event triggered by clicking the register button
     */
    @FXML
    void loginUser(ActionEvent event) {
        String username = usernameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPassField.getText();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            alert.errorMessage("Fill in all fields.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            alert.errorMessage("Passwords do not match.");
            return;
        }

        if (!validateInput(username, email, password)) {
            return;
        }

        if (asc.usernameExists(username)) {
            alert.errorMessage("The username already exists. Please choose a different username.");
            return;
        }

        asc.insertUser(username, email, password);

        alert.successMessage("Successfully created your account!");

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/org.education.hospitalmanagementapp/MainMenu.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Navigates back to the login page from the registration view.
     * @param event the action event triggered by clicking the "Go back to login" button
     */
    @FXML
    private void goBackToLogInPage(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/org.education.hospitalmanagementapp/LoginView.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Validates user input for username, email, and password against predefined regex patterns.
     * Displays error messages if any input fails validation.
     * @param username the username entered by the user
     * @param email the email entered by the user
     * @param password the password entered by the user
     * @return true if all inputs are valid, false otherwise
     */
    private boolean validateInput(String username, String email, String password) {
        if (!USERNAME_PATTERN.matcher(username).matches()) {
            alert.errorMessage("Invalid username format.");
            return false;
        }
        if (!EMAILPATTERN.matcher(email).matches()) {
            alert.errorMessage("Invalid email format.");
            return false;
        }
        if (!PASSWORD_PATTERN.matcher(password).matches()) {
            alert.errorMessage("Invalid password format. Must include uppercase, lowercase, number, and special character.");
            return false;
        }
        return true;
    }
}