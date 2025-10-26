package com.alura.br.RoomReservation.repositories;

import com.alura.br.RoomReservation.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
