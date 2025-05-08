package org.example.eventmanagingsystem.managers;

import org.example.eventmanagingsystem.models.Room;
import org.example.eventmanagingsystem.services.Database;

import java.util.ArrayList;
import java.util.List;

public class RoomManager {
    public static Room bookAvailableRoom(String timeslot)
    {
        for(Room room : Database.getRoomList())
        {
            if(room.isAvailable(timeslot))
            {
                room.bookRoom(timeslot);
                return room;
            }
        }
        return null;
    }

    public static void showAvailableRooms()
    {
        ArrayList<Room> roomList = Database.getRoomList();
        boolean noAvailable = true;
        System.out.println("Available Rooms\n");
        for (Room room : roomList)
        {
            ArrayList<String> slots = new ArrayList<>(List.of(room.getScheduleSlots().split(",")));
            for(String slot : slots)
            {
                if (room.isAvailable(slot))
                {
                    System.out.println("models.Room ID: " + room.getRoomID());
                    System.out.println("schedule hours: " + room.getScheduleSlots());
                    System.out.println("Capacity: " + room.getCapacity() + "\n");
                    noAvailable = false;
                }
            }

        }
        if (noAvailable) {    System.out.println("No available rooms");}
    }

    public static void showAllRooms()
    {
        if(Database.getRoomList().isEmpty())
        {
            System.out.println("No rooms in the system");
        }
        for(Room room : Database.getRoomList())
        {
            System.out.println("====================");
            room.printRoomDetails();
            System.out.println("====================");

        }
    }

    public static void addRoom(int newRoomCapacity) throws IllegalArgumentException
    {
        Room r = new Room();
        r.setCapacity(newRoomCapacity);
        // r.set price

        Database.getRoomList().add(r);
        System.out.println("Room added successfully");
    }
}
