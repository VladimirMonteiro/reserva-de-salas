package com.alura.br.RoomReservation.utils;

import com.alura.br.RoomReservation.dto.room.CreateRoomRequestDto;
import com.alura.br.RoomReservation.dto.room.RoomDto;
import com.alura.br.RoomReservation.dto.room.RoomSummaryDto;
import com.alura.br.RoomReservation.dto.room.UpdateRoomRequestDto;
import com.alura.br.RoomReservation.models.Room;

public class RoomMapper {

    public static Room toEntity(CreateRoomRequestDto dto) {
        return new Room(null, dto.name(), dto.capacity(), dto.roomStatus(), null);
    }

    public static RoomDto toDto(Room room) {
        return new RoomDto(room.getId(), room.getName(), room.getCapacity(), room.getRoomStatus(),
                room.getReservations());
    }

    public static RoomSummaryDto toSummaryDto(Room room) {
        return new RoomSummaryDto(room.getId(), room.getName(), room.getCapacity(), room.getRoomStatus());
    }


    public static Room updateRoomFromDto(UpdateRoomRequestDto dto, Room room) {
    if (dto.name() != null && !dto.name().isBlank())
        room.setName(dto.name());
    if (dto.capacity() != null)
        room.setCapacity(dto.capacity());
    if (dto.roomStatus() != null)
        room.setRoomStatus(dto.roomStatus());
    return room;
}

}
