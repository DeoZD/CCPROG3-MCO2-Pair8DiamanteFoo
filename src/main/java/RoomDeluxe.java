/**
 * The RoomDeluxe class represents a deluxe room in the hotel. It extends the
 * Room class,
 * applying a price modifier and setting the room type to "Deluxe".
 * 
 * @author James Foo
 * @author Zami Diamante
 * @version 1.0
 */
public class RoomDeluxe extends Room {
    /**
     * Constructor for creating a new deluxe room.
     *
     * @param name      the name of the room
     * @param basePrice the base price of the room before applying the deluxe
     *                  modifier
     */
    public RoomDeluxe(String name, double basePrice) {
        super(name + " (DX)", basePrice * 1.2); // Increase base price by 20%
        setRoomType("Deluxe"); // Set room type to "Deluxe"
    }
}