//package com.room.reservation.program.model;
//
//import java.util.HashSet;
//import java.util.Set;
//
//public class Hotel implements com.system.roomreservation.model.HotelReservation {
//
//    private static final Hotel hotelInstance = new Hotel();
//
//    private Set<Room> rooms;
//
//    private static Hotel getInstance() {
//        return hotelInstance;
//    }
//
//    private Hotel() {
//        rooms = new HashSet<>();
//    }
//
//    public void setRooms(Set<Room> rooms) {
//        this.rooms = rooms;
//    }
//
//    public Set<Room> getRooms() {
//        return rooms;
//    }
//
//    @Override
//    public void reserve( final Room room ) {
//        rooms.stream()
//                .filter(r -> r.equals(room) )
//                .findAny()
//                .ifPresent(value -> value.setReserved(true));
//    }
//
//
//    public static class Builder {
//        private Set<Room> builderRooms = new HashSet<>();
//
//        public Builder addRoom( Room room ) {
//            builderRooms.add( room );
//
//            return this;
//        }
//
//        public Hotel build() {
//            Hotel hotel = getInstance();
//            hotel.setRooms(builderRooms);
//
//            return hotel;
//        }
//
//    }
//}
