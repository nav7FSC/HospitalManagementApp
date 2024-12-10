package org.education.hospitalmanagementapp;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.stage.StageStyle;

/**
 * Utility class for displaying various types of alert messages in the application.
 */
public class AlertMessages {

    private Alert alert;
    /**
     * Displays an alert dialog with the specified title, content, and type.
     *
     * @param title     the title of the alert
     * @param content   the content/message of the alert
     * @param alertType the type of the alert (e.g., ERROR, INFORMATION, WARNING)
     */
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

    /**
     * Displays an error alert dialog with the specified text.
     *
     * @param text the error message to be displayed
     */
    public void errorMessage(String text) {
        showAlert("Error", text, Alert.AlertType.ERROR);
    }

    /**
     * Displays a success alert dialog with the specified text.
     *
     * @param text the success message to be displayed
     */
    public void successMessage(String text) {
        showAlert("Success", text, Alert.AlertType.INFORMATION);
    }

    /**
     * Displays a warning alert dialog with the specified text.
     *
     * @param text the warning message to be displayed
     */
    public void warningMessage(String text) {
        showAlert("Warning", text, Alert.AlertType.WARNING);
    }
}