import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller class for managing hotel operations.
 * Handles actions triggered from the view and updates the model accordingly.
 * 
 * @author James Foo
 * @author Zami Diamante
 * @version 1.0
 */
public class HotelManagementController {

    private HotelManagementView view;
    private List<Hotel> hotels;

    /**
     * Constructor for HotelManagementController.
     * Initializes the view and hotel list, and sets up action listeners.
     * 
     * @param view the HotelManagementView instance
     */
    public HotelManagementController(HotelManagementView view) {
        this.view = view;
        this.hotels = new ArrayList<Hotel>();
        this.view.setCreateHotelButtonListener(new CreateHotelListener());
        this.view.setRemoveHotelButtonListener(new RemoveHotelListener());
        this.view.setViewHotelButtonListener(new ViewHotelListener());
        this.view.setManageHotelButtonListener(new ManageHotelListener());
        this.view.setSimulateBookingButtonListener(new SimulateBookingListener());
        displayHotelOptions();
    }

    /**
     * Listener class for creating a new hotel.
     */
    class CreateHotelListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            displayHotelOptions();
            String name = view.getUserInput("Enter Hotel Name:");
            if (name == null) {
                // User clicked 'X', do nothing
                return;
            } else if (name.isEmpty()) {
                view.showMessage("Hotel name cannot be empty.");
                return;
            }
            for (Hotel hotel : hotels) {
                if (hotel.getName().equals(name)) {
                    view.showMessage("Hotel with this name already exists.");
                    return;
                }
            }

            String priceInput = view.getUserInput("Enter Base Price (default 1299.00):");
            double basePrice;

            if (priceInput == null) {
                view.showMessage("Operation cancelled."); // User clicked 'X'
                return;
            } else if (priceInput.isEmpty()) {
                view.showMessage("No entered price. Using default price 1299.00.");
                basePrice = 1299.00;
            } else {
                try {
                    basePrice = Double.parseDouble(priceInput);
                    if (basePrice < 100) {
                        view.showMessage("Base Price should be >= 100. Using default price 1299.00.");
                        basePrice = 1299.00;
                    }
                } catch (NumberFormatException ex) {
                    view.showMessage("Invalid price. Using default price 1299.00.");
                    basePrice = 1299.00;
                }
            }

            Hotel newHotel = new Hotel(name, basePrice);

            hotels.add(newHotel);
            view.showMessage("Hotel successfully added.");

            // addRoom(newHotel);

            if (hotels.isEmpty()) {
                view.showMessage("No hotels added.");
                return;
            } else {
                displayHotelOptions();
            }
        }
    }

    /**
     * Listener class for removing an existing hotel.
     */
    class RemoveHotelListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (hotels.isEmpty()) {
                view.showMessage("No hotels to remove.");
                return;
            } else {
                displayHotelOptions();
            }

            String name = view.getUserInput("Enter hotel name to remove:");
            if (name == null) {
                view.showMessage("Operation cancelled.");
                return;
            }
            for (Hotel hotel : hotels) {
                if (hotel.getName().equals(name)) {
                    hotels.remove(hotel);
                    view.showMessage(name + " successfully removed.");
                    return;
                }
            }
            view.showMessage(name + " does not exist.");
        }
    }

    /**
     * Listener class for viewing the details of a hotel.
     */
    class ViewHotelListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (hotels.isEmpty()) {
                view.showMessage("No hotels found.");
                return;
            } else {
                displayHotelOptions();

                String name = view.getUserInput("Enter hotel name to view:");
                if (name == null) {
                    view.showMessage("Operation cancelled.");
                    return;
                }
                for (Hotel hotel : hotels) {
                    if (hotel.getName().equals(name)) {
                        viewHotel(hotel);
                        return;
                    }
                }
                view.showMessage(name + " does not exist.");
            }
        }
    }

    /**
     * Listener class for managing a hotel.
     */
    class ManageHotelListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (hotels.isEmpty()) {
                view.showMessage("No hotels found.");
                return;
            } else {
                displayHotelOptions();
                String name = view.getUserInput("Enter hotel name to manage:");
                if (name == null) {
                    view.showMessage("Operation cancelled.");
                    return;
                }
                for (Hotel hotel : hotels) {
                    if (hotel.getName().equals(name)) {
                        manageHotel(hotel);
                        return;
                    }
                }
                view.showMessage(name + " does not exist.");
            }
        }
    }

    /**
     * Listener class for simulating a booking.
     */
    class SimulateBookingListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (hotels.isEmpty()) {
                view.showMessage("No hotels to Book.");
                return;
            }

            // Display hotel details to the user
            displayHotelOptions();
            // Get hotel name from the user
            String hotelName = view.getUserInput("Enter Hotel Name:");
            // Display rooms of the selected hotel
            displayRooms(hotelName);
            // Get room name from the user
            String roomName = view.getUserInput("Enter Room Name:");
            // Find the hotel and room
            for (Hotel hotel : hotels) {
                if (hotel.getName().equals(hotelName)) {
                    Room selectedRoom = hotel.getRoomByName(roomName);
                    if (selectedRoom != null) {
                        // Display available dates for the selected room
                        displayAvailableDates(selectedRoom);
                        // Get guest name from the user
                        String guestName = view.getUserInput("Enter guest name:");
                        // Get check-in date from the user
                        int checkIn = Integer.parseInt(view.getUserInput("Enter check-in date:"));
                        // Get check-out date from the user
                        int checkOut = Integer.parseInt(view.getUserInput("Enter check-out date:"));
                        // Validate check-in and check-out dates
                        if (checkIn < 1 || checkIn > 30 || checkOut < 2 || checkOut > 31 || checkIn >= checkOut) {
                            view.showMessage("Invalid date range.");
                            return;
                        }
                        // Check availability and book the room
                        if (selectedRoom.isAvailable(checkIn, checkOut)) {
                            // Ask for discount code
                            String discountCode = view.getUserInput("Enter discount code (or press Enter to skip):");

                            // Create a reservation and add it to the room
                            Reservation reservation = new Reservation(guestName, checkIn, checkOut, roomName, hotel,
                                    selectedRoom);
                            double totalPrice = reservation.getTotalCost();
                            if (!discountCode.isEmpty()) {
                                if (reservation.isValidDiscountCode(discountCode)) {
                                    totalPrice = reservation.calculateDiscountedPrice(discountCode);
                                } else {
                                    if (discountCode.equals("STAY4_GET1"))
                                        view.showMessage("Discount code unapplicable: Not enough days for discount.");
                                    if (discountCode.equals("PAYDAY"))
                                        view.showMessage(
                                                "Discount code unapplicable: Reservation does not span the 15th or 30th.");
                                    view.showMessage("No valid discount applied.");
                                }
                            }
                            if (selectedRoom.addReservation(reservation)) {
                                view.showMessage("Booking Successful!" + "\n" + "Total Price: " + totalPrice);
                                return; // Exit the loop after successful booking
                            } else {
                                view.showMessage("Selected Room " + roomName + " encountered an error.");
                                return; // Exit the loop if the room is not available
                            }
                        } else {
                            view.showMessage("Selected Room " + roomName + " is not available for those dates.");
                            return;
                        }
                    } else {
                        view.showMessage("Room " + roomName + " not found in hotel " + hotelName);
                        return;
                    }
                }
            }
            view.showMessage("Hotel " + hotelName + " not found.");
        }
    }

    /**
     * Displays details of a specific hotel.
     * 
     * @param hotel the hotel to display details for
     */
    private void displayHotelDetails(Hotel hotel) {
        StringBuilder details = new StringBuilder();
        details.append("Hotel Name: ").append(hotel.getName()).append("\n");
        details.append("Base Price: ").append(hotel.getBasePrice()).append("\n");
        details.append("Estimated Earnings: ").append(hotel.getEstimatedEarnings()).append("\n");
        details.append("Rooms:\n");
        for (Room room : hotel.getRooms()) {
            details.append("  ").append(room.getName()).append(" - Earnings: ").append(room.getEarnings()).append("\n");
        }
        view.displayHotelDetails(details.toString());
    }

    /**
     * Manages the specified hotel by providing various options such as changing the
     * hotel name,
     * base price, adding or removing rooms, and setting date price modifiers.
     * 
     * @param hotel the hotel to be managed
     */
    private void manageHotel(Hotel hotel) {
        String[] options = { "Change Name", "Change Base Price", "Add Room", "Remove Room", "Remove Reservation",
                "Remove Hotel", "Date Price Modifier" };

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Create buttons for each management option and add them to the panel
        for (String option : options) {
            JButton button = new JButton(option);
            button.setActionCommand(option);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    handleOptionSelection(e.getActionCommand(), hotel);
                }
            });
            panel.add(button);
        }

        // Display the panel with management options in a dialog
        JOptionPane.showMessageDialog(view.getFrame(), panel, "Manage Hotel", JOptionPane.PLAIN_MESSAGE);
        displayHotelOptions();
    }

    /**
     * Handles the selection of different hotel management options and performs the
     * corresponding actions.
     * 
     * @param option the selected management option
     * @param hotel  the hotel to be managed
     */
    private void handleOptionSelection(String option, Hotel hotel) {
        switch (option) {
            case "Change Name":
                String newName = view.getUserInput("Enter new name:");
                hotel.setName(newName);
                view.showMessage("Hotel name updated.");
                break;
            case "Change Base Price":
                double newBasePrice;
                try {
                    newBasePrice = Double.parseDouble(view.getUserInput("Enter new base price:"));
                } catch (NumberFormatException ex) {
                    view.showMessage("Invalid price.");
                    return;
                }
                if (hotel.isPriceUpdateable()) {
                    hotel.setBasePrice(newBasePrice);
                    for (Room room : hotel.getRooms()) {
                        room.setPrice(newBasePrice);
                    }
                    view.showMessage("Base price updated.");
                } else {
                    view.showMessage("Cannot update base price. Some rooms are occupied.");
                }
                break;
            case "Add Room":
                // add room function ask the user to enter the room name and base price
                addRoom(hotel);
                displayHotelOptions();
                break;
            case "Remove Room":
                displayRooms(hotel.getName());

                // Ask for room name and check if it exists in the system
                String roomNameToRemove = view.getUserInput("Enter room name to remove:");
                if (hotel.removeRoomByName(roomNameToRemove)) {
                    view.showMessage("Room " + roomNameToRemove + " removed successfully.");
                } else {
                    view.showMessage("Room " + roomNameToRemove + " not found in this hotel.");
                }
                displayHotelOptions();
                break;
            case "Remove Reservation":
                displayRooms(hotel.getName());

                roomNameToRemove = view.getUserInput("Enter room name:");
                displayRoomDetails(hotel, roomNameToRemove);
                String guestName = view.getUserInput("Enter guest name:");
                Room room = hotel.getRoomByName(roomNameToRemove);
                if (room != null && room.removeReservation(guestName)) {
                    view.showMessage("Reservation removed successfully.");
                } else {
                    view.showMessage("Reservation for " + guestName + " not found.");
                }
                break;
            case "Remove Hotel":
                hotels.remove(hotel);
                view.showMessage("Hotel " + hotel.getName() + " removed.");
                break;
            case "Date Price Modifier":
                int date = Integer.parseInt(view.getUserInput("Enter date (1-30):"));
                double priceRate = Double.parseDouble(view.getUserInput("Enter price rate (0.5 - 1.5):"));
                hotel.setDatePriceModifier(date, priceRate);
                view.showMessage("Date price modifier updated.");
                displayHotelOptions();
                break;
        }
        displayHotelOptions();
    }

    /**
     * Displays options for viewing hotel information such as checking availability
     * on a specific date,
     * viewing room details, and viewing reservation details.
     * 
     * @param hotel the hotel to be viewed
     */
    private void viewHotel(Hotel hotel) {
        displayHotelDetails(hotel);
        String[] options = { "Check Availability on a Specific Date", "View Room Details", "View Reservation Details" };

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Create buttons for each viewing option and add them to the panel
        for (String option : options) {
            JButton button = new JButton(option);
            button.setActionCommand(option);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    handleViewOptionSelection(e.getActionCommand(), hotel);
                }
            });
            panel.add(button);
        }

        // Display the panel with viewing options in a dialog
        JOptionPane.showMessageDialog(view.getFrame(), panel, "Manage Hotel", JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Handles the selection of different viewing options and performs the
     * corresponding actions.
     * 
     * @param option the selected viewing option
     * @param hotel  the hotel to be viewed
     */
    private void handleViewOptionSelection(String option, Hotel hotel) {
        switch (option) {
            case "Check Availability on a Specific Date":
                checkAvailability(hotel);
                break;
            case "View Room Details":
                displayRooms(hotel.getName());
                String roomName = view.getUserInput("Enter room name:");
                displayRoomDetails(hotel, roomName);
                break;
            case "View Reservation Details":
                String guestName = view.getUserInput("Enter guest name:");
                displayReservationDetails(hotel, guestName);
                break;
        }
    }

    /**
     * Adds a new room to the specified hotel.
     * 
     * @param hotel the hotel to which the room will be added
     */
    private void addRoom(Hotel hotel) {
        if (hotel.getTotalRooms() >= 50) {
            view.showMessage("Maximum number of rooms reached.");
        } else {
            displayRooms(hotel.getName());

            // Ask for room name and check for duplicates
            String roomName = view.getUserInput("Enter room name:");
            while (hotel.checkDuplicateRoomName(roomName)) {
                roomName = view.getUserInput("Room name already exists. Please enter a different room name:");
            }

            // Ask for room type and validate user input
            String roomType = view.getUserInput("Enter room type (Standard, Deluxe, Executive):");
            while (!roomType.equalsIgnoreCase("Standard") && !roomType.equalsIgnoreCase("Deluxe")
                    && !roomType.equalsIgnoreCase("Executive")) {
                roomType = view.getUserInput("Invalid room type. Enter Standard, Deluxe, or Executive:");
            }

            // Create the new room object based on the type
            Room newRoom;
            if (roomType.equalsIgnoreCase("Standard")) {
                newRoom = new Room(roomName, hotel.getBasePrice());
            } else if (roomType.equalsIgnoreCase("Deluxe")) {
                newRoom = new RoomDeluxe(roomName, hotel.getBasePrice());
            } else {
                newRoom = new RoomExecutive(roomName, hotel.getBasePrice());
            }

            // Add the new room to the hotel
            hotel.addRoom(newRoom);
            view.showMessage("Room added.");
        }
    }

    /**
     * Displays the available hotel options with their details.
     */
    private void displayHotelOptions() {
        StringBuilder details = new StringBuilder();
        details.append("Available Hotels:\n");
        if (hotels.isEmpty()) {
            details.append("No hotels currently registered.\n");
        } else {
            for (Hotel hotel : hotels) {
                details.append(hotel.getName()).append(" | Rooms: ").append(hotel.getRooms().size()).append("\n");
            }
        }
        view.displayHotelDetails(details.toString());
    }

    /**
     * Displays the rooms of a specified hotel.
     * 
     * @param hotelName the name of the hotel whose rooms are to be displayed
     */
    private void displayRooms(String hotelName) {
        StringBuilder details = new StringBuilder();
        details.append("Viewing rooms of hotel ").append(hotelName).append(":\n");
        for (Hotel hotel : hotels) {
            if (hotel.getName().equals(hotelName)) {
                for (Room room : hotel.getRooms()) {
                    details.append(String.format("%-7s | ", room.getName()));
                    if (hotel.getRooms().indexOf(room) % 5 == 4) {
                        details.append("\n");
                    }
                }
            }
        }
        view.displayHotelDetails(details.toString());
    }

    /**
     * Displays the available dates for a specific room.
     * 
     * @param room the room whose availability is to be displayed
     */
    private void displayAvailableDates(Room room) {
        StringBuilder dates = new StringBuilder();
        dates.append("Available Dates for ").append(room.getName()).append(":\n");
        for (int date = 1; date <= 30; date++) {
            if (room.isAvailable(date, date + 1)) { // Check for single-day availability
                dates.append(date).append(", ");
            }
        }
        // Remove trailing comma and space
        if (dates.lastIndexOf(", ") == dates.length() - 2) {
            dates.delete(dates.length() - 2, dates.length());
        }
        view.displayInfo(dates.toString());
    }

    /**
     * Displays the details of a specified room in a hotel.
     * 
     * @param hotel    the hotel to which the room belongs
     * @param roomName the name of the room whose details are to be displayed
     */
    private void displayRoomDetails(Hotel hotel, String roomName) {
        Room room = hotel.getRoomByName(roomName);
        if (room != null) {
            StringBuilder details = new StringBuilder();
            details.append("Room Name: ").append(room.getName()).append("\n");
            details.append("Base Price: ").append(room.getPrice()).append("\n");
            details.append("Availability: ");
            for (int date = 1; date <= 30; date++) {
                if (room.isAvailable(date, date + 1)) {
                    details.append(date).append(", ");
                }
            }
            // Remove trailing comma and space
            if (details.lastIndexOf(", ") == details.length() - 2) {
                details.delete(details.length() - 2, details.length());
            }
            view.displayInfo(details.toString());
        } else {
            view.showMessage("Room not found in this hotel.");
        }
    }

    /**
     * Displays the details of a reservation made by a specific guest.
     * 
     * @param hotel     the hotel to which the reservation belongs
     * @param guestName the name of the guest whose reservation details are to be
     *                  displayed
     */
    private void displayReservationDetails(Hotel hotel, String guestName) {
        for (Room room : hotel.getRooms()) {
            for (Reservation reservation : room.getReservations()) {
                if (reservation.getGuestName().equals(guestName)) {
                    StringBuilder details = new StringBuilder();
                    details.append("Guest Name: ").append(reservation.getGuestName()).append("\n");
                    details.append("Room Name: ").append(reservation.getRoomName()).append("\n");
                    details.append("Check-in: ").append(reservation.getCheckIn()).append("\n");
                    details.append("Check-out: ").append(reservation.getCheckOut()).append("\n");
                    details.append("Total Price: ").append(reservation.getTotalCost()).append("\n");
                    view.displayInfo(details.toString());
                    return; // Found the reservation, no need to search further
                }
            }
        }
        view.showMessage("Reservation not found for guest: " + guestName);
    }

    /**
     * Checks the availability of rooms in a specified hotel on a specific date.
     * 
     * @param hotel the hotel to be checked for availability
     */
    private void checkAvailability(Hotel hotel) {
        if (hotel == null) {
            view.showMessage("Please select a hotel first.");
            return;
        }
        // Get the date from the user (implement using Swing UI)
        int date = Integer.parseInt(view.getUserInput("Enter date (1-30):"));
        if (date < 1 || date > 30) {
            view.showMessage("Invalid date. Please enter a date between 1 and 30.");
            return;
        }
        int availableRooms = 0;
        int bookedRooms = 0;
        for (Room room : hotel.getRooms()) {
            if (room.isAvailable(date, date + 1)) {
                availableRooms++;
            } else {
                bookedRooms++;
            }
        }

        view.displayInfo("\n");
        view.displayInfo("Available Rooms: " + availableRooms);
        view.displayInfo("Booked Rooms: " + bookedRooms);
    }

}