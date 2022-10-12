package com.room.reservation.program.enums;

public enum RoomType {

    NORMAL("NORMAL"), JUNIOR("JUNIOR"), QUEEN("QUEEN"), KING("KING");

    RoomType(String name) {
        this.name = name;
    }

    public boolean isNormal( RoomType roomType ) {
        return this.compareTo( RoomType.NORMAL ) == 0;
    }

    public boolean isJunior( RoomType roomType ) {
        return this.compareTo( RoomType.JUNIOR ) == 0;
    }

    public boolean isQueen( RoomType roomType ) {
        return this.compareTo( RoomType.QUEEN ) == 0;
    }

    public boolean isKing( RoomType roomType ) {
        return this.compareTo( RoomType.KING ) == 0;
    }

    public boolean isEqualTo( RoomType otherRoomType ) {
        return this.compareTo( otherRoomType ) == 0;
    }

    private String name;
}
