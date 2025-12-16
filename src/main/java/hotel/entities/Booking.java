package hotel.entities;

import java.time.LocalDate;

public record Booking(User user, Room room, LocalDate checkIn, LocalDate checkOut) {
    public Booking {
        if (checkOut.isBefore(checkIn) || checkOut.isEqual(checkIn)) {
            throw new IllegalArgumentException("Check-out must be after check-in");
        }
    }

    public long nights() {
        return checkOut.toEpochDay() - checkIn.toEpochDay();
    }

    @Override
    public String toString() {
        return String.format("Booking[user=%d, room=%d, %s to %s, nights=%d]",
                user.userId(), room.roomNumber(), checkIn, checkOut, nights());
    }
}
