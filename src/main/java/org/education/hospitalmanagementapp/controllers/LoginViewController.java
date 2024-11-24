package org.education.hospitalmanagementapp.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.education.hospitalmanagementapp.services.AuthServiceClass;

public class LoginViewController {

    private static AuthServiceClass asc;

    @FXML
    private TextField userField, passField, emailField;

    @FXML
    void loginUser(ActionEvent event) {
        String username = userField.getText();
        String email = emailField.getText();
        String password = passField.getText();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            System.out.println("All fields are required.");
            return;
        }

        asc = new AuthServiceClass();

        asc.insertUser(username, email, password);

        System.out.println("User details saved to the database!");
    }

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
