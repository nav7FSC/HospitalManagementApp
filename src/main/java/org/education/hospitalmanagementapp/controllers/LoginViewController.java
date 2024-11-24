package org.education.hospitalmanagementapp.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import org.education.hospitalmanagementapp.services.AuthServiceClass;

public class LoginViewController {

    private static AuthServiceClass asc;

    @FXML
    private ImageView calendar_image;

    @FXML
    private TextField userField, passField, emailField;

    @FXML
    private Button login_Button;

    @FXML
    private ImageView menu;


    @FXML
    private ImageView noti_image;

    @FXML
    private Label num_of_noti;

    @FXML
    private Button regiter_Button;

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

    }

}
