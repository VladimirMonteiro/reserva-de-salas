package com.alura.br.RoomReservation.dto.reservation;

import com.alura.br.RoomReservation.models.enums.ReservationStatus;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record UpdateReservationRequestDto(
        @NotNull(message = "A data inicial é obrigatória.") LocalDate initialDate,
        @NotNull(message = "A data final é obrigatória.") LocalDate endDate,
        @NotNull(message = "O status é obrigatório.") ReservationStatus reservationStatus) {
}
