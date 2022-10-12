package com.room.reservation.program.service;

import com.room.reservation.program.exception.ReservationNotFoundException;
import com.room.reservation.program.exception.UnAvailableRoomException;
import com.room.reservation.program.model.*;
import com.room.reservation.program.repository.RoomRepository;
import com.room.reservation.program.repository.RoomReservationCounterRepository;
import com.room.reservation.program.enums.RoomType;
import com.room.reservation.program.utils.StringUtils;

import java.io.IOException;
import java.util.*;


public class RoomReservationServiceImpl implements RoomReservationService {

    private RoomRepository roomRepository;

    private RoomReservationCounterRepository roomReservationCounterRepository;

    public RoomReservationServiceImpl() {
        try {
            roomRepository = new RoomRepository();
        } catch (IOException e) {
            System.err.println("An unexpected error occurred.");
        }

        roomReservationCounterRepository = new RoomReservationCounterRepository();
    }

    public Room findAvailableRoomWithType( List<Room> rooms, RoomType roomType) throws IOException {
        for( Room room : rooms ) {
            if( room.isAvailable() && roomType.isEqualTo( room.getRoomType() ) ) {
                return room;
            }
        }

        return null;
    }


    @Override
    public Room reserveRoom( RoomType roomType, final String customerName ) throws UnAvailableRoomException, IOException {
        List<Room> rooms = roomRepository.getRooms();
        Room willBeReservedRoom = findAvailableRoomWithType(rooms, roomType);
        if( willBeReservedRoom != null ) {
            String number = roomReservationCounterRepository.getNextReservationNumber();
            willBeReservedRoom.reserve( number );
            willBeReservedRoom.setRegisteredCustomerName( customerName );
            roomRepository.save( rooms );
            return willBeReservedRoom;
        }

        throw new UnAvailableRoomException("Sorry, our " + StringUtils.toTitleCase(roomType.name()) +" rooms is full." );
    }

    @Override
    public boolean isAvailable(RoomType roomType) throws IOException {
        List<Room> rooms = roomRepository.getRooms();
        return findAvailableRoomWithType(rooms, roomType) != null;
    }

    @Override
    public boolean areAllRoomsFull() throws IOException {
        List<Room> rooms = roomRepository.getRooms();
        for( RoomType roomType : RoomType.values() ) {
            if( findAvailableRoomWithType(rooms, roomType) != null ) return false;
        }

        return true;
    }

    @Override
    public void cancelReservation( String reservationNumber, String registeredName ) throws IOException, ReservationNotFoundException {
        List<Room> rooms = roomRepository.getRooms();
        for( Room room : rooms ) {
            if( reservationNumber.equalsIgnoreCase(room.getReservationNumber()) && registeredName.equalsIgnoreCase(room.getRegisteredCustomerName()) ) {
                room.setDefault();
                roomRepository.save( rooms );
                return;
            }
        }

        throw new ReservationNotFoundException("Reservation not Found");
    }

    @Override
    public Map<String, List<Room>> getReservationListByReservationNumber() throws IOException {
        Map<String, List<Room>> reservedListByReservationNumber = new HashMap<>();
        List<Room> reservedRooms = roomRepository.getReservedRooms();

        for( Room room : reservedRooms ) {
            String key = room.getRegisteredCustomerName();
            if( reservedListByReservationNumber.containsKey( key ) ) {
                List<Room> reservedRoomsByCustomer = reservedListByReservationNumber.get(key);
                reservedRoomsByCustomer.add( room );
                reservedListByReservationNumber.put( key, reservedRoomsByCustomer );
                continue;
            }

            List<Room> reserved = new ArrayList<>();
            reserved.add(room);
            reservedListByReservationNumber.put( key, reserved );
        }

        return Collections.unmodifiableMap( reservedListByReservationNumber );
    }


}
