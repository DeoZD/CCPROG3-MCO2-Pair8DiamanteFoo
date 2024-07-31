/**
 * The RoomExecutive class represents an executive room in the hotel. It extends
 * the Room class,
 * applying a price modifier and setting the room type to "Executive".
 * 
 * @author James Foo
 * @author Zami Diamante
 * @version 1.0
 */
public class RoomExecutive extends Room {
    /**
     * Constructor for creating a new executive room.
     *
     * @param name      the name of the room
     * @param basePrice the base price of the room before applying the executive
     *                  modifier
     */
    public RoomExecutive(String name, double basePrice) {
        super(name + " (EC)", basePrice * 1.35); // Increase base price by 35%
        setRoomType("Executive"); // Set room type to "Executive"
    }
}