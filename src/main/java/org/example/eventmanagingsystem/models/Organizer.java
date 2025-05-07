package org.example.eventmanagingsystem.models;

import org.example.eventmanagingsystem.managers.EventManager;
import org.example.eventmanagingsystem.managers.RoomManager;
import org.example.eventmanagingsystem.services.Database;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Organizer extends User {
    private static int organizerCount = 0;
    private Wallet wallet;

    public static void main(String[] args)
    {
        Organizer org = new Organizer("Nour", "mysteryBooks", LocalDate.of(2001, 11, 3));
        org.showDashboard();
    }

    public Organizer() {
        this.ID = 20000 + organizerCount++ ;
        this.wallet = new Wallet();
    }    // No-arg constructor

    // Constructor with parameters
    public Organizer(String userName, String password, LocalDate dateOfBirth) {
        super(userName, password, dateOfBirth);
        this.ID = 20000 + organizerCount++ ;
        this.wallet = new Wallet();
    }

    @Override
    public void showDashboard() {
        while (true) {
            displayDashboardMenu();
            short choice = getValidMenuChoice(1, 11);

            switch (choice) {
                case 1 -> RoomManager.showAvailableRooms();

                case 2 -> showMyEvents();

                case 3 -> showEventAttendeesInteractive();

                case 4 -> removeEventAttendee();

                case 5 -> createEvent();


                case 6 ->  updateEventInteractive();

                case 7 -> {
                    while (true) {
                        System.out.println("Enter The Event ID: ");
                        try {
                            String Id = input.nextLine();
                            deleteEvent(Id);
                            break;
                        } catch (InputMismatchException e) {
                            System.out.println("Event ID is a String");
                        }
                    }
                }

                case 8 -> manageWallet();

                case 9 -> showProfile();

                case 10 -> {
                    return;
                } // Exit dashboard

                default->
                    System.out.println("Invalid input! Please choose between 1 and 11:");
            }
        }
    }

    // Helper methods
    private void displayDashboardMenu() {
        System.out.println("========================================");
        System.out.println("             Dashboard");
        System.out.println("========================================");
        System.out.println("1: Show available rooms\n2: Show my events \n3: Show attendees for my event\n4: Remove attendee from my event");
        System.out.println("5: Create an event\n6: Update an event\n7: Delete an event\n8: Wallet");
        System.out.println("9: Check Profile\n10: Logout");
    }

    public void createEvent() {

        while (true) {
            System.out.println("Enter title of event");
            String eventTitle = input.nextLine();
            System.out.println("Enter description of event");
            String eventDescription = input.nextLine();
            System.out.println("Enter category name of Event");
            String eventCategoryName = input.nextLine();
            System.out.println("Enter time slot of Event");
            String eventTimeSlot = input.nextLine();
            System.out.println("Enter ticket price of the Event");
            double ticketPrice = input.nextDouble();
            try {
                Event newEvent = new Event(eventTitle, eventDescription, this, eventCategoryName, eventTimeSlot, ticketPrice);
                createEvent(newEvent);
                break;
            } catch (IllegalArgumentException ex) {
                System.out.println(ex);
            } catch (IllegalStateException ex) {
                System.out.println(ex);

            }

        }

    }

//    private Event readEvent(int eventID) {
//        input.nextLine(); // Consume newline
//         Event event = readEvent(eventID);
//         if (event != null) {
//             event.showEventDetails();
//         } else {
//             System.out.println("Event not found");
//         }
//    }

    private void manageWallet() {
        System.out.println("Current balance");
        // System.out.println(wallet.getBalance());
        System.out.println("1: Add money     2: Withdraw money     3: Exit");

        short walletChoice = getValidMenuChoice(1, 3);
        if (walletChoice == 3) return;

        System.out.println("Enter amount: ");
        int amount = input.nextInt();
        input.nextLine(); // Consume newline
        // updateWallet(amount, walletChoice);
    }

    // models.Organizer's Methods

    // EVENT CRUD
    public void createEvent (Event newEvent) {    Database.getEventList().add(newEvent);    }
                    
    public Event readEvent (String eventId)
    {
        for (Event event : Database.getEventList())
        {
        if (event.getEventID().equals(eventId))
        {
            return event;
        }
        }
        return null;
    }

    public boolean deleteEvent(String eventId) {
        // Input validation
        if (eventId.isEmpty()) {
            System.out.println("Error: Invalid event ID");
            return false;
        }

        // Find the event by ID
        for (int i = 0; i < Database.getEventList().size(); i++) {
            if (Database.getEventList().get(i).getEventID().equals(eventId)) {
                Database.getEventList().remove(i);
                System.out.println("Event with ID " + eventId + " deleted successfully");
                return true;
            }
        }

        System.out.println("Error: Event with ID " + eventId + " not found");
        return false;
    }

    public void updateEventInteractive() {
        Scanner scanner = new Scanner(System.in);

        // 1. Get event ID to update
        System.out.print("Enter Event ID to update: ");
        String eventId = scanner.nextLine();

        // 2. Find the existing event
        Event existingEvent = null;
        for (Event event : Database.getEventList()) {
            if (event.getEventID().equals(eventId)) {
                existingEvent = event;
                break;
            }
        }

        if (existingEvent == null) {
            System.out.println("Event not found!");
            return;
        }

        // 3. Prompt for updated details
        System.out.println("\nCurrent Event Details:");
        System.out.println("Title: " + existingEvent.getTitle());
        System.out.println("Description: " + existingEvent.getDescription());
        System.out.println("Ticket Price: " + existingEvent.getTicketPrice());
        System.out.println("Timeslot: " + existingEvent.getTimeslot());

        System.out.println("\nEnter new details (press Enter to keep current value):");

        // Title
        System.out.print("Title [" + existingEvent.getTitle() + "]: ");
        String title = scanner.nextLine();
        if (!title.isEmpty()) {
            existingEvent.setTitle(title);
        }

        // Description
        System.out.print("Description [" + existingEvent.getDescription() + "]: ");
        String description = scanner.nextLine();
        if (!description.isEmpty()) {
            existingEvent.setDescription(description);
        }

        // Ticket Price
        while (true) {
            System.out.print("Ticket Price [" + existingEvent.getTicketPrice() + "]: ");
            String priceInput = scanner.nextLine();
            if (priceInput.isEmpty()) break;

            try {
                double newPrice = Double.parseDouble(priceInput);
                existingEvent.setTicketPrice(newPrice);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid price! Please enter a number.");
            }
        }

        // Timeslot
        System.out.print("Timeslot [" + existingEvent.getTimeslot() + "]: ");
        String timeslot = scanner.nextLine();
        if (!timeslot.isEmpty()) {
            existingEvent.setTimeslot(timeslot);
        }

        // 4. Perform the update
        if (update(existingEvent)) {
            System.out.println("\nEvent updated successfully!");
        } else {
            System.out.println("\nFailed to update event!");
        }
    }

    public boolean update(Event updatedEvent)
    {
        ArrayList<Event> events = Database.getEventList();
        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).getEventID().equals(updatedEvent.getEventID()) ) {
                events.set(i, updatedEvent);
                return true;
            }
        }
        return false; // not found
    }

    public void showMyEvents()
    {
        ArrayList<Event> events = Database.getEventList();
        System.out.println("My Events");

        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).getOrganizer().getUserName().equalsIgnoreCase(this.userName)) {
                events.get(i).showEventDetails();
            }
        }
        if (events.isEmpty()) {
            System.out.println("You have no events");
        }
    }

//    public void showAttendeesForMyEvent(models.Event e) {
//        System.out.println("Showing attendees for event ID: ");
//
//    }

    public void removeAttendee(Attendee attendee, Event event) {
        if (event.getAttendees().contains(attendee)) {
            event.getAttendees().remove(attendee);
            System.out.println("models.Attendee removed successfully from event: " + event.getEventID());
        } else {
            System.out.println("models.Attendee not found in this event.");
        }
    }

    public Wallet getWallet(){return wallet;}

    /// TODO: Override toString()
//    @Override
//    public String toString()
//    {
//        return
//    }

    private void showProfile() {
        boolean inProfile = true;

        while (inProfile) {
            System.out.println("========================================");
            System.out.println("Profile");
            System.out.println("========================================");
            System.out.println("Username              1: Change Username\n" + getUserName());
            System.out.println("Password              2: Change Password\n" + getPassword());
            System.out.println("Date of birth         3: Change date of birth\n" + getDateOfBirth());
            System.out.println("ID                    4: Exit Profile\n" + getId());

            short profChoice = getValidMenuChoice(1, 6);

            switch (profChoice) {
                case 1:
                    changeUsername();
                    break;

                case 2:
                    changePassword();
                    break;

                case 3:
                    changeDateOfBirth();
                    break;

                case 4:
                    inProfile = false;
                    break;
            }
        }

        showDashboard();
    }

    // Helper methods for each operation
    private void changeUsername() {
        System.out.println("Enter your new username ");
        String newName = input.next();
        setUserName(newName);
    }

    private void changePassword() {
        while (true) {
            System.out.println("Enter your new password ");
            String newPass = input.next();
            try {
                setPassword(newPass);
                break;
            } catch (IllegalArgumentException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private void changeDateOfBirth() {
        int year = getValidInt("Enter year of birth (e.g., 2000): ", 1, 2024);
        int month = getValidInt("Enter month of birth (1-12): ", 1, 12);
        int day = getValidInt("Enter day of birth (1-31): ", 1, 31);
        setDateOfBirth(LocalDate.of(year, month, day));
    }

    // Utility methods
    private short getValidMenuChoice(int min, int max) {
        while (true) {
            if (input.hasNextShort()) {
                short choice = input.nextShort();
                input.nextLine();
                if (choice >= min && choice <= max) {
                    return choice;
                }
            } else {
                input.next(); // Clear invalid input
            }
            System.out.printf("Invalid input! Please choose between %d and %d:%n", min, max);
        }
    }

    private int getValidInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            if (input.hasNextInt()) {
                int value = input.nextInt();
                if (value >= min && value <= max) {
                    return value;
                }
            } else {
                input.next(); // Clear invalid input
            }
            System.out.printf("Invalid input! Please enter a value between %d and %d:%n", min, max);
        }
    }

    private void showEventAttendeesInteractive() {
        while (true) {
            ArrayList<Event> events = Database.getEventList();

            System.out.println("\n=== Select Event to View Attendees ===");
            System.out.println("0: Return to main menu");
            for (int i = 0; i < events.size(); i++) {
                System.out.println((i+1) + ": " + events.get(i).getTitle());
            }

            System.out.print("Select event (0-" + events.size() + "): ");
            try {
                int choice = input.nextInt();
                input.nextLine(); // Consume newline

                if (choice == 0) return;
                if (choice < 1 || choice > events.size()) {
                    System.out.println("Invalid selection!");
                    continue;
                }

                Event selected = events.get(choice-1);
                System.out.println("\n=== Attendees for '" + selected.getTitle() + "' ===");
                EventManager.showEventAttendees(selected);

                System.out.print("\nView another event? (yes/no): ");
                if (!input.nextLine().trim().equalsIgnoreCase("yes")) {
                    return;
                }

            } catch (InputMismatchException e) {
                System.out.println("Please enter a number!");
                input.nextLine(); // Clear invalid input
            }
        }
    }

    private void removeEventAttendee() {
        while (true) {
            System.out.println("\n=== Remove Attendee ===");

            // Get event title
            System.out.print("Enter event title (or 'back' to cancel): ");
            String title = input.nextLine().trim();

            if (title.equalsIgnoreCase("back")) {
                System.out.println("Returning to menu...");
                return;
            }

            // Find event
            Event targetEvent = null;
            ArrayList<Event> events = Database.getEventList();

            for (Event event : events) {
                if (event.getTitle().equalsIgnoreCase(title)) {
                    targetEvent = event;
                    break;
                }
            }

            if (targetEvent == null) {
                System.out.println("Event not found! Available events:");
                for (Event event : events) {
                    System.out.println("- " + event.getTitle());
                }
                continue;
            }

            // Show current attendees
            System.out.println("\nCurrent attendees for '" + targetEvent.getTitle() + "':");
            ArrayList<Attendee> attendees = targetEvent.getAttendees();
            for (Attendee attendee : attendees) {
                System.out.println("- " + attendee.userName);
            }

            // Get attendee name
            System.out.print("\nEnter attendee name to remove (or 'back' to cancel): ");
            String name = input.nextLine().trim();

            if (name.equalsIgnoreCase("back")) {
                continue;
            }

            // Find and remove attendee
            boolean removed = false;
            for (int i = 0; i < attendees.size(); i++) {
                if (attendees.get(i).userName.equalsIgnoreCase(name)) {
                    Attendee removedAttendee = attendees.remove(i);
                    System.out.println("Successfully removed " + removedAttendee.userName +
                            " from " + targetEvent.getTitle());
                    removed = true;
                    break;
                }
            }

            if (!removed) {
                System.out.println("Attendee not found in this event!");
                continue;
            }

            // Ask to continue
            System.out.print("\nRemove another attendee? (yes/no): ");
            String choice = input.nextLine().trim();
            if (!choice.equalsIgnoreCase("yes")) {
                return;
            }
        }
    }
}







