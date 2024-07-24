import java.util.ArrayList;
import java.util.List;

public class Room {
    private String name;
    private double basePrice;
    private List<Reservation> reservations;
    private String roomType;

    public Room(String name, double basePrice) {
        this.name = name;
        this.basePrice = basePrice;
        this.reservations = new ArrayList<>();
        this.roomType = "Standard";
    }

    public String getName() {
        return name;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public String getroomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public boolean isAvailable(int checkIn, int checkOut) {
        for (Reservation reservation : reservations) {
            if ((checkIn >= reservation.getCheckIn() && checkIn < reservation.getCheckOut()) ||
                    (checkOut > reservation.getCheckIn() && checkOut <= reservation.getCheckOut())) {
                return false;
            }
        }
        return true;
    }

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

    public boolean removeReservation(String guestName) {
        return reservations.removeIf(reservation -> reservation.getGuestName().equals(guestName));
    }

    public boolean isEmpty() {
        return reservations.isEmpty();
    }

    public double getEarnings() {
        return reservations.stream().mapToDouble(Reservation::getTotalCost).sum();
    }

}
