package com.alura.br.RoomReservation.utils;

import com.alura.br.RoomReservation.dto.room.CreateRoomRequestDto;
import com.alura.br.RoomReservation.dto.room.RoomDto;
import com.alura.br.RoomReservation.models.Room;

public class RoomMapper {

    public static Room toEntity(CreateRoomRequestDto dto) {
        return new Room(null, dto.name(), dto.capacity(), dto.roomStatus(), null);
    }

    public static RoomDto toDto(Room room) {
        return new RoomDto(room.getId(), room.getName(), room.getCapacity(), room.getRoomStatus(),
                room.getReservations());
    }

}
