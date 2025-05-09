package org.example.eventmanagingsystem.services.chat;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import java.io.*;
import java.net.Socket;

public class OrganizerChatClient
{
    @FXML
    public void onClearClicked(ActionEvent actionEvent) {
    }

    public class ChatClient extends Application {

        private PrintWriter out;
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
            primaryStage.setTitle("Organizer Chat Client");
            primaryStage.setScene(scene);
            primaryStage.show();

            // Socket Setup
            Socket socket = new Socket("localhost", 1234);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            // Read messages in a separate thread
            new Thread(() -> {
                try {
                    String line;
                    while ((line = in.readLine()) != null) {
                        String finalLine = line;
                        javafx.application.Platform.runLater(() -> chatArea.appendText(finalLine + "\n"));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        private void sendMessage() {
            String msg = inputField.getText();
            if (!msg.isEmpty()) {
                out.println(msg);
                chatArea.appendText("You: " + msg + "\n");
                inputField.clear();
            }
        }

        public static void main(String[] args) {
            launch(args);
        }
    }

}
