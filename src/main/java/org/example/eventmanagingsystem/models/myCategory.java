package org.example.eventmanagingsystem.models;

import org.example.eventmanagingsystem.services.Database;

/**
 * The {@code Category} class represents a classification or type of events
 * (e.g., Music, Tech, Sports). Each category maintains a list of events that
 * fall under it.
 * <p>
 * It provides methods to add events and retrieve all events associated
 * with this category.

 * Example usage:
 * <pre>
 *     Category musicCategory = new Category("Music");
 *     musicCategory.addEvent(concertEvent);
 * </pre>
 *
 * @author Nour
 */

public class myCategory {
    private String name;

    //constructor
    public myCategory(){}

    public myCategory(String name){
        this.name=name;
    }
    //methods
//    public void addEvent(Event e){
//        events.add(e);
//    }
    //getters

    public int getEventCount()
    {
        int count = 0;
        for(Event ev : Database.getEventList())
        {
            if(ev.getCategory().equals(name))
                count++;
        }
        return count;
    }

    public String getName(){ //notice I didn't add it to the UML diagram
        return this.name;
    }

    public void setName(String newName) throws IllegalArgumentException
    {
        if (newName == null || newName.trim().isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be null or empty.");

        }
        for (myCategory cat : Database.getCategoryList()) {
            if (cat.getName().equalsIgnoreCase(newName.trim())) {
                throw new IllegalArgumentException("Category name is already made");
            }
        }
        this.name = newName;
    }

    public String toString(){
        return "[" + name + "]" +" Number of Events in "+ name+ ": " + getEventCount();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof myCategory)) return false;
        myCategory other = (myCategory) o;
        return this.name.equalsIgnoreCase(other.name);
    }

}
