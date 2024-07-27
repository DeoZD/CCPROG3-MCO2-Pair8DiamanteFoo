import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class HotelManagementController {

    private HotelManagementView view;
    private List<Hotel> hotels;

    public HotelManagementController(HotelManagementView view) {
        this.view = view;
        this.hotels = new ArrayList<Hotel>();
        this.view.setCreateHotelButtonListener(new CreateHotelListener());
        this.view.setRemoveHotelButtonListener(new RemoveHotelListener());
        this.view.setViewHotelButtonListener(new ViewHotelListener());
        this.view.setManageHotelButtonListener(new ManageHotelListener());
        this.view.setSimulateBookingButtonListener(new SimulateBookingListener());
    }

    class CreateHotelListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = view.getUserInput("Enter Hotel Name:");
            if (name == null || name.trim().isEmpty()) {
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

            if (priceInput.isEmpty()) {
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

            hotels.add(new Hotel(name, basePrice));
            view.showMessage("Hotel successfully added.");

            if (hotels.isEmpty()) {
                view.showMessage("No hotels added.");
                return;
            } else {
                displayHotelOptions();
            }
        }
    }

    class RemoveHotelListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (hotels.isEmpty()) {
                view.showMessage("No hotels to remove.");
                return;
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
                        displayHotelDetails(hotel);
                        viewHotel(hotel);
                        return;
                    }
                }
                view.showMessage(name + " does not exist.");
            }
        }
    }

    class ManageHotelListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (hotels.isEmpty()) {
                view.showMessage("No hotels found.");
                return;
            } else {
                displayHotelOptions();
                String name = view.getUserInput("Enter hotel name to manage:");
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
                            Reservation reservation = new Reservation(guestName, checkIn, checkOut, roomName,
                                    hotel.getBasePrice(), hotel);
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
                                    view.showMessage("no valid Discount applied.");
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

    // Helper methods

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

    private void manageHotel(Hotel hotel) {
        String[] options = { "Change Name", "Change Base Price", "Add Room", "Remove Room", "Remove Reservation",
                "Remove Hotel", "Date Price Modifier" };
        int choice = JOptionPane.showOptionDialog(null, "Select an option:", "Manage Hotel",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        switch (choice) {
            case 0:
                String newName = view.getUserInput("Enter new name:");
                hotel.setName(newName);
                view.showMessage("Hotel name updated.");
                break;
            case 1:
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
            case 2: // add room
                if (hotel.getTotalRooms() > 50) {
                    view.showMessage("Maximum number of rooms reached.");
                } else {
                    String roomName = view.getUserInput("Enter room name:");
                    // Ask for the room type
                    String roomType = view.getUserInput("Enter room type (Standard, Deluxe, Executive):");
                    while (!roomType.equalsIgnoreCase("Standard") && !roomType.equalsIgnoreCase("Deluxe")
                            && !roomType.equalsIgnoreCase("Executive")) {
                        roomType = view.getUserInput("Invalid room type. Enter Standard, Deluxe, or Executive:");
                    }                        // Create the appropriate room based on the type
                   Room newRoom;
                    if (roomType.equalsIgnoreCase("Standard")) {
                        newRoom = new Room(roomName, hotel.getBasePrice());
                    } else if (roomType.equalsIgnoreCase("Deluxe")) {
                        newRoom = new RoomDeluxe(roomName, hotel.getBasePrice());
                    } else {
                        newRoom = new RoomExecutive(roomName, hotel.getBasePrice());
                    }
                    hotel.addRoom(newRoom);
                    view.showMessage("Room added.");
                }
                break;
            case 3:
                String roomNameToRemove = view.getUserInput("Enter room name to remove:");
                if (hotel.removeRoomByName(roomNameToRemove)) {
                    view.showMessage("Room " + roomNameToRemove + " removed successfully.");
                } else {
                    view.showMessage("Room " + roomNameToRemove + " not found in this hotel.");
                }
                break;
            case 4:
                roomNameToRemove = view.getUserInput("Enter room name:");
                String guestName = view.getUserInput("Enter guest name:");
                Room room = hotel.getRoomByName(roomNameToRemove);
                if (room != null && room.removeReservation(guestName)) {
                    view.showMessage("Reservation removed successfully.");
                } else {
                    view.showMessage("Reservation for " + guestName + " not found.");
                }
                break;
            case 5:
                hotels.remove(hotel);
                view.showMessage("Hotel " + hotel.getName() + " removed.");
                break;
            case 6: // Add Date Price Modifier
                int date = Integer.parseInt(view.getUserInput("Enter date (1-30):"));
                double priceRate = Double.parseDouble(view.getUserInput("Enter price rate (0.5 - 1.5):"));
                hotel.setDatePriceModifier(date, priceRate);
                view.showMessage("Date price modifier updated.");
                break;
        }
    }

    private void viewHotel(Hotel hotel) {
        String[] options = { "Check Availability on a Specific Date", "View Room Details", "View Reservation Details",
                "Troubleshoot", "Go Back" };
        int choice = JOptionPane.showOptionDialog(null, "Select an option:", "View Hotel",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        switch (choice) {
            case 0:
                checkAvailability(hotel);
                break;
            case 1:
                String roomName = view.getUserInput("Enter room name:");
                displayRoomDetails(hotel, roomName);
                break;
            case 2:
                String guestName = view.getUserInput("Enter guest name:");
                displayReservationDetails(hotel, guestName);
                break;
            /*
            case 3: // troubleshooting reservations
                String roomNameToTroubleshoot = view.getUserInput("Enter room name to troubleshoot:");
                displayRoomReservations(hotel.getRoomByName(roomNameToTroubleshoot));
                break;
            */
        }
    }
/*
    // for troubleshooting
    private void displayRoomReservations(Room room) {
        if (room != null) {
            StringBuilder details = new StringBuilder();
            details.append("Reservations for Room ").append(room.getName()).append(":\n");
            if (room.getReservations().isEmpty()) {
                details.append("  No reservations found.\n");
            } else {
                for (Reservation reservation : room.getReservations()) {
                    details.append("  Guest: ").append(reservation.getGuestName()).append("\n");
                    details.append("  Check-in: ").append(reservation.getCheckIn()).append("\n");
                    details.append("  Check-out: ").append(reservation.getCheckOut()).append("\n");
                    details.append("  Total Price: ").append(reservation.getTotalCost()).append("\n\n");
                }
            }
            view.displayInfo(details.toString());
        } else {
            view.showMessage("Room not found.");
        }
    }
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

    private void handleRoomSelection() {
        String selectedRoomName = view.getSelectedHotelOption();
        // ... (Use the selectedRoomName to access the corresponding room object)
    }

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