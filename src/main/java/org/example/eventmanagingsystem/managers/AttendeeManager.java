package org.example.eventmanagingsystem.managers;

import org.example.eventmanagingsystem.models.Attendee;
import org.example.eventmanagingsystem.models.Gender;
import org.example.eventmanagingsystem.services.Database;

import java.time.LocalDate;

public class AttendeeManager {

    // Custom exceptions
    static class InvalidInputException extends Exception {
        public InvalidInputException(String message) {
            super(message);
        }
    }

    static class UserAlreadyExistsException extends Exception {
        public UserAlreadyExistsException(String message) {
            super(message);
        }
    }

    // The method to add an attendee
    public static void addAttendee(String username, String password, String address, LocalDate dateOfBirth, Gender gender)
            throws InvalidInputException, UserAlreadyExistsException {

        // Validate username
        if (username == null || username.trim().isEmpty()) {
            throw new InvalidInputException("Username cannot be empty or null.");
        }

        // Validate password
        if (password == null || password.trim().isEmpty()) {
            throw new InvalidInputException("Password cannot be empty or null.");
        }

        // Validate address
        if (address == null || address.trim().isEmpty()) {
            throw new InvalidInputException("Address cannot be empty or null.");
        }


        // Check if the date of birth is in the future
        if (dateOfBirth.isAfter(LocalDate.now())) {
            throw new InvalidInputException("Date of birth cannot be in the future.");
        }

        // Check if the user already exists (this would normally be checked by querying a database)
        if (userExists(username)) {
            throw new UserAlreadyExistsException("User with username " + username + " already exists.");
        }

        // If everything is valid, proceed with adding the user (this could involve saving to a database)
        System.out.println("Attendee " + username + " added successfully!");

        Database.addAttendee(new Attendee(username, password, dateOfBirth, address, gender));
        // addUserToDatabase(username, password, address, dob);
    }

    // Helper method to simulate checking if a user already exists (this could involve querying a database)
    private static boolean userExists(String username) {
        // For demo purposes, we'll say that "existingUser" already exists
        for(Attendee attendee : Database.getAttendeeList())
        {
            if(attendee.getUserName().equals(username))
                return true;
        }
        return false;
    }
}
