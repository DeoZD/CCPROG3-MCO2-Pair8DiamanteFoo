public class RoomDeluxe extends Room {
  public RoomDeluxe (String name, double basePrice) {
        super(name + " (DX)", basePrice * 1.2); // increases base price by 20%
        setRoomType("Deluxe");
  }
}