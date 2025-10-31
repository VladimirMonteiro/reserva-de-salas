package com.alura.br.RoomReservation.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

import javax.print.attribute.standard.Media;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.alura.br.RoomReservation.dto.room.CreateRoomRequestDto;
import com.alura.br.RoomReservation.dto.room.RoomDto;
import com.alura.br.RoomReservation.dto.room.UpdateRoomRequestDto;
import com.alura.br.RoomReservation.models.enums.RoomStatus;
import com.alura.br.RoomReservation.services.implementations.RoomService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(RoomController.class)
public class RoomControllerTest {

    private static final String PATH = "/api/v1/rooms";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockitoBean
    private RoomService roomService;

    private RoomDto roomDto;

    @BeforeEach
    void setUp() {
        roomDto = buildRoomDto();
    }

    @Test
    void shouldReturn201WhenRoomDataAreValid() throws Exception {
        when(roomService.createRoom(any(CreateRoomRequestDto.class))).thenReturn(roomDto);
        var dto = new CreateRoomRequestDto("Sala de reuniões", 30, RoomStatus.ACTIVE);
        var json = mapper.writeValueAsString(dto);

        mockMvc.perform(post(PATH).contentType(MediaType.APPLICATION_JSON).content(json))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(roomDto.id()))
        .andExpect(jsonPath("$.name").value(roomDto.name()))
        .andExpect(jsonPath("$.capacity").value(roomDto.capacity()))
        .andExpect(jsonPath("$.roomStatus").value(roomDto.roomStatus().name()));

        verify(roomService, times(1)).createRoom(dto);
    }

    @Test
    void shouldReturn204AndDeleteRoomIfExists() throws Exception {
        Long id = 1L;
        doNothing().when(roomService).deleteRoom(id);

        mockMvc.perform(delete(PATH + "/" + id).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());

        verify(roomService, times(1)).deleteRoom(id);
    }

    @Test
    void shouldReturn200AndListOfRoom() throws Exception {
        when(roomService.findAll(anyInt(), anyInt())).thenReturn(List.of(roomDto));

        mockMvc.perform(get(PATH).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value(roomDto.name()))
                .andExpect(jsonPath("$[0].capacity").value(roomDto.capacity()))
                .andExpect(jsonPath("$[0].roomStatus").value(roomDto.roomStatus().name()));

        verify(roomService, times(1)).findAll(0, 20);
    }

    @Test
    void shouldReturn200AndOneRoom() throws Exception {
        Long id = 1L;
        when(roomService.findById(anyLong())).thenReturn(roomDto);

        mockMvc.perform(get(PATH + "/" + id).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(roomDto.id()))
        .andExpect(jsonPath("$.name").value(roomDto.name()))
        .andExpect(jsonPath("$.capacity").value(roomDto.capacity()))
        .andExpect(jsonPath("$.roomStatus").value(roomDto.roomStatus().name()));

        verify(roomService, times(1)).findById(id);
    }

    @Test
    void shouldReturn200AndReturnUpdatedRoom() throws Exception {
        Long id = 1L;

        var updatedRoomDto = new UpdateRoomRequestDto("Sala reunião 02", 35, RoomStatus.INACTIVE);
        var updatedRoom = new RoomDto(1L, "Sala reunião 02", 35, RoomStatus.INACTIVE, null);

        when(roomService.updateRoom(updatedRoomDto, id)).thenReturn(updatedRoom);

        var json = mapper.writeValueAsString(updatedRoomDto);

        mockMvc.perform(put(PATH + "/" + id).content(json).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(updatedRoom.id()))
        .andExpect(jsonPath("$.name").value(updatedRoom.name()))
        .andExpect(jsonPath("$.capacity").value(updatedRoom.capacity()))
        .andExpect(jsonPath("$.roomStatus").value(updatedRoom.roomStatus().name()));
        

        verify(roomService, times(1)).updateRoom(updatedRoomDto, id);
    }

    // Helpers
    private RoomDto buildRoomDto() {
        return new RoomDto(1L, "Sala de reuniões", 30, RoomStatus.ACTIVE, null);
    }
}
