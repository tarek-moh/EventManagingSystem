package org.example.eventmanagingsystem.managers;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.example.eventmanagingsystem.models.Attendee;
import org.example.eventmanagingsystem.services.Database;

import java.util.ArrayList;


public class DashboardManager {

    //******************* Event Form *******************//
    @FXML private VBox eventForm;
    @FXML private TextField eventTitleField;
    @FXML private TextArea eventDescriptionField;
    @FXML private ComboBox<String> eventCategoryField;
    @FXML private TextField eventTimeSlotField;
    @FXML private TextField eventPriceField;
    @FXML private Button eventCreateButton;
    // Controller code
    private final ParallelTransition eventZoomIn = new ParallelTransition();
    private final ParallelTransition eventZoomOut = new ParallelTransition();
    private final BooleanProperty isEventVisible = new SimpleBooleanProperty(false);

    //******************* Room Form *******************//
    @FXML private VBox roomForm;
    @FXML private TextField roomCapacityField;
    @FXML private ComboBox<String> roomStartField;
    @FXML private ComboBox<String> roomEndField;
    @FXML private Button roomCreateButton;
    private final ParallelTransition roomZoomIn = new ParallelTransition();
    private final ParallelTransition roomZoomOut = new ParallelTransition();
    private final BooleanProperty isRoomVisible = new SimpleBooleanProperty(false);

    //******************* Category Form *******************//
    @FXML private VBox categoryForm;
    @FXML private TextField categoryNameField;
    @FXML private Button categoryCreateButton;
    private final ParallelTransition categoryZoomIn = new ParallelTransition();
    private final ParallelTransition categoryZoomOut = new ParallelTransition();
    private final BooleanProperty isCategoryVisible = new SimpleBooleanProperty(false);

    //******************* Attendee Table *******************//
    @FXML private VBox allAttendeesForm;
    @FXML private TableView<Attendee> attendeeTable;
    @FXML private Task<ArrayList<Attendee>> loadAllAttendeesTask;
    private ObservableList<Attendee> attendees = FXCollections.observableArrayList();
    private final ParallelTransition allAttendeesFormZoomIn = new ParallelTransition();
    private final ParallelTransition allAttendeesFormZoomOut = new ParallelTransition();
    private final BooleanProperty isAllAttendeesFormVisible = new SimpleBooleanProperty(false);
    @FXML private ProgressIndicator allAttendeesLoadingSpinner;
    @FXML private TableColumn<Attendee, String> idColumn;
    @FXML private TableColumn<Attendee, String> usernameColumn;
    @FXML private TableColumn<Attendee, String> genderColumn;
    @FXML private TableColumn<Attendee, String> addressColumn;


    @FXML
    public void initialize() {
        // Set up transitions for both forms
        setupZoomTransition(eventForm, eventZoomIn, eventZoomOut, isEventVisible);
        setupZoomTransition(roomForm, roomZoomIn, roomZoomOut, isRoomVisible);
        setupZoomTransition(categoryForm, categoryZoomIn, categoryZoomOut, isCategoryVisible);
        setupZoomTransition(allAttendeesForm, allAttendeesFormZoomIn, allAttendeesFormZoomOut, isAllAttendeesFormVisible);

        // Additional form-specific setup
        eventCategoryField.getItems().addAll("Concert", "Conference", "Workshop", "Exhibition");

        idColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("Address"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));

        genderColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getGender().toString()));
        attendeeTable.setItems(attendees);

    }

    @FXML
    private void toggleEventForm() {
        if (isEventVisible.get()) {
            eventZoomOut.play();
        } else {
            eventForm.setVisible(true);
            eventZoomIn.play();
            // Ensure only one form is visible at a time
            if (isRoomVisible.get())
                roomZoomOut.play();
            else if(isCategoryVisible.get())
                categoryZoomOut.play();
            else if(isAllAttendeesFormVisible.get())
            {
                allAttendeesFormZoomOut.play();
                cancelAllAttendeesLoading();
            }
        }
    }
    @FXML private void handleCreateEvent()
    {}

    @FXML
    private void toggleRoomForm() {
        if (isRoomVisible.get()) {
            roomZoomOut.play();
        } else {
            roomForm.setVisible(true);
            roomZoomIn.play();
            // Ensure only one form is visible at a time
            if (isEventVisible.get())
                eventZoomOut.play();
            else if(isCategoryVisible.get())
                categoryZoomOut.play();
            else if(isAllAttendeesFormVisible.get())
            {
                allAttendeesFormZoomOut.play();
                cancelAllAttendeesLoading();
            }
        }
    }

    @FXML
    private void handleCreateRoom()
    {

    }

    @FXML
    private void toggleCategoryForm()
    {
        if(isCategoryVisible.get())
            categoryZoomOut.play();
        else
        {
            categoryForm.setVisible(true);
            categoryZoomIn.play();
            // Ensure only one form is visible at a time
            if (isEventVisible.get())
                eventZoomOut.play();
            else if(isRoomVisible.get())
                roomZoomOut.play();
            else if(isAllAttendeesFormVisible.get())
            {
                allAttendeesFormZoomOut.play();
                cancelAllAttendeesLoading();
            }
        }
    }

    @FXML
    private void handleCreateCategory()
    {}

    @FXML
    private void toggleAllAttendeesForm()
    {
        if(isAllAttendeesFormVisible.get()) {
            allAttendeesFormZoomOut.play();
            cancelAllAttendeesLoading();
        }
        else
        {
            allAttendeesForm.setVisible(true);
            allAttendeesFormZoomIn.play();
            loadAttendeeTable();
            // Ensure only one form is visible at a time
            if (isEventVisible.get())
                eventZoomOut.play();
            else if(isRoomVisible.get())
                roomZoomOut.play();
            else if(isCategoryVisible.get())
                categoryZoomOut.play();
        }
    }


    private void loadAttendeeTable()
    {
        attendees.clear();
        allAttendeesLoadingSpinner.setVisible(true);
        loadAllAttendeesTask = new Task<ArrayList<Attendee>>() {
            @Override
            protected ArrayList<Attendee> call() throws Exception {
                return Database.getAttendeeList();// Your data access
            }
        };

        loadAllAttendeesTask.setOnSucceeded(e -> {
            Platform.runLater(() -> {
                if (loadAllAttendeesTask.getValue() != null) {
                    attendees.addAll(loadAllAttendeesTask.getValue());
                }

                allAttendeesLoadingSpinner.setVisible(false);
            });
        });

        loadAllAttendeesTask.setOnCancelled(e ->
        {});

        loadAllAttendeesTask.setOnFailed(e -> {
            new Alert(Alert.AlertType.ERROR, "Failed to load attendees").show();
        });

        new Thread(loadAllAttendeesTask).start();
    }

    private void cancelAllAttendeesLoading()
    {
        if(loadAllAttendeesTask != null && loadAllAttendeesTask.isRunning())
            loadAllAttendeesTask.cancel();
    }

    private void setupZoomTransition(VBox form, ParallelTransition zoomIn, ParallelTransition zoomOut, BooleanProperty isVisible) {
        // Initial state
        form.setScaleX(0.7);
        form.setScaleY(0.7);
        form.setOpacity(0);
        form.setVisible(false);

        // Zoom-in effect
        ScaleTransition scaleIn = new ScaleTransition(Duration.millis(300), form);
        scaleIn.setFromX(0.7);
        scaleIn.setFromY(0.7);
        scaleIn.setToX(1.0);
        scaleIn.setToY(1.0);
        scaleIn.setInterpolator(Interpolator.EASE_OUT);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(250), form);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        zoomIn.getChildren().addAll(scaleIn, fadeIn);
        zoomIn.setOnFinished(e -> {
            form.setMouseTransparent(false);
            isVisible.set(true);
        });

        // Zoom-out effect
        ScaleTransition scaleOut = new ScaleTransition(Duration.millis(250), form);
        scaleOut.setFromX(1.0);
        scaleOut.setFromY(1.0);
        scaleOut.setToX(0.7);
        scaleOut.setToY(0.7);

        FadeTransition fadeOut = new FadeTransition(Duration.millis(200), form);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);

        zoomOut.getChildren().addAll(scaleOut, fadeOut);
        zoomOut.setOnFinished(e -> {
            form.setVisible(false);
            form.setMouseTransparent(true);
            isVisible.set(false);
        });
    }

}
