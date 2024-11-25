package org.education.hospitalmanagementapp.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import org.education.hospitalmanagementapp.SceneManager;

public class LoginViewController {

    @FXML
    private ImageView calendar_image;

    @FXML
    private TextField emailField;

    @FXML
    private Button login_Button;

    @FXML
    private ImageView menu;

    @FXML
    private ImageView noti_image;

    @FXML
    private Label num_of_noti;

    @FXML
    private TextField passField;

    @FXML
    private Button regiter_Button;

    @FXML
    private TextField userField;

    @FXML
    void loginUser(ActionEvent event) {
        // Add any login logic here if needed
        // For now, we'll just navigate to the MainMenu.fxml
        SceneManager.loadScene("MainMenu.fxml");
    }

    @FXML
    void registerUser(ActionEvent event) {
        // Add registration logic here if needed
    }
}