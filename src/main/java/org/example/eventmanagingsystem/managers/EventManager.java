package org.example.eventmanagingsystem.managers;

import org.example.eventmanagingsystem.models.Attendee;
import org.example.eventmanagingsystem.models.Event;
import org.example.eventmanagingsystem.services.Database;

import java.util.ArrayList;


public class EventManager {

    public static void showAllEvents(){
        ArrayList <Event> events= Database.getEventList();
        for (Event ev:events){
            System.out.println("=========================================");
            ev.showEventDetails();
            System.out.println("=========================================");
        }
    }
    public static void showEventAttendees(Event e){
        e.showEventAttendees();
    }
    public static void removeEventAttendee(Event e, Attendee attendee){
        e.removeEventAttendee(attendee);
    }

    public static void addEvent(String title, String description, String category, double ticketprice)
            throws IllegalArgumentException    {
        Event ev = new Event();
        ev.setTitle(title);
        ev.setDescription(description);
        ev.setCategory(category);
        ev.setTicketPrice(ticketprice);
        //ev.setTimeslot();

        Database.getEventList().add(ev);
        System.out.println("Event added successfully");
    }

}
