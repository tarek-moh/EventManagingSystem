package org.example.eventmanagingsystem.models;

import java.time.LocalDate;
import java.util.Scanner;

public abstract class User 
{
    Scanner input = new Scanner(System.in);
    // Properties
    protected int ID;
    protected static int userCount = 0; //all users (admin - attendee - organizers)
    protected String userName;
    protected String password;
    protected LocalDate dateOfBirth;

    // No-arg constructor
    public User() { }

    // Parameterised Constructor 
    public User(String userName, String password, LocalDate dateOfBirth) {
        this.ID = ++userCount; // Increment the total users when a new one is created, Assign the user an ID
        this.userName = userName;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
    }

    // Getters and Setters 
    public int getId() {    return ID;    }

    public String getUserName() {    return this.userName;     }

    public void setUserName(String newUserName) throws IllegalArgumentException {
        if (newUserName == null || newUserName.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty.");
        }

        if (newUserName.length() < 3 || newUserName.length() > 20) {
            throw new IllegalArgumentException("Username must be between 3 and 20 characters.");
        }

        if (!newUserName.matches("^[a-zA-Z0-9_]+$"))
        {
            throw new IllegalArgumentException("Username can only contain letters, digits, and underscores.");
        }
        this.userName = newUserName;
    }

    public String getPassword() {    return this.password;    }

    public void setPassword(String newPassword) throws IllegalArgumentException {
        if (newPassword == null || newPassword.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty.");
        }

        if (newPassword.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long.");
        }

        if (!newPassword.matches(".*[A-Z].*")) {
            throw new IllegalArgumentException("Password must contain at least one uppercase letter.");
        }

        if (!newPassword.matches(".*[a-z].*")) {
            throw new IllegalArgumentException("Password must contain at least one lowercase letter.");
        }

        if (!newPassword.matches(".*\\d.*")) {
            throw new IllegalArgumentException("Password must contain at least one digit.");
        }

        if (newPassword.contains(" ")) {
            throw new IllegalArgumentException("Password must not contain spaces.");
        }

        this.password = newPassword;
    }

    public LocalDate getDateOfBirth() {   return this.dateOfBirth;    }

    public void setDateOfBirth(int year, int month, int day) 
    {
        this.dateOfBirth = LocalDate.of(year, month, day);
    }

    public static int getUserCount() {    return userCount;    }
}