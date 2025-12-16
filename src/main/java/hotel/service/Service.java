package hotel.service;

import hotel.entities.Booking;
import hotel.entities.Room;
import hotel.entities.User;
import hotel.enums.RoomType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Service {
    private final List<Room> rooms = new ArrayList<>();
    private final List<User> users = new ArrayList<>();
    private final List<Booking> bookings = new ArrayList<>();



    public synchronized void setRoom(int roomNumber, RoomType roomType, int pricePerNight) {
        if (pricePerNight <= 0)
            throw new IllegalArgumentException("Price must be positive");

        boolean exists = rooms.stream()
                .anyMatch(r -> r.roomNumber() == roomNumber);

        if (!exists) {
            rooms.add(new Room(roomNumber, roomType, pricePerNight));
        }
    }

    public synchronized void setUser(int userId, int balance) {
        if (balance < 0)
            throw new IllegalArgumentException("Balance cannot be negative");

        boolean exists = users.stream().anyMatch(u -> u.userId() == userId);

        if (!exists) {
            users.add(new User(userId, balance));
        }
    }

    public synchronized void bookRoom(int userId, int roomNumber, LocalDate checkIn, LocalDate checkOut) {
        Objects.requireNonNull(checkIn, "checkIn cannot be null");
        Objects.requireNonNull(checkOut, "checkOut cannot be null");

        User user = users.stream()
                .filter(u -> u.userId() == userId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        Room room = rooms.stream()
                .filter(r -> r.roomNumber() == roomNumber)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Room not found: " + roomNumber));

        if (!checkOut.isAfter(checkIn)) {
            throw new IllegalArgumentException("checkout must be after checkIn");
        }

        boolean available = bookings.stream()
                .filter(b -> b.room().roomNumber() == roomNumber)
                .noneMatch(b -> overlaps(b.checkIn(), b.checkOut(), checkIn, checkOut));

        if (!available) {
            throw new IllegalArgumentException("Room not available during requested period");
        }

        long nights = checkOut.toEpochDay() - checkIn.toEpochDay();
        int totalPrice = (int) (nights * room.pricePerNight());

        if (user.balance() < totalPrice) {
            throw new IllegalArgumentException("User balance insufficient");
        }

        user.debit(totalPrice);

        bookings.add(new Booking(user, room, checkIn, checkOut));
    }

    private boolean overlaps(LocalDate start1, LocalDate end1, LocalDate start2, LocalDate end2) {
        return !start1.isAfter(end2.minusDays(1)) && !start2.isAfter(end1.minusDays(1));
    }

    public void printAll() {
        System.out.println("----------Rooms -----------:");
        rooms.stream()
                .sorted(Comparator.comparingInt(Room::roomNumber).reversed())
                .forEach(System.out::println);

        System.out.println("\n------Bookings ----------:");
        bookings.stream()
                .sorted(Comparator.comparing(Booking::checkIn).reversed())
                .forEach(System.out::println);
    }

    public void printAllUsers() {
        System.out.println("----------Users -----------:");
        users.stream()
                .sorted(Comparator.comparingInt(User::userId).reversed())
                .forEach(System.out::println);
    }
}
