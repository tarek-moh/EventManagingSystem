package org.example.eventmanagingsystem;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.eventmanagingsystem.managers.LoginManager;
import org.example.eventmanagingsystem.managers.ProfileManager;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            // 1. Create the loader instance (don't call load() yet)
                FXMLLoader loader = new FXMLLoader(getClass().getResource
                        ("/org/example/eventmanagingsystem/views/loginView.fxml"));

            // 2. First load() call - this actually loads the FXML
            Parent root = null;

            root = loader.load();

            // 3. Now get the controller and set the stage
            LoginManager loginManager = loader.getController();
            loginManager.setPrimaryStage(primaryStage);

            // 4. Set up the scene
            primaryStage.setTitle("Event Management System");
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setFullScreen(true);
            primaryStage.show();

        } catch (IOException e) {
            System.err.println("Failed to load login view:");
            e.printStackTrace();
            Platform.exit();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}