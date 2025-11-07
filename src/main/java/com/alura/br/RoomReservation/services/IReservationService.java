package com.alura.br.RoomReservation.services;

import com.alura.br.RoomReservation.dto.reservation.CreateReservationRequestDto;
import com.alura.br.RoomReservation.dto.reservation.ReservationDto;

public interface IReservationService {

    ReservationDto createReservation(CreateReservationRequestDto dto);
    ReservationDto findById(Long id);
}
