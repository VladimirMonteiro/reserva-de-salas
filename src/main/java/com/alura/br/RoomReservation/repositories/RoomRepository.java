package com.alura.br.RoomReservation.repositories;

import com.alura.br.RoomReservation.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
