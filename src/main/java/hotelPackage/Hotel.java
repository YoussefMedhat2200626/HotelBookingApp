package hotelPackage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Hotel  {
    private String hotelName;
    private final List<Room> rooms;
    private final List<Booking> bookings;

    public String getHotelName() {
        return hotelName;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public Hotel(String hotelName) {
        this.hotelName = hotelName;
        this.rooms = new ArrayList<>();
        this.bookings = new ArrayList<>();
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public void showAllRooms() {
        for (Room room : rooms) {
            System.out.println(room);
        }
    }

    public <T extends Comparable<T>> void sortList(List<T> list) {
        Collections.sort(list);
    }






    public boolean bookRoom(int roomNumber, Customer customer, int nights) {
        boolean bookingSuccessful = false;
        try {
            if (nights <= 0) {
                throw new IllegalStateException("Number of nights must be positive.");
            }

            Room room = findRoomByNumber(roomNumber);
            if (room == null) {
                throw new IllegalStateException("Room " + roomNumber + " not found.");
            }

            if (!room.isAvailable()) {
                throw new IllegalStateException("Room " + roomNumber + " is not available.");
            }

            Booking booking = new Booking(room, customer, nights);
            if (!booking.processPayment()) {
                throw new IllegalStateException("Payment failed for room " + roomNumber + ".");
            }

            bookings.add(booking);
            customer.addBooking(booking);
            room.book();
            bookingSuccessful = true;
        } catch (IllegalStateException e) {
            System.out.println("Booking failed");
        } catch (Exception e) {
            System.out.println("An unexpected error occurred ");
        } finally {
            if (bookingSuccessful) {
                System.out.println("Room " + roomNumber + " booked successfully.");
            } else {
                System.out.println("Failed to complete booking.");
            }
        }
        return bookingSuccessful;
    }

    public Room findRoomByNumber(int roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                return room;
            }
        }
        return null;
    }


    public static void main(String[] args) {
        Hotel hotel = new Hotel("Grand Hotel");

        // Add rooms
        hotel.addRoom(new singleRoom(101, 100.0));
        hotel.addRoom(new doubleRoom(102, 300.0));
        hotel.addRoom(new Suite(103, 250.0, true));

        // Create bank accounts
        BankAccount youssef = new BankAccount(1000.0);
        BankAccount mohamed = new BankAccount(2000.0);

        // Sort rooms by price using generic method
        List<Room> sortableRooms = new ArrayList<>(hotel.getRooms()); //by making a copy
        hotel.sortList(sortableRooms);

        System.out.println("\nRooms after sorting by price:");
        for (Room room : sortableRooms) {
            System.out.println(room);
        }
        System.out.println();
        // Create users
        Customer Youssef = new Customer("Youssef Medhat", "Youssef.doe@example.com", youssef);
        Admin Mohamed = new Admin("Mohamed salah", "Mohamed.smith@example.com");

        // Display user info
        Youssef.displayInfo();
        Mohamed.displayInfo();

        // Show all rooms before booking
        System.out.println("\nAvailable Rooms:");
        hotel.showAllRooms();

        // Try booking a room
        System.out.println("\nBooking room 102 for John Doe (3 nights)...");
        boolean bookingSuccess = hotel.bookRoom(102, Youssef, 3);
        System.out.println(bookingSuccess ? "Booking successful!" : "Booking failed.");

        // Show updated info
        System.out.println("\nJohn's updated info:");
        Youssef.displayInfo();

        System.out.println("\nRooms after booking:");
        hotel.showAllRooms();

    }

}
