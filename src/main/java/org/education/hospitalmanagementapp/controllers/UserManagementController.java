package org.education.hospitalmanagementapp.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.util.regex.Pattern;
import org.education.hospitalmanagementapp.AlertMessages;
import org.education.hospitalmanagementapp.services.AuthServiceClass;

/**
 * Controller class for user management functionalities, including user authentication,
 * navigation to main menu, sign-out, and updating user credentials with validation.
 */
public class UserManagementController  {

    @FXML
    private ImageView clearEmail;

    @FXML
    private ImageView clearUserName;

    @FXML
    private ImageView clearCurrPassField;

    @FXML
    private ImageView clearNewUserField;

    @FXML
    private ImageView clearNewPassField;

    @FXML
    private PasswordField currPassField;


    @FXML
    private TextField emailField;

    @FXML
    private TextField currUserField;

    @FXML
    private ImageView menu;

    @FXML
    private PasswordField newPassField;

    @FXML
    private TextField newUserField;

    @FXML
    private ImageView profile_Image;

    private AuthServiceClass asc = new AuthServiceClass();
    private AlertMessages alert = new AlertMessages();

    // Regex patterns for username and password validation
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_-]{5,20}$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%?&])[A-Za-z\\d@$!%?&]{8,20}$");

    @FXML
    public void initialize() {
        setupClearButtons();
    }
    /**
     * Navigates to the main menu view.
     *
     * @param event ActionEvent triggered by the user.
     */
    @FXML
    void goToMainMenu(ActionEvent event) {
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

    public void setupClearButtons() {
        clearEmail.setOnMouseClicked(event -> emailField.clear());
        clearUserName.setOnMouseClicked(event -> currUserField.clear());
        clearCurrPassField.setOnMouseClicked(event -> currPassField.clear());
        clearNewUserField.setOnMouseClicked(event -> newUserField.clear());
        clearNewPassField.setOnMouseClicked(event -> newPassField.clear());
    }

    /**
     * Signs the user out and navigates to the login view.
     *
     * @param event ActionEvent triggered by the user.
     */
    @FXML
    void signOut(ActionEvent event) {
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
    @FXML
    void goToMain(MouseEvent event) {
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
     * Handles user updates such as username and password changes with validation checks.
     *
     * @param event ActionEvent triggered by the user.
     */
    @FXML
    void confirmChanges(ActionEvent event) {
        String email = emailField.getText();
        String currentUsername = currUserField.getText();
        String currentPassword = currPassField.getText();
        String newUsername = newUserField.getText();
        String newPassword = newPassField.getText();

        if (email.isEmpty() || currentUsername.isEmpty() || currentPassword.isEmpty()) {
            alert.warningMessage("Fill in any blank field.");
            return;
        }

        // Validate new username
        if (!USERNAME_PATTERN.matcher(newUsername).matches()) {
            alert.errorMessage("Invalid username. It must be 5-20 characters long and include only letters, numbers, hyphens, or underscores.");
            return;
        }

        // Validate new password
        if (!PASSWORD_PATTERN.matcher(newPassword).matches()) {
            alert.errorMessage("Invalid password. It must have 8 characters, including 1 uppercase, 1 lowercase, 1 digit, and 1 special character.");
            return;
        }

        // Authenticate user and attempt to update credentials
        if (asc.validateUser(currentUsername, email, currentPassword)) {
            if (newUsername.isEmpty() || newPassword.isEmpty()) {
                alert.warningMessage("New username or password cannot be empty");
                return;
            }

            try {
                asc.updateUser(email, newUsername, newPassword);
                alert.successMessage("Successfully updated username and password!");
            } catch (Exception e) {
                alert.errorMessage("Failed to update user.");
                e.printStackTrace();
            }
        } else {
            alert.errorMessage("Invalid email, username, or password.");
        }
    }
}