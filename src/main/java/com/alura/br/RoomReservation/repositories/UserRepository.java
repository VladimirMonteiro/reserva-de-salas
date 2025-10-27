package com.alura.br.RoomReservation.repositories;

import com.alura.br.RoomReservation.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByCpf(String cpf);
}
