
package org.education.hospitalmanagementapp.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Controller class for the Dashboard view.
 * Manages the calendar view, charts, and user interactions on the dashboard.
 */
public class DashBoardController implements Initializable {

    private final AlertMessages alertMessages = new AlertMessages();

    String username = LoginViewController.username;
    // LoginViewController user;

/*    public DashBoardController(LoginViewController user)
    {
        this.user = user;
    }*/

    @FXML
    private VBox calendarContainer;
    @FXML
    private ImageView userProfileImage;
    @FXML
    private Label userNameLabel;

    // Chart containers
    @FXML
    private BarChart<String, Number> salesChart;
    @FXML
    private BarChart<String, Number> patientChart;
    @FXML
    private BarChart<String, Number> staffChart;

    private final Map<LocalDate, String> sampleAppointments = new HashMap<>();
    private LocalDate currentDate = LocalDate.now();
    AuthServiceClass asc = new AuthServiceClass();

    private String currentUsername;

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
                userProfileImage.setImage(image);
                bis.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Initializes the dashboard by setting up charts, calendar, and user label.
     * @param location URL location for controller
     * @param resources ResourceBundle
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeSampleAppointments();
        createCalendarInContainer();
        initializeCharts();

        // Example data for patientChart
        Map<String, Number> patientData = Map.of(
                "General", 50,
                "Surgery", 30,
                "Pediatrics", 40
        );
        updatePatientChart(patientData);

        // Example data for staffChart
        Map<String, Number> staffData = Map.of(
                "Doctors", 15,
                "Nurses", 25,
                "Technicians", 10
        );
        updateStaffChart(staffData);
        updateSalesChart(); // Populate salesChart with specific data

        updateUserNameLabel();



    }


    /**
     * Updates the user name label if the username exists in the authentication service.
     */
    private void updateUserNameLabel() {

        if (asc.usernameExists(username)) {
            // Set the username label to the username
            //userNameLabel.setText(user.getUsername());
            userNameLabel.setText(username);
        } else {
            // Optionally handle cases where the username doesn't exist
            userNameLabel.setText("User not found");
        }
    }

    /**
     * Initializes a sample list of appointments for calendar demonstration.
     */
    private void initializeSampleAppointments() {
        LocalDate today = LocalDate.now();
        sampleAppointments.put(today.plusDays(2), "Doctor's Appointment");
        sampleAppointments.put(today.plusDays(5), "Follow-up Checkup");
        sampleAppointments.put(today.plusDays(10), "Specialist Consultation");
    }

    /**
     * Creates the calendar interface in the calendar container view.
     */
    private void createCalendarInContainer() {
        calendarContainer.getChildren().clear();
        calendarContainer.setSpacing(20);
        calendarContainer.setPadding(new Insets(20));

        // Create header with navigation
        HBox header = new HBox(40);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(0, 0, 20, 0));

        Button prevMonth = new Button("<");
        Button nextMonth = new Button(">");

        // Updated the label to show current month and year
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

        // Calculating first day of month offset
        LocalDate firstOfMonth = currentDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue() % 7;
        int daysInMonth = currentDate.lengthOfMonth();

        int row = 1;
        int col = dayOfWeek;

        // Adding the calendar days
        for (int day = 1; day <= daysInMonth; day++) {
            StackPane dateContainer = new StackPane();
            Label dateLabel = new Label(String.valueOf(day));
            dateLabel.getStyleClass().add("date-label");
            dateLabel.setMaxWidth(Double.MAX_VALUE);
            dateLabel.setAlignment(Pos.CENTER);

            LocalDate currentDateInLoop = LocalDate.of(currentDate.getYear(),
                    currentDate.getMonth(), day);

            if (hasAppointment(currentDateInLoop)) {
                Circle indicator = new Circle(3);
                indicator.getStyleClass().add("appointment-indicator");

                Tooltip tooltip = new Tooltip(sampleAppointments.get(currentDateInLoop));
                tooltip.getStyleClass().add("appointment-tooltip");
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

            dateContainer.setOnMouseEntered(e ->
                    dateLabel.getStyleClass().add("date-hover"));
            dateContainer.setOnMouseExited(e ->
                    dateLabel.getStyleClass().remove("date-hover"));

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

    private void createDateLabel(int day, StackPane dateContainer) {
        Label dateLabel = new Label(String.valueOf(day));
        dateLabel.getStyleClass().add("date-label");
        dateLabel.setMaxWidth(Double.MAX_VALUE);
        dateLabel.setAlignment(Pos.CENTER);

        LocalDate currentDateInLoop = LocalDate.of(currentDate.getYear(),
                currentDate.getMonth(), day);

        if (hasAppointment(currentDateInLoop)) {
            Circle indicator = new Circle(3);
            indicator.getStyleClass().add("appointment-indicator");

            Tooltip tooltip = new Tooltip(sampleAppointments.get(currentDateInLoop));
            tooltip.getStyleClass().add("appointment-tooltip");
            Tooltip.install(dateContainer, tooltip);

            dateContainer.getChildren().addAll(dateLabel, indicator);
            StackPane.setAlignment(indicator, Pos.BOTTOM_CENTER);
            StackPane.setMargin(indicator, new Insets(0, 0, 2, 0));
        } else {
            dateContainer.getChildren().add(dateLabel);
        }
    }

    private boolean hasAppointment(LocalDate date) {
        return sampleAppointments.containsKey(date);
    }

    /**
     * Navigates the calendar by changing the displayed month.
     * @param months Number of months to navigate
     */
    private void navigateMonth(int months) {
        currentDate = currentDate.plusMonths(months);
        createCalendarInContainer();
    }

    /**
     * Initializes the four charts on the dashboard.
     */
    private void initializeCharts() {
        setupChart(salesChart, "#6750A4");
        setupChart(patientChart, "#4B9B9B");
        setupChart(staffChart, "#FF8C42");
    }

    /**
     * Sets up a bar chart with the given color and data.
     * @param chart BarChart to configure
     * @param color Color string for the bars
     */
    private void setupChart(BarChart<String, Number> chart, String color) {
        chart.setAnimated(false);
        chart.setLegendVisible(false);
        chart.setVerticalGridLinesVisible(false);
        chart.setHorizontalGridLinesVisible(true);

        CategoryAxis xAxis = (CategoryAxis) chart.getXAxis();
        NumberAxis yAxis = (NumberAxis) chart.getYAxis();

        xAxis.setTickLabelGap(10);
        xAxis.setTickMarkVisible(false);
        xAxis.setTickLabelsVisible(false);

        yAxis.setTickLabelFill(javafx.scene.paint.Color.valueOf("#666666"));
        yAxis.setTickMarkVisible(false);
        yAxis.setMinorTickVisible(false);

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.getData().addAll(
                new XYChart.Data<>("1", 100),
                new XYChart.Data<>("2", 150),
                new XYChart.Data<>("3", 80),
                new XYChart.Data<>("4", 200),
                new XYChart.Data<>("5", 120),
                new XYChart.Data<>("6", 170)
        );

        chart.getData().add(series);

        series.getData().forEach(data -> {
            data.getNode().setStyle("-fx-bar-fill: " + color + ";");
        });
    }
    private ObservableList<XYChart.Data<String, Number>> getSalesData() {
        // Example: Dynamic data
        return FXCollections.observableArrayList(
                new XYChart.Data<>("January", 200),
                new XYChart.Data<>("February", 300),
                new XYChart.Data<>("March", 150),
                new XYChart.Data<>("April", 400)
        );
    }

    private void updateSalesChart() {
        salesChart.getData().clear(); // Clear existing data

        XYChart.Series<String, Number> salesSeries = new XYChart.Series<>();
        salesSeries.setName("Monthly Sales");
        salesSeries.getData().addAll(getSalesData());

        salesChart.getData().add(salesSeries);

        // Optional: Style the bars
        salesSeries.getData().forEach(data -> {
            data.getNode().setStyle("-fx-bar-fill: #4CAF50;"); // Green bars
        });
    }



    private void updatePatientChart(Map<String, Number> patientData) {
        patientChart.getData().clear(); // Clear existing data
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        patientData.forEach((category, value) -> {
            XYChart.Data<String, Number> dataPoint = new XYChart.Data<>(category, value);
            series.getData().add(dataPoint);
        });

        patientChart.getData().add(series);

        // Optional: Style the bars
        series.getData().forEach(data -> {
            data.getNode().setStyle("-fx-bar-fill: #4B9B9B;"); // Match the patientChart color
        });
    }


    private void updateStaffChart(Map<String, Number> staffData) {
        staffChart.getData().clear(); // Clear existing data
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        staffData.forEach((category, value) -> {
            XYChart.Data<String, Number> dataPoint = new XYChart.Data<>(category, value);
            series.getData().add(dataPoint);
        });

        staffChart.getData().add(series);

        // Optional: Style the bars
        series.getData().forEach(data -> {
            data.getNode().setStyle("-fx-bar-fill: #FF8C42;"); // Match the staffChart color
        });
    }




    /**
     * Navigates the user back to the main menu.
     * @param event the mouse click event triggered by clicking the navigation button
     */
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
    /**
     * Handles sign-out action and redirects to the login screen.
     * @param event ActionEvent triggered by button press
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
     * Navigates to the main menu.
     * @param event ActionEvent triggered by button press
     */
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
