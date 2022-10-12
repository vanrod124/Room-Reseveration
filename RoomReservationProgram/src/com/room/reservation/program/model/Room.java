package com.room.reservation.program.model;

import com.room.reservation.program.enums.RoomType;
import com.room.reservation.program.utils.StringUtils;

import java.util.Objects;

public class Room {
    private RoomType roomType;

    private String reservationNumber = "unreserved";

    private String registeredCustomerName = "none";

    public Room() {
        this(RoomType.NORMAL);
    }

    public Room(String reservationNumber) {
        this();
        this.reservationNumber = reservationNumber;
    }

    public Room(String reservationNumber, String registeredCustomerName) {
        this();
        this.reservationNumber = reservationNumber;
        this.registeredCustomerName = registeredCustomerName;
    }

    public Room(RoomType roomType, String reservationNumber, String registeredCustomerName) {
        this(reservationNumber, registeredCustomerName);
        this.roomType = roomType;
    }

    protected Room( RoomType roomType ) {
        this.roomType = roomType;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public String getReservationNumber() {
        return reservationNumber;
    }

    public void setReservationNumber(String reservationNumber) {
        this.reservationNumber = reservationNumber;
    }

    public String getRegisteredCustomerName() {
        return registeredCustomerName;
    }

    public void setRegisteredCustomerName(String registeredCustomerName) {
        this.registeredCustomerName = registeredCustomerName;
    }

    public boolean isAvailable() {
        return "unreserved".equalsIgnoreCase(reservationNumber);
    }

    public void reserve( final String reservationNumber ) {
        this.reservationNumber = reservationNumber;
    }

    public void setDefault() {
        this.registeredCustomerName = "none";
        this.reservationNumber = "unreserved";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return Objects.equals(roomType, room.roomType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomType);
    }

    @Override
    public String toString() {
        return StringUtils.toTitleCase(roomType.name()) + "," + reservationNumber + "," + registeredCustomerName;
    }
}
