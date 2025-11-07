package com.alura.br.RoomReservation.dto.reservation;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreateReservationRequestDto(@NotNull(message = "Informe o usuário.") Long userId,
                                          @NotNull(message = "Informe a sala.") Long roomId,
                                          @NotNull(message = "A data inicial é obrigatória.") LocalDate initialDate,
                                          @NotNull(message = "A data final é obrigatória.") LocalDate endDate){
}
