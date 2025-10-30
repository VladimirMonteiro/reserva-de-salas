package com.alura.br.RoomReservation.dto.room;

import java.util.List;

import com.alura.br.RoomReservation.models.Reservation;
import com.alura.br.RoomReservation.models.enums.RoomStatus;

public record RoomDto(Long id, String name, Integer capacity, RoomStatus roomStatus, List<Reservation> reservations) {  
}
