module org.example.eventmanagingsystem {
	requires javafx.controls;
	requires javafx.fxml;

	opens org.example.eventmanagingsystem to javafx.fxml;
	exports org.example.eventmanagingsystem;
	exports org.example.eventmanagingsystem.managers;
	opens org.example.eventmanagingsystem.managers to javafx.fxml;
}