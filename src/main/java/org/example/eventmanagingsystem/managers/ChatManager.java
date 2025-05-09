package org.example.eventmanagingsystem.managers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class ChatManager
{



    @FXML
        private TextArea chatArea;

        @FXML
        private TextField inputField;

        @FXML
        private Button sendButton;

        @FXML
        private Button ClearButton;

        @FXML
        private VBox controlmassage;

        @FXML
        private VBox chatWindow;

        @FXML
        private Button sendbutton;

        @FXML
        private Button openChatWindow;


    @FXML
    private void openChatWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/path/to/OrganizerChat.fxml")); // update with your actual path
            Parent root = loader.load();

            Stage stage = new Stage(); // A new window
            stage.setTitle("Chat with Admin");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
        public void initialize() {
            // Optional: Setup behavior
            sendButton.setOnAction(event -> {
                String message = inputField.getText();
                if (!message.isEmpty()) {
                    chatArea.appendText("Admin: " + message + "\n");
                    inputField.clear();
                }
            });
        }

    @FXML
    public void onClearClicked(ActionEvent actionEvent) {
    }
}
