package com.room.reservation.program.model;

import com.room.reservation.program.enums.RoomType;

public class KingRoom extends Room{

    public KingRoom() {
        super(RoomType.KING);
    }

    public KingRoom( String reservationNumber, String registeredCustomerName ) {
        super( RoomType.KING, reservationNumber, registeredCustomerName );
    }
}
