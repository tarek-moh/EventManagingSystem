package org.example.eventmanagingsystem.services;

import org.example.eventmanagingsystem.models.*;
import org.example.eventmanagingsystem.util.SplayTree;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * @author Tarek Mohamed
 *
 * This class holds static dummy data to simulate a real database.
 * It also acts as a CRUD manager for the user hierarchy: models.Attendee, models.Organizer, and models.Admin.
 *
 * Note: models.Room is managed by models.Admin. models.Event and Category are managed separately by models.Organizer and models.Admin respectively.
 *
 */
public class Database{
    private static ArrayList<Attendee> attendeeList = new ArrayList<Attendee>();
    private static ArrayList<Organizer> organizerList = new ArrayList<Organizer>();
    private static ArrayList<Admin> adminList = new ArrayList<Admin>();
    private static SplayTree<Event> eventTree = new SplayTree<Event>();
    private static ArrayList<Room> roomList = new ArrayList<Room>();
    private static ArrayList<myCategory> categoryList = new ArrayList<myCategory>();

    static {
        Attendee tarek = new Attendee("Tarek", "attendee", LocalDate.of(2002, 4, 20), "Cairo", Gender.MALE);
        tarek.getWallet().addFunds(1000);
        attendeeList.add(tarek);
        attendeeList.add(new Attendee("Sara", "saraPass", LocalDate.of(1998, 12, 5), "Alexandria", Gender.FEMALE));
        attendeeList.add(new Attendee("Omar", "omar321", LocalDate.of(2000, 7, 15), "Giza", Gender.MALE));
        attendeeList.add(new Attendee("Laila", "helloWorld", LocalDate.of(1995, 2, 28), "Tanta", Gender.FEMALE));
        attendeeList.add(new Attendee("Nour", "nour789", LocalDate.of(2003, 11, 3), "Mansoura", Gender.FEMALE));



        adminList.add(new Admin("Tarek", "admin", LocalDate.of(2002, 4, 20), "models.Event Manager", "09-16","Cairo", Gender.MALE));
        adminList.add(new Admin("Omar", "adminpass", LocalDate.of(1995, 3, 10), "Venue Coordinator", "08-14", "Cairo", Gender.FEMALE));
        adminList.add(new Admin("Nour", "novelQueen", LocalDate.of(2001, 11, 3), "Tech Supervisor", "10-18", "Cairo", Gender.MALE));
        adminList.add(new Admin("Ahmed", "rootAccess", LocalDate.of(1990, 6, 25), "Operations Lead", "07-15", "Cairo", Gender.FEMALE));
        adminList.add(new Admin("Mona", "admin123", LocalDate.of(1998, 1, 30), "Security Head", "11-20", "Cairo", Gender.FEMALE));

        organizerList.add(new Organizer("Tarek", "organizer", LocalDate.of(2002, 4, 20),"Cairo", Gender.MALE));
        organizerList.add(new Organizer("Omar", "organizer456", LocalDate.of(1999, 8, 12), "Cairo", Gender.MALE));
        organizerList.add(new Organizer("Nour", "mysteryBooks", LocalDate.of(2001, 11, 3), "Cairo", Gender.FEMALE));
        organizerList.add(new Organizer("Lina", "eventsPro", LocalDate.of(1997, 2, 27),"Cairo", Gender.FEMALE));
        organizerList.add(new Organizer("Youssef", "letsPlanIt", LocalDate.of(2000, 6, 15), "Cairo", Gender.MALE));

        roomList.add(new Room("10-12,18-20",100));
        roomList.add(new Room("14-16,09-11",100));
        roomList.add(new Room("20-22",100));

        categoryList.add(new myCategory("Classical Music"));
        categoryList.add(new myCategory("Jazz"));
        categoryList.add(new myCategory("Technology"));
        categoryList.add(new myCategory("Art"));
        categoryList.add(new myCategory("Literature"));
        categoryList.add(new myCategory("Photography"));
        categoryList.add(new myCategory("Stand-up Comedy"));
        categoryList.add(new myCategory("Business & Startups"));
        categoryList.add(new myCategory("blablabla"));

        eventTree.insert(new Event("Nocturne in C# Minor", "Frederic Chopin piece", organizerList.get(0), "Classical Music", "10-12", 100,new File("src/main/resources/org/example/eventmanagingsystem/images/musicPlaying.jpg")));
        eventTree.insert(new Event("Jazz Night", "Smooth jazz evening with live saxophone", organizerList.get(1), "Jazz", "18-20", 150,new File("src/main/resources/org/example/eventmanagingsystem/images/jazzEV.jpeg")));
        eventTree.insert(new Event("Tech Talk 2025", "Exploring the future of AI and Quantum Computing", organizerList.get(2), "Technology", "14-16", 0,new File("src/main/resources/org/example/eventmanagingsystem/images/TechtalkEV.jpg")));
        eventTree.insert(new Event("Painting Workshop", "Hands-on acrylic painting workshop for beginners", organizerList.get(3), "Art", "09-11", 75,new File("src/main/resources/org/example/eventmanagingsystem/images/paintingEV2.jpeg")));
      //  eventTree.insert(new Event("Poetry Slam", "An open mic night for poetry lovers", organizerList.get(4), "Literature", "20-22", 50,new File("src/main/resources/org/example/eventmanagingsystem/images/musicPlaying.jpg")));

    }

    // Getters
    public static ArrayList<Attendee> getAttendeeList() {
        return attendeeList;
    }
    public static ArrayList<Organizer> getOrganizerList() {
        return organizerList;
    }
    public static ArrayList<Admin> getAdminList() {
        return adminList;
    }
    public static ArrayList<Event> getEventList() {return eventTree.inOrderTraversal();}
    public static SplayTree<Event> getEventTree() {return eventTree;}
    public static ArrayList<Room> getRoomList() {return roomList;}
    public static ArrayList<myCategory> getCategoryList() {
        return categoryList;
    }


    ////////////////////////// CRUD operations for *ATTENDEE* //////////////////////
    // CREATE
    public static void addCategory(myCategory category){categoryList.add(category); }

    public static void addAttendee(Attendee attendee) {attendeeList.add(attendee);}

    public static void addRoom(Room room){roomList.add(room);}

    // READ
    public static Attendee getAttendee(int id)
    {
        for(Attendee attendee : attendeeList)
        {
            if(attendee.getId() == id)
                return attendee;
        }
        return null;
    }

    // UPDATE
    public static boolean updateAttendee(Attendee updated)
    {
        for (int i = 0; i < attendeeList.size(); i++) {
            if (attendeeList.get(i).getId() == updated.getId())
            {
                Database.attendeeList.set(i, updated);
                return true;
            }
        }
        return false;
    }

    public static ArrayList<Attendee> getAttendeesByOrganizer(int organizerId) {
        ArrayList<Attendee> filteredAttendees = new ArrayList<>();

        // Iterate over all events
        for (Event event : eventTree.inOrderTraversal()) {
            // Check if the event is organized by the given organizer
            if (event.getOrganizer().getId() == organizerId) {
                // Add attendees of this event to the filtered list
                filteredAttendees.addAll(event.getAttendees());
            }
        }

        return filteredAttendees;
    }


    // DELETE
    public static boolean deleteAttendee(int id)
    {
        return attendeeList.removeIf(a -> a.getId() == id);
    }

    ////////////////////////// end of CRUD operations for *ATTENDEE* ///////////////
    ///
    ////////////////////////// CRUD operations for *ORGANIZER* //////////////////////
    // CREATE
    public static void addOrganizer(Organizer organizer)
    {
        organizerList.add(organizer);
    }

    // READ
    public static Organizer getOrganizer(int id)
    {
        for(Organizer organizer : organizerList)
        {
            if(organizer.getId() == id)
                return organizer;
        }
        return null;
    }

    // UPDATE
    public static boolean updateOrganizer(Organizer updated){
        for (int i = 0; i < organizerList.size(); i++) {
            if (organizerList.get(i).getId() == updated.getId()) {
                Database.organizerList.set(i, updated);
                return true;
            }
        }
        return false;
    }

    // DELETE
    public static boolean deleteOrganizer(int id)
    {
        return organizerList.removeIf(a -> a.getId() == id);
    }

    ////////////////////////// end of CRUD operations for *ORGANIZER* ///////////////
    ///
    ////////////////////////// CRUD operations for *ORGANIZER* //////////////////////
    // CREATE
    public static void addAdmin(Admin admin)
    {
        adminList.add(admin);
    }

    // READ
    public static Admin getAdmin(int id)
    {
        for(Admin admin : adminList)
        {
            if(admin.getId() == id)
                return admin;
        }
        return null;
    }

    // UPDATE
    public static boolean updateAdmin(Admin updated)
    {
        for (int i = 0; i < adminList.size(); i++) {
            if(adminList.get(i).getId() == updated.getId())
            {
                Database.adminList.set(i, updated);
                return true;
            }
        }
        return false;
    }

    // DELETE
    public static boolean deleteAdmin(int id)
    {
        return adminList.removeIf(a -> a.getId() == id);
    }

    ////////////////////////// end of CRUD operations for *ORGANIZER* //////////////////////

}
