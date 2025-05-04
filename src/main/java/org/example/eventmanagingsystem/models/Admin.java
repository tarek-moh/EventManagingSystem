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
    public Admin(String userName, String password, LocalDate dateOfBirth, String adminRole, String workingHours) {
        super(userName, password, dateOfBirth);
        this.adminRole = adminRole;
        this.workingHours = workingHours;
        this.ID = 30000 + adminCount++;
    }

    // Implement abstract method: Dashboard
    @Override
    public void showDashboard() {
        Scanner input = new Scanner(System.in);
        boolean inDashboard = true;

        while (inDashboard) {
            System.out.println("========================================");
            System.out.println("Dashboard");
            System.out.println("========================================");
            System.out.println("""
        1: Show all rooms
        2: Show all events
        3: Show all attendees
        4: Create a category
        5: show all categories
        6: Delete a Category
        7: Create a room
        8: show all rooms
        9: Delete a room
        10: Check Profile
        11: Logout
        """);

            System.out.print("Choose an option: ");

            if (input.hasNextShort()) {
                short choice = input.nextShort();
                input.nextLine(); // consume leftover newline

                switch (choice) {
                    case 1 -> {
                        System.out.println("All Rooms:");
                        showAllRooms();
                    }
                    case 2 -> {
                        System.out.println("All Events:");
                        showAllEvents();
                    }
                    case 3 -> {
                        System.out.println("All Attendees:");
                        showAllAttendees();
                    }
                    case 4 -> {
                        System.out.println("Enter name of Category:");
                        String categoryName = input.nextLine();
                        myCategory newCategory = new myCategory(categoryName);
                        createCategory(newCategory);
                    }
                    case 5 -> {
                        System.out.println("All Categories:");
                        showAllCategories();
                    }
                    case 6 -> {
                        System.out.println("Enter name of Category to delete:");
                        String name = input.nextLine();
                        if (deleteCategory(name))
                            System.out.println("Category deleted.");
                        else
                            System.out.println("Category not found.");
                    }
                    case 7 -> {
                        System.out.println("Enter available hours of Room format HH-HH :");
                        String roomHours = input.nextLine();
                        System.out.println("Enter capacity of Room:");
                        int roomCapacity = input.nextInt();
                        input.nextLine(); // consume newline
                        try
                        {
                            Room newRoom = new Room(roomHours, roomCapacity);
                            createRoom(newRoom);
                        }
                        catch(IllegalArgumentException ex)
                        {
                            System.out.println(ex);
                        }
                    }
                    case 8 -> RoomManager.showAllRooms();
                    case 9 -> {
                        System.out.println("Enter ID of Room to delete:");
                        int roomID = input.nextInt();
                        input.nextLine(); // consume newline
                        if (deleteRoom(roomID))
                            System.out.println("Room deleted.");
                        else
                            System.out.println("Room not found.");
                    }
                    case 10 -> showProfile();
                    case 11 -> {
                        System.out.println("Logging out...");
                        inDashboard = false;
                    }
                    default -> System.out.println("Invalid input! Please choose between 1 and 13.");

                }
            } else {
                System.out.println("Invalid input! Please enter a number.");
                input.next(); // clear the invalid input
            }
        }
    }

    private void showProfile() {
        boolean inProfile = true;

        while (inProfile) {
            System.out.println("========================================");
            System.out.println("Profile");
            System.out.println("========================================");
            System.out.println("Username              1: Change Username\n" + getUserName());
            System.out.println("Password              2: Change Password\n" + getPassword());
            System.out.println("Date of birth         3: Change date of birth\n" + getDateOfBirth());
            System.out.println("Role                  4: Change Role\n" + getAdminRole());
            System.out.println("Working hours         5: Change working hours\n" + getWorkingHours());
            System.out.println("ID                    6: Exit Profile\n" + getId());

            short profChoice = getValidMenuChoice(1, 6);

            switch (profChoice) {
                case 1:
                    changeUsername();
                    break;

                case 2:
                    changePassword();
                    break;

                case 3:
                    changeDateOfBirth();
                    break;

                case 4:
                    changeRole();
                    break;

                case 5:
                    changeWorkingHours();
                    break;

                case 6:
                    inProfile = false;
                    break;
            }
        }

        showDashboard();
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

