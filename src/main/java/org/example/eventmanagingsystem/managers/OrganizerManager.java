package org.example.eventmanagingsystem.managers;

import org.example.eventmanagingsystem.models.Gender;
import org.example.eventmanagingsystem.models.Organizer;
import org.example.eventmanagingsystem.services.Database;

import java.time.LocalDate;

public class OrganizerManager {
    public static void addOrganizer(String username, String password, LocalDate dateOfBirth,String address, Gender gender) throws IllegalArgumentException {
        // Validate username (must not be empty)
        Organizer org  = new Organizer();
        org.setUserName(username);
        org.setPassword(password);
        org.setDateOfBirth(dateOfBirth);

        System.out.println("Organizer " + username + " added successfully!");

        Database.addOrganizer(new Organizer(username, password, dateOfBirth, address, gender));
    }
}
