package org.example.eventmanagingsystem.services.chat;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import java.io.*;
import java.net.*;

public class AminChatServer
{

    public class ChatServer extends Application {

        private PrintWriter clientOut;
        private TextArea chatArea;
        private TextField inputField;

        @Override
        public void start(Stage primaryStage) throws Exception {
            // GUI Layout
            chatArea = new TextArea();
            chatArea.setEditable(false);
            inputField = new TextField();
            Button sendButton = new Button("Send");

            sendButton.setOnAction(e -> sendMessage());
            inputField.setOnAction(e -> sendMessage());

            HBox inputBox = new HBox(10, inputField, sendButton);
            VBox root = new VBox(10, chatArea, inputBox);
            root.setPadding(new Insets(10));

            Scene scene = new Scene(root, 400, 300);
            primaryStage.setTitle("Admin Chat Server");
            primaryStage.setScene(scene);
            primaryStage.show();

            // Start server in background thread
            new Thread(this::startServer).start();
        }

        private void startServer() {
            try (ServerSocket serverSocket = new ServerSocket(1234)) {
                Socket clientSocket = serverSocket.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                clientOut = new PrintWriter(clientSocket.getOutputStream(), true);

                String line;
                while ((line = in.readLine()) != null) {
                    String finalLine = line;
                    javafx.application.Platform.runLater(() -> chatArea.appendText("Organizer: " + finalLine + "\n"));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void sendMessage() {
            String msg = inputField.getText();
            if (!msg.isEmpty() && clientOut != null) {
                clientOut.println(msg);
                chatArea.appendText("You: " + msg + "\n");
                inputField.clear();
            }
        }

        public static void main(String[] args) {
            launch(args);
        }
    }

}
