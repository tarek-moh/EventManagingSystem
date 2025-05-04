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

}
