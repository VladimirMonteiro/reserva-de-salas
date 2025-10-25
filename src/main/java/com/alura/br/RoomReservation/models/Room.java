package com.alura.br.RoomReservation.models;

import com.alura.br.RoomReservation.models.enums.RoomStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer capacity;

    @Enumerated(EnumType.STRING)
    private RoomStatus roomStatus;

    @Setter(AccessLevel.NONE)
    private List<Reservation> reservations = new ArrayList<>();
}
