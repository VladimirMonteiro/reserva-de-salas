package com.alura.br.RoomReservation.services;

import com.alura.br.RoomReservation.dto.room.CreateRoomRequestDto;
import com.alura.br.RoomReservation.dto.room.RoomDto;

public interface IRoomService {
    
    RoomDto createRoom(CreateRoomRequestDto dto);
    RoomDto findById(Long id);
}
