package org.example.eventmanagingsystem.models;

import org.example.eventmanagingsystem.managers.RoomManager;
import org.example.eventmanagingsystem.services.Database;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import static org.example.eventmanagingsystem.managers.AttendeeManager.showAllAttendees;
import static org.example.eventmanagingsystem.managers.EventManager.showAllEvents;
import static org.example.eventmanagingsystem.managers.RoomManager.showAllRooms;


public class Admin extends User {
    private static int adminCount = 0;
    private String adminRole;
    private String workingHours;

    // No-arg constructor
    public Admin() {    super();    }

    // Parameterised Constructor
    public Admin(String userName, String password, LocalDate dateOfBirth, String adminRole, String workingHours,String address,Gender gender) {
        super(userName, password, dateOfBirth, address,gender);
        this.adminRole = adminRole;
        this.workingHours = workingHours;
        this.ID = 30000 + adminCount++;
    }


    // Helper methods for each operation
    private void changeUsername() {
        System.out.println("Enter your new username ");
        String newName = input.next();
        setUserName(newName);
    }

    private void changePassword() {
        while (true) {
            System.out.println("Enter your new password ");
            String newPass = input.next();
            try {
                setPassword(newPass);
                break;
            } catch (IllegalArgumentException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private void changeDateOfBirth() {
        int year = getValidInt("Enter year of birth (e.g., 2000): ", 1, 2024);
        int month = getValidInt("Enter month of birth (1-12): ", 1, 12);
        int day = getValidInt("Enter day of birth (1-31): ", 1, 31);
        setDateOfBirth(year, month, day);
    }

    private void changeRole() {
        System.out.println("Enter your new role ");
        String newRole = input.nextLine();
        setAdminRole(newRole);
    }

    private void changeWorkingHours() {
        System.out.println("Enter your new working hours ");
        String newWorkingHours = input.next();
        setWorkingHours(newWorkingHours);
    }

    // Utility methods
    private short getValidMenuChoice(int min, int max) {
        while (true) {
            if (input.hasNextShort()) {
                short choice = input.nextShort();
                if (choice >= min && choice <= max) {
                    return choice;
                }
            } else {
                input.next(); // Clear invalid input
            }
            System.out.printf("Invalid input! Please choose between %d and %d:%n", min, max);
        }
    }

    private int getValidInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            if (input.hasNextInt()) {
                int value = input.nextInt();
                if (value >= min && value <= max) {
                    return value;
                }
            } else {
                input.next(); // Clear invalid input
            }
            System.out.printf("Invalid input! Please enter a value between %d and %d:%n", min, max);
        }
    }

    // Getters and Setters
    public String getAdminRole() {    return adminRole;    }

    public void setAdminRole(String newAdminRole)
    {
        this.adminRole = newAdminRole;
    }

    public String getWorkingHours() {    return workingHours;    }

    public void setWorkingHours(String newWorkingHours)
    {
        this.workingHours = newWorkingHours;
    }

    public void createCategory (myCategory newCategory) {  Database.getCategoryList().add(newCategory);    }

    public myCategory readCategory (String name)
    {
        for (myCategory category : Database.getCategoryList()) {
            if (category.getName().equals(name)) {
                return category;
            }
        }
        return null;
    }

    public boolean deleteCategory(String name)
    {
        if (readCategory(name) != null) {
            Database.getCategoryList().remove(readCategory(name));
            return true;
        }
        return false;
    }

    public void createRoom (Room newRoom) {   Database.getRoomList().add(newRoom);    }

    public Room readRoom (int roomId)
    {
        for (Room room : Database.getRoomList())
        {
        if (room.getRoomID() == roomId)
        {
            return room;
        }
        }
        return null;
    }

    public boolean deleteRoom(int Id)
    {
        ArrayList<Room> rooms = Database.getRoomList();
        for(int i =0; i<rooms.size();i++)
        {
            if(rooms.get(i).getRoomID() == Id) {
                Database.getRoomList().remove(i);
                return true;
            }
        }
        return false;
    }

    public void showAllCategories() {
        for (myCategory c : Database.getCategoryList()) {
            System.out.println(c);
        }
    }
}

