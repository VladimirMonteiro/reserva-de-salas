package com.alura.br.RoomReservation.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alura.br.RoomReservation.dto.room.CreateRoomRequestDto;
import com.alura.br.RoomReservation.dto.room.RoomDto;
import com.alura.br.RoomReservation.services.implementations.RoomService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping
    public ResponseEntity<RoomDto> createRoom(@RequestBody CreateRoomRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roomService.createRoom(dto));
    }
    
    
}
