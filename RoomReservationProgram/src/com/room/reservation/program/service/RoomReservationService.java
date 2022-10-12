package com.room.reservation.program.service;

import com.room.reservation.program.exception.ReservationNotFoundException;
import com.room.reservation.program.exception.UnAvailableRoomException;
import com.room.reservation.program.model.Room;
import com.room.reservation.program.enums.RoomType;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface RoomReservationService {

    Room findAvailableRoomWithType(List<Room> rooms, RoomType roomType) throws IOException;

    Room reserveRoom( RoomType roomType, String customerName ) throws UnAvailableRoomException, IOException;

    boolean isAvailable( RoomType roomType ) throws IOException;

    boolean areAllRoomsFull() throws IOException;

    void cancelReservation(String reservationNumber, String registeredName) throws IOException, ReservationNotFoundException;

    Map<String, List<Room>> getReservationListByReservationNumber() throws IOException;
}
