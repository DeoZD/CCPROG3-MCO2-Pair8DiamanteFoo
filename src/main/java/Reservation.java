/**
 * The Reservation class represents a reservation made by a guest at a hotel.
 * It contains information about the guest, the room, check-in and check-out
 * dates,
 * the base price of the room, and the total cost of the reservation.
 * 
 * @author James Foo
 * @author Zami Diamante
 * @version 1.0
 */
public class Reservation {
    private String guestName;
    private int checkIn;
    private int checkOut;
    private String roomName;
    private double basePrice;
    private double totalCost;
    private Hotel hotel;
    private Room selectedRoom;

    /**
     * Constructor for creating a new Reservation.
     *
     * @param guestName the name of the guest
     * @param checkIn   the check-in date
     * @param checkOut  the check-out date
     * @param roomName  the name of the room
     * @param hotel     the hotel where the reservation is made
     * @param room      the selected room
     */
    public Reservation(String guestName, int checkIn, int checkOut, String roomName, Hotel hotel,
            Room room) {
        this.guestName = guestName;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.roomName = roomName;
        this.selectedRoom = room;
        this.basePrice = selectedRoom.getPrice();
        this.hotel = hotel;
        this.totalCost = calculateTotalCost(checkIn, checkOut, basePrice); // Calculate the total cost of the
                                                                           // reservation
    }

    /**
     * Checks if the provided discount code is valid for the reservation.
     *
     * @param code the discount code to check
     * @return true if the discount code is valid, false otherwise
     */
    public boolean isValidDiscountCode(String code) {
        if (code.equals("_WORK_HERE")) {
            return true; // Always valid
        } else if (code.equals("STAY4_GET1")) {
            return (checkOut - checkIn) >= 5; // Valid if reservation is 5 days or more
        } else if (code.equals("PAYDAY")) {
            return (checkIn <= 15 && checkOut > 15) || (checkIn <= 30 && checkOut > 30);
            // Valid if check-in spans 15 or 30 but is NOT check out.
        } else {
            return false; // Invalid code
        }
    }

    /**
     * Calculates the discounted price based on the provided discount code.
     *
     * @param code the discount code to apply
     * @return the discounted price
     */
    public double calculateDiscountedPrice(String code) {
        double discountedPrice = this.getTotalCost(); // Original price
        if (isValidDiscountCode(code)) {
            if (code.equals("I_WORK_HERE")) {
                discountedPrice *= 0.9; // 10% discount
            } else if (code.equals("STAY4_GET1")) {
                discountedPrice -= this.getBasePrice(); // First day free
            } else if (code.equals("PAYDAY")) {
                discountedPrice *= 0.93; // 7% discount
            }
        }
        return discountedPrice;
    }

    /**
     * Calculates the total cost of the reservation considering date price
     * modifiers.
     *
     * @param checkIn   the check-in date
     * @param checkOut  the check-out date
     * @param basePrice the base price of the room
     * @return the total cost of the reservation
     */
    private double calculateTotalCost(int checkIn, int checkOut, double basePrice) {
        double totalCost = 0;
        for (int date = checkIn; date < checkOut; date++) {
            double priceModifier = hotel.getPriceModifierForDate(date); // Get modifier for each date
            totalCost += basePrice * priceModifier;
        }
        return totalCost;
    }

    /**
     * Gets the name of the guest.
     *
     * @return the guest's name
     */
    public String getGuestName() {
        return guestName;
    }

    /**
     * Gets the check-in date.
     *
     * @return the check-in date
     */
    public int getCheckIn() {
        return checkIn;
    }

    /**
     * Gets the check-out date.
     *
     * @return the check-out date
     */
    public int getCheckOut() {
        return checkOut;
    }

    /**
     * Gets the total cost of the reservation.
     *
     * @return the total cost
     */
    public double getTotalCost() {
        return totalCost;
    }

    /**
     * Gets the name of the room.
     *
     * @return the room name
     */
    public String getRoomName() {
        return roomName;
    }

    /**
     * Gets the base price of the room.
     *
     * @return the base price
     */
    public double getBasePrice() {
        return basePrice;
    }
}
