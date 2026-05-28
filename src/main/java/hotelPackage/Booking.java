package hotelPackage;

import java.time.LocalDate;

public class Booking {
    private Room room;
    private Customer customer;
    private int nights;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    public Booking(Room room, Customer customer, int nights) {
        this.room = room;
        this.customer = customer;
        this.nights = nights;
        this.checkInDate = null;
        this.checkOutDate = null;
    }

    public Booking(Customer customer, Room room, LocalDate checkInDate, LocalDate checkOutDate) {
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.nights = (int) (checkOutDate.toEpochDay() - checkInDate.toEpochDay());
        if (this.nights < 1) this.nights = 1;  // Minimum 1 night
    }

    public double getTotalCost() {
        return room.calculateCost(nights);
    }

    /**
     * Process payment for the booking, returns true if successful.
     */
    public boolean processPayment() {
        try {
            double amount = getTotalCost();
            if (amount <= 0) {
                throw new IllegalArgumentException("Invalid amount.");
            }

            boolean success = customer.makePayment(amount);
            if (!success) {
                throw new IllegalStateException("Payment failed.");
            }
            return success;
        } catch (Exception e) {
            System.out.println("Payment failed");
            return false;
        }
    }

    public Room getRoom() {
        return room;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getNights() {
        return nights;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "room=" + room +
                ", customer=" + customer.getName() +
                ", nights=" + nights +
                ", checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate +
                '}';
    }
}
