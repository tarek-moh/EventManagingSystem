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

    public Organizer() {    super();    }    // No-arg constructor

    // Constructor with parameters
    public Organizer(String userName, String password, LocalDate dateOfBirth) {
        super(userName, password, dateOfBirth);
        this.ID = 20000 + organizerCount ;
        this.wallet = new Wallet();
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
    public void createEvent (Event newEvent) {    Database.getEventTree().insert(newEvent);    }
                    
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
                Database.getEventTree().erase(Database.getEventList().get(i));
                System.out.println("Event with ID " + eventId + " deleted successfully");
                return true;
            }
        }

        System.out.println("Error: Event with ID " + eventId + " not found");
        return false;
    }

    public boolean update(Event updatedEvent)
    {
        ArrayList<Event> events = Database.getEventList();
        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).getEventID().equals(updatedEvent.getEventID()) ) {
                Database.getEventTree().erase(events.get(i));
                Database.getEventTree().insert(updatedEvent);
                return true;
            }
        }
        return false; // not found
    }

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
        setDateOfBirth(year, month, day);
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

}







