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

public class ReportGeneratorController {

    @FXML
    private Button billingCsvBttn;

    @FXML
    private Button billingPdfBttn;

    @FXML
    private ImageView menu;

    @FXML
    private ImageView noti_image;

    @FXML
    private Label num_of_noti;

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

    @FXML
    void generateBillingPdf(ActionEvent event) {

    }

    @FXML
    void generatePtntCsv(ActionEvent event) {

    }

    @FXML
    void generatePtntPdf(ActionEvent event) {

    }

    @FXML
    void generateStaffCsv(ActionEvent event) {

    }

    @FXML
    void generateStaffPdf(ActionEvent event) {

    }

    @FXML
    void genrateBillingCsv(ActionEvent event) {

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
