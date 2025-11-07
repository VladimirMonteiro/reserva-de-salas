package com.alura.br.RoomReservation.utils;

import com.alura.br.RoomReservation.dto.reservation.CreateReservationRequestDto;
import com.alura.br.RoomReservation.dto.reservation.ReservationDto;
import com.alura.br.RoomReservation.models.Reservation;
import com.alura.br.RoomReservation.models.enums.ReservationStatus;

import java.util.List;

public class ReservationMapper {

    public static Reservation toEntity (CreateReservationRequestDto dto) {
        return new Reservation(null, dto.initialDate(), dto.endDate(), ReservationStatus.ACTIVE, null, null);
    }

    public static ReservationDto toDto (Reservation reservation) {
        return new ReservationDto(reservation.getId(),
                reservation.getInitialDate(),
                reservation.getEndDate(),
                reservation.getReservationStatus(),
                UserMapper.toDto(reservation.getUser()),
                RoomMapper.toSummaryDto(reservation.getRoom()));
    }

    public static List<ReservationDto> toListDto(List<Reservation> reservations) {
        return reservations.stream().map(ReservationMapper::toDto).toList();
    }
}
