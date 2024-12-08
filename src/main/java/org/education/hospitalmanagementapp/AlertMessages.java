package org.education.hospitalmanagementapp;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.stage.StageStyle;

public class AlertMessages {
    private Alert alert;

    public void showAlert(String title, String content, Alert.AlertType alertType) {
        alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
        dialogPane.getStyleClass().add("custom-alert");

        alert.getButtonTypes().setAll(ButtonType.OK);
        alert.initStyle(StageStyle.UNDECORATED);

        alert.showAndWait();
    }

    public void errorMessage(String text) {
        showAlert("Error", text, Alert.AlertType.ERROR);
    }

    public void successMessage(String text) {
        showAlert("Success", text, Alert.AlertType.INFORMATION);
    }

    public void warningMessage(String text) {
        showAlert("Warning", text, Alert.AlertType.WARNING);
    }
}