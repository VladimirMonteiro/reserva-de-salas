package com.alura.br.RoomReservation.services.implementations;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alura.br.RoomReservation.dto.room.CreateRoomRequestDto;
import com.alura.br.RoomReservation.dto.room.RoomDto;
import com.alura.br.RoomReservation.models.Room;
import com.alura.br.RoomReservation.repositories.RoomRepository;
import com.alura.br.RoomReservation.services.IRoomService;
import com.alura.br.RoomReservation.services.exceptions.ObjectNotFoundException;
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

    @Override
    public RoomDto findById(Long id) {
        var roomIfExists = roomRepository.findById(id).
        orElseThrow(() -> new ObjectNotFoundException("Sala não encontrada."));

        return RoomMapper.toDto(roomIfExists);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoomDto> findAll(int page, int size) {
       Pageable pageable = PageRequest.of(page, size);
       Page<Room> roomsPage = roomRepository.findAll(pageable);
       return roomsPage.getContent().stream().map(RoomMapper::toDto).toList();
    }

}
