package org.education.hospitalmanagementapp;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.animation.FadeTransition;
import javafx.util.Duration;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import org.education.hospitalmanagementapp.controllers.SplashController;
import org.education.hospitalmanagementapp.services.AuthServiceClass;

/**
 * Main application class for the Hospital Management System.
 * Handles initialization, showing the splash screen, managing the login screen transition,
 * and setting up the primary stage with properties like full-screen handling.
 */
public class App extends Application {

    private static AuthServiceClass asc;
    private Stage primaryStage;
    private BorderPane root;
    private boolean allowFullScreenExit = false;

    /**
     * Entry point for initializing the JavaFX application.
     * Sets up the initial stage, splash screen, and transitions to the login screen.
     * @param primaryStage the main application stage
     * @throws Exception if an error occurs during initialization
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        root = new BorderPane();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Hospital Management System");

        showSplashScreen();

        // Set the stage to maximize
        primaryStage.setMaximized(true);

        // Get the visual bounds of the primary screen
        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();

        // Set the stage size to match the screen size
        primaryStage.setX(visualBounds.getMinX());
        primaryStage.setY(visualBounds.getMinY());
        primaryStage.setWidth(visualBounds.getWidth());
        primaryStage.setHeight(visualBounds.getHeight());

        primaryStage.show();

        // Allow full screen exit after a short delay
        new Thread(() -> {
            try {
                Thread.sleep(2000); // 2 seconds delay
                allowFullScreenExit = true;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        primaryStage.fullScreenProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal && !allowFullScreenExit) {
                Platform.runLater(() -> primaryStage.setFullScreen(true));
            }
        });
    }

    /**
     * Displays the splash screen at the application startup.
     * Sets a callback to transition to the login screen after the splash screen finishes.
     * @throws Exception if there is an error loading the splash screen
     */
    private void showSplashScreen() throws Exception {
        FXMLLoader splashLoader = new FXMLLoader(getClass().getResource("/org.education.hospitalmanagementapp/SplashScreen.fxml"));
        Parent splashRoot = splashLoader.load();
        root.setCenter(splashRoot);

        SplashController splashController = splashLoader.getController();
        splashController.setOnSplashFinished(this::showLoginScreen);
    }

    /**
     * Handles the transition from the splash screen to the login screen with a fade-in/out effect.
     * This ensures a smooth visual experience for the user.
     */
    private void showLoginScreen() {
        Platform.runLater(() -> {
            try {
                FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("/org.education.hospitalmanagementapp/LoginView.fxml"));
                Parent loginRoot = loginLoader.load();

                FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), root.getCenter());
                fadeOut.setFromValue(1.0);
                fadeOut.setToValue(0.0);

                FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), loginRoot);
                fadeIn.setFromValue(0.0);
                fadeIn.setToValue(1.0);

                fadeOut.setOnFinished(event -> {
                    root.setCenter(loginRoot);
                    fadeIn.play();
                    primaryStage.setMaximized(true);
                    primaryStage.toFront();
                });

                fadeOut.play();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Main method to initialize the application and connect to the database.
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        asc = new AuthServiceClass();
        asc.connectToDatabase();
        launch(args);
    }
}