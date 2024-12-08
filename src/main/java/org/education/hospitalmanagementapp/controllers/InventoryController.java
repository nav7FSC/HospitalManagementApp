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

public class InventoryController{

    @FXML
    private ImageView menu;

    @FXML
    private ImageView noti_image;

    @FXML
    private Label numOfAnalgesics;

    @FXML
    private Label numOfAnesthesia;

    @FXML
    private Label numOfAntiBio;

    @FXML
    private Label numOfAntiDep;

    @FXML
    private Label numOfAntiemetics;

    @FXML
    private Label numOfBeds;

    @FXML
    private Label numOfDE;

    @FXML
    private Label numOfEkg;

    @FXML
    private Label numOfGD;

    @FXML
    private Label numOfGloves;

    @FXML
    private Label numOfGowns;

    @FXML
    private Label numOfHepaB;

    @FXML
    private Label numOfHpv;

    @FXML
    private Label numOfInfluenza;

    @FXML
    private Label numOfLD;

    @FXML
    private Label numOfPneumo;

    @FXML
    private Label numOfSC;

    @FXML
    private Label numOfSE;

    @FXML
    private Label numOfShingles;

    @FXML
    private Label numOfVentilators;

    @FXML
    private Label num_of_noti;

    @FXML
    private ImageView profile_Image;

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
