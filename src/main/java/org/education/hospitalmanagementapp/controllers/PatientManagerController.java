package org.education.hospitalmanagementapp.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class PatientManagerController {

    @FXML
    private TextField adressField;

    @FXML
    private Button changePatientBttn;

    @FXML
    private ImageView claerID;

    @FXML
    private ImageView clearAddressField;

    @FXML
    private ImageView clearEmailField;

    @FXML
    private ImageView clearFirstNameField;

    @FXML
    private ImageView clearLastNameField;

    @FXML
    private ImageView clearPhoneNum;

    @FXML
    private Button confirmPatienBttn;

    @FXML
    private TextField emailField;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private ImageView menu;

    @FXML
    private ImageView noti_image;

    @FXML
    private Label num_of_noti;

    @FXML
    private TextField patienIDField;

    @FXML
    private TextField phoneNumField;

    @FXML
    private ImageView profile_Image;

    @FXML
    void changePatient(ActionEvent event) {

    }

    @FXML
    void confirm(ActionEvent event) {

    }

    @FXML
    void goToMain(ActionEvent event){
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
