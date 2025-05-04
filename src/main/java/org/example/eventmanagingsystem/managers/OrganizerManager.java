package org.example.eventmanagingsystem.managers;

import org.example.eventmanagingsystem.models.Organizer;
import org.example.eventmanagingsystem.services.Database;

import java.time.LocalDate;

public class OrganizerManager {
    public static void addOrganizer(String username, String password, LocalDate dateOfBirth) throws IllegalArgumentException {
        // Validate username (must not be empty)
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty.");
        }

        // Validate password (must not be empty and should have a minimum length)
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty.");
        }
        if (password.length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters long.");
        }

        // Validate date of birth (must not be empty)
        if (dateOfBirth == null) {
            throw new IllegalArgumentException("Date of birth cannot be empty.");
        }

        // You can add more validations here, for example, ensure date is in the past
        if (dateOfBirth.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Date of birth cannot be in the future.");
        }

        Database.addOrganizer(new Organizer(username, password, dateOfBirth));
    }
}
