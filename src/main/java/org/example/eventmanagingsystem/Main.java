package org.example.eventmanagingsystem;

//import com.models.Admin;
//import com.models.Attendee;
//import com.models.Organizer;
//import com.services.Database;
//
//
//import java.util.Scanner;
//
//public class Main {
//    public static void main(String[] args) {
//        Scanner input = new Scanner(System.in);
//
//        System.out.println("=== com.managers.Login System ===");
//        System.out.print("Enter Username: ");
//        String username = input.nextLine();
//
//        System.out.print("Enter Password: ");
//        String password = input.nextLine();
//
//        // Check Admin login
//        for (Admin admin : Database.getAdminList()) {
//            if (admin.getUserName().equals(username) && admin.getPassword().equals(password)) {
//                System.out.println("\nLogin successful! Welcome Admin: " + admin.getUserName());
//                admin.showDashboard(); // Call admin-specific menu
//                input.close();
//                return;
//            }
//        }
//
//        // Check Organizer login
//        for (Organizer organizer : Database.getOrganizerList()) {
//            if (organizer.getUserName().equals(username) && organizer.getPassword().equals(password)) {
//                System.out.println("\nLogin successful! Welcome Organizer: " + organizer.getUserName());
//                organizer.showDashboard(); // Call admin-specific menu
//                input.close();
//                return;
//            }
//        }
//
//        // Check Attendee login
//        for (Attendee attendee : Database.getAttendeeList()) {
//            if (attendee.getUserName().equals(username) && attendee.getPassword().equals(password)) {
//                System.out.println("\nLogin successful! Welcome Attendee: " + attendee.getUserName());
//                attendee.showDashboard(); // Call admin-specific menu
//                input.close();
//                return;
//            }
//        }
//
//        // If no match found
//        System.out.println("\nLogin failed! Invalid username or password.");
//        input.close();
//    }
//}

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.eventmanagingsystem.managers.LoginManager;


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