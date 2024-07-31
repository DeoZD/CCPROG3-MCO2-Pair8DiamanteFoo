import java.util.ArrayList;
import java.util.List;

/**
 * The Room class represents a room in the hotel. It contains information about
 * the room's
 * name, price, reservations, and room type. It provides methods to manage
 * reservations
 * and check room availability.
 * 
 * @author James Foo
 * @author Zami Diamante
 * @version 1.0
 */
public class Room {
    private String name;
    private double price;
    private List<Reservation> reservations;
    private String roomType;

    /**
     * Constructor for creating a new Room.
     *
     * @param name  the name of the room
     * @param price the price of the room
     */
    public Room(String name, double price) {
        this.name = name;
        this.price = price;
        this.reservations = new ArrayList<>();
        this.roomType = "Standard";
    }

    /**
     * Gets the name of the room.
     *
     * @return the room name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the price of the room.
     *
     * @return the room price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Gets the list of reservations for the room.
     *
     * @return the list of reservations
     */
    public List<Reservation> getReservations() {
        return reservations;
    }

    /**
     * Sets the price of the room.
     *
     * @param price the new price of the room
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Gets the type of the room.
     *
     * @return the room type
     */
    public String getRoomType() {
        return roomType;
    }

    /**
     * Sets the type of the room.
     *
     * @param roomType the new room type
     */
    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    /**
     * Checks if the room is available for a given check-in and check-out period.
     *
     * @param checkIn  the check-in date
     * @param checkOut the check-out date
     * @return true if the room is available, false otherwise
     */
    public boolean isAvailable(int checkIn, int checkOut) {
        for (Reservation reservation : reservations) {
            // Check for any overlapping reservations
            if ((checkIn >= reservation.getCheckIn() && checkIn < reservation.getCheckOut()) ||
                    (checkOut > reservation.getCheckIn() && checkOut <= reservation.getCheckOut())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Adds a reservation to the room if it is available.
     *
     * @param reservation the reservation to be added
     * @return true if the reservation was successfully added, false otherwise
     */
    public boolean addReservation(Reservation reservation) {
        if (reservation == null) {
            return false;
        }

        if (isAvailable(reservation.getCheckIn(), reservation.getCheckOut())) {
            reservations.add(reservation);
            return true; // Reservation successfully added
        } else {
            return false; // Reservation could not be added due to unavailability
        }
    }

    /**
     * Removes a reservation for a given guest name.
     *
     * @param guestName the name of the guest
     * @return true if the reservation was successfully removed, false otherwise
     */
    public boolean removeReservation(String guestName) {
        return reservations.removeIf(reservation -> reservation.getGuestName().equals(guestName));
    }

    /**
     * Checks if the room has no reservations.
     *
     * @return true if the room is empty, false otherwise
     */
    public boolean isEmpty() {
        return reservations.isEmpty();
    }

    /**
     * Calculates the total earnings from all reservations.
     *
     * @return the total earnings
     */
    public double getEarnings() {
        return reservations.stream().mapToDouble(Reservation::getTotalCost).sum();
    }
}
