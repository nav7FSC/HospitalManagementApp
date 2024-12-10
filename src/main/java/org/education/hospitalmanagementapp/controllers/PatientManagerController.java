package org.education.hospitalmanagementapp.controllers;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.education.hospitalmanagementapp.AlertMessages;
import org.education.hospitalmanagementapp.services.AuthServiceClass;

/**
 * Controller class for managing patient information in the Hospital Management App.
 * Provides functionality for adding, updating, and navigating between views.
 */
public class PatientManagerController {

    private final AlertMessages alertMessages = new AlertMessages();
    @FXML
    private TextField firstNameField, lastNameField, dobField, phoneNumberField, addressField;
    @FXML
    private TextField confirmFirstNameField, confirmLastNameField, changePhoneNumberField, changeAddressField;
    @FXML
    private ImageView clearFirstName, clearLastName, clearDOB, clearPhoneNumber, clearAddress, clearConfirmFirstName, clearConfirmLastName, clearChangePhoneNumber, clearChangeAddress;
    private AuthServiceClass asc = new AuthServiceClass();
    private AlertMessages alert = new AlertMessages();

    @FXML
    private ImageView profile_Image;
    private String currentUsername;

    /**
     * Sets the current username and loads the profile picture.
     */
    public void setCurrentUsername(String username) {
        this.currentUsername = username;
        loadProfilePicture();
    }

    /**
     * Loads the profile picture from the database.
     */
    private void loadProfilePicture() {
        try {
            byte[] imageData = asc.getProfilePicture(currentUsername);
            if (imageData != null && imageData.length > 0) {
                ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
                Image image = new Image(bis);
                profile_Image.setImage(image);
                bis.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Initializes the controller by setting up listeners for real-time input validation.
     */
    public void initialize() {
        // Add listeners for real-time validation
        addValidationListeners();
        setupClearButtons();
    }

    /**
     * Adds listeners to text fields for real-time validation using regular expressions.
     * Highlights the fields in green if valid, red if invalid.
     */
    private void addValidationListeners() {
        // First Name & Last Name Validation
        firstNameField.textProperty().addListener((observable, oldValue, newValue) -> validateTextField(firstNameField, "^[a-zA-Z]+$"));
        lastNameField.textProperty().addListener((observable, oldValue, newValue) -> validateTextField(lastNameField, "^[a-zA-Z]+$"));

        // Date of Birth Validation
        dobField.textProperty().addListener((observable, oldValue, newValue) -> validateTextField(dobField, "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$"));

        // Phone Number Validation
        phoneNumberField.textProperty().addListener((observable, oldValue, newValue) -> validateTextField(phoneNumberField, "^\\d{10}$"));

        // Address Validation
        addressField.textProperty().addListener((observable, oldValue, newValue) -> validateTextField(addressField, "^[a-zA-Z0-9\\s]+$"));

        // Confirm First & Last Name Validation
        confirmFirstNameField.textProperty().addListener((observable, oldValue, newValue) -> validateTextField(confirmFirstNameField, "^[a-zA-Z]+$"));
        confirmLastNameField.textProperty().addListener((observable, oldValue, newValue) -> validateTextField(confirmLastNameField, "^[a-zA-Z]+$"));

        changePhoneNumberField.textProperty().addListener((observable, oldValue, newValue) -> validateTextField(changePhoneNumberField, "^\\d{10}$"));
        changeAddressField.textProperty().addListener((observable, oldValue, newValue) -> validateTextField(changeAddressField, "^[a-zA-Z0-9\\s]+$"));
    }

    public void setupClearButtons() {
        clearFirstName.setOnMouseClicked(event -> firstNameField.clear());
        clearLastName.setOnMouseClicked(event -> lastNameField.clear());
        clearDOB.setOnMouseClicked(event -> dobField.clear());
        clearPhoneNumber.setOnMouseClicked(event -> phoneNumberField.clear());
        clearAddress.setOnMouseClicked(event -> addressField.clear());
        clearConfirmFirstName.setOnMouseClicked(event -> confirmFirstNameField.clear());
        clearConfirmLastName.setOnMouseClicked(mouseEvent -> confirmLastNameField.clear());
        clearChangePhoneNumber.setOnMouseClicked(mouseEvent -> changePhoneNumberField.clear());
        clearChangeAddress.setOnMouseClicked(mouseEvent -> changeAddressField.clear());
    }

    /**
     * Validates a text field's input against a provided regular expression.
     * Highlights the field's border green if valid or red if invalid.
     *
     * @param field The text field to validate.
     * @param regex The regular expression to validate the text against.
     */
    private void validateTextField(TextField field, String regex) {
        if (field.getText().matches(regex)) {
            field.setStyle("-fx-background-color: #d4edda; -fx-border-color: #28a745; -fx-border-width: 2px;"); // Valid input
        } else {
            field.setStyle("-fx-background-color: #f8d7da; -fx-border-color: #dc3545; -fx-border-width: 2px;"); // Invalid input
        }
    }

    /**
     * Handles the submission of patient information for insertion into the database.
     * Ensures all fields are filled and validates their inputs before proceeding.
     * Displays appropriate success or warning messages based on validation results.
     *
     * @param event The action event triggered by the user clicking the add button.
     */
    @FXML
    void addPatientInfo(ActionEvent event) {
        // Ensure no blank fields are left
        if (firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty()
                || dobField.getText().isEmpty() || phoneNumberField.getText().isEmpty()
                || addressField.getText().isEmpty()) {
            alert.warningMessage("All fields must be filled!");
            return;
        }

        // Call service after validation
        if (firstNameField.getStyle().contains("red") ||
                lastNameField.getStyle().contains("red") ||
                dobField.getStyle().contains("red") ||
                phoneNumberField.getStyle().contains("red") ||
                addressField.getStyle().contains("red")) {
            alert.warningMessage("Please fix invalid input fields before proceeding.");
            return;
        }

        asc.insertPatient(
                firstNameField.getText(),
                lastNameField.getText(),
                dobField.getText(),
                phoneNumberField.getText(),
                addressField.getText()
        );

        alert.successMessage("Successfully inserted new patient into the database!");
    }

    /**
     * Handles the submission of updates to patient information, such as address and phone number changes.
     * Validates the fields and determines whether to update only the address, only the phone number, or both.
     * Displays appropriate success or warning messages based on the input validation and results of the operation.
     *
     * @param event The action event triggered by the user clicking the change button.
     */
    @FXML
    void changePatientInfo(ActionEvent event) {
        if (confirmFirstNameField.getText().isEmpty() || confirmLastNameField.getText().isEmpty()) {
            alert.warningMessage("First and Last Name must be filled!");
            return;
        }

        if (changePhoneNumberField.getStyle().contains("red") || changeAddressField.getStyle().contains("red")) {
            alert.warningMessage("Invalid phone number or address.");
            return;
        }

        if (changePhoneNumberField.getText().isEmpty()) {
            asc.updatePatientAddress(
                    confirmFirstNameField.getText(),
                    confirmLastNameField.getText(),
                    changeAddressField.getText()
            );
            alert.successMessage("Successfully updated patient's address!");
        } else if (changeAddressField.getText().isEmpty()) {
            asc.updatePatientNumber(
                    confirmFirstNameField.getText(),
                    confirmLastNameField.getText(),
                    changePhoneNumberField.getText()
            );
            alert.successMessage("Successfully updated patient's phone number!");
        } else {
            asc.updatePatientAddress(
                    confirmFirstNameField.getText(),
                    confirmLastNameField.getText(),
                    changeAddressField.getText()
            );
            asc.updatePatientNumber(
                    confirmFirstNameField.getText(),
                    confirmLastNameField.getText(),
                    changePhoneNumberField.getText()
            );
            alert.successMessage("Successfully updated both information.");
        }
    }

    /**
     * Navigates to the main menu view.
     *
     * @param event the action event triggered by the user
     */
    @FXML
    void goToMain(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org.education.hospitalmanagementapp/MainMenu.fxml"));
            Parent root = loader.load();

            MainMenuController mainMenuController = loader.getController();
            mainMenuController.setCurrentUsername(currentUsername);

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
     * Navigates the user back to the main menu.
     * @param event the mouse click event triggered by clicking the navigation button
     */
    @FXML
    void goToTheMain(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org.education.hospitalmanagementapp/MainMenu.fxml"));
            Parent root = loader.load();

            MainMenuController mainMenuController = loader.getController();
            mainMenuController.setCurrentUsername(currentUsername);

            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
            alertMessages.errorMessage("Failed to load the Main Menu.");
        }
    }

    /**
     * Logs out the current user and navigates to the login view.
     *
     * @param event the action event triggered by the user
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

}
