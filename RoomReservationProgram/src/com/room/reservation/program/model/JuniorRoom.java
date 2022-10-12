package com.room.reservation.program.model;

import com.room.reservation.program.enums.RoomType;

public class JuniorRoom extends Room{

    public JuniorRoom() {
        super(RoomType.JUNIOR);
    }

    public JuniorRoom( String reservationNumber, String registeredCustomerName ) {
        super( RoomType.JUNIOR, reservationNumber, registeredCustomerName );
    }


}
