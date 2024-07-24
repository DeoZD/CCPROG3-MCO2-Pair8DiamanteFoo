public class Reservation {
    private String guestName;
    private int checkIn;
    private int checkOut;
    private String roomName;
    private double basePrice;
    private double totalCost;
    private Hotel hotel;
    // private double discount;

    public Reservation(String guestName, int checkIn, int checkOut, String roomName, double basePrice, Hotel hotel) {
        this.guestName = guestName;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.roomName = roomName;
        this.basePrice = basePrice;
        this.hotel = hotel;
        this.totalCost = calculateTotalCost(checkIn, checkOut, basePrice);
    }

    public boolean isValidDiscountCode(String code) {
        if (code.equals("_WORK_HERE")) {
            return true; // Always valid
        } else if (code.equals("STAY4_GET1")) {
            return (checkOut - checkIn) >= 5; // Valid if reservation is 5 days or more
        } else if (code.equals("PAYDAY")) {
            return (checkIn == 15 && checkOut > 15) || (checkIn < 30 && checkOut == 30);
            // Valid if check-in is 15 and check-out is after 15 OR check-in is before 30
            // and check-out is 30
        } else {
            return false; // Invalid code
        }
    }

    public double calculateDiscountedPrice(String code) {
        double discountedPrice = this.getTotalCost(); // Original price
        if (isValidDiscountCode(code)) {
            if (code.equals("_WORK_HERE")) {
                discountedPrice *= 0.9; // 10% discount
            } else if (code.equals("STAY4_GET1")) {
                discountedPrice -= this.getBasePrice(); // First day free
            } else if (code.equals("PAYDAY")) {
                discountedPrice *= 0.93; // 7% discount
            }
        }
        return discountedPrice;
    }

    // Calculate total cost considering date price modifiers
    private double calculateTotalCost(int checkIn, int checkOut, double basePrice) {
        double totalCost = 0;
        for (int date = checkIn; date < checkOut; date++) {
            double priceModifier = hotel.getPriceModifierForDate(date); // Get modifier for each date
            totalCost += basePrice * priceModifier;
        }
        return totalCost;
    }

    public String getGuestName() {
        return guestName;
    }

    public int getCheckIn() {
        return checkIn;
    }

    public int getCheckOut() {
        return checkOut;
    }

    public double getTotalCost() {
        totalCost = (checkOut - checkIn) * basePrice;
        return totalCost;
    }

    public String getRoomName() {
        return roomName;
    }

    public double getBasePrice() {
        return basePrice;
    }

}
