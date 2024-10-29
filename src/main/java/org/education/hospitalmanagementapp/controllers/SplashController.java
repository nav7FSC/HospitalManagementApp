package org.education.hospitalmanagementapp.controllers;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * SplashController manages the splash screen of the Hospital Management App.
 * It handles the initialization of the splash screen and transitions to the login screen
 * after a specified duration.
 */
public class SplashController {

    @FXML
    private ImageView logoImage; // The ImageView for displaying the logo on the splash screen

    private Runnable onSplashFinished; // A callback to be executed after the splash screen finishes

    /**
     * Initializes the splash screen by loading the logo image and setting up
     * a pause before transitioning to the login scene.
     */
    @FXML
    public void initialize() {
        // Load logo image from the resources/images directory
        logoImage.setImage(new Image(getClass().getResourceAsStream("/org/education/hospitalmanagementapp/images/logo.png"))); // Adjust the path as necessary

        // Set a pause for the splash screen
        PauseTransition pause = new PauseTransition(Duration.seconds(3)); // Display for 3 seconds
        pause.setOnFinished(event -> {
            // Execute the callback if it is set
            if (onSplashFinished != null) {
                onSplashFinished.run();
            }
        });
        pause.play();
    }

    /**
     * Sets a callback to be executed when the splash screen finishes.
     *
     * @param onSplashFinished the Runnable callback to execute
     */
    public void setOnSplashFinished(Runnable onSplashFinished) {
        this.onSplashFinished = onSplashFinished;
    }
}