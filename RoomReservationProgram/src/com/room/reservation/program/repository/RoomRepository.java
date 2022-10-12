package com.room.reservation.program.repository;

import com.room.reservation.program.constants.FileConstants;
import com.room.reservation.program.model.JuniorRoom;
import com.room.reservation.program.model.KingRoom;
import com.room.reservation.program.model.QueenRoom;
import com.room.reservation.program.model.Room;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static com.room.reservation.program.enums.RoomType.*;

public class RoomRepository {

    private BufferedReader reader;

    private BufferedWriter writer;

    public RoomRepository() throws IOException {


    }


    public List<Room> getRooms() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(new File(FileConstants.ROOM_LIST_FILE)));

        String line = "";
        List<Room> rooms = new ArrayList<>();

        // roomtype,reservationNumber
        while( (line = reader.readLine()) != null ) {
            String[] roomArr = line.trim().split(",");

            String roomType = roomArr[0].trim();
            String reservationNumber = roomArr[1].trim();
            String registeredName = roomArr[2].trim();


            if( NORMAL.name().equalsIgnoreCase( roomType ) ) {
                rooms.add( new Room(reservationNumber, registeredName ) );
            }else if ( JUNIOR.name().equalsIgnoreCase( roomType ) ) {
                rooms.add( new JuniorRoom(  reservationNumber, registeredName ));
            }else if ( KING.name().equalsIgnoreCase( roomType ) ) {
                rooms.add( new KingRoom( reservationNumber, registeredName ));
            }else {
                rooms.add( new QueenRoom( reservationNumber, registeredName ));
            }

        }

        return rooms;
    }

    public List<Room> getReservedRooms() throws IOException {
        List<Room> reservedRooms = new ArrayList<>();
        List<Room> rooms = getRooms();
        for( Room room : rooms ) {
            if( !room.isAvailable() ) reservedRooms.add( room );
        }

        return reservedRooms;
    }

    public void save(List<Room> rooms ) throws IOException {
        BufferedWriter writer = new BufferedWriter( new FileWriter( new File( FileConstants.ROOM_LIST_FILE ) ) );
        for (Room room : rooms) {
            writer.write( room.toString() );
            writer.newLine();
        }

        writer.close();
    }

    public void close() {
        try {
            reader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
