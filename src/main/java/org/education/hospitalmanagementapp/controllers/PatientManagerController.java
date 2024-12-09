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
import org.education.hospitalmanagementapp.AlertMessages;
import org.education.hospitalmanagementapp.services.AuthServiceClass;

public class PatientManagerController {

    @FXML
    private TextField firstNameField, lastNameField, dobField, phoneNumberField, addressField;
    @FXML
    private TextField confirmFirstNameField, confirmLastNameField, changePhoneNumberField, changeAddressField;
    private AuthServiceClass asc = new AuthServiceClass();
    private AlertMessages alert = new AlertMessages();

    @FXML
    void addPatientInfo(ActionEvent event){
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String dob = dobField.getText();
        String phoneNumber = phoneNumberField.getText();
        String address = addressField.getText();

        if(firstName.isEmpty() || lastName.isEmpty() || dob.isEmpty() || phoneNumber.isEmpty() || address.isEmpty()){
            alert.warningMessage("Fill in any blank field.");
            return;
        }

        asc.insertPatient(firstName,lastName,dob,phoneNumber,address);
        alert.successMessage("Successfully inserted new patient into the database!");
    }

    @FXML
    void changePatientInfo(ActionEvent event){

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
