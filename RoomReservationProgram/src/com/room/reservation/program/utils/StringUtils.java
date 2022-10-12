package com.room.reservation.program.utils;

public class StringUtils {

    public static String toTitleCase( final String string ) {
        return (string.charAt(0)+"").toUpperCase() + string.toLowerCase().substring( 1 );
    }
}
