package com.room.reservation.program.service;

import com.room.reservation.program.enums.RoomType;
import com.room.reservation.program.exception.NoRoomLeftToDecommissionException;
import com.room.reservation.program.model.Room;

import java.io.IOException;
import java.util.Map;

public interface RoomService {

    void addRoom(RoomType roomType,  int numberOfRoomsToAdd) throws IOException;

    void decommissionRoom(RoomType roomType, int numberOfRoomsToDecommission) throws IOException, NoRoomLeftToDecommissionException;

    Map<RoomType, Integer> countRoomsPerRoomType() throws IOException;
}
