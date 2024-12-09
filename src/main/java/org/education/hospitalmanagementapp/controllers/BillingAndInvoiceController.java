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
import org.education.hospitalmanagementapp.services.AuthServiceClass;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

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

    private final AlertMessages alertMessages = new AlertMessages();
    private final AuthServiceClass authService = new AuthServiceClass();

    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z]{2,30}$");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupServiceTypes();
        setupPriceChangeListener();
        setupClearButtons();
        setupNameValidation();
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

    private void setupNameValidation() {
        firstNameField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!NAME_PATTERN.matcher(newValue).matches()) {
                firstNameField.setStyle("-fx-border-color: red;");
            } else {
                firstNameField.setStyle("");
            }
        });

        lastNameField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!NAME_PATTERN.matcher(newValue).matches()) {
                lastNameField.setStyle("-fx-border-color: red;");
            } else {
                lastNameField.setStyle("");
            }
        });
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

        if (!NAME_PATTERN.matcher(firstName).matches() || !NAME_PATTERN.matcher(lastName).matches()) {
            alertMessages.errorMessage("Please enter valid names (2-30 letters only).");
            return;
        }

        Double price = servicePrices.get(selectedService);
        int patientId = generateRandomPatientId();

        if (insertPatientData(patientId, firstName, lastName, selectedService, price)) {
            String message = String.format(
                    "Patient ID: %d\nPatient: %s %s\nService: %s\nCharge: $%.2f",
                    patientId, firstName, lastName, selectedService, price
            );
            alertMessages.successMessage(message);
        } else {
            alertMessages.errorMessage("Failed to save patient data.");
        }
    }

    private int generateRandomPatientId() {
        Random random = new Random();
        return 100000 + random.nextInt(900000);
    }

    private boolean insertPatientData(int patientId, String firstName, String lastName, String service, Double cost) {
        String sql = "INSERT INTO patients (PatientID, FirstName, LastName, Services, Cost) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = authService.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, patientId);
            pstmt.setString(2, firstName);
            pstmt.setString(3, lastName);
            pstmt.setString(4, service);
            pstmt.setDouble(5, cost);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
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