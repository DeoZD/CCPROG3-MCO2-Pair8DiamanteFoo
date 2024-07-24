
public class Main {
    public static void main(String[] args) {
        HotelManagementView view = new HotelManagementView();
        new HotelManagementController(view);
        view.showFrame();
    }
}