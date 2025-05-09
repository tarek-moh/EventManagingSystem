package org.example.eventmanagingsystem.managers;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;
import org.example.eventmanagingsystem.models.*;
import org.example.eventmanagingsystem.models.Attendee;
import org.example.eventmanagingsystem.models.User;
import org.example.eventmanagingsystem.services.Database;


import java.io.IOException;
import java.util.ArrayList;


public class DashboardManager {

    /// ***********User logged in reference*********///
    private User user ;
    @FXML private Label usernameLabel;

    private static final ObservableList<String> HOURS =
            FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12");

    //******************* Event Form *******************//
    @FXML private VBox eventForm;
    @FXML private TextField eventTitleField;
    @FXML private TextArea eventDescriptionField;
    @FXML private ComboBox<String> eventCategoryField;
    @FXML private Button eventCreateButton;
    @FXML private TextField ticketPriceField;
    @FXML private HBox eventFormButton;
    // Controller code
    private final ParallelTransition eventZoomIn = new ParallelTransition();
    private final ParallelTransition eventZoomOut = new ParallelTransition();
    private final BooleanProperty isEventVisible = new SimpleBooleanProperty(false);

    //******************* Room Form *******************//
    @FXML private VBox roomForm;
    @FXML private TextField roomCapacityField;
    @FXML private ComboBox<String> roomStartField;
    @FXML private ComboBox<String> roomEndField;
    private final ParallelTransition roomZoomIn = new ParallelTransition();
    private final ParallelTransition roomZoomOut = new ParallelTransition();
    private final BooleanProperty isRoomVisible = new SimpleBooleanProperty(false);
    @FXML private HBox roomFormButton;

    //******************* Category Form *******************//
    @FXML private VBox categoryForm;
    @FXML private TextField categoryNameField;
    private final ParallelTransition categoryZoomIn = new ParallelTransition();
    private final ParallelTransition categoryZoomOut = new ParallelTransition();
    private final BooleanProperty isCategoryVisible = new SimpleBooleanProperty(false);
    @FXML private HBox categoryFormButton;

    //******************* Attendee Table *******************//
    @FXML private VBox allAttendeesForm;
    @FXML private TableView<Attendee> attendeeTable;
    @FXML private Task<ArrayList<Attendee>> loadAllAttendeesTask;
    private ObservableList<Attendee> attendees = FXCollections.observableArrayList();
    private final ParallelTransition allAttendeesFormZoomIn = new ParallelTransition();
    private final ParallelTransition allAttendeesFormZoomOut = new ParallelTransition();
    private final BooleanProperty isAllAttendeesFormVisible = new SimpleBooleanProperty(false);
    @FXML private TableColumn<Attendee, String> idColumn;
    @FXML private TableColumn<Attendee, String> usernameColumn;
    @FXML private TableColumn<Attendee, String> genderColumn;
    @FXML private TableColumn<Attendee, String> addressColumn;
    @FXML private HBox viewAttendeesButton;

    //*******************Profile View***************************//
    @FXML private ImageView viewProfileButton;
    @FXML private VBox profileForm;
    @FXML private Text usernameField;
    @FXML private Text IdField;
    @FXML private Text genderField;
    @FXML private Text dofbField;
    @FXML private Text addressField;
    private final BooleanProperty isProfileVisible = new SimpleBooleanProperty(false);
    private final ParallelTransition myProfileFormZoomIn = new ParallelTransition();
    private final ParallelTransition myProfileFormZoomOut = new ParallelTransition();


    //******************* Events Table *******************//
    @FXML private VBox allEventsTable;
    @FXML private TableView<Event> eventsTable;
    @FXML private Task<ArrayList<Event>> loadAllEventsTask;
    private ObservableList<Event> events = FXCollections.observableArrayList();
    private final ParallelTransition allEventsZoomIn = new ParallelTransition();
    private final ParallelTransition allEventsZoomOut = new ParallelTransition();
    private final BooleanProperty isAllEventsVisible = new SimpleBooleanProperty(false);
    @FXML private TableColumn<Event, String> eventTitleColumn;
    @FXML private TableColumn<Event, String> ticketPriceColumn;
    @FXML private TableColumn<Event, String> eventTimeColumn;
    @FXML private TableColumn<Event, String> eventCategoryColumn;
    @FXML private TableColumn<Event, Void> buyTicketColumn;
    @FXML private HBox viewEventsButton;

    //******************* Attendee Table *******************//
    @FXML private VBox allRoomsTable;
    @FXML private TableView<Room> roomsTable;
    @FXML private Task<ArrayList<Room>> loadAllRoomsTask;
    private ObservableList<Room> rooms = FXCollections.observableArrayList();
    private final ParallelTransition allRoomsZoomIn = new ParallelTransition();
    private final ParallelTransition allRoomsZoomOut = new ParallelTransition();
    private final BooleanProperty isAllRoomsVisible = new SimpleBooleanProperty(false);
    @FXML private TableColumn<Room, String> roomIdColumn;
    @FXML private TableColumn<Room, String> roomCapacityColumn;
    @FXML private ComboBox<String> eventStartField;
    @FXML private ComboBox<String> eventEndField;
    @FXML private HBox viewRoomsButton;

    //******************* Balance  *******************//
    @FXML private Label balanceLabel;
    private Stage primaryStage;

    //*****************logout button******************//
    @FXML private Button logoutButton;

    @FXML
    public void initialize() {

        // Set up transitions for all forms
        setupZoomTransition(eventForm, eventZoomIn, eventZoomOut, isEventVisible);
        setupZoomTransition(roomForm, roomZoomIn, roomZoomOut, isRoomVisible);
        setupZoomTransition(categoryForm, categoryZoomIn, categoryZoomOut, isCategoryVisible);
        setupZoomTransition(allAttendeesForm, allAttendeesFormZoomIn, allAttendeesFormZoomOut, isAllAttendeesFormVisible);
        setupZoomTransition(profileForm, myProfileFormZoomIn, myProfileFormZoomOut, isProfileVisible);

        setupZoomTransition(allEventsTable, allEventsZoomIn, allEventsZoomOut, isAllEventsVisible);
        setupZoomTransition(allRoomsTable, allRoomsZoomIn, allRoomsZoomOut, isAllRoomsVisible);

        // populate combo boxes
        eventStartField.setItems(HOURS);
        eventEndField.setItems(HOURS);
        roomStartField.setItems(HOURS);
        roomEndField.setItems(HOURS);


        // Additional form-specific setup
        ArrayList<myCategory> categories = Database.getCategoryList();
        ArrayList<String> catNames = new ArrayList<>();
        for(myCategory cat : categories)
            catNames.add(cat.getName());
        eventCategoryField.getItems().addAll(catNames);

        populateCategories(eventCategoryField);
        populateRooms(roomsTable);

        attendees.addAll(Database.getAttendeeList());
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        genderColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getGender().toString()));
        attendeeTable.setItems(attendees);


        events.addAll(Database.getEventList());
        eventTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        ticketPriceColumn.setCellValueFactory(new PropertyValueFactory<>("ticketPrice"));
        eventTimeColumn.setCellValueFactory(new PropertyValueFactory<>("timeslot"));
        eventCategoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        eventsTable.setItems(events);

        rooms.addAll(Database.getRoomList());
        roomIdColumn.setCellValueFactory(new PropertyValueFactory<>("roomID"));
        roomCapacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        roomsTable.setItems(rooms);

        //registerButton.setOnAction(event->handleRegister());
        eventCreateButton.setOnAction(event->handleCreateEvent());

        buyTicketColumn.setCellFactory(param -> new TableCell<>(){
            private final Button buyButton = new Button("Buy Ticket");

            {
                buyButton.setOnAction(event -> {
                    Event eventObj = getTableView().getItems().get(getIndex());
                    System.out.println(eventObj);
                    if(((Attendee)user).buyTicket(eventObj))
                    {
                        ((Attendee)user).getWallet().deductFunds(eventObj.getTicketPrice());
                        showAlert(Alert.AlertType.INFORMATION, "Ticket purchased", "Ticket Purchased successfully");
                    }
                    else
                    {
                        showAlert(Alert.AlertType.ERROR, "purchase failed", "insufficient balance or tickets sold out");
                    }
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(buyButton);
                }
            }
        });
    }

        // TODO: needs current user from session inorder to call their respective getBalance
//        balanceLabel.textProperty().bind(
//                Bindings.createStringBinding(() -> {
//                            double bal = currentUser.getWallet().getBalance();
//                            String color = bal < 0 ? "red" : bal < 50 ? "orange" : "green";
//                            return String.format("-fx-text-fill: %s; Balance: $%.2f", color, bal);
//                        },
//                        currentUser.getWallet().balanceProperty()
//                );
   // }
    @FXML
    private void toggleEventForm() {
        if (isEventVisible.get()) {
            eventZoomOut.play();
        } else {
            populateCategories(eventCategoryField);
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
            else if(isAllEventsVisible.get())
            {
                cancelAllEventsLoading();
                allEventsZoomOut.play();
            }else if(isAllRoomsVisible.get())
            {
                allRoomsZoomOut.play();
              //  cancelAllRoomsLoading();
            }
            else if (isProfileVisible.get()) {
            myProfileFormZoomOut.play();
            }
        }
    }

    private void populateCategories(ComboBox<String> cats)
    {
        cats.getItems().clear();
        ArrayList<String> names = new ArrayList<String>();
        for(myCategory cat : Database.getCategoryList())
            names.add(cat.getName());
        ObservableList<String> li = FXCollections.observableArrayList(names);
        cats.setItems(li);
    }

    private void populateRooms(TableView<Room>roomsTable){
        roomsTable.getItems().clear();
        ArrayList<Room> rooms = new ArrayList<Room>();
        for(Room room : Database.getRoomList()){
            rooms.add(room);
        }
        ObservableList<Room> roomData = FXCollections.observableArrayList(rooms);
        roomsTable.setItems(roomData);
    }

    @FXML private void handleCreateEvent()
    {
        String title = eventTitleField.getText();
        String descrip = eventDescriptionField.getText();
        String categori = eventCategoryField.getValue();
        String tickprice = ticketPriceField.getText();
        int startHour = Integer.parseInt(eventStartField.getValue().toString());
        int endHour = Integer.parseInt(eventEndField.getValue().toString());

        String timeslot = String.format("%02d:00-%02d:00", startHour, endHour);
        // timeslot = eventTimeSlotField.getText();
        try {
            double price = Double.parseDouble(tickprice);
            EventManager.addEvent(title, descrip, categori, timeslot, price);
            showAlert(Alert.AlertType.INFORMATION, "Event created", "Event Created successfully");
        }
        catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "", "Enter a valid number for price.");
        }
        catch (IllegalArgumentException ex) {
            showAlert(Alert.AlertType.ERROR, "", ex.getMessage());
        }
    }

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
            else if(isProfileVisible.get()){
                myProfileFormZoomOut.play();
            }
            else if(isAllEventsVisible.get())
            {
                cancelAllEventsLoading();
                allEventsZoomOut.play();
            }else if(isAllRoomsVisible.get())
            {
                allRoomsZoomOut.play();
               // cancelAllRoomsLoading();
            }
        }
    }

    @FXML
    private void handleCreateRoom()
    {
        String roomCap = roomCapacityField.getText();
        // String timeslot = roomstart and end get()
        try
        {
            if (roomCap == null || roomCap.isEmpty())
                throw new IllegalArgumentException("Capacity cannot be left empty");
            int cap = Integer.parseInt(roomCap);
            RoomManager.createRoom(cap);
        }
        catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "", "Enter a valid number for capacity.");
        }
        catch(IllegalArgumentException ex)
        {
            showAlert(Alert.AlertType.ERROR, "", ex.getMessage());
        }

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
            else if(isProfileVisible.get()){
                myProfileFormZoomOut.play();
            }
            else if(isAllEventsVisible.get())
            {
                cancelAllEventsLoading();
                allEventsZoomOut.play();
            }else if(isAllRoomsVisible.get())
            {
                allRoomsZoomOut.play();
              //  cancelAllRoomsLoading();
            }
        }
    }

    @FXML
    private void handleCreateCategory()
    {
        String catgName = categoryNameField.getText();
        if(CategoryManager.isValid(catgName)) {
            showAlert(Alert.AlertType.ERROR, "Can't create category","Category already exists!");
        }

        try {
            CategoryManager.createCategory(catgName);
          } catch (IllegalArgumentException ex) {
            showAlert(Alert.AlertType.ERROR, "couldn't create category", ex.getMessage());
          }

    }

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
            loadAttendeeTable(user);
            // Ensure only one form is visible at a time
            if (isEventVisible.get())
                eventZoomOut.play();
            else if(isRoomVisible.get())
                roomZoomOut.play();
            else if(isCategoryVisible.get())
                categoryZoomOut.play();
            else if(isProfileVisible.get()){
                myProfileFormZoomOut.play();
            }
            else if(isAllEventsVisible.get())
            {
                cancelAllEventsLoading();
                allEventsZoomOut.play();
            }else if(isAllRoomsVisible.get())
            {
                allRoomsZoomOut.play();
                //cancelAllRoomsLoading();
            }
        }

    }

    // TODO: Needs testing whether a user gets added to the organizer's attendee list if they purchased a ticket for the
    // organizer's event
    private void loadAttendeeTable(User currentUser) {
        attendees.clear();

        loadAllAttendeesTask = new Task<ArrayList<Attendee>>() {
            @Override
            protected ArrayList<Attendee> call() throws Exception {
                if (currentUser instanceof Admin) {
                    return Database.getAttendeeList(); // All attendees
                } else if (currentUser instanceof Organizer) {
                    Organizer organizer = (Organizer) currentUser;
                    return Database.getAttendeesByOrganizer(organizer.getId()); // Filtered by organizer
                } else {
                    return new ArrayList<>(); // Default empty list
                }
            }
        };

        loadAllAttendeesTask.setOnSucceeded(e -> {
            Platform.runLater(() -> {
                if (loadAllAttendeesTask.getValue() != null) {
                    attendees.addAll(loadAllAttendeesTask.getValue());
                }
            });
        });

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


    private void loadAllEvents()
    {
        events.clear();
        loadAllEventsTask = new Task<ArrayList<Event>>(){
            @Override
            protected ArrayList<Event> call() throws Exception{
                return Database.getEventList();
            }
        };
        loadAllEventsTask.setOnSucceeded(e -> {
            Platform.runLater(() -> {
                if (loadAllEventsTask.getValue() != null) {
                    events.addAll(loadAllEventsTask.getValue());
                }
            });
        });

        loadAllEventsTask.setOnCancelled(e ->
        {});

        loadAllEventsTask.setOnFailed(e -> {
            new Alert(Alert.AlertType.ERROR, "Failed to load attendees").show();
        });

        new Thread(loadAllEventsTask).start();
    }

    @FXML
    private void toggleAllEvents()
    {
        if(isAllEventsVisible.get())
        {
            allEventsZoomOut.play();
            cancelAllEventsLoading();
        }
        else {
            allEventsTable.setVisible(true);
            allEventsZoomIn.play();
            loadAllEvents();
            if (isEventVisible.get())
                eventZoomOut.play();
            else if(isRoomVisible.get())
                roomZoomOut.play();
            else if(isCategoryVisible.get())
                categoryZoomOut.play();
            else if(isAllAttendeesFormVisible.get()) {
                allAttendeesFormZoomOut.play();
                cancelAllAttendeesLoading();
            }else if(isAllRoomsVisible.get())
            {
                allRoomsZoomOut.play();
                //cancelAllRoomsLoading();
            }
            else if(isAllEventsVisible.get())
            {
                cancelAllEventsLoading();
                allEventsZoomOut.play();
            }else if(isAllRoomsVisible.get())
            {
                allRoomsZoomOut.play();
                //cancelAllRoomsLoading();
            }
            else if (isProfileVisible.get()) {
                myProfileFormZoomOut.play();
            }
        }
    }

    private void cancelAllEventsLoading()
    {
        if(loadAllEventsTask != null && loadAllEventsTask.isRunning())
            loadAllEventsTask.cancel();
    }

//    @FXML
//    private void loadAllRooms()
//    {
//        rooms.clear();
//        loadAllRoomsTask = new Task<ArrayList<Room>>() {
//            @Override
//            protected ArrayList<Room> call() throws Exception {
//                return Database.getRoomList();
//            }
//        };
//        loadAllRoomsTask.setOnSucceeded(e -> {
//            Platform.runLater(() -> {
//                if (loadAllRoomsTask.getValue() != null) {
//                    rooms.addAll(loadAllRoomsTask.getValue());
//                }
//            });
//        });
//
//        loadAllRoomsTask.setOnFailed(e -> {
//            new Alert(Alert.AlertType.ERROR, "Failed to load Rooms").show();
//        });
//
//        new Thread(loadAllRoomsTask).start();
//    }

//    @FXML
//    private void cancelAllRoomsLoading()
//    {
//        if(loadAllRoomsTask!= null && loadAllRoomsTask.isRunning())
//            loadAllRoomsTask.cancel();
//    }

    @FXML
    private void toggleAllRooms()
    {
        if(isAllRoomsVisible.get()) {
            allRoomsZoomOut.play();
        }
        else{
//            populateRooms(roomsTable);
//            allRoomsTable.setVisible(true);
//            allRoomsZoomIn.play();
//            loadAllRooms();
//        }
//        else{
                if (isEventVisible.get())
                    eventZoomOut.play();
                else if (isRoomVisible.get())
                    roomZoomOut.play();
                else if (isCategoryVisible.get())
                    categoryZoomOut.play();
                else if (isAllAttendeesFormVisible.get()) {
                    allAttendeesFormZoomOut.play();
                    cancelAllAttendeesLoading();
                } else if (isAllEventsVisible.get()) {
                    allEventsZoomOut.play();
                    cancelAllEventsLoading();
                } else if (isAllRoomsVisible.get()) {
                    allRoomsZoomOut.play();
                  //  cancelAllRoomsLoading();
                } else if (isProfileVisible.get()) {
                    myProfileFormZoomOut.play();
                }
        }

    }

    @FXML
    private void toggleProfile() {
        if(isProfileVisible.get()){
            myProfileFormZoomOut.play();
            System.out.println("profile toggle workkksss!!!");
        }
        else{
            profileForm.setVisible(true);
            myProfileFormZoomIn.play();
            loadProfileInfo(user);     // <--------- load profile info

            System.out.println("profile toggle shouldnt workkk!!!");
            //hide all other forms
            if(isEventVisible.get()){
                eventZoomOut.play();
            }
            else if(isCategoryVisible.get()){
               categoryZoomOut.play();
            }
            else if(isAllAttendeesFormVisible.get()){
                allAttendeesFormZoomOut.play();
                cancelAllAttendeesLoading();
            }
            else if(isRoomVisible.get()){
                roomZoomOut.play();
            }
           else if(isAllEventsVisible.get())
            {
            allEventsZoomOut.play();
            cancelAllEventsLoading();
            }
            else
           {
             allRoomsZoomOut.play();
            // cancelAllRoomsLoading();
           }

        }
    }

    private void loadProfileInfo(User user){
        usernameField.setText(user.getUserName());
        IdField.setText(String.valueOf(user.getId()));
        genderField.setText(user.getGender().toString());
        dofbField.setText(user.getDateOfBirthAsString());
        addressField.setText(user.getAddress());
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.setResizable(true);
        alert.showAndWait();
    }

    @FXML
    private void logout() {
        Parent loginPage = null;
        try{

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/eventmanagingsystem/views/loginView.fxml"));
            loginPage = loader.load();
            LoginManager loginManager = loader.getController();
            loginManager.setloginUsernameField(this.user.getUserName());
            loginManager.setloginPasswordField(this.user.getPassword());
        }
        catch(IOException ex){
            showAlert(Alert.AlertType.ERROR, "Error loading login", ex.getMessage());
        }
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        stage.setScene(new Scene(loginPage));
        stage.setFullScreen(true);


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

    private void setupUserUI() {
        StringProperty userNameProperty = new SimpleStringProperty(user.getUserName());
        usernameLabel.textProperty().bind(userNameProperty);

        if (user instanceof Admin) {
            // Example: Admin gets full access
            eventForm.setVisible(true);
            roomForm.setVisible(true);
            categoryForm.setVisible(true);
            allAttendeesForm.setVisible(true);
            balanceLabel.setVisible(false); // Admin may not need a balance
            viewAttendeesButton.setVisible(true);
            viewRoomsButton.setVisible(true);

        }
        else if (user instanceof Organizer) {
            eventFormButton.setVisible(true);
            roomFormButton.setVisible(true);
            categoryFormButton.setVisible(false);
            viewEventsButton.setVisible(true);
            balanceLabel.setVisible(true);
            viewAttendeesButton.setVisible(true);
            viewRoomsButton.setVisible(true);
            bindBalance(((Organizer) user).getWallet().balanceProperty());
        }
        else if (user instanceof Attendee) {
            eventFormButton.setVisible(false);
            roomFormButton.setVisible(false);
            categoryFormButton.setVisible(false);
            viewEventsButton.setVisible(true);
            balanceLabel.setVisible(true);
            viewAttendeesButton.setVisible(false);
            viewRoomsButton.setVisible(false);
            bindBalance(((Attendee) user).getWallet().balanceProperty());
        }
    }

    private void bindBalance(DoubleProperty balanceProperty) {
        balanceLabel.textProperty().bind(
                Bindings.createStringBinding(() ->
                                String.format("$%.1f", balanceProperty.get()),
                        balanceProperty
                )
        );
    }

    private void bindName(StringProperty nameProperty) {
        usernameLabel.textProperty().bind(nameProperty);
    }
}
