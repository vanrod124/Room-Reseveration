package com.room.reservation.program.repository;

import com.room.reservation.program.model.Room;

import java.io.*;
import java.util.List;
import java.util.Random;

public class RoomReservationCounterRepository {


    public RoomReservationCounterRepository(){
    }

    public String getNextReservationNumber( List<Room> rooms ) throws IOException {
        int max = Integer.MIN_VALUE;

        int reservationNumber;

        for( Room room : rooms ) {
            if( !"unreserved".equalsIgnoreCase(room.getReservationNumber()) ) {
                reservationNumber = Integer.parseInt(room.getReservationNumber());

                max = Math.max( reservationNumber, max );
            }
        }

        if( max == Integer.MIN_VALUE ) return "100000";

        return max+"";
    }

    public String getNextReservationNumber() {
        Random random = new Random();
        return String.valueOf(random.nextInt(9999999-1000000) + 1000000);
    }


}
