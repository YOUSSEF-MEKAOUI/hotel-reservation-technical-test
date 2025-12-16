package hotel;

import hotel.enums.RoomType;
import hotel.service.Service;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Service service = new Service();

        // Rooms
        service.setRoom(1, RoomType.STANDARD_SUITE, 1000);
        service.setRoom(2, RoomType.JUNIOR_SUITE, 2000);
        service.setRoom(3, RoomType.MASTER_SUITE, 3000);

        // Users
        service.setUser(1, 5000);
        service.setUser(2, 10000);

        try {
            service.bookRoom(1, 2, LocalDate.of(2026, 6, 30), LocalDate.of(2026, 7, 7));
        } catch (Exception e) {
            System.out.println("Booking 1 failed: " + e.getMessage());
        }

        try {
            service.bookRoom(1, 2, LocalDate.of(2026, 7, 7), LocalDate.of(2026, 6, 30));
        } catch (Exception e) {
            System.out.println("Booking 2 failed: " + e.getMessage());
        }

        try {
            service.bookRoom(1, 1, LocalDate.of(2026, 7, 7), LocalDate.of(2026, 7, 8));
        } catch (Exception e) {
            System.out.println("Booking 3 failed: " + e.getMessage());
        }

        try {
            service.bookRoom(2, 1, LocalDate.of(2026, 7, 7), LocalDate.of(2026, 7, 9));
        } catch (Exception e) {
            System.out.println("Booking 4 failed: " + e.getMessage());
        }

        try {
            service.bookRoom(2, 3, LocalDate.of(2026, 7, 7), LocalDate.of(2026, 7, 8));
        } catch (Exception e) {
            System.out.println("Booking 5 failed: " + e.getMessage());
        }

        // Update room without affecting previous bookings
        service.setRoom(1, RoomType.MASTER_SUITE, 10000);

        // Print all data
        service.printAll();
        System.out.println();
        service.printAllUsers();
    }
}
