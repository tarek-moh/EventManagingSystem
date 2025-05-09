package org.example.eventmanagingsystem.managers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.eventmanagingsystem.models.*;
import org.example.eventmanagingsystem.services.Database;

import java.io.IOException;
import java.time.LocalDate;

public class LoginManager {

    private Stage primaryStage;
    public void setPrimaryStage(Stage stage)
    {
        this.primaryStage = stage;
    }

    // user logged in ref
    private User user;

    private final ToggleGroup group1 = new ToggleGroup(); // Create group
    private final ToggleGroup group2 = new ToggleGroup();

    @FXML
    private TextField loginUsernameField;

    @FXML
    private PasswordField loginPasswordField;

    @FXML
    private Button loginButton;

    @FXML
    private Label registerLink;

    // Register form fields

    @FXML
    private TextField registerUsernameField;

    @FXML
    private PasswordField registerPasswordField1;

    @FXML
    private PasswordField registerPasswordField2;


    @FXML
    TextField addressField;

    @FXML
    DatePicker dateField;

    @FXML
    private RadioButton organizerRadio;

    @FXML
    private RadioButton attendeeRadio;

    @FXML
    private RadioButton maleRadio;

    @FXML
    private RadioButton femaleRadio;

    @FXML
    private Button registerButton;

    @FXML
    private VBox loginForm;

    @FXML
    private VBox registerForm;

    @FXML
    private void initialize() {
        loginButton.setOnAction(event -> handleLogin());
        registerLink.setOnMouseClicked(event -> handleShowRegister());
        registerButton.setOnAction(event->handleRegister());
        attendeeRadio.setToggleGroup(group1);
        organizerRadio.setToggleGroup(group1);
        maleRadio.setToggleGroup(group2);
        femaleRadio.setToggleGroup(group2);

        group1.selectToggle(attendeeRadio); // Default selection
        group2.selectToggle(maleRadio); // Default selection
    }

    @FXML
    private void handleLogin() {
        String username = loginUsernameField.getText();
        String password = loginPasswordField.getText();
        Parent dashboard = null;
        if (login(username, password)) {
            try
            {
              //  dashboard = FXMLLoader.load(getClass().getResource("/org/example/eventmanagingsystem/views/dashboardView.fxml"));
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/org/example/eventmanagingsystem/views/dashboardView.fxml"));
                dashboard = loader.load();
                DashboardManager dashboardManager = loader.getController();
                dashboardManager.assignUserReference(user);
            }catch(IOException ex)
            {
                showAlert(AlertType.ERROR, "Error loading dashboard", ex.getMessage());
            }
            if (primaryStage == null) {
                // Fallback to dynamic stage detection
                primaryStage = (Stage) loginUsernameField.getScene().getWindow();
            }
            Scene scene = new Scene(dashboard);
            primaryStage.setScene(scene);
            primaryStage.setFullScreen(true);
            primaryStage.show();

        } else {
            showAlert(AlertType.ERROR, "Login Failed", "Invalid username or Password");
        }
    }

    @FXML
    private void handleRegister()
    {
        String username = registerUsernameField.getText();
        String password = registerPasswordField1.getText();
        String confirmation = registerPasswordField2.getText();
        String address = addressField.getText();
        LocalDate dob = dateField.getValue();
        Gender gender = maleRadio.isSelected() ? Gender.MALE : Gender.FEMALE;
        if(register(username, password, confirmation))
        {
            if(attendeeRadio.isSelected())
            {
                try
                {
                    AttendeeManager.addAttendee(username, password, address, dob,gender);
                    //show dashboard
                }
                catch (IllegalArgumentException ex)
                {
                    showAlert(AlertType.ERROR, "", ex.getMessage());
                }
            }
            else if(organizerRadio.isSelected())
            {
                try
                {
                    OrganizerManager.addOrganizer(username, password, dob, address, gender);
                    // show dashboard
                    OrganizerManager.addOrganizer(username, password, dob, address, gender);
                }
                catch (IllegalArgumentException ex)
                {
                    showAlert(AlertType.ERROR, "", ex.getMessage());
                }

            }
        }
        else
        {
            showAlert(AlertType.ERROR, "Password doesn't match", "Password and confirm-password must match");
        }
    }

    @FXML
    private void handleShowRegister(){
        registerForm.setVisible(true);
        loginForm.setVisible(false);
    }

    @FXML
    private void handleBack() {
        Platform.runLater(() -> {
            // Hide register form
            registerForm.setVisible(false);
            registerForm.setManaged(false);

            // Show login form and bring it to the front
            loginForm.setVisible(true);
            loginForm.setManaged(true);
            loginForm.toFront();

            // Request layout update
            loginForm.getParent().requestLayout();
        });
    }

    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.setResizable(true);
        alert.showAndWait();
    }

    private boolean register(String username, String password, String confirmPassowrd)
    {
        return password.equals(confirmPassowrd);
    }

    private boolean login(String username, String password)
    {
        for ( Admin admin : Database.getAdminList()) {
            if (admin.getUserName().equals(username) && admin.getPassword().equals(password)) {
                user = admin;
                return true;
            }
        }

        // Check Organizer login
        for (Organizer organizer : Database.getOrganizerList()) {
            if (organizer.getUserName().equals(username) && organizer.getPassword().equals(password)) {
                user = organizer;
                return true;
            }
        }

        // Check Attendee login
        for (Attendee attendee : Database.getAttendeeList()) {
            if (attendee.getUserName().equals(username) && attendee.getPassword().equals(password)) {
                user = attendee;
                return true;
            }
        }

        return false;
    }

    public void setloginUsernameField(String username){
        loginUsernameField.setText(username);
    }
    public void setloginPasswordField(String password){
        loginPasswordField.setText(password);
    }

    //get user reference
    public  User getUserRef(){
        return user;
    }

    @FXML
    public void initializeWithCredentials(String username, String password) {
        // Make sure these fields are @FXML annotated in your controller
        loginUsernameField.setText(username);
        loginPasswordField.setText(password);
    }

}
