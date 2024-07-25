import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Hotel {
    private String name;
    private double basePrice;
    private List<Room> rooms;
    private Map<Integer, Double> datePriceModifiers;

    public Hotel(String name, double basePrice) {
        this.name = name;
        this.basePrice = basePrice;
        this.rooms = new ArrayList<Room>();
        // Adding default rooms // set to 10 instead of 1
        for (int i = 1; i <= 10; i++) {
            if (i <= 7)
                rooms.add(new Room("Room " + i, basePrice));
            if (i > 7 && i < 10)
                rooms.add(new RoomDeluxe("Room " + i, basePrice));
            if (i == 10)
                rooms.add(new RoomExecutive("Room " + i, basePrice));
        }
        // Initialize datePriceModifiers map
        this.datePriceModifiers = new HashMap<Integer, Double>();
        // Set default price rates to 100%
        for (int date = 1; date <= 30; date++) {
            datePriceModifiers.put(date, 1.0); // 100%
        }

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public int getTotalRooms() {
        return rooms.size();
    }

    public Room getRoomByName(String name) {
        for (Room room : rooms) {
            if (room.getName().equals(name)) {
                return room;
            }
        }
        return null;
    }

    public Map<Integer, Double> getDatePriceModifiers() {
        return datePriceModifiers;
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public void removeRoom(Room room) {
        rooms.remove(room);
    }

    public boolean removeRoomByName(String roomName) {
        Room roomToRemove = getRoomByName(roomName);
        if (roomToRemove != null && isRoomRemovable(roomToRemove)) {
            rooms.remove(roomToRemove);
            return true;
        }
        return false;
    }

    public boolean isRoomRemovable(Room room) {
        return room.isEmpty();
    }

    public boolean isPriceUpdateable() {
        for (Room room : rooms) {
            if (room.getReservations().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public double getEstimatedEarnings() {
        double totalEarnings = 0.0;
        for (Room room : rooms) {
            for (Reservation reservation : room.getReservations()) {
                totalEarnings += reservation.getTotalCost();
            }
        }
        return totalEarnings;
    }

    public int getAvailableRooms(int date) {
        int availableRooms = 0;
        for (Room room : rooms) {
            if (room.isAvailable(date, date + 1)) {
                availableRooms++;
            }
        }
        return availableRooms;
    }

    public int getBookedRooms(int date) {
        return getTotalRooms() - getAvailableRooms(date);
    }

    // Method to set a price modifier for a specific date
    public void setDatePriceModifier(int date, double priceRate) {
        if (date >= 1 && date <= 30 && priceRate >= 0.5 && priceRate <= 1.5) {
            datePriceModifiers.put(date, priceRate);
        } else {
            // Handle invalid date or price rate (e.g., throw an exception)
            System.err.println("Invalid date or price rate.");
        }
    }

    // Method to get the price modifier for a specific date
    public double getPriceModifierForDate(int date) {
        if (datePriceModifiers.containsKey(date)) {
            return datePriceModifiers.get(date);
        } else {
            // Handle case where no modifier exists for this date (e.g., return 1.0)
            return 1.0;
        }
    }

}