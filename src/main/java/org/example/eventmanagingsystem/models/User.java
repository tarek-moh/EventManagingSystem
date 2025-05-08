package org.example.eventmanagingsystem.models;

import org.example.eventmanagingsystem.services.Database;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    protected String address;
    protected Gender gender;

    // No-arg constructor
    public User() { }

    // Parameterised Constructor 
    public User(String userName, String password, LocalDate dateOfBirth,String address,Gender gender) {
        this.ID = ++userCount; // Increment the total users when a new one is created, Assign the user an ID
        this.userName = userName;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.address =address;
        this.gender = gender;
    }

    // Getters and Setters 
    public int getId() {    return ID;    }

    public String getUserName() {    return this.userName;     }
    public Gender getGender() {return gender;}
    public String getAddress() {return address;}
    public LocalDate getDateOfBirth() {return this.dateOfBirth;}

    public String getDateOfBirthAsString(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        String formatted = dateOfBirth.format(formatter);  // "31 December 2000"
        return formatted;
    }
    public void setGender(Gender gender)
    {
        this.gender= gender;
    }
    public void setAddress(String address){
        this.address = address;
    }

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
        for(Attendee attendee : Database.getAttendeeList())
        {
            if(attendee.getUserName().equals(newUserName))
            {  throw new IllegalArgumentException("Username is already taken");  }
        }

        for(Organizer organizer : Database.getOrganizerList())
        {
            if(organizer.getUserName().equals(newUserName))
            {  throw new IllegalArgumentException("Username is already taken");  }
        }

        for(Admin admin : Database.getAdminList())
        {
            if(admin.getUserName().equals(newUserName))
            {  throw new IllegalArgumentException("Username is already taken");  }
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

    public void setDateOfBirth(LocalDate dateOfBirth) throws IllegalArgumentException
    {
        if (dateOfBirth == null) {
            throw new IllegalArgumentException("Date of birth cannot be empty.");
        }

        if (dateOfBirth.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Date of birth cannot be in the future.");
        }
        this.dateOfBirth = dateOfBirth;
    }

    public static int getUserCount() {    return userCount;    }
}