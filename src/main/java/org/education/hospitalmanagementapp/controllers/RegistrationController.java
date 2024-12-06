package org.education.hospitalmanagementapp.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.education.hospitalmanagementapp.AlertMessages;
import org.education.hospitalmanagementapp.services.AuthServiceClass;

import java.util.regex.Pattern;

public class RegistrationController {

    private static final Pattern FIRSTNAME_PATTERN = Pattern.compile("^[A-Z][a-zA-Z]$");
    private static final Pattern LAST_NAME_PATTERN = Pattern.compile("^[A-Z][a-zA-Z](-[A-Z][a-zA-Z]*)?$");
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9.-]{5,20}$");
    private static final Pattern EMAILPATTERN = Pattern.compile(
            "^[A-Za-z0-9.%+-]+@(gmail|yahoo|hotmail|outlook|aol|icloud|protonmail|zoho|yandex|mail)\\.(com|edu|gov|org|net|io|co)$",
            Pattern.CASE_INSENSITIVE
    );
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%?&])[A-Za-z\\d@$!%?&]{8,20}$");
    private AlertMessages alert = new AlertMessages();
    AuthServiceClass asc = new AuthServiceClass();

    @FXML
    private TextField usernameField, emailField, passwordField;

    @FXML
    void loginUser(ActionEvent event) {
        String username = usernameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty()){
            alert.errorMessage("Fill in any blank field.");
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

    private boolean validateInput(String username, String email, String password) {
        if (!USERNAME_PATTERN.matcher(username).matches() && !EMAILPATTERN.matcher(email).matches() && !PASSWORD_PATTERN.matcher(password).matches()) {
            alert.errorMessage("Wrong login credentials");
            return false;
        }
        if (!USERNAME_PATTERN.matcher(username).matches()) {
            alert.errorMessage("Wrong username format.");
            return false;
        }
        if (!EMAILPATTERN.matcher(email).matches()) {
            alert.errorMessage("Wrong email format.");
            return false;
        }
        if (!PASSWORD_PATTERN.matcher(password).matches()) {
            alert.errorMessage("Wrong password pattern.");
            return false;
        }
        return true;
    }
}
