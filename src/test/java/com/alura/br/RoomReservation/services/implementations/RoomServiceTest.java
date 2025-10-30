package com.alura.br.RoomReservation.services.implementations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.alura.br.RoomReservation.dto.room.CreateRoomRequestDto;
import com.alura.br.RoomReservation.dto.room.RoomDto;
import com.alura.br.RoomReservation.models.Room;
import com.alura.br.RoomReservation.models.enums.RoomStatus;
import com.alura.br.RoomReservation.repositories.RoomRepository;

@ExtendWith(MockitoExtension.class)
class RoomServiceTest {

    @InjectMocks
    private RoomService roomService;

    @Mock
    private RoomRepository roomRepository;

    private Room room;
    private RoomDto roomDto;
    private CreateRoomRequestDto createRoomRequestDto;

    @BeforeEach
    void setUp() {
        room = buildRoom();
        roomDto = buildRoomDto();
        createRoomRequestDto = buildCreateRoomRequestDto();
    }

    @Test
    void shouldCreateRoomWhenDataAreValid() {
        when(roomRepository.save(any(Room.class))).thenReturn(room);

        RoomDto result = roomService.createRoom(createRoomRequestDto);

        assertNotNull(result);
        assertNotNull(result.id());
        assertEquals(room.getId(), result.id());
        assertEquals(room.getName(), result.name());
        assertEquals(room.getCapacity(), result.capacity());
        assertEquals(room.getRoomStatus(), result.roomStatus());
        assertEquals(room.getReservations().size(), result.reservations().size());

        verify(roomRepository, times(1)).save(any(Room.class));
    }


    // Helpers
    private Room buildRoom() {
        return new Room(1L, "Sala Reunião 01", 30, RoomStatus.ACTIVE, new ArrayList<>());
    }

    private RoomDto buildRoomDto() {
        return new RoomDto(1L, "Sala Reunião 01", 30, RoomStatus.ACTIVE, new ArrayList<>());
    }

    private CreateRoomRequestDto buildCreateRoomRequestDto() {
        return new CreateRoomRequestDto("Sala Reunião 01", 30, RoomStatus.ACTIVE);
    }
}
