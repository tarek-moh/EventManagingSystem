package org.example.eventmanagingsystem.managers;

import org.example.eventmanagingsystem.models.Attendee;
import org.example.eventmanagingsystem.models.Event;
import org.example.eventmanagingsystem.models.Organizer;
import org.example.eventmanagingsystem.services.Database;

import java.io.IOException;
import java.util.ArrayList;


public class EventManager {

    public static void removeEventAttendee(Event e, Attendee attendee){
        e.removeEventAttendee(attendee);
    }

    public static Event addEvent(String title, String description, String category, String timeslot, double ticketprice, Organizer org)
            throws IllegalArgumentException , IOException {
        Event ev = new Event();
        ev.setTitle(title);
        ev.setDescription(description);
        ev.setCategory(category);
        ev.setTicketPrice(ticketprice);
        ev.setTimeslot(timeslot);
        ev.setOrganizer(org);
        Database.getEventList().add(ev);
        Database.getEventTree().insert(ev);
        System.out.println("Event added successfully");
        return ev;
    }



}
