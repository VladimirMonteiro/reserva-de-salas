package com.alura.br.RoomReservation.dto.room;

import com.alura.br.RoomReservation.models.enums.RoomStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateRoomRequestDto(@NotBlank(message = "O nome é obrigatório.") String name,
        @NotNull(message = "A capacidade é obrigatória.") Integer capacity,
        @NotNull(message = "O status é obrigatória.") RoomStatus roomStatus) {
}
