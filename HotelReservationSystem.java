import java.util.ArrayList;
import java.util.Scanner;

class Room {
    int roomNumber;
    double price;
    boolean available;

    Room(int roomNumber, double price) {
        this.roomNumber = roomNumber;
        this.price = price;
        this.available = true;
    }
}

class Reservation {
    int bookingId;
    String guestName;
    int roomNumber;
    int nights;
    double totalCost;

    Reservation(int bookingId, String guestName,
                int roomNumber, int nights, double totalCost) {
        this.bookingId = bookingId;
        this.guestName = guestName;
        this.roomNumber = roomNumber;
        this.nights = nights;
        this.totalCost = totalCost;
    }
}

public class HotelReservationSystem {

    static Scanner sc = new Scanner(System.in);

    static ArrayList<Room> rooms = new ArrayList<>();
    static ArrayList<Reservation> bookings = new ArrayList<>();

    static int nextBookingId = 1;

    public static void main(String[] args) {

        rooms.add(new Room(101, 1500));
        rooms.add(new Room(102, 1500));
        rooms.add(new Room(103, 2000));
        rooms.add(new Room(104, 2500));
        rooms.add(new Room(105, 3000));

        int choice;

        do {
            System.out.println("\n===== HOTEL RESERVATION SYSTEM =====");
            System.out.println("1. View Available Rooms");
            System.out.println("2. Book a Room");
            System.out.println("3. View Booking Details");
            System.out.println("4. Cancel Reservation");
            System.out.println("5. Exit");

            System.out.print("Enter your choice: ");

            try {
                choice = Integer.parseInt(sc.nextLine());

                switch (choice) {
                    case 1:
                        viewRooms();
                        break;

                    case 2:
                        bookRoom();
                        break;

                    case 3:
                        viewBookings();
                        break;

                    case 4:
                        cancelBooking();
                        break;

                    case 5:
                        System.out.println("Thank you for visiting!");
                        break;

                    default:
                        System.out.println("Invalid choice!");
                }

            } catch (NumberFormatException e) {
                choice = 0;
                System.out.println("Please enter a valid number.");
            }

        } while (choice != 5);

        sc.close();
    }

    static void viewRooms() {

        System.out.println("\n--- AVAILABLE ROOMS ---");

        for (Room room : rooms) {
            System.out.println(
                "Room: " + room.roomNumber +
                " | Price per night: Rs." + room.price +
                " | Status: " +
                (room.available ? "Available" : "Booked")
            );
        }
    }

    static void bookRoom() {

        viewRooms();

        System.out.print("\nEnter room number: ");
        int roomNumber;

        try {
            roomNumber = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid room number.");
            return;
        }

        Room selectedRoom = null;

        for (Room room : rooms) {
            if (room.roomNumber == roomNumber) {
                selectedRoom = room;
                break;
            }
        }

        if (selectedRoom == null || !selectedRoom.available) {
            System.out.println("Room is not available!");
            return;
        }

        System.out.print("Enter guest name: ");
        String name = sc.nextLine();

        System.out.print("Enter number of nights: ");

        int nights;

        try {
            nights = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number of nights.");
            return;
        }

        if (nights <= 0 || name.trim().isEmpty()) {
            System.out.println("Please enter valid booking details.");
            return;
        }

        double total = selectedRoom.price * nights;

        Reservation booking = new Reservation(
            nextBookingId++, name, roomNumber, nights, total
        );

        bookings.add(booking);
        selectedRoom.available = false;

        System.out.println("\nBooking successful!");
        System.out.println("Booking ID: " + booking.bookingId);
        System.out.println("Total cost: Rs." + total);
    }

    static void viewBookings() {

        System.out.println("\n--- BOOKING DETAILS ---");

        if (bookings.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        for (Reservation booking : bookings) {
            System.out.println("Booking ID: " + booking.bookingId);
            System.out.println("Guest Name: " + booking.guestName);
            System.out.println("Room Number: " + booking.roomNumber);
            System.out.println("Nights: " + booking.nights);
            System.out.println("Total Cost: Rs." + booking.totalCost);
            System.out.println("------------------------");
        }
    }

    static void cancelBooking() {

        System.out.print("\nEnter Booking ID to cancel: ");

        int id;

        try {
            id = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid booking ID.");
            return;
        }

        Reservation found = null;

        for (Reservation booking : bookings) {
            if (booking.bookingId == id) {
                found = booking;
                break;
            }
        }

        if (found == null) {
            System.out.println("Booking not found!");
            return;
        }

        for (Room room : rooms) {
            if (room.roomNumber == found.roomNumber) {
                room.available = true;
                break;
            }
        }

        bookings.remove(found);

        System.out.println("Reservation cancelled successfully!");
    }
}

