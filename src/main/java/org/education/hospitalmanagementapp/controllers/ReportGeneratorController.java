package org.education.hospitalmanagementapp.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.education.hospitalmanagementapp.AlertMessages;
import org.education.hospitalmanagementapp.services.AuthServiceClass;

import java.io.ByteArrayInputStream;
import java.io.IOException;

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

    private String currentUsername;
    private AuthServiceClass asc = new AuthServiceClass();

    public void setCurrentUsername(String username) {
        this.currentUsername = username;
        loadProfilePicture();
    }

    private void loadProfilePicture() {
        try {
            byte[] imageData = asc.getProfilePicture(currentUsername);
            if (imageData != null && imageData.length > 0) {
                ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
                Image image = new Image(bis);
                profile_Image.setImage(image);
                bis.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
    void goToMain(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org.education.hospitalmanagementapp/MainMenu.fxml"));
            Parent root = loader.load();

            MainMenuController mainMenuController = loader.getController();
            mainMenuController.setCurrentUsername(currentUsername);

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org.education.hospitalmanagementapp/MainMenu.fxml"));
            Parent root = loader.load();

            MainMenuController mainMenuController = loader.getController();
            mainMenuController.setCurrentUsername(currentUsername);

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
