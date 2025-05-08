package org.example.eventmanagingsystem.models;

import org.example.eventmanagingsystem.managers.EventManager;
import org.example.eventmanagingsystem.services.Database;
import org.example.eventmanagingsystem.services.PaymentService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Attendee extends User
{
    Scanner input = new Scanner(System.in);

    private static int attendeeCount = 0;
    private Wallet wallet;
    private ArrayList<myCategory> interestList;
    private ArrayList<Ticket> ticketList;


    public Attendee() {    super();     } // no-arg-constructor

    // parameterized constructor
    public Attendee(String userName, String password, LocalDate dateOfBirth, String address, Gender gender) 
    {
        super(userName, password, dateOfBirth, address, gender);
        this.ID = 10000 + attendeeCount;
         this.wallet = new Wallet();
         this.ticketList = new ArrayList<Ticket>();
         this.interestList = new ArrayList<myCategory>();
        attendeeCount++;
    }


    // Helper methods
    private void displayMainMenu() {
        System.out.println("========================================");
        System.out.println("Dashboard");
        System.out.println("========================================");
        System.out.println("1: View Events\n2: View my tickets \n3: Buy ticket\n4: My wallet");
        System.out.println("5: Check Profile\n6: Manage Interests\n7: Logout");
    }

    private short getValidChoice(int min, int max) {
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

    private void manageWallet() {
        System.out.println("Current balance");
        // System.out.println(wallet.getBalance());

        while (true) {
            System.out.println("1: Add money     2: Withdraw money     3: Exit");
            short walletChoice = getValidChoice(1, 3);

            if (walletChoice == 3) break;

            System.out.println("Enter amount: ");
            int amount = input.nextInt();
            input.nextLine(); // Consume newline
            updateWallet(amount, walletChoice);
        }
    }


    // helper methods
    private void changeUsername() {
        System.out.println("Enter your new username ");
        String newName = input.next();
        setUserName(newName);
    }

    private void changePassword()
    {
        while(true) {
            try {
                System.out.println("Enter your new password ");
                String password = input.next();
                setPassword(password);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e);
            }
        }
    }

    /**
     * Prompts the user to change their address with validation
     */
    private void changeAddress() {
        System.out.println("Current address: " + getAddress());
        System.out.print("Enter new address (at least 10 characters): ");
        input.nextLine(); // Clear buffer
        String newAddress = input.nextLine();

        try {
            setAddress(newAddress);
            System.out.println("Address updated successfully!");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("Address not changed");
        }
    }

    /**
     * Sets the address with validation
     * @param address The new address to set
     * @throws IllegalArgumentException if address is invalid
     */
    public void setAddress(String address) {
        if (address == null || address.trim().isEmpty()) {
            throw new IllegalArgumentException("Address cannot be empty");
        }

        if (address.trim().length() < 10) {
            throw new IllegalArgumentException("Address must be at least 10 characters");
        }

        // Additional validation rules could be added here
        if (!address.matches("^[\\w\\s\\-,.#]+$")) {
            throw new IllegalArgumentException("Address contains invalid characters");
        }

        this.address = address.trim();
    }

    public boolean buyTicket(Event e){
        if (e.isAvailable()){
            if(!PaymentService.transferFunds(wallet, e.getOrganizer().getWallet(), e.getTicketPrice()))
            {
                return false;
            }
            Ticket ticket = e.generateTicket();
            ticketList.add(ticket);
            e.addAttendee(this);
            return true;
        }
        else{
            return false;
        }
    }

    public void viewMyTickets()
    {
        System.out.println("Viewing my tickets...");
        for(Ticket ticket : ticketList)
        {
            System.out.println(ticket.showTicketDetsils());
        }
    }

    /*
     * @brief deposit or withdraw amount from attendees wallet
     * @param choice takes 1 for deposit, 2 for withdraw
     */
    public void updateWallet(double amount, short choice)
    {
        if (choice == 1) {    wallet.addFunds(amount);    }
        else if (choice == 2) {    wallet.deductFunds(amount);    }
    }

    public ArrayList<Ticket> getMyTickets() {
        return ticketList;
    }


    public void setGender(int select) 
    {    
                if (select == 1) 
                {
                    this.gender = Gender.MALE;
                } 
                else if (select == 2) 
                {
                    this.gender = Gender.FEMALE;
                }
    }

    public ArrayList<myCategory> getInterests() {
        return interestList;
    }

    public void setInterests(ArrayList<myCategory> interests) {
        this.interestList = interests;
    }

    public static int getAttendeeCount() {    return attendeeCount;    }

    public void showAttendeeDetails()
    {
        System.out.println("Attendee ID: " + this.ID);
        System.out.println("Attendee Username: " + this.userName);
        System.out.println("Gender: " + this.gender);
        System.out.println("Address: " + this.address);
    }


    private void manageInterests() {
        // Get all available categories
        ArrayList<myCategory> allCategories = Database.getCategoryList();

        System.out.println("\n=== Manage Interests ===");
        System.out.println("Current interests: " + getInterestNames());
        System.out.println("Available categories: " + getCategoryNames(allCategories));

        while (true) {
            System.out.print("\nEnter a category name to add/remove (or 'done' to finish): ");
            String inputName = input.nextLine().trim();

            // Exit condition
            if (inputName.equalsIgnoreCase("done")) {
                System.out.println("Updated interests: " + getInterestNames());
                return;
            }

            // Find matching category
            myCategory foundCategory = null;
            for (myCategory category : allCategories) {
                if (category.getName().equalsIgnoreCase(inputName)) {
                    foundCategory = category;
                    break;
                }
            }

            if (foundCategory == null) {
                System.out.println("Invalid category! Available categories: " + getCategoryNames(allCategories));
                continue;
            }

            // Toggle interest
            if (interestList.contains(foundCategory)) {
                interestList.remove(foundCategory);
                System.out.println("Removed '" + foundCategory.getName() + "' from interests");
            } else {
                interestList.add(foundCategory);
                System.out.println("Added '" + foundCategory.getName() + "' to interests");
            }
            System.out.println("Current interests: " + getInterestNames());
        }
    }

    // Helper methods
    private ArrayList<String> getInterestNames() {
        ArrayList<String> names = new ArrayList<>();
        for (myCategory category : interestList) {
            names.add(category.getName());
        }
        return names;
    }

    private ArrayList<String> getCategoryNames(ArrayList<myCategory> categories) {
        ArrayList<String> names = new ArrayList<>();
        for (myCategory category : categories) {
            names.add(category.getName());
        }
        return names;
    }

    @Override
    public String toString()
    {
        return "Attendee ID: " + this.ID + "Attendee Username: " + this.userName + "Gender: " + this.gender
                + "Address: " + this.address;
    }
}

