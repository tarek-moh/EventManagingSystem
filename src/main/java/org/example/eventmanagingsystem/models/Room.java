package org.example.eventmanagingsystem.models;

import java.util.ArrayList;

/**
 * @author Omaro
 *<p>
 * Represents a room that can be booked for events.
 * Tracks available hours and already-booked slots.
 *
 * Booking is done based on time slots as String identifiers.
 * </p>
 */
public class Room {

	private static int roomCount = 0;
	private int roomID;
	private int capacity;

	// A String containing range of hours that the room may be available.
	// The time is in a 24hr format
	// e.g. "12-14,00-02"
	private String slots;

	// A String containing range of booked hours from the available hours. The range
	// must match one of the ranges in available hours string.
	private ArrayList<String> bookedSlots;

	public Room(){
		this.roomID = ++roomCount;
		this.bookedSlots = new ArrayList<String>();
	}

	public Room(String availableHours, int capacity) {
		if (capacity <= 0) {
			throw new IllegalArgumentException("Room capacity must be a positive number.");
		}
		if (availableHours == null || availableHours.isBlank()) {
			throw new IllegalArgumentException("Available hours cannot be empty.");
		}
		this.roomID = ++roomCount;
		this.slots = availableHours;
		this.capacity = capacity;
		this.bookedSlots = new ArrayList<String>();
	}

	public void setAvailableHours(String availableHours) {
		if (availableHours == null || availableHours.isBlank()) {
			throw new IllegalArgumentException("Available hours cannot be empty.");
		}
		if(!isValidTimeslotFormat(availableHours))
		{
			throw new IllegalArgumentException("Invalid timeslot format. Expected format: \"hh-hh\" (24-hour).");
		}

		this.slots = availableHours;
	}

	public String getScheduleSlots() {
		return slots;
	}

	public void setCapacity(int capacity) throws IllegalArgumentException {
		if (capacity<=0)
		{
			throw new IllegalArgumentException("Capacity must be a positive number");
		}
		this.capacity = capacity;
	}

	public int getCapacity() {
		return capacity;
	}

	public int getRoomID() {
		return roomID;
	}

	public ArrayList<String> getBookedSlots() {
		return bookedSlots;
	}

	public boolean isAvailable(String timeslot) {
		String[] availableSlots = slots.split(",");
		for (String slot : availableSlots) {
			if (slot.equals(timeslot)) {
				return !bookedSlots.contains(timeslot);
			}
		}
		return false;
	}

	public boolean bookRoom(String timeslot) {
		if (!isValidTimeslotFormat(timeslot)) {
			throw new IllegalArgumentException("Invalid timeslot format. Expected format: \"hh-hh\" (24-hour).");
		}

		if (isAvailable(timeslot)) {
			bookedSlots.add(timeslot);
			return true;
		} else {
			return false;
		}
	}

	public void printRoomDetails() {
		System.out.println("models.Room ID: " + roomID);
		System.out.println("Capacity: " + capacity);
		System.out.println("schedule slots: " + slots);
		System.out.println("Booked Slots: " + bookedSlots);
	}

	public void cancelBooking(String timeslot) {
		if (!isValidTimeslotFormat(timeslot)) {
			throw new IllegalArgumentException("Invalid timeslot format. Expected format: \"hh-hh\" (24-hour).");
		}

		if (bookedSlots.contains(timeslot)) {
			bookedSlots.remove(timeslot);
			System.out.println("Booking canceled for timeslot: " + timeslot);
		} else {
			System.out.println("No booking found for timeslot: " + timeslot);
		}
	}

	@Override
	public String toString()
	{
		return "models.Room ID: " + roomID + "\n" +
				"Capacity: " + capacity + "\n" +
				"Available Hours: " + slots  + "\n" +
				"Booked Slots: " + bookedSlots;

	}

	private static boolean isValidTimeslotFormat(String timeslot) {
		return timeslot.matches("(0?[0-9]|1[0-9]|2[0-3])\\-(0?[0-9]|1[0-9]|2[0-3])");
	}
}

