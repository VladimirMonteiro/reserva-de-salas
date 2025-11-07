package com.alura.br.RoomReservation.models;

import com.alura.br.RoomReservation.models.enums.ReservationStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

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

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Room room;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
