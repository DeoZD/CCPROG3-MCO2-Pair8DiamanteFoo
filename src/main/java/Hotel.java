import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a hotel with a collection of rooms and price modifiers based on
 * dates.
 * 
 * @author James Foo
 * @author Zami Diamante
 * @version 1.0
 */
public class Hotel {
    private String name;
    private double basePrice;
    private List<Room> rooms;
    private Map<Integer, Double> datePriceModifiers;

    /**
     * Constructs a Hotel with the specified name and base price.
     * Initializes the room list and sets default price modifiers for each day of
     * the month.
     * 
     * @param name      the name of the hotel
     * @param basePrice the base price of rooms in the hotel
     */
    public Hotel(String name, double basePrice) {
        this.name = name;
        this.basePrice = basePrice;
        this.rooms = new ArrayList<Room>();
        /*
         * // Adding default rooms // set to 10 instead of 1
         * for (int i = 1; i <= 10; i++) {
         * if (i <= 7)
         * rooms.add(new Room("Room " + i, basePrice));
         * if (i > 7 && i < 10)
         * rooms.add(new RoomDeluxe("Room " + i, basePrice));
         * if (i == 10)
         * rooms.add(new RoomExecutive("Room " + i, basePrice));
         * }
         */
        // Initialize datePriceModifiers map
        this.datePriceModifiers = new HashMap<Integer, Double>();
        // Set default price rates to 100% for each day of the month (1 to 30)
        for (int date = 1; date <= 30; date++) {
            datePriceModifiers.put(date, 1.0); // 100% of the base price
        }
    }

    /**
     * Gets the name of the hotel.
     * 
     * @return the name of the hotel
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the hotel.
     * 
     * @param name the new name of the hotel
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the base price of the rooms.
     * 
     * @return the base price of the rooms
     */
    public double getBasePrice() {
        return basePrice;
    }

    /**
     * Sets the base price of the rooms.
     * 
     * @param basePrice the new base price for rooms
     */
    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    /**
     * Gets the list of rooms in the hotel.
     * 
     * @return the list of rooms
     */
    public List<Room> getRooms() {
        return rooms;
    }

    /**
     * Gets the total number of rooms in the hotel.
     * 
     * @return the number of rooms
     */
    public int getTotalRooms() {
        return rooms.size();
    }

    /**
     * Retrieves a room by its name.
     * 
     * @param name the name of the room
     * @return the room with the specified name, or null if no such room exists
     */
    public Room getRoomByName(String name) {
        for (Room room : rooms) {
            if (room.getName().equals(name)) {
                return room;
            }
        }
        return null; // Room not found
    }

    /**
     * Gets the map of date-based price modifiers.
     * 
     * @return the map of date-based price modifiers
     */
    public Map<Integer, Double> getDatePriceModifiers() {
        return datePriceModifiers;
    }

    /**
     * Adds a new room to the hotel.
     * 
     * @param room the room to be added
     */
    public void addRoom(Room room) {
        rooms.add(room);
    }

    /**
     * Removes a specified room from the hotel.
     * 
     * @param room the room to be removed
     */
    public void removeRoom(Room room) {
        rooms.remove(room);
    }

    /**
     * Removes a room identified by its name if it meets the criteria for removal.
     * 
     * @param roomName the name of the room to be removed
     * @return true if the room was successfully removed, false otherwise
     */
    public boolean removeRoomByName(String roomName) {
        Room roomToRemove = getRoomByName(roomName);
        if (roomToRemove != null && isRoomRemovable(roomToRemove)) {
            rooms.remove(roomToRemove);
            return true;
        }
        return false; // Room not found or not removable
    }

    /**
     * Checks if a room is removable based on its current status.
     * 
     * @param room the room to check
     * @return true if the room is empty and can be removed, false otherwise
     */
    public boolean isRoomRemovable(Room room) {
        return room.isEmpty(); // Room can be removed if it is empty
    }

    /**
     * Determines if the room prices can be updated based on the current
     * reservations.
     * 
     * @return true if at least one room has no reservations, false otherwise
     */
    public boolean isPriceUpdateable() {
        for (Room room : rooms) {
            if (room.getReservations().isEmpty()) {
                return true; // At least one room is available for price updates
            }
        }
        return false; // All rooms have reservations
    }

    /**
     * Calculates the estimated earnings from all reservations in the hotel.
     * 
     * @return the total estimated earnings
     */
    public double getEstimatedEarnings() {
        double totalEarnings = 0.0;
        for (Room room : rooms) {
            for (Reservation reservation : room.getReservations()) {
                totalEarnings += reservation.getTotalCost(); // Sum total cost of all reservations
            }
        }
        return totalEarnings;
    }

    /**
     * Gets the number of available rooms for a given date.
     * 
     * @param date the date for which to check room availability
     * @return the number of available rooms
     */
    public int getAvailableRooms(int date) {
        int availableRooms = 0;
        for (Room room : rooms) {
            if (room.isAvailable(date, date + 1)) {
                availableRooms++; // Increment count for available rooms
            }
        }
        return availableRooms;
    }

    /**
     * Gets the number of booked rooms for a given date.
     * 
     * @param date the date for which to check room bookings
     * @return the number of booked rooms
     */
    public int getBookedRooms(int date) {
        return getTotalRooms() - getAvailableRooms(date); // Calculate booked rooms as total minus available
    }

    /**
     * Sets a price modifier for a specific date.
     * 
     * @param date      the date for which the price modifier is set
     * @param priceRate the price modifier (rate) for the given date
     */
    public void setDatePriceModifier(int date, double priceRate) {
        if (date >= 1 && date <= 30 && priceRate >= 0.5 && priceRate <= 1.5) {
            datePriceModifiers.put(date, priceRate); // Valid date and price rate, update the modifier
        } else {
            // Handle invalid date or price rate
            System.err.println("Invalid date or price rate.");
        }
    }

    /**
     * Retrieves the price modifier for a specific date.
     * 
     * @param date the date for which to retrieve the price modifier
     * @return the price modifier for the given date, or 1.0 if no modifier exists
     */
    public double getPriceModifierForDate(int date) {
        if (datePriceModifiers.containsKey(date)) {
            return datePriceModifiers.get(date); // Return existing modifier
        } else {
            // No modifier exists for this date, return default value
            return 1.0; // 100% (no change)
        }
    }

    /**
     * Checks if a room with the given name already exists in the hotel.
     * 
     * @param roomName the name of the room to check
     * @return true if a room with the specified name exists, false otherwise
     */
    public boolean checkDuplicateRoomName(String roomName) {
        for (Room room : rooms) {
            if (room.getName().equals(roomName)) {
                return true; // Duplicate room name found
            }
        }
        return false; // No duplicate found
    }
}
