package com.alura.br.RoomReservation.models;

import com.alura.br.RoomReservation.models.enums.ReservationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate initialDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus;

    @Setter(AccessLevel.NONE)
    private List<Room> rooms = new ArrayList<>();

    private User user;
}
