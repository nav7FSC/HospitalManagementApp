package org.education.hospitalmanagementapp;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * SceneManager manages the loading of different scenes in the application.
 */
public class SceneManager {

    private static Stage stage;


    /**
     * Sets the primary stage for the application.
     *
     * @param primaryStage the primary stage to set
     */
    public static void setStage(Stage primaryStage) {
        stage = primaryStage;
    }

    /**
     * Loads a scene based on the given FXML file name.
     *
     * @param fxmlFile the name of the FXML file to load
     */
    public static void loadScene(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource("/org/education/hospitalmanagementapp/" + fxmlFile));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}