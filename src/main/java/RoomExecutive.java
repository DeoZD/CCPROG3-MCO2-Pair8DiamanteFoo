public class RoomExecutive extends Room {
  public RoomExecutive (String name, double basePrice) {
        super(name + " (EC)", basePrice * 1.35); // increases base price by 35%
        setRoomType("Executive");
  }
}