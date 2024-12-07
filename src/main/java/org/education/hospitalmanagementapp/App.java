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

public class App extends Application {
//comment to see if my commits are working
    private static AuthServiceClass asc;
    private Stage primaryStage;
    private BorderPane root;
    private boolean allowFullScreenExit = false;

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

    private void showSplashScreen() throws Exception {
        FXMLLoader splashLoader = new FXMLLoader(getClass().getResource("/org.education.hospitalmanagementapp/SplashScreen.fxml"));
        Parent splashRoot = splashLoader.load();
        root.setCenter(splashRoot);

        SplashController splashController = splashLoader.getController();
        splashController.setOnSplashFinished(this::showLoginScreen);
    }

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

    public static void main(String[] args) {
        asc = new AuthServiceClass();
        asc.connectToDatabase();
        launch(args);
    }
}