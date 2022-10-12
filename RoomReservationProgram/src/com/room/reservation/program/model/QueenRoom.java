package com.room.reservation.program.model;

import com.room.reservation.program.enums.RoomType;

public class QueenRoom extends Room{


    public QueenRoom() {
        super(RoomType.QUEEN);
    }

    public QueenRoom(String reservationNumber , String registeredCustomerName) {
        super( RoomType.QUEEN, reservationNumber, registeredCustomerName );
    }
}
