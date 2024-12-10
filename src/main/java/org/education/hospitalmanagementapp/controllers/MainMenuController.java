package org.education.hospitalmanagementapp.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.education.hospitalmanagementapp.services.AuthServiceClass;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Controller for the Main Menu of the Hospital Management App.
 * Handles navigation to various modules within the application.
 */
public class MainMenuController {

    @FXML
    private ImageView profile_Image;

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
     * Navigates to the Billing and Invoices module.
     *
     * @param event the action event triggered by the user
     */
    @FXML
    void goToBilling(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org.education.hospitalmanagementapp/Billing_And_Invoices.fxml"));
            Parent root = loader.load();

            BillingAndInvoiceController controller = loader.getController();
            controller.setCurrentUsername(currentUsername);

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org.education.hospitalmanagementapp/HospitalReportGenerator.fxml"));
            Parent root = loader.load();

            ReportGeneratorController controller = loader.getController();
            controller.setCurrentUsername(currentUsername);

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org.education.hospitalmanagementapp/UserManagement.fxml"));
            Parent root = loader.load();

            UserManagementController controller = loader.getController();
            controller.setCurrentUsername(currentUsername);

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org.education.hospitalmanagementapp/HospitalDashBoard.fxml"));
            Parent root = loader.load();

            DashBoardController controller = loader.getController();
            controller.setCurrentUsername(currentUsername);

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org.education.hospitalmanagementapp/ScheduleAppointment.fxml"));
            Parent root = loader.load();

            AppointmentSchedulerController controller = loader.getController();
            controller.setCurrentUsername(currentUsername);

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org.education.hospitalmanagementapp/HospitalPatientManagement.fxml"));
            Parent root = loader.load();

            PatientManagerController controller = loader.getController();
            controller.setCurrentUsername(currentUsername);

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