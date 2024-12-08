package org.education.hospitalmanagementapp.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.education.hospitalmanagementapp.AlertMessages;
import org.education.hospitalmanagementapp.services.AuthServiceClass;

public class UserManagementController  {

    @FXML
    private ImageView clearCurrPassField;

    @FXML
    private ImageView clearNewPassField;

    @FXML
    private ImageView clearNewUserField;

    @FXML
    private ImageView clear_editUserNameField;

    @FXML
    private TextField currPassField;

    @FXML
    private TextField edit_UserNameField;

    @FXML
    private ImageView edit_clearEmailfield;

    @FXML
    private TextField edit_emailField;

    @FXML
    private ImageView menu;

    @FXML
    private TextField newPassField;

    @FXML
    private TextField newUserField;

    @FXML
    private ImageView noti_image;

    @FXML
    private Label num_of_noti;

    @FXML
    private ImageView profile_Image;

    private AuthServiceClass asc = new AuthServiceClass();
    private AlertMessages alert = new AlertMessages();

    @FXML
    void goToMainMenu(ActionEvent event){
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
    @FXML
    void signOut(ActionEvent event){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/org.education.hospitalmanagementapp/LoginView.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void confirmChanges(ActionEvent event) {
        String email = edit_emailField.getText();
        String currentUsername = edit_UserNameField.getText();
        String currentPassword = currPassField.getText();
        String newUsername = newUserField.getText();
        String newPassword = newPassField.getText();

        if (email.isEmpty() || currentUsername.isEmpty() || currentPassword.isEmpty()) {
            alert.warningMessage("Fill in any blank field.");
            return;
        }

        if (asc.validateUser(currentUsername, email, currentPassword)) {
            if (newUsername.isEmpty() || newPassword.isEmpty()) {
                alert.warningMessage("New username or password cannot be empty");
                return;
            }

            try {
                asc.updateUser(email, newUsername, newPassword);
                alert.successMessage("Successfully updated username and password!");
            } catch (Exception e) {
                alert.errorMessage("Failed to update user.");
                e.printStackTrace();
            }
        } else {
            alert.errorMessage("Invalid email, username, or password.");
        }
    }

}
