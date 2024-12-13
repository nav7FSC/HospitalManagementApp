package org.education.hospitalmanagementapp.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.education.hospitalmanagementapp.AlertMessages;
import org.education.hospitalmanagementapp.services.AuthServiceClass;
import javafx.stage.FileChooser;
import javafx.scene.image.Image;

import javafx.scene.control.Dialog;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import java.util.Optional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import java.util.Optional;
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

    @FXML
    private ImageView tosDisagree;

    private ContextMenu profileMenu;

    private byte[] profileImageData;

    /**
     * Initializes the controller, sets up the profile image context menu, and adds real-time input validation listeners.
     */
    @FXML
    public void initialize() {
        setupProfilePictureMenu();
        profileImageView.setOnMouseClicked(event -> {
            profileMenu.show(profileImageView, event.getScreenX(), event.getScreenY());
        });

        String defaultImagePath = "/images/Generic_avatar.png";
        Image defaultImage = new Image(getClass().getResourceAsStream(defaultImagePath));
        profileImageView.setImage(defaultImage);

        setupRealTimeValidation();
    }


    /**
     * Sets up real-time validation for user input fields.
     */
    private void setupRealTimeValidation() {
        addValidationListener(firstNameField, FIRSTNAME_PATTERN, "Invalid first name format. Must start with uppercase.");
        addValidationListener(lastNameField, LAST_NAME_PATTERN, "Invalid last name format. Must start with uppercase.");
        addValidationListener(usernameField, USERNAME_PATTERN, "Invalid username format.");
        addValidationListener(emailField, EMAILPATTERN, "Invalid email format.");
        addValidationListener(passwordField, PASSWORD_PATTERN, "Invalid password format. Must include uppercase, lowercase, number, and special character.");
        addConfirmPasswordValidation();
    }

    /**
     * Adds a validation listener to a text field for real-time input validation.
     *
     * @param textField    the text field to validate
     * @param pattern      the regex pattern to validate against
     * @param errorMessage the error message to display if validation fails
     */
    private void addValidationListener(TextField textField, Pattern pattern, String errorMessage) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty() && pattern.matcher(newValue).matches()) {
                textField.setStyle("-fx-background-color: #d4edda; -fx-border-color: #28a745; -fx-border-width: 2px;");
            } else {
                textField.setStyle("-fx-background-color: #f8d7da; -fx-border-color: #dc3545; -fx-border-width: 2px;");
            }
        });
    }

    /**
     * Validates the confirm password field in real-time, highlighting it green if it matches
     * the password field or red if it does not.
     */
    private void addConfirmPasswordValidation() {
        confirmPassField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                confirmPassField.setStyle(null);
            } else if (newValue.equals(passwordField.getText())) {
                confirmPassField.setStyle("-fx-background-color: #d4edda; -fx-border-color: #28a745; -fx-border-width: 2px;");
            } else {
                confirmPassField.setStyle("-fx-background-color: #f8d7da; -fx-border-color: #dc3545; -fx-border-width: 2px;");
            }
        });

        passwordField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (confirmPassField.getText().isEmpty()) {
                confirmPassField.setStyle(null);
            } else if (confirmPassField.getText().equals(newValue)) {
                confirmPassField.setStyle("-fx-background-color: #d4edda; -fx-border-color: #28a745; -fx-border-width: 2px;");
            } else {
                confirmPassField.setStyle("-fx-background-color: #f8d7da; -fx-border-color: #dc3545; -fx-border-width: 2px;");
            }
        });
    }

    /**
     * Opens a file chooser to select a profile image and updates the profile image view.
     */
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

    /**
     * Sets up the context menu for the profile image, allowing the user to add or remove a profile picture.
     */
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

    /**
     * Removes the profile picture from the view.
     */
    private void removeProfilePicture() {
        String defaultImagePath = "/images/Generic_avatar.png";
        Image defaultImage = new Image(getClass().getResourceAsStream(defaultImagePath));
        profileImageView.setImage(defaultImage);
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

        if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
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

        // Show TOS dialog before creating account
        if (showTOSDialog()) {
            asc.insertUser(username, email, password, profileImageData);
            alert.successMessage("Successfully created your account!");
            navigateToMainMenu(event, username);
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

    private boolean showTOSDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Terms of Service");
        dialog.setHeaderText("Hospital Management System Terms of Service");

        VBox content = new VBox(15);
        content.setPadding(new Insets(20));

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefViewportHeight(400);

        Label tosText = new Label(
                "Hospital Staff Terms of Service\n\n" +
                        "1. Patient Data Confidentiality\n" +
                        "• Maintain strict HIPAA compliance\n" +
                        "• Access patient data only when necessary\n" +
                        "• Never share login credentials\n\n" +
                        "2. Appointment Management\n" +
                        "• Schedule appointments accurately\n" +
                        "• Update patient records promptly\n" +
                        "• Maintain scheduling integrity\n\n" +
                        "3. System Usage\n" +
                        "• Use system for authorized purposes only\n" +
                        "• Report technical issues immediately\n" +
                        "• Follow hospital data protocols\n\n" +
                        "4. Professional Conduct\n" +
                        "• Maintain professional communication\n" +
                        "• Follow hospital policies\n" +
                        "• Protect patient privacy"
        );
        tosText.setWrapText(true);

        CheckBox agreeCheckBox = new CheckBox("I agree to the Terms of Service");
        CheckBox disagreeCheckBox = new CheckBox("I do not agree");

        // Make checkboxes mutually exclusive
        agreeCheckBox.setOnAction(e -> {
            if (agreeCheckBox.isSelected()) disagreeCheckBox.setSelected(false);
        });
        disagreeCheckBox.setOnAction(e -> {
            if (disagreeCheckBox.isSelected()) agreeCheckBox.setSelected(false);
        });

        scrollPane.setContent(tosText);
        content.getChildren().addAll(scrollPane, agreeCheckBox, disagreeCheckBox);

        dialog.getDialogPane().setContent(content);
        dialog.getDialogPane().getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
        dialog.getDialogPane().getStyleClass().add("custom-alert");

        ButtonType confirmButton = new ButtonType("Continue", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(confirmButton);

        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent() && result.get() == confirmButton) {
            if (agreeCheckBox.isSelected()) {
                Image agreeImage = new Image(getClass().getResourceAsStream("/images/tosAgree.png"));
                tosDisagree.setImage(agreeImage);
                return true;
            } else if (disagreeCheckBox.isSelected()) {
                alert.errorMessage("You must accept the Terms of Service to create an account.");
            } else {
                alert.errorMessage("Please select whether you agree or disagree with the Terms of Service.");
            }
        }
        return false;
    }
    private void navigateToMainMenu(ActionEvent event, String username) {
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

    private void proceedWithRegistration() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org.education.hospitalmanagementapp/MainMenu.fxml"));
            Parent root = loader.load();

            MainMenuController mainMenuController = loader.getController();
            mainMenuController.setCurrentUsername(usernameField.getText());

            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
            Stage window = (Stage) usernameField.getScene().getWindow();
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