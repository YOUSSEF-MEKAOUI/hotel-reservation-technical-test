package hotel.entities;


import hotel.enums.RoomType;

public record Room(int roomNumber, RoomType roomType, int pricePerNight) {}

