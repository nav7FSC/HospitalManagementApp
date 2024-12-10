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
 * Controller class for handling the login view in the Hospital Management App.
 * Manages user login and registration navigation.
 */
public class LoginViewController {

    /**
     * Regex pattern for validating a username.
     * The username must be between 5 and 20 characters long and can contain letters, numbers, dots, and hyphens.
     */
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9.-]{5,20}$");

    /**
     * Regex pattern for validating an email address.
     * The email must match common email formats for domains like Gmail, Yahoo, Outlook, and others.
     * The domain must have a valid extension such as .com, .edu, .org, etc.
     */
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9.%+-]+@(gmail|yahoo|hotmail|outlook|aol|icloud|protonmail|zoho|yandex|mail)\\.(com|edu|gov|org|net|io|co)$",
            Pattern.CASE_INSENSITIVE
    );

    /**
     * Regex pattern for validating a password.
     * The password must be between 8 and 20 characters long and must contain at least one uppercase letter, one lowercase letter, one number, and one special character.
     */
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%?&])[A-Za-z\\d@$!%?&]{8,20}$");

    private AlertMessages alert = new AlertMessages();
    private static AuthServiceClass asc = new AuthServiceClass();
    public static String username;

    @FXML
    private TextField userField, emailField;
    @FXML
    private PasswordField passField;

    /**
     * Getter method to retrieve the currently logged-in username.
     * @return the username
     */
    public String getuser()
    {
        return username;
    }

    /**
     * Initializes the controller by attaching real-time validation listeners to the fields.
     * Ensures the fields for username, email, and password are validated as the user types.
     */
    @FXML
    private void initialize() {
        addValidationListener(userField, USERNAME_PATTERN);
        addValidationListener(emailField, EMAIL_PATTERN);
        addValidationListener(passField, PASSWORD_PATTERN);
    }

    /**
     * Adds a listener to a text field that validates the input in real-time.
     * If the input matches the given regex pattern, the field's style is updated to green; otherwise, it is updated to red.
     *
     * @param field the text field to which the listener is added
     * @param pattern the regex pattern against which the input is validated
     */
    private void addValidationListener(TextField field, Pattern pattern) {
        field.textProperty().addListener((observable, oldValue, newValue) -> {
            if (pattern.matcher(newValue).matches()) {
                field.setStyle("-fx-background-color: #d4edda; -fx-border-color: #28a745; -fx-border-width: 2px;");
            } else {
                field.setStyle("-fx-background-color: #f8d7da; -fx-border-color: #dc3545; -fx-border-width: 2px;");
            }
        });
    }

    /**
     * Handles user login action. Validates user credentials and navigates to the main menu on success.
     * Displays error messages if fields are empty or credentials are invalid.
     * @param event the action event triggered by clicking the login button
     */
    @FXML
    void loginUser(ActionEvent event) {
        this.username = userField.getText();
        String email = emailField.getText();
        String password = passField.getText();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            alert.errorMessage("Please fill in all fields.");
            return;
        }

        boolean isValidUser = asc.validateUser(username, email, password);

        if (isValidUser) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org.education.hospitalmanagementapp/MainMenu.fxml"));
                Parent root = loader.load();

                MainMenuController mainMenuController = loader.getController();
                mainMenuController.setCurrentUsername(username);

                Scene scene = new Scene(root);
                scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            alert.errorMessage("Invalid login credentials. Please try again.");
        }
    }

    /**
     * Handles user registration navigation. Navigates to the registration view on clicking the register button.
     * Displays an error message if navigation fails.
     * @param event the action event triggered by clicking the register button
     */
    @FXML
    void registerUser(ActionEvent event) {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/org.education.hospitalmanagementapp/RegistrationView.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
