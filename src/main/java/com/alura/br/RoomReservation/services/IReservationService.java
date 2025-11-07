package com.alura.br.RoomReservation.services;

import com.alura.br.RoomReservation.dto.reservation.CreateReservationRequestDto;
import com.alura.br.RoomReservation.dto.reservation.ReservationDto;

import java.util.List;

public interface IReservationService {

    ReservationDto createReservation(CreateReservationRequestDto dto);
    ReservationDto findById(Long id);
    List<ReservationDto> findAll(int page, int size);
    void deleteReservation(Long id);
}
