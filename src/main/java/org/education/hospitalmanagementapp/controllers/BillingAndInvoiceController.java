package org.education.hospitalmanagementapp.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.education.hospitalmanagementapp.AlertMessages;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class BillingAndInvoiceController implements Initializable {

    @FXML
    private TextField firstNameField;

    @FXML
    private ImageView clear_FirstName;

    @FXML
    private TextField lastNameField;

    @FXML
    private ImageView clearLastName;

    @FXML
    private ImageView noti_image;

    @FXML
    private Label num_of_noti;

    @FXML
    private TextField priceField;

    @FXML
    private ImageView profile_Image;

    @FXML
    private ComboBox<String> typeOfService;

    private Map<String, Double> servicePrices;

    // Instance of AlertMessages to handle alerts
    private final AlertMessages alertMessages = new AlertMessages();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupServiceTypes();
        setupPriceChangeListener();
        setupClearButtons();
    }

    private void setupServiceTypes() {
        servicePrices = new HashMap<>();
        servicePrices.put("General Consultation", 100.0);
        servicePrices.put("Specialist Consultation", 150.0);
        servicePrices.put("X-Ray", 200.0);
        servicePrices.put("Blood Test", 80.0);
        servicePrices.put("MRI Scan", 500.0);
        servicePrices.put("CT Scan", 450.0);
        servicePrices.put("Ultrasound", 150.0);
        servicePrices.put("Physical Therapy Session", 120.0);
        servicePrices.put("Dental Cleaning", 90.0);
        servicePrices.put("Eye Examination", 110.0);
        servicePrices.put("Vaccination", 60.0);
        servicePrices.put("Allergy Test", 130.0);
        servicePrices.put("ECG", 95.0);
        servicePrices.put("Mammogram", 180.0);
        servicePrices.put("Colonoscopy", 800.0);

        typeOfService.getItems().addAll(servicePrices.keySet());
    }

    private void setupPriceChangeListener() {
        typeOfService.setOnAction(event -> {
            String selectedService = typeOfService.getValue();
            if (selectedService != null) {
                Double price = servicePrices.get(selectedService);
                priceField.setText(String.format("%.2f", price));
            }
        });
    }

    private void setupClearButtons() {
        clear_FirstName.setOnMouseClicked(event -> firstNameField.clear());
        clearLastName.setOnMouseClicked(event -> lastNameField.clear());
    }

    @FXML
    void calculatePatientCharge(ActionEvent event) {
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String selectedService = typeOfService.getValue();

        if (firstName.isEmpty() || lastName.isEmpty() || selectedService == null) {
            alertMessages.errorMessage("Please fill in all fields and select a service.");
            return;
        }

        Double price = servicePrices.get(selectedService);
        String message = String.format(
                "Patient: %s %s\nService: %s\nCharge: $%.2f",
                firstName, lastName, selectedService, price
        );

        alertMessages.successMessage(message);
    }

    @FXML
    void goToMain(MouseEvent event) {
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
            alertMessages.errorMessage("Failed to load the Login View.");
        }
    }
}