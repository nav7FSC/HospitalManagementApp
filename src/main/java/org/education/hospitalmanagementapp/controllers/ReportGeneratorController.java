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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.education.hospitalmanagementapp.AlertMessages;

/**
 * Controller for the Report Generator module in the Hospital Management App.
 * Provides functionality to generate reports in various formats.
 */
public class ReportGeneratorController {

    @FXML
    private Button billingCsvBttn;

    @FXML
    private Button billingPdfBttn;

    @FXML
    private Button patntRecPdfBttn;

    @FXML
    private ImageView profile_Image;

    @FXML
    private Button ptntCsvBttn;

    @FXML
    private Button staffCsvBttn;

    @FXML
    private Button staffPdfBttn;

    private final AlertMessages alertMessages = new AlertMessages();

    /**
     * Generates a PDF report for billing records.
     *
     * @param event the action event triggered by the user
     */
    @FXML
    void generateBillingPdf(ActionEvent event) {

    }

    /**
     * Generates a CSV report for patient records.
     *
     * @param event the action event triggered by the user
     */
    @FXML
    void generatePtntCsv(ActionEvent event) {

    }

    /**
     * Generates a PDF report for patient records.
     *
     * @param event the action event triggered by the user
     */
    @FXML
    void generatePtntPdf(ActionEvent event) {

    }

    /**
     * Generates a CSV report for staff records.
     *
     * @param event the action event triggered by the user
     */
    @FXML
    void generateStaffCsv(ActionEvent event) {

    }

    /**
     * Generates a PDF report for staff records.
     *
     * @param event the action event triggered by the user
     */
    @FXML
    void generateStaffPdf(ActionEvent event) {

    }

    /**
     * Generates a CSV report for billing records.
     *
     * @param event the action event triggered by the user
     */
    @FXML
    void genrateBillingCsv(ActionEvent event) {

    }

    /**
     * Navigates back to the Main Menu.
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
     * Navigates the user back to the main menu.
     * @param event the mouse click event triggered by clicking the navigation button
     */
    @FXML
    void goToTheMain(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/org.education.hospitalmanagementapp/MainMenu.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
            alertMessages.errorMessage("Failed to load the Main Menu.");
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

}
