package org.example.eventmanagingsystem.managers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ChatManager
{

        @FXML
        private TextArea chatArea;

        @FXML
        private TextField inputField;

        @FXML
        private Button sendButton;

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

}
