package org.example.eventmanagingsystem.models;/*
 *add parameters for the room constructor
 * implement showEventDetails()
 **/

import org.example.eventmanagingsystem.managers.CategoryManager;
import org.example.eventmanagingsystem.managers.RoomManager;
import org.example.eventmanagingsystem.services.Database;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
/**
 * The {@code models.Event} class represents an event that can be booked and attended.
 * It includes attributes such as title, description, category, time slot, and associated room.
 * <p>
 * Each event generates a unique event ID following a patterned format and manages
 * its own tickets and attendees.
 * <p>
 * The class also handles availability checking, ticket generation, and basic attendee management.
 * GUI-specific logic (e.g., event detail display) can be added later.
 *
 * Example usage:
 * <pre>
 *     models.Event e = new models.Event("Tech Talk", "A talk on AI", "Nour", "Tech", "10:00AM-12:00PM");
 *     if (e.isAvailable()) {
 *         models.Ticket t = e.generateTicket();
 *     }
 * </pre>
 *
 * @author Nour
 */
public class Event {
    //attributes
    private static int evCounter = 1000;
    private String eventID;
    private double ticketPrice;
    private String title;
    private String description;
    private Organizer organizer;
    private final String category;
    private String timeslot;
    private Room room;
    private ArrayList <Ticket> soldTickets;
    private ArrayList<Attendee> attendees;

    //constructor
    public Event(String title, String description, Organizer organizer, String category, String timeslot, double ticketPrice){
        this.eventID =generateEventID();//patterned id ex: EVT202504201000
        this.title = title;
        this.description = description;
        this.organizer = organizer;
        if(CategoryManager.isValid(category))
        {
            this.category = category;
        }else{
            throw new IllegalArgumentException("Invalid Category");
        }
        this.timeslot = timeslot;

        room = RoomManager.bookAvailableRoom(timeslot);
        if (this.room == null) {
            throw new IllegalStateException("Cannot create event without available room.");
        }
        soldTickets=new ArrayList<>();
        attendees=new ArrayList<>();
        this.ticketPrice = ticketPrice;
    }

    //methods
    public boolean isAvailable(){  //checks for available tickets
        if(room.getCapacity()==soldTickets.size()){return false;}
        else {return true;}
    }

    public void addAttendee(Attendee a){
        attendees.add(a);
    }

    public Ticket generateTicket(){   //add payment logic here ig !!
        int newTicketId=soldTickets.size()+1;
        Ticket ticket=new Ticket(newTicketId,ticketPrice,this.title);
        soldTickets.add(ticket);
        return ticket;
    }

    private String generateEventID(){ //didn't add it to the UML diagram!!
        String prefix = "EVT";
        String datePart = new SimpleDateFormat("yyyyMMdd").format(new Date());
        return prefix + datePart + evCounter++;
    }

    public String getEventID() {return eventID;}

    public ArrayList<Attendee> getAttendees(){return attendees;}

    public Organizer getOrganizer() {return organizer;}

    public double getTicketPrice() {return ticketPrice;}

    private int attendeesCount(){
        return attendees.size();
    }

    public void showEventDetails(){
       //ig it's gonna change when implementing the GUI!!
        System.out.println("Event ID: " + eventID);
        System.out.println("Title: " + title);
        System.out.println("Description: " + description);
        System.out.println("Organizer: "+ organizer.getUserName());
        System.out.println("Category: " + category);
        System.out.println("Time Slot: " + timeslot);
        System.out.println("Ticket Price: " + ticketPrice + " EGP");
        System.out.println("Total Attendees Registered:"+ attendeesCount());

    }
    public void showEventAttendees(){
        System.out.println("List of attendees:");
        for(int i=0;i<attendees.size();i++) {
            System.out.println(attendees.get(i));
        }
    }

    public String toString(){
        return eventID+","+title+", "+description+", "+organizer+", "+category+", "+timeslot+", "+ticketPrice+", "+ attendeesCount()+".";
    }

    //  getters
    public String getTitle() { return title; }
    public String getCategory() { return category; }  // No setter - final field
    public String getDescription() { return description; }
    public String getTimeslot() { return timeslot; }

    // setters with validation
    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        this.title = title.trim();
    }

    public void setDescription(String description) {
        this.description = (description != null) ? description.trim() : null;
    }

    public void setTimeslot(String timeslot) {
        if (timeslot == null || !timeslot.matches("^\\d{2}:\\d{2}-\\d{2}:\\d{2}$")) {
            throw new IllegalArgumentException("Timeslot must be in HH:MM-HH:MM format");
        }
        this.timeslot = timeslot;
    }

    public void setTicketPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        this.ticketPrice = price;
    }

    public void setRoom(Room room) {
        this.room = room;  // Optional: Add validation if needed
    }

    public void removeEventAttendee(Attendee a){
        for(int i=0;i<attendees.size();i++){
            if(a.ID==attendees.get(i).ID){
                attendees.remove(i);
                Database.deleteAttendee(a.ID);}
        }
    }

}
