package com.alura.br.RoomReservation.services.implementations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.alura.br.RoomReservation.dto.room.CreateRoomRequestDto;
import com.alura.br.RoomReservation.dto.room.RoomDto;
import com.alura.br.RoomReservation.models.Room;
import com.alura.br.RoomReservation.models.enums.RoomStatus;
import com.alura.br.RoomReservation.repositories.RoomRepository;
import com.alura.br.RoomReservation.services.exceptions.ObjectNotFoundException;

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

    @Test
    void shouldReturnRoomByIdWhenRoomExists() {
        when(roomRepository.findById(anyLong())).thenReturn(Optional.of(room));

        var result = roomService.findById(room.getId());

        assertNotNull(result);
        assertEquals(room.getId(), result.id());
        assertEquals(room.getName(), result.name());
        assertEquals(room.getCapacity(), result.capacity());
        assertEquals(room.getRoomStatus(), result.roomStatus());
        assertEquals(room.getReservations().size(), result.reservations().size());

        verify(roomRepository, times(1)).findById(anyLong());
    }

    @Test
    void shouldThrowsObjectNotFoundExceptionWhenRoomNotFound() {
        when(roomRepository.findById(anyLong())).thenReturn(Optional.empty());

        var exception = assertThrows(ObjectNotFoundException.class, () -> roomService.findById(anyLong()));

        assertEquals("Sala não encontrada.", exception.getMessage());
    }

    @Test
    void shouldReturnListOfRooms() {
        var listRoom = List.of(room);
        Page<Room> roomsPage = new PageImpl<>(listRoom);

        when(roomRepository.findAll(any(Pageable.class))).thenReturn(roomsPage);

        var result = roomService.findAll(0, 20);

         assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(room.getName(), result.getFirst().name());
        assertEquals(room.getCapacity(), result.getFirst().capacity());
        assertEquals(room.getRoomStatus(), result.getFirst().roomStatus());
        assertEquals(room.getReservations().size(), result.get(0).reservations().size());
      
        verify(roomRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void shouldDeleteRoomWhenRoomAlreadyExists() {
        when(roomRepository.existsById(anyLong())).thenReturn(true);

        assertDoesNotThrow(() -> roomService.deleteRoom(anyLong()));
    }

    @Test
    void shouldThrowsObjectNotFoundExceptionWhenRoomNotExists() {
        when(roomRepository.existsById(anyLong())).thenReturn(false);

        var exception = assertThrows(ObjectNotFoundException.class, () -> roomService.deleteRoom(anyLong()));

        assertEquals("Sala não encontrada.", exception.getMessage());
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
