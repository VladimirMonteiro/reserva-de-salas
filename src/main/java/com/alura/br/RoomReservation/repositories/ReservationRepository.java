package com.alura.br.RoomReservation.repositories;

import com.alura.br.RoomReservation.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
