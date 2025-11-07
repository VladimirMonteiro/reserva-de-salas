package com.alura.br.RoomReservation.dto.reservation;

import com.alura.br.RoomReservation.dto.room.RoomSummaryDto;
import com.alura.br.RoomReservation.dto.user.UserDto;
import com.alura.br.RoomReservation.models.enums.ReservationStatus;

import java.time.LocalDate;

public record ReservationDto(Long id,
                             LocalDate initialDate,
                             LocalDate endDate,
                             ReservationStatus reservationStatus,
                             UserDto userDto,
                             RoomSummaryDto roomDto) {
}
