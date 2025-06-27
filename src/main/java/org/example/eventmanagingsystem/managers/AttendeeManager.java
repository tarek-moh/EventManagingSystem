package org.example.eventmanagingsystem.managers;

import org.example.eventmanagingsystem.models.Attendee;
import org.example.eventmanagingsystem.models.Gender;
import org.example.eventmanagingsystem.services.Database;

import java.time.LocalDate;

public class AttendeeManager {

    // Custom exceptions

    // The method to add an attendee
    public static void addAttendee(String username, String password, String address, LocalDate dateOfBirth, Gender gender)
            throws IllegalArgumentException{

        // Validate username
        Attendee Att = new Attendee();

        Att.setUserName(username);
        Att.setPassword(password);
        Att.setAddress(address);
        Att.setDateOfBirth(dateOfBirth);
        Att.setGender(gender);

        // If everything is valid, proceed with adding the user (this could involve saving to a database)
        System.out.println("Attendee " + username + " added successfully!");

        Database.addAttendee(Att);
    }
}
