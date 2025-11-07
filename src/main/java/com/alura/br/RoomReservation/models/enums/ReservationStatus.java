package com.alura.br.RoomReservation.models.enums;

public enum ReservationStatus {
    ACTIVE("ATIVO"),
    CANCELLED("CANCELADO"),
    FINISHED("FINALIZADO");

    private String description;

    ReservationStatus (String description) {
        this.description = description;
    }
}
