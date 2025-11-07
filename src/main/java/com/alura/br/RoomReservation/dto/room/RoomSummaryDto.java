package com.alura.br.RoomReservation.dto.room;

import com.alura.br.RoomReservation.models.enums.RoomStatus;

public record RoomSummaryDto(Long id, String name, Integer capacity, RoomStatus roomStatus) {
}
