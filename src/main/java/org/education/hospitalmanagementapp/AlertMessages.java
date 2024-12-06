package org.education.hospitalmanagementapp;

import javafx.scene.control.Alert;

public class AlertMessages {
    private Alert alert;

    public void errorMessage(String text){
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Message");
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }

    public void successMessage(String text){
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Message");
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }
}
