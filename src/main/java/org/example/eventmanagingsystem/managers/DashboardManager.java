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
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.eventmanagingsystem.models.*;
import org.example.eventmanagingsystem.models.Attendee;
import org.example.eventmanagingsystem.models.User;
import org.example.eventmanagingsystem.services.Database;
import javafx.geometry.Pos;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


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
    @FXML private HBox viewMyEventsButton;

    //******************* Balance  *******************//
    @FXML private Label balanceLabel;
    private Stage primaryStage;

    //*****************logout button******************//
    @FXML private Button logoutButton;

    //*****************Manage Interests******************//
    @FXML private HBox manageInterestsButton;
    @FXML private ComboBox<String> interestComboBox;
    @FXML private VBox interestsPane;
    @FXML private VBox selectedInterestsPane;
    private Set<String> selectedInterests = new HashSet<>();
    private final ParallelTransition interestsZoomIn = new ParallelTransition();
    private final ParallelTransition interestsZoomOut = new ParallelTransition();
    private final BooleanProperty isInterestsVisible = new SimpleBooleanProperty(false);

    //*****************My Tickets******************//
    @FXML ListView<Ticket> ticketsListView;
    private final ParallelTransition ticketsZoomIn = new ParallelTransition();
    private final ParallelTransition ticketsZoomOut = new ParallelTransition();
    private final BooleanProperty isTicketsVisible = new SimpleBooleanProperty(false);
    @FXML private VBox ticketsPane;
    private ObservableList<Ticket> myTickets = FXCollections.observableArrayList();
    @FXML HBox ticketsButton;

    //*****************My Events******************//
    @FXML ListView<Event> eventListView;
    private final ParallelTransition myEventsZoomIn = new ParallelTransition();
    private final ParallelTransition myEventsZoomOut = new ParallelTransition();
    private final BooleanProperty isMyEventsVisible = new SimpleBooleanProperty(false);
    @FXML private VBox myEventsPane;
    private ObservableList<Ticket> myEvents = FXCollections.observableArrayList();
    @FXML HBox myEventsButton;


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
        setupZoomTransition(interestsPane, interestsZoomIn, interestsZoomOut, isInterestsVisible);
        setupZoomTransition(ticketsPane, ticketsZoomIn, ticketsZoomOut, isTicketsVisible);
        setupZoomTransition(myEventsPane, myEventsZoomIn, myEventsZoomOut, isMyEventsVisible);

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
        interestComboBox.getItems().addAll(catNames);

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
                        myTickets.clear();
                        myTickets.addAll(((Attendee)user).getMyTickets());
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
            } else if (isMyEventsVisible.get())
            {
                myEventsZoomOut.play();
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
            } else if (isMyEventsVisible.get())
            {
                myEventsZoomOut.play();
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
            } else if (isMyEventsVisible.get())
            {
                myEventsZoomOut.play();
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

    // TODO: Needs testing whether it shows the organizer's event attendees.
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
            } else if (isMyEventsVisible.get())
            {
                myEventsZoomOut.play();
            }
        }

    }

    /// TODO: Needs testing whether a user gets added to the organizer's attendee list if they purchased a ticket for the
    /// organizer's event
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
            else if(isInterestsVisible.get())
            {
                interestsZoomOut.play();
            }
            else if(isTicketsVisible.get())
            {
                ticketsZoomOut.play();
            } else if (isMyEventsVisible.get())
            {
                myEventsZoomOut.play();
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
            populateRooms(roomsTable);
            allRoomsTable.setVisible(true);
            allRoomsZoomIn.play();
            //loadAllRooms();
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
                } else if (isMyEventsVisible.get())
                {
                    myEventsZoomOut.play();
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
            else if(isInterestsVisible.get())
            {
                isInterestsVisible.set(false);
            }
            else
           {
             allRoomsZoomOut.play();
            // cancelAllRoomsLoading();
           }

        }
    }

    @FXML
    private void toggleInterests() {
        if (isInterestsVisible.get()) {
            interestsZoomOut.play();
        } else {
            // Populate or load the interests UI if needed
            //populateInterests(); // optional
            interestsPane.setVisible(true);
            interestsZoomIn.play();

            // Hide other views
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
                //cancelAllRoomsLoading();
            } else if (isProfileVisible.get()) {
                myProfileFormZoomOut.play();
            }
            else if(isTicketsVisible.get())
            {
                ticketsZoomOut.play();
            }
        }
    }
    @FXML
    private void toggleTickets() {
        if (isTicketsVisible.get()) {
            ticketsZoomOut.play();
        } else {
            ticketsPane.setVisible(true);
            ticketsZoomIn.play();

            // Hide other views
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
                //cancelAllRoomsLoading();
            } else if (isProfileVisible.get()) {
                myProfileFormZoomOut.play();
            } else if (isInterestsVisible.get()) {
                interestsZoomOut.play();
            } else if (isMyEventsVisible.get())
            {
                myEventsZoomOut.play();
            }
        }
    }

    @FXML
    private void toggleMyEvents() {
        if (isMyEventsVisible.get()) {
            myEventsZoomOut.play();
        } else {
            myEventsPane.setVisible(true);
            myEventsZoomIn.play();

            // Hide other views
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
            } else if (isProfileVisible.get()) {
                myProfileFormZoomOut.play();
            } else if (isInterestsVisible.get()) {
                interestsZoomOut.play();
            } else if (isTicketsVisible.get()) {
                ticketsZoomOut.play();
            }
        }
    }


    private void loadInterests(Attendee attendee) {
        selectedInterestsPane.getChildren().clear();

        for (myCategory interest : attendee.getInterests()) {
            Label nameLabel = new Label(interest.getName());
            Button removeButton = new Button("❌");

            // Optional: Add styling
            nameLabel.setStyle("-fx-font-size: 14px;");
            removeButton.setStyle("-fx-background-color: transparent; -fx-text-fill: red;");

            // Action to remove interest (implement this logic)
            removeButton.setOnAction(e -> {
                attendee.getInterests().remove(interest);  // remove from model
                loadInterests(attendee);  // reload the list
                // Optionally call backend here to persist the removal
            });

            HBox item = new HBox(10, nameLabel, removeButton);
            item.setAlignment(Pos.CENTER_LEFT);
            item.setStyle("-fx-padding: 5px; -fx-background-color: #f0f0f0; -fx-background-radius: 5px;");

            interestsPane.getChildren().add(item);
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
    public void assignUserReference(User user){ this.user = user; setupUserUI();}
    private void setupUserUI() {
        StringProperty userNameProperty = new SimpleStringProperty(user.getUserName());
        bindName(userNameProperty);

        if (user instanceof Admin) {
            // Example: Admin gets full access
            eventForm.setVisible(true);
            roomForm.setVisible(true);
            categoryForm.setVisible(true);
            allAttendeesForm.setVisible(true);
            balanceLabel.setVisible(false); // Admin may not need a balance
            viewAttendeesButton.setVisible(true);
            viewRoomsButton.setVisible(true);
            manageInterestsButton.setVisible(false);
            ticketsButton.setVisible(false);
            viewMyEventsButton.setVisible(false);
        }
        else if (user instanceof Organizer) {
            eventFormButton.setVisible(true);
            roomFormButton.setVisible(true);
            categoryFormButton.setVisible(false);
            balanceLabel.setVisible(true);
            viewAttendeesButton.setVisible(true);
            viewRoomsButton.setVisible(true);
            bindBalance(((Organizer) user).getWallet().balanceProperty());
            manageInterestsButton.setVisible(false);
            ticketsButton.setVisible(false);
            viewEventsButton.setVisible(false);
            viewMyEventsButton.setVisible(true);

            //List<Event> myEvents = ((Organizer)user).get;
            //System.out.println("Ticket count: " + tickets.size()); // debug
            viewMyEventsButton.setVisible(false);

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
            manageInterestsButton.setVisible(true);
            loadInterests((Attendee)user);

            List<Ticket> tickets = ((Attendee)user).getMyTickets();
            System.out.println("Ticket count: " + tickets.size()); // debug
            viewMyEventsButton.setVisible(false);


            myTickets.addAll(tickets);
            ticketsListView.setItems(myTickets);
            ticketsListView.setCellFactory(param -> new ListCell<Ticket>() {
                @Override
                protected void updateItem(Ticket item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.toString());  // Customize the string format here
                    }
                }
            });


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

    @FXML
    private void onAddInterest() {
        String selected = interestComboBox.getValue();
        if (selected != null && !selectedInterests.contains(selected)) {
            selectedInterests.add(selected);
            addInterestChip(selected);
            ((Attendee)user).addInterest(selected);
        }
        for(myCategory cat : ((Attendee)user).getInterests())
        {
            System.out.println(cat);
        }
    }

    private void addInterestChip(String interest) {
        Label label = new Label(interest);
        Button removeButton = new Button("❌");

        removeButton.setOnAction(e -> {
            ((Attendee) user).removeInterest(interest);
            selectedInterests.remove(interest);
            selectedInterestsPane.getChildren().remove(removeButton.getParent());
        });

        // Spacer to push the ❌ to the right
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox chip = new HBox(10, label, spacer, removeButton);
        chip.setStyle("-fx-padding: 5; -fx-background-color: #e0e0e0; -fx-background-radius: 5;");
        chip.setAlignment(Pos.CENTER_LEFT);

        selectedInterestsPane.getChildren().add(chip);
        VBox.setMargin(chip, new Insets(0, 0, 10, 0));
    }


}
