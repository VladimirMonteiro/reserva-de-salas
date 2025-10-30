package com.alura.br.RoomReservation.services.implementations;

import org.springframework.stereotype.Service;

import com.alura.br.RoomReservation.dto.room.CreateRoomRequestDto;
import com.alura.br.RoomReservation.dto.room.RoomDto;
import com.alura.br.RoomReservation.repositories.RoomRepository;
import com.alura.br.RoomReservation.services.IRoomService;
import com.alura.br.RoomReservation.utils.RoomMapper;

@Service
public class RoomService implements IRoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public RoomDto createRoom(CreateRoomRequestDto dto) {
        var roomSave = RoomMapper.toEntity(dto);
        var savedRoom = roomRepository.save(roomSave);
        return RoomMapper.toDto(savedRoom);
    }

}
