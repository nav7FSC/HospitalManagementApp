package org.education.hospitalmanagementapp.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.education.hospitalmanagementapp.services.AuthServiceClass;

public class RegistrationController {

    AuthServiceClass asc;

    @FXML
    private TextField usernameField, emailField, passwordField;

    @FXML
    void loginUser(ActionEvent event) {
        String username = usernameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            System.out.println("All fields are required.");
            return;
        }

        asc = new AuthServiceClass();

        asc.insertUser(username, email, password);

        System.out.println("User details saved to the database!");
    }

}
