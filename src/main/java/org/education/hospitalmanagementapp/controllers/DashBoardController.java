package org.education.hospitalmanagementapp.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class DashBoardController implements Initializable {
    // Existing FXML injected fields
    @FXML private Button dashSignOut;
    @FXML private Button dashGotoMain;

    // New FXML injected containers
    @FXML private VBox calendarContainer;
    @FXML private VBox upcomingEventsContainer;
    @FXML private VBox salesChartContainer;
    @FXML private VBox patientChartContainer;
    @FXML private VBox staffChartContainer;
    @FXML private VBox inventoryChartContainer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeCalendar();
        initializeCharts();
    }
    private VBox createUpcomingEventsList() {
        VBox eventsContainer = new VBox(10); // 10 is the spacing between elements
        eventsContainer.getStyleClass().add("upcoming-events-list");

        // Add sample events or placeholder
        for (int i = 0; i < 3; i++) {
            HBox eventRow = new HBox(10);
            eventRow.getStyleClass().add("event-row");

            // Add star icon
            Label starIcon = new Label("★");
            starIcon.getStyleClass().add("star-icon");

            // Add event details
            Label eventDetails = new Label("...");
            eventDetails.getStyleClass().add("event-details");

            eventRow.getChildren().addAll(starIcon, eventDetails);
            eventsContainer.getChildren().add(eventRow);
        }

        return eventsContainer;
    }

    private void initializeCalendar() {
        GridPane calendar = createCalendar();
        calendarContainer.getChildren().add(calendar);

        VBox upcomingEvents = createUpcomingEventsList();
        upcomingEventsContainer.getChildren().add(upcomingEvents);
    }


    private void initializeCharts() {
        // Create and add charts to their containers
        salesChartContainer.getChildren().add(createBarChart("Revenue"));
        patientChartContainer.getChildren().add(createBarChart("Patients"));
        staffChartContainer.getChildren().add(createBarChart("Staff"));
        inventoryChartContainer.getChildren().add(createBarChart("Inventory"));
    }

    private GridPane createCalendar() {
        GridPane calendar = new GridPane();
        calendar.getStyleClass().add("calendar-grid");

        // Add month navigation
        HBox header = new HBox();
        Button prevMonth = new Button("<");
        Button nextMonth = new Button(">");
        Label monthLabel = new Label("CALENDAR EVENTS");
        header.getChildren().addAll(monthLabel, prevMonth, nextMonth);
        calendar.add(header, 0, 0, 7, 1);

        // Add weekday headers
        String[] days = {"SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"};
        for (int i = 0; i < 7; i++) {
            Label dayLabel = new Label(days[i]);
            dayLabel.getStyleClass().add("weekday-label");
            calendar.add(dayLabel, i, 1);
        }

        // Add calendar days
        int dayOfMonth = 1;
        for (int week = 0; week < 6; week++) {
            for (int day = 0; day < 7; day++) {
                if (dayOfMonth <= 30) {  // Adjust based on actual month length
                    Label dateLabel = new Label(String.valueOf(dayOfMonth));
                    dateLabel.getStyleClass().add("date-label");
                    calendar.add(dateLabel, day, week + 2);
                    dayOfMonth++;
                }
            }
        }

        return calendar;
    }

    private BarChart<String, Number> createBarChart(String title) {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> chart = new BarChart<>(xAxis, yAxis);

        VBox chartContainer = new VBox(5);
        Label titleLabel = new Label(title);
        Label subtitleLabel = new Label("TOTAL " + title.toUpperCase() + " FOR TODAY");

        chart.setTitle("");
        chart.setLegendVisible(false);
        chart.setAnimated(false);
        chart.getStyleClass().add("custom-chart");

        // Add more realistic data points
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.getData().addAll(
                new XYChart.Data<>("1", 80),
                new XYChart.Data<>("2", 120),
                new XYChart.Data<>("3", 90),
                new XYChart.Data<>("4", 110),
                new XYChart.Data<>("5", 140),
                new XYChart.Data<>("6", 100)
        );

        chart.getData().add(series);
        return chart;
    }

    // Your existing methods
    @FXML
    private void signOut(ActionEvent event) {
        // Existing code
    }

    @FXML
    private void goToMain(ActionEvent event) {
        // Existing code
    }
}