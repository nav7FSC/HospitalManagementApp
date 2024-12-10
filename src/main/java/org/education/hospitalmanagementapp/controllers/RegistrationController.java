package org.education.hospitalmanagementapp.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.education.hospitalmanagementapp.AlertMessages;
import org.education.hospitalmanagementapp.services.AuthServiceClass;
import javafx.stage.FileChooser;
import javafx.scene.image.Image;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

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
    private TextField firstNameField, lastNameField;

    @FXML
    private PasswordField passwordField, confirmPassField;

    @FXML
    private ImageView profileImageView;

    private ContextMenu profileMenu;

    private byte[] profileImageData;

    @FXML
    public void initialize() {
        setupProfilePictureMenu();
        profileImageView.setOnMouseClicked(event -> {
            profileMenu.show(profileImageView, event.getScreenX(), event.getScreenY());
        });
    }

    private void selectProfileImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Profile Picture");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            try {
                profileImageData = Files.readAllBytes(selectedFile.toPath());
                Image image = new Image(selectedFile.toURI().toString());
                profileImageView.setImage(image);
            } catch (IOException e) {
                alert.errorMessage("Error reading image file: " + e.getMessage());
            }
        }
    }

    private void setupProfilePictureMenu() {
        profileMenu = new ContextMenu();
        MenuItem addPictureItem = new MenuItem("Add profile picture");
        MenuItem removePictureItem = new MenuItem("Remove Profile Picture");

        addPictureItem.setOnAction(event -> selectProfileImage());
        removePictureItem.setOnAction(event -> removeProfilePicture());

        profileMenu.getItems().addAll(addPictureItem, removePictureItem);

        profileMenu.setStyle(
                "-fx-background-color: #F8F7FA; " +
                        "-fx-background-radius: 8px; " +
                        "-fx-border-radius: 8px;"
        );
    }

    private void removeProfilePicture() {
        profileImageView.setImage(null);
        profileImageData = null;
    }


    /**
     * Handles the user registration process. Validates input, checks for existing usernames,
     * creates a new user, and navigates to the main menu upon successful registration.
     *
     * @param event the action event triggered by clicking the register button
     */
    @FXML
    void loginUser(ActionEvent event) {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String username = usernameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPassField.getText();

        if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() ||
                email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
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

        asc.insertUser(username, email, password, profileImageData);

        alert.successMessage("Successfully created your account!");

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
    }

    /**
     * Navigates back to the login page from the registration view.
     *
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
     *
     * @param username the username entered by the user
     * @param email    the email entered by the user
     * @param password the password entered by the user
     * @return true if all inputs are valid, false otherwise
     */
    private boolean validateInput(String username, String email, String password) {
        if (!FIRSTNAME_PATTERN.matcher(firstNameField.getText()).matches()) {
            alert.errorMessage("Invalid first name format. Must start with uppercase letter.");
            return false;
        }
        if (!LAST_NAME_PATTERN.matcher(lastNameField.getText()).matches()) {
            alert.errorMessage("Invalid last name format. Must start with uppercase letter.");
            return false;
        }
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