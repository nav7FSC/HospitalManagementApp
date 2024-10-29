package org.education.hospitalmanagementapp;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.education.hospitalmanagementapp.controllers.SplashController;

/**
 * Main application class for the Hospital Management System.
 * Starts with a splash screen and then transitions to the login screen.
 */
public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the splash screen
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/education/hospitalmanagementapp/SplashScreen.fxml"));
        Parent splashRoot = loader.load();

        // Set up the splash screen scene
        Scene splashScene = new Scene(splashRoot);
        primaryStage.setScene(splashScene);
        primaryStage.setTitle("Hospital Management System");
        primaryStage.show();

        // Automatically load the login scene after the splash screen duration
        SplashController splashController = loader.getController();
        splashController.setOnSplashFinished(() -> loadLoginScene(primaryStage));
    }

    /**
     * Loads the login screen after the splash screen is finished.
     *
     * @param primaryStage the primary stage to display the login screen
     */
    private void loadLoginScene(Stage primaryStage) {
        try {
            // Load the login view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/education/hospitalmanagementapp/LoginView.fxml"));
            Parent loginRoot = loader.load();

            // Set the login scene
            Scene loginScene = new Scene(loginRoot);
            primaryStage.setScene(loginScene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}