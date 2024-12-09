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

/**
 * Controller class for managing patient information in the Hospital Management App.
 * Provides functionality for adding, updating, and navigating between views.
 */
public class PatientManagerController {

    @FXML
    private TextField firstNameField, lastNameField, dobField, phoneNumberField, addressField;
    @FXML
    private TextField confirmFirstNameField, confirmLastNameField, changePhoneNumberField, changeAddressField;
    private AuthServiceClass asc = new AuthServiceClass();
    private AlertMessages alert = new AlertMessages();

    /**
     * Handles adding a new patient's information to the database.
     *
     * @param event the action event triggered by the user
     */
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

    /**
     * Handles updating an existing patient's phone number and/or address in the database.
     *
     * @param event the action event triggered by the user
     */
    @FXML
    void changePatientInfo(ActionEvent event){
        String firstName = confirmFirstNameField.getText();
        String lastName = confirmLastNameField.getText();
        String phoneNumber = changePhoneNumberField.getText();
        String address = changeAddressField.getText();

        if(firstName.isEmpty() || lastName.isEmpty()){
            alert.warningMessage("First and last name must be filled in.");
            return;
        }

        if(phoneNumber.isEmpty()){
            asc.updatePatientAddress(firstName,lastName,address);
            alert.successMessage("Successfully updated patients address!");
        }
        if(address.isEmpty()){
            asc.updatePatientNumber(firstName,lastName,phoneNumber);
            alert.successMessage("Successfully updated patients phone number!");
        }
        if(!address.isEmpty() && !phoneNumber.isEmpty()){
            asc.updatePatientAddress(firstName,lastName,address);
            asc.updatePatientNumber(firstName,lastName,phoneNumber);
            alert.successMessage("Successfully updated patients phone number and address!");
        }
    }

    /**
     * Navigates to the main menu view.
     *
     * @param event the action event triggered by the user
     */
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
