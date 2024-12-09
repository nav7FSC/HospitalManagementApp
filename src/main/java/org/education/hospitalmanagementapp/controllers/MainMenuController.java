package org.education.hospitalmanagementapp.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller for the Main Menu of the Hospital Management App.
 * Handles navigation to various modules within the application.
 */
public class MainMenuController {

    @FXML
    private ImageView menu;

    @FXML
    private ImageView noti_image;

    @FXML
    private Label num_of_noti;

    @FXML
    private ImageView profile_Image;

    /**
     * Navigates to the Billing and Invoices module.
     *
     * @param event the action event triggered by the user
     */
    @FXML
    void goToBilling(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/org.education.hospitalmanagementapp/Billing_And_Invoices.fxml"));
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
     * Navigates to the Hospital Report Generator module.
     *
     * @param event the action event triggered by the user
     */
    @FXML
    void goToReport(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/org.education.hospitalmanagementapp/HospitalReportGenerator.fxml"));
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
     * Navigates to the User Management module.
     *
     * @param event the action event triggered by the user
     */
    @FXML
    void goToUsrManagement(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/org.education.hospitalmanagementapp/UserManagement.fxml"));
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
     * Navigates to the Hospital Dashboard module.
     *
     * @param event the action event triggered by the user
     */
    @FXML
    void goToDash(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/org.education.hospitalmanagementapp/HospitalDashBoard.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    /**
     * Navigates to the Appointment Management module.
     *
     * @param event the action event triggered by the user
     */
    @FXML
    void goToApptManagement(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/org.education.hospitalmanagementapp/ScheduleAppointment.fxml"));
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
     * Logs out the user and navigates to the Login View.
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

    /**
     * Navigates to the Patient Management module.
     *
     * @param event the action event triggered by the user
     */
    @FXML
    void goToPatient(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/org.education.hospitalmanagementapp/HospitalPatientManagement.fxml"));
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