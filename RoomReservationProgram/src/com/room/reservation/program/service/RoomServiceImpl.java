package com.room.reservation.program.service;

import com.room.reservation.program.enums.RoomType;
import com.room.reservation.program.exception.NoRoomLeftToDecommissionException;
import com.room.reservation.program.model.JuniorRoom;
import com.room.reservation.program.model.KingRoom;
import com.room.reservation.program.model.QueenRoom;
import com.room.reservation.program.model.Room;
import com.room.reservation.program.repository.RoomRepository;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoomServiceImpl implements RoomService {

    private RoomRepository roomRepository;

    private RoomReservationService roomReservationService;

    public RoomServiceImpl() {
        try {
            this.roomRepository = new RoomRepository();
            this.roomReservationService = new RoomReservationServiceImpl();
        } catch (IOException e) {
            System.err.println("An unexpected error occurred.");
        }
    }

    @Override
    public void addRoom(RoomType roomType, int numberOfRoomsToAdd) throws IOException {
        List<Room> rooms = roomRepository.getRooms();

        switch(roomType) {
            case JUNIOR:
                for( int i = 0; i < numberOfRoomsToAdd; i++ ) {
                    rooms.add( new JuniorRoom() );
                }
                break;
            case KING:
                for( int i = 0; i < numberOfRoomsToAdd; i++ ) {
                    rooms.add( new KingRoom() );
                }
                break;
            case QUEEN:
                for( int i = 0; i < numberOfRoomsToAdd; i++ ) {
                    rooms.add( new QueenRoom() );
                }
                break;
            default:
                for( int i = 0; i < numberOfRoomsToAdd; i++ ) {
                    rooms.add( new Room() );
                }
                break;
        }

        roomRepository.save( rooms );
    }

    @Override
    public void decommissionRoom(RoomType roomType, int numberOfRoomsToDecommission) throws IOException, NoRoomLeftToDecommissionException {
        List<Room> rooms = roomRepository.getRooms();

        for(int i = 0; i <numberOfRoomsToDecommission; i++) {
            Room room = roomReservationService.findAvailableRoomWithType( rooms, roomType );
            if( room == null ) {
                throw new NoRoomLeftToDecommissionException("There's no available " + roomType.name() + " room left to decommission.");
            }

            rooms.remove(room);
        }

        roomRepository.save(rooms);
    }

    @Override
    public Map<RoomType, Integer> countRoomsPerRoomType() throws IOException {
        Map<RoomType, Integer> countOfRoomsMap = new HashMap<>();

        countOfRoomsMap.put(RoomType.NORMAL, getNumberOfRoomsByRoomType(RoomType.NORMAL));
        countOfRoomsMap.put(RoomType.JUNIOR, getNumberOfRoomsByRoomType(RoomType.JUNIOR));
        countOfRoomsMap.put(RoomType.KING, getNumberOfRoomsByRoomType(RoomType.KING));
        countOfRoomsMap.put(RoomType.QUEEN, getNumberOfRoomsByRoomType(RoomType.QUEEN));

        return Collections.unmodifiableMap(countOfRoomsMap);
    }

    private int getNumberOfRoomsByRoomType( RoomType roomType ) throws IOException {
        List<Room> rooms = roomRepository.getRooms();
        int count = 0;

        for( Room room : rooms ) {
            if( room.getRoomType().isEqualTo(roomType) ) count++;
        }

        return count;
    }
}
