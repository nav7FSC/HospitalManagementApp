package org.education.hospitalmanagementapp.controllers;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.education.hospitalmanagementapp.AlertMessages;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import java.security.SecureRandom;
import java.sql.ResultSet;
import java.util.Random;

public class AppointmentSchedulerController {

    @FXML
    private ToggleButton amtoggle;


    @FXML
    private DatePicker datePicker;


    @FXML
    private TextField doctorFirstName;

    @FXML
    private TextField doctorLastName;

    @FXML
    private TextField appointmentType;

    @FXML
    private TextField hourField;

    @FXML
    private TextField minuteField;

    @FXML
    private ImageView noti_image;

    @FXML
    private Label num_of_noti;

    @FXML
    private TextField patientFirstName;

    @FXML
    private TextField patientLastName;

    @FXML
    private ToggleButton pmtoggle;

    @FXML
    private ImageView profile_Image;

    private AlertMessages alertMessages = new AlertMessages();

    final String MYSQL_SERVER_URL = "jdbc:mysql://hospitalmanagement.mysql.database.azure.com/";
    final String DB_URL = "jdbc:mysql://hospitalmanagement.mysql.database.azure.com/hospital-management";
    final String USERNAME = "hospitaladmin";
    final String PASSWORD = "Manager1!";

    @FXML
    private void initialize() {
        addTimeFieldValidation(hourField);
        addTimeFieldValidation(minuteField);
        addNameFieldValidation(patientFirstName);
        addNameFieldValidation(patientLastName);
        addNameFieldValidation(doctorFirstName);
        addNameFieldValidation(doctorLastName);
        addNameFieldValidation(appointmentType);
    }

    // Method to reset the date picker when dateCancel is clicked
    @FXML
    void resetDatePicker(MouseEvent event) {
        datePicker.setValue(null);
        alertMessages.successMessage("Date picker reset.");
    }

    @FXML
    void saveSelectedDate(MouseEvent event) {
        LocalDate selectedDate = datePicker.getValue();
        LocalDate currentDate = LocalDate.now();

        if (selectedDate != null) {
            if (selectedDate.isBefore(currentDate)) {
                alertMessages.warningMessage("Cannot select a past date. Please choose a future date.");
                datePicker.setValue(null);
            } else {
                alertMessages.successMessage("Selected Date: " + selectedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            }
        } else {
            alertMessages.warningMessage("No date selected.");
        }
    }

    private void addTimeFieldValidation(TextField field) {
        field.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,2}")) {
                field.setText(oldValue);
            }
        });
    }
    private void addNameFieldValidation(TextField field) {
        field.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-Z\\s]*")) {
                field.setText(oldValue);
            }
        });
    }


    @FXML
    void toggleAMPM(ActionEvent event) {
        if (event.getSource() == amtoggle) {
            pmtoggle.setSelected(false);
        } else if (event.getSource() == pmtoggle) {
            amtoggle.setSelected(false);
        }
    }

    @FXML
    void resetTimeFields(MouseEvent event) {
        hourField.clear();
        minuteField.clear();
        alertMessages.successMessage("Time fields reset.");
    }

    @FXML
    void saveSelectedTime(MouseEvent event) {
        String hour = hourField.getText();
        String minute = minuteField.getText();
        String amPm = amtoggle.isSelected() ? "AM" : "PM";

        if (!hour.isEmpty() && !minute.isEmpty()) {
            alertMessages.successMessage("Saved Time: " + hour + ":" + minute + " " + amPm);
        } else {
            alertMessages.warningMessage("Hour or Minute field is empty.");
        }
    }


    private void insertAppointment(int patientID, String patientFirstName, String patientLastName, String appointmentType, String doctorFirstName, String doctorLastName, String appointmentDate, String appointmentTime) {
        String sql = "INSERT INTO appointments (PatientID, patient_first_name, patient_last_name, appointment_type, doctor_first_name, doctor_last_name, appointment_date, appointment_time) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, patientID);
            pstmt.setString(2, patientFirstName);
            pstmt.setString(3, patientLastName);
            pstmt.setString(4, appointmentType);
            pstmt.setString(5, doctorFirstName);
            pstmt.setString(6, doctorLastName);
            pstmt.setString(7, appointmentDate);
            pstmt.setString(8, appointmentTime);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                alertMessages.successMessage("Appointment scheduled successfully.");
            } else {
                alertMessages.errorMessage("Failed to schedule appointment.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void confirmAppoitnment(ActionEvent event) {
        String patientFirstName = this.patientFirstName.getText();
        String patientLastName = this.patientLastName.getText();
        String appointmentType = this.appointmentType.getText();
        String doctorFirstName = this.doctorFirstName.getText();
        String doctorLastName = this.doctorLastName.getText();

        LocalDate date = datePicker.getValue();
        LocalDate currentDate = LocalDate.now();

        if (date == null || date.isBefore(LocalDate.now())) {
            alertMessages.warningMessage("Please select a valid future date.");
            return;
        }

        String appointmentDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        String hour = hourField.getText();
        String minute = minuteField.getText();
        String amPm = amtoggle.isSelected() ? "AM" : "PM";

        if (hour.isEmpty() || minute.isEmpty()) {
            alertMessages.warningMessage("Please enter both hour and minute.");
            return;
        }

        int hourInt = Integer.parseInt(hour);
        int minuteInt = Integer.parseInt(minute);

        if (hourInt < 1 || hourInt > 12 || minuteInt < 0 || minuteInt > 59) {
            alertMessages.errorMessage("Invalid time. Please enter a valid time.");
            return;
        }

        try {
            LocalTime time = LocalTime.parse(String.format("%02d:%02d %s", hourInt, minuteInt, amPm),
                    DateTimeFormatter.ofPattern("hh:mm a"));
            String appointmentTime = time.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

            int patientID = generatePatientID();
            insertAppointment(patientID, patientFirstName, patientLastName, appointmentType, doctorFirstName, doctorLastName, appointmentDate, appointmentTime);
            alertMessages.successMessage("Appointment scheduled successfully.");
        } catch (Exception e) {
            alertMessages.errorMessage("Error scheduling appointment. Please try again.");
            e.printStackTrace();
        }
    }

    private int generatePatientID() {
        final int MAX_ATTEMPTS = 10;
        final int ID_RANGE = 1000000; // Increased the range to reduce collision probability

        Random random = new SecureRandom(); // Using SecureRandom for better randomness

        for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {
            int patientID = random.nextInt(ID_RANGE) + 1;

            if (!patientIDExists(patientID)) {
                return patientID;
            }
        }

        throw new RuntimeException("Failed to generate a unique PatientID after " + MAX_ATTEMPTS + " attempts");
    }

    private boolean patientIDExists(int patientID) {
        String sql = "SELECT COUNT(*) FROM patients WHERE PatientID = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, patientID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @FXML
    void goToMain(ActionEvent event) {
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
    void goToMainFromImage(MouseEvent event) {
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
