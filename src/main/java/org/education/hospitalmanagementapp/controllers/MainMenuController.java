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

public class MainMenuController {

    @FXML
    private Button ApptmntBttn;

    @FXML
    private Button billingBttn;

    @FXML
    private Button goToDashBttn;

    @FXML
    private Button goToUsrManagementBttn;

    @FXML
    private Button inventoryBttn;

    @FXML
    private Button mainMenuSignOutBttn;

    @FXML
    private ImageView menu;

    @FXML
    private ImageView noti_image;

    @FXML
    private Label num_of_noti;

    @FXML
    private Button patientBttn;

    @FXML
    private ImageView profile_Image;

    @FXML
    private Button reportBttn;

    @FXML
    void GoToUsrManagement(ActionEvent event) {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/org.education.hospitalmanagementapp/HospitalDashBoard.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}
