package org.example.eventmanagingsystem.models;

/**
 * The {@code models.Ticket} class represents a ticket for an event.
 * Each ticket has a unique ID, a price, and is associated with a specific event title.
 * <p>
 * This class provides a method to display the ticket's details in a formatted string,
 * which can later be adapted for GUI display.
 *
 * Example usage:
 * <pre>
 *     models.Ticket t = new models.Ticket(101, 50.0, "Music Concert");
 *     System.out.println(t.showTicketDetails());
 * </pre>
 *
 * @author Nour
 */

public class Ticket {
    private int ticketID;
    private double price;
    private String eventTitle;
    private String roomId;
    private String timeslot;

    //constructor
    public Ticket(int ticketID,double price,String eventTitle, String roomId, String timeslot){
        this.ticketID=ticketID;
        this.price=price;
        this.eventTitle=eventTitle;
        this.roomId = roomId;
        this.timeslot = timeslot;
    }
    public String showTicketDetsils(){ //gonna change for GUI ig !!
        return "models.Ticket ID : "+this.ticketID+", models.Event : " +eventTitle+", Price : "+this.price;
    }

    @Override
    public String toString() {
        return String.format(
                "🎟 Ticket: %s\nEvent: %s\nRoom: %s\nTime: %s\nPrice: $%.2f",
                ticketID, eventTitle, roomId, timeslot, price
        );
    }
}


