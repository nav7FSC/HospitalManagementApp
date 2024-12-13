package org.education.hospitalmanagementapp.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import org.education.hospitalmanagementapp.AlertMessages;
import org.education.hospitalmanagementapp.services.AuthServiceClass;
import org.education.hospitalmanagementapp.services.Appointment;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class DashBoardController implements Initializable {
    private final AlertMessages alertMessages = new AlertMessages();
    @FXML private VBox calendarContainer;
    @FXML private ImageView userProfileImage;
    @FXML private Label userNameLabel;
    private LocalDate currentDate = LocalDate.now();
    AuthServiceClass asc = new AuthServiceClass();
    private String currentUsername;
    private Map<LocalDate, List<Appointment>> appointmentMap = new HashMap<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadAppointments();
        createCalendarInContainer();
    }

    private void loadAppointments() {
        List<Appointment> appointments = asc.getAppointments();
        for (Appointment appointment : appointments) {
            appointmentMap.computeIfAbsent(appointment.getAppointmentDate(), k -> new ArrayList<>()).add(appointment);
        }
    }

    public void setCurrentUsername(String username) {
        this.currentUsername = username;
        loadProfilePicture();
        updateUserNameLabel();
    }

    private void loadProfilePicture() {
        try {
            byte[] imageData = asc.getProfilePicture(currentUsername);
            if (imageData != null && imageData.length > 0) {
                ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
                Image image = new Image(bis);
                userProfileImage.setImage(image);
                bis.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateUserNameLabel() {
        if (currentUsername != null && !currentUsername.isEmpty()) {
            userNameLabel.setText(currentUsername);
        } else {
            userNameLabel.setText("User not found");
        }
    }

    private void createCalendarInContainer() {
        calendarContainer.getChildren().clear();
        calendarContainer.setSpacing(20);
        calendarContainer.setPadding(new Insets(20));

        HBox header = new HBox(40);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(0, 0, 20, 0));
        Button prevMonth = new Button("<");
        Button nextMonth = new Button(">");
        String monthYearText = currentDate.getMonth().toString() + " " + currentDate.getYear();
        Label monthLabel = new Label(monthYearText);
        prevMonth.getStyleClass().add("calendar-nav-button");
        nextMonth.getStyleClass().add("calendar-nav-button");
        monthLabel.getStyleClass().add("calendar-header");
        header.getChildren().addAll(prevMonth, monthLabel, nextMonth);

        GridPane calendarGrid = new GridPane();
        calendarGrid.getStyleClass().add("calendar-grid");
        calendarGrid.setHgap(30);
        calendarGrid.setVgap(30);
        calendarGrid.setAlignment(Pos.CENTER);
        calendarGrid.setPadding(new Insets(10));

        String[] days = {"SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"};
        for (int i = 0; i < 7; i++) {
            Label dayLabel = new Label(days[i]);
            dayLabel.getStyleClass().add("weekday-label");
            dayLabel.setMaxWidth(Double.MAX_VALUE);
            dayLabel.setAlignment(Pos.CENTER);
            calendarGrid.add(dayLabel, i, 0);
        }

        LocalDate firstOfMonth = currentDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue() % 7;
        int daysInMonth = currentDate.lengthOfMonth();
        int row = 1;
        int col = dayOfWeek;

        for (int day = 1; day <= daysInMonth; day++) {
            LocalDate currentDateInLoop = LocalDate.of(currentDate.getYear(), currentDate.getMonth(), day);
            StackPane dateContainer = new StackPane();
            Label dateLabel = new Label(String.valueOf(day));
            dateLabel.getStyleClass().add("date-label");
            dateLabel.setMaxWidth(Double.MAX_VALUE);
            dateLabel.setAlignment(Pos.CENTER);

            if (appointmentMap.containsKey(currentDateInLoop)) {
                Circle indicator = new Circle(3);
                indicator.getStyleClass().add("appointment-indicator");

                List<Appointment> dayAppointments = appointmentMap.get(currentDateInLoop);
                String tooltipText = dayAppointments.stream()
                        .map(a -> a.getPatientFirstName() + " " + a.getPatientLastName() + ": " + a.getAppointmentType())
                        .collect(Collectors.joining("\n"));


                Tooltip tooltip = new Tooltip(tooltipText);
                tooltip.getStyleClass().add("tooltip");
                Tooltip.install(dateContainer, tooltip);

                dateContainer.getChildren().addAll(dateLabel, indicator);
                StackPane.setAlignment(indicator, Pos.BOTTOM_CENTER);
                StackPane.setMargin(indicator, new Insets(0, 0, 2, 0));
            } else {
                dateContainer.getChildren().add(dateLabel);
            }

            if (day == currentDate.getDayOfMonth()) {
                dateLabel.getStyleClass().add("selected");
            }

            dateContainer.setOnMouseEntered(e -> dateLabel.getStyleClass().add("date-hover"));
            dateContainer.setOnMouseExited(e -> dateLabel.getStyleClass().remove("date-hover"));

            calendarGrid.add(dateContainer, col, row);
            col++;
            if (col > 6) {
                col = 0;
                row++;
            }
        }

        calendarContainer.getChildren().addAll(header, calendarGrid);
        prevMonth.setOnAction(e -> navigateMonth(-1));
        nextMonth.setOnAction(e -> navigateMonth(1));
    }

    private void navigateMonth(int months) {
        currentDate = currentDate.plusMonths(months);
        createCalendarInContainer();
    }

    @FXML
    void goToTheMain(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org.education.hospitalmanagementapp/MainMenu.fxml"));
            Parent root = loader.load();
            MainMenuController mainMenuController = loader.getController();
            mainMenuController.setCurrentUsername(currentUsername);
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
        }
    }

    @FXML
    private void goToMain(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org.education.hospitalmanagementapp/MainMenu.fxml"));
            Parent root = loader.load();
            MainMenuController mainMenuController = loader.getController();
            mainMenuController.setCurrentUsername(currentUsername);
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
