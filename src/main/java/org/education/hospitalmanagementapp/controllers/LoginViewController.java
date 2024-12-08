package org.education.hospitalmanagementapp.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.education.hospitalmanagementapp.AlertMessages;
import org.education.hospitalmanagementapp.services.AuthServiceClass;

public class LoginViewController {
    private AlertMessages alert = new AlertMessages();
    private static AuthServiceClass asc = new AuthServiceClass();
    public static String username;



    @FXML
    private TextField userField, passField, emailField;

    public String getuser()
    {
        return username;
    }

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
                Parent root = FXMLLoader.load(getClass().getResource("/org.education.hospitalmanagementapp/MainMenu.fxml"));
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
